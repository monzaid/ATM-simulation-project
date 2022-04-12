import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DepositUI extends sourceUI {
    JLabel jl_money = new JLabel("金额：");
    JTextField jtf_money = new JTextField();
    public DepositUI(String[] txt, String title, String tip) {
        super(txt, title, tip);
        super.jp_center.add(jl_money);
        super.jp_center.add(jtf_money);
        jl_money.setBounds(100, 150, 80, 80);
        jtf_money.setBounds(180, 170, 250, 40);
        jl_money.setFont(super.font);
        jtf_money.setFont(super.font);

        super.jb[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtf_money.setText("");
                DepositUI.super.type = DepositUI.super.functionNum[3];
            }
        });

        super.jb[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtf_money.setText("");
            }
        });

        super.jb[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cnt = jtf_money.getText().trim();
                if (cnt.matches("^[0-9]*$")){
                    addMoney(Integer.parseInt(cnt));
                }else{
                    JOptionPane.showMessageDialog(DepositUI.this, "输入非法字符！");
                    jtf_money.requestFocus(true);
                }
            }
        });
    }

    public void addMoney(int money) {
        if (money < 0 || money % 100 != 0){
            JOptionPane.showMessageDialog(this, "存的钱不是100的倍数！");
            jtf_money.requestFocus(true);
            return;
        }
        double d = money;
        try {
            Connection conn = DBUtils.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select balance from account where username = '" + User.getUsername() + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            d += rs.getDouble("balance");
            String sqll = "update account set balance = " + d + " where username = '" + User.getUsername() + "'";
            stmt.executeUpdate(sqll);
            JOptionPane.showMessageDialog(this, "存款成功!");
            DBUtils.close(conn, stmt, rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ATM系统维护中...");
        }
    }
}
