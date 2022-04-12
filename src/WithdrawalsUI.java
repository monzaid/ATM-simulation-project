import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WithdrawalsUI extends sourceUI implements ActionListener {
    JLabel jl_money = new JLabel("金额：");
    JTextField jtf_money = new JTextField();
    int[] money = {500, 1000, 1500, 0, 100, 200, 300, 0};

    public WithdrawalsUI(String[] txt, String title, String tip) {
        super(txt, title, tip);
        super.jp_center.add(jl_money);
        super.jp_center.add(jtf_money);
        jl_money.setBounds(100, 150, 80, 80);
        jtf_money.setBounds(180, 170, 250, 40);
        jl_money.setFont(super.font);
        jtf_money.setFont(super.font);
        for (int i = 0; i < 8; i++){
            super.jb[i].addActionListener(this);
        }
    }

    public void subMoney(int money) {
        if (money < 0 || money % 100 != 0){
            JOptionPane.showMessageDialog(this, "取的钱不是100的倍数！");
            return;
        }
        double d = -money;
        try {
            Connection conn = DBUtils.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select balance from account where username = '" + User.getUsername() + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            d += rs.getDouble("balance");
            if (d < 0){
                JOptionPane.showMessageDialog(this, "你没有这么多钱！");
                return;
            }
            String sqll = "update account set balance = " + d + " where username = '" + User.getUsername() + "'";
            stmt.executeUpdate(sqll);
            JOptionPane.showMessageDialog(this, "提取成功!");
            DBUtils.close(conn, stmt, rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ATM系统维护中...");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 8; i++){
            if (e.getSource().equals(super.jb[i])){
                if (i == 3){
                    if (jl_money.isVisible()){
                        String cnt = jtf_money.getText().trim();
                        if (cnt.matches("^[0-9]*$") && !cnt.equals("")){
                            subMoney(Integer.parseInt(cnt));
                            disShow();
                        }else{
                            JOptionPane.showMessageDialog(this, "输入非法字符！");
                        }
                    }else{
                        jl_money.setVisible(true);
                        jtf_money.setVisible(true);
                        super.jl_txt[3].setText("<<确定");
                    }
                    break;
                }else if (i == 7){
                    super.type = super.functionNum[7];
                    break;
                }else{
                    subMoney(money[i]);
                    break;
                }
            }
        }
    }

    private void disShow(){
        jtf_money.setText("");
        jl_money.setVisible(false);
        jtf_money.setVisible(false);
        super.jl_txt[3].setText("<<其他");
    }

    @Override
    public void toEnable() {
        super.toEnable();
        disShow();
    }
}
