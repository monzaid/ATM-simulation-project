import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends sourceUI implements ActionListener{

    public MainUI(String[] txt, String title, String tip) {
        super(txt, title, tip);
        initUI();
    }
    private void initUI() {
        /* 添加按钮事件 */
        for (int i = 0; i < 8; i++){
            super.jb[i].addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* 切换对应的界面 */
        for (int i = 0; i < 8; i++){
            if (e.getSource().equals(super.jb[i])){
                super.type = super.functionNum[i];
                break;
            }
        }
    }
}
