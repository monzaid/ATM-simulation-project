import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InquireUI extends sourceUI{
    JLabel jl_money = new JLabel();
    public InquireUI(String[] txt, String title, String tip) {
        super(txt, title, tip);

        super.jp_center.add(jl_money);
        jl_money.setFont(new Font("宋体", Font.PLAIN, 24));
        jl_money.setBounds(100, 130, 200, 80);
        super.jl_tip.setHorizontalAlignment(SwingConstants.LEFT);

        super.jb[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InquireUI.super.type = InquireUI.super.functionNum[3];
            }
        });
    }

    @Override
    public void toEnable() {
        super.toEnable();
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "select balance from account where username = '"+User.getUsername()+"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            double d = rs.getDouble("balance");
            super.jl_tip.setText("账号：" + User.getUsername());
            jl_money.setText("余额：" + d);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ATM系统维护中...");
        }
    }
}
