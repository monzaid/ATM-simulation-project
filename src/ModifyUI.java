import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ModifyUI extends sourceUI{
    JLabel jl_pwd = new JLabel("原密码：");
    JLabel jl_pwdNew = new JLabel("新密码：");
    JLabel jl_pwdConfirm = new JLabel("确认密码：");
    JPasswordField jpf_pwd = new JPasswordField();
    JPasswordField jpf_pwdNew = new JPasswordField();
    JPasswordField jpf_pwdConfirm = new JPasswordField();
    public ModifyUI(String[] txt, String title, String tip) {
        super(txt, title, tip);
        jp_center.add(jl_pwd);
        jp_center.add(jl_pwdNew);
        jp_center.add(jl_pwdConfirm);
        jp_center.add(jpf_pwd);
        jp_center.add(jpf_pwdNew);
        jp_center.add(jpf_pwdConfirm);

        jl_pwd.setBounds(100, 100, 100, 80);
        jl_pwdNew.setBounds(100, 180, 100, 80);
        jl_pwdConfirm.setBounds(80, 260, 120, 80);
        jpf_pwd.setBounds(180, 120, 250, 40);
        jpf_pwdNew.setBounds(180, 200, 250, 40);
        jpf_pwdConfirm.setBounds(180, 280, 250, 40);

        jl_pwd.setFont(super.font);
        jl_pwdNew.setFont(super.font);
        jl_pwdConfirm.setFont(super.font);
        jpf_pwd.setFont(super.font);
        jpf_pwdNew.setFont(super.font);
        jpf_pwdConfirm.setFont(super.font);

        super.jb[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpf_pwd.setText("");
                jpf_pwdNew.setText("");
                jpf_pwdConfirm.setText("");
                ModifyUI.super.type = ModifyUI.super.functionNum[3];
            }
        });

        super.jb[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] pwd = new String[3];
                pwd[1] = new String(jpf_pwdNew.getPassword());
                pwd[2] = new String(jpf_pwdConfirm.getPassword());
                if (!pwd[1].equals(pwd[2])){
                    JOptionPane.showMessageDialog(ModifyUI.this, "新密码和确认密码不一致！");
                    jpf_pwdNew.requestFocus(true);
                    return;
                }
                pwd[0] = new String(jpf_pwd.getPassword());
                try {
                    Connection conn = DBUtils.getConnection();
                    String sql = "select * from account where username = ? and password = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, User.getUsername());
                    stmt.setString(2, pwd[0]);
                    ResultSet rs = stmt.executeQuery();
                    if (!rs.next()){
                        JOptionPane.showMessageDialog(ModifyUI.this, "原密码不一致！");
                        jpf_pwd.requestFocus(true);
                    }else{
                        String sqll = "update account set password = '"+pwd[1]+"' where username = '"+User.getUsername()+"'";
                        Statement stmtt = conn.createStatement();
                        stmtt.executeUpdate(sqll);
                        JOptionPane.showMessageDialog(ModifyUI.this, "修改成功！");
                        DBUtils.close(null, stmtt, null);
                    }
                    DBUtils.close(conn, stmt, rs);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(ModifyUI.this, "ATM系统维护中...");
                }
            }
        });
    }
}
