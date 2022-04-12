import javafx.util.BuilderFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class sourceUI extends JFrame {
    int[] functionNum = {0, 1, 2, 3, 4, 5, 6, 7};
    JButton[] jb = new JButton[8];
    JLabel[] jl_txt = new JLabel[8];
    JLabel jl_tip = new JLabel();
    JPanel jp_center = new JPanel(null);
    int[] jb_posX = {50, 800};
    int[] jb_posY = {100, 200, 300, 400};
    int[] jl_posX = {10, 300};
    int[] jl_posY = {40, 140, 240, 340};
    int tip_posX = 100;
    int tip_posY = 60;
    Font font = new Font("宋体", Font.BOLD, 20);
    String[] txt;
    String tip;
    String title;
    int type;
    int ISALIVE = 10;
    int ISDEAD = -1;

    public sourceUI(String[] txt, String title, String tip){
        super("欢迎使用银行ATM机");
        this.txt = txt;
        this.tip = tip;
        this.title = title;
        initUI();
    }

    private void initUI() {
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1000, 600);
        setResizable(false);
        type = 0;

        for (int i = 0; i < 8; i++){
            jb[i] = new JButton();
            add(jb[i]);
            jb[i].setBounds(jb_posX[i/4], jb_posY[i%4], 130, 70);
        }
        jp_center.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(TitledBorder.DEFAULT_JUSTIFICATION), title, TitledBorder.CENTER, TitledBorder.DEFAULT_JUSTIFICATION, font));//主窗口按键布局
        add(jp_center);
        jp_center.setBounds(220, 70, 530, 430);

        for (int i = 0; i < 8; i++){
            jl_txt[i] = new JLabel(txt[i]);
            jp_center.add(jl_txt[i]);
            jl_txt[i].setBounds(jl_posX[i/4], jl_posY[i%4], 220, 50);
            jl_txt[i].setFont(font);
            if (i / 4 != 0){
                jl_txt[i].setHorizontalAlignment(SwingConstants.RIGHT);
            }
        }

        jp_center.add(jl_tip);
        jl_tip.setText(tip);
        jl_tip.setBounds(tip_posX, tip_posY, 350, 100);
        jl_tip.setFont(new Font("宋体", Font.PLAIN, 24));
        jl_tip.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void toDisable() {
        setVisible(false);
        type = ISDEAD;
    }

    public void toEnable(){
        setVisible(true);
        type = ISALIVE;
    }
}
