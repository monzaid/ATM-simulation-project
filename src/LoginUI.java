import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginUI extends sourceUI implements ActionListener {
    JLabel jl_name = new JLabel("账号：");
    JLabel jl_pwd = new JLabel("密码：");
    List<String> name = new ArrayList<>();
    JComboBox jcb_name;
    JPasswordField jpf_pwd = new JPasswordField();

    public LoginUI(String[] txt, String title, String tip) {
        super(txt, title, tip);
        initUI();
    }

    private void initUI() {
        /* 按鍵布局 */
        super.jp_center.add(jl_name);
        jl_name.setBounds(100, 150, 80, 80);
        jl_name.setFont(super.font);
        super.jp_center.add(jl_pwd);
        jl_pwd.setBounds(100, 250, 80, 80);
        jl_pwd.setFont(super.font);
        /* 初始化账号选项 */
        name.add("请选择预置账号");
        try {
            Connection conn = DBUtils.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select username from account";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                name.add(rs.getString("username"));
            }
            DBUtils.close(conn, stmt, rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ATM系统维护中...");
        }
        jcb_name = new JComboBox(name.toArray());
        jcb_name.setSelectedIndex(0);
        super.jp_center.add(jcb_name);
        jcb_name.setBounds(180, 170, 250, 40);
        jcb_name.setFont(super.font);
        super.jp_center.add(jpf_pwd);
        jpf_pwd.setBounds(180, 270, 250, 40);
        jpf_pwd.setFont(super.font);
        /* 添加按钮事件 */
        super.jb[7].addActionListener(this);
        super.jb[3].addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(super.jb[7])){
            /* 账号和密码匹配 */
            try {
                Connection conn = DBUtils.getConnection();
                String sql = "select * from account where username = ? and password = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);//预处理sql语句，防止sql注入
                stmt.setString(1, name.get(jcb_name.getSelectedIndex()).toString());
                stmt.setString(2, new String(jpf_pwd.getPassword()));
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()){
                    JOptionPane.showMessageDialog(this, "密码错误！");
                    jpf_pwd.requestFocus(true);
                }else{
                    JOptionPane.showMessageDialog(this, "登录成功！");
                    User.setUsername(name.get(jcb_name.getSelectedIndex()).toString());
                    jcb_name.setSelectedIndex(0);
                    jpf_pwd.setText("");
                    super.type = super.functionNum[7];
                }
                DBUtils.close(conn, stmt, rs);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "ATM系统维护中...");
            }
        }else if (e.getSource().equals(super.jb[3])){
            /* 清空选项输入 */
            jcb_name.setSelectedIndex(0);
            jpf_pwd.setText("");
        }
    }
}
