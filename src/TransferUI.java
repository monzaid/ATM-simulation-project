import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TransferUI extends sourceUI{
    JLabel jl_name = new JLabel("账号：");
    JLabel jl_money = new JLabel("金额：");
    List<String> name = new ArrayList<>();
    JComboBox jcb_name;
    JTextField jtf_money = new JTextField();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public TransferUI(String[] txt, String title, String tip) {
        super(txt, title, tip);
        initUI();
    }

    private void initUI() {
        super.jp_center.add(jl_name);
        jl_name.setBounds(100, 150, 80, 80);
        jl_name.setFont(super.font);
        super.jp_center.add(jl_money);
        jl_money.setBounds(100, 250, 80, 80);
        jl_money.setFont(super.font);
        jcb_name = new JComboBox(name.toArray());
        super.jp_center.add(jcb_name);
        jcb_name.setBounds(180, 170, 250, 40);
        jcb_name.setFont(super.font);
        super.jp_center.add(jtf_money);
        jtf_money.setBounds(180, 270, 250, 40);
        jtf_money.setFont(super.font);

        super.jb[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cnt = jtf_money.getText().trim();
                if (cnt.matches("^[0-9]*.?[0-9]*$")){
                    transMoney(Double.parseDouble(cnt));
                }else{
                    JOptionPane.showMessageDialog(TransferUI.this, "输入非法字符！");
                    jtf_money.requestFocus(true);
                }
            }
        });

        super.jb[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtf_money.setText("");
                jcb_name.setSelectedIndex(0);
            }
        });

        super.jb[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtf_money.setText("");
                jcb_name.setSelectedIndex(0);
                TransferUI.super.type = TransferUI.super.functionNum[3];
            }
        });
    }

    private void transMoney(double money) {
        if (money < 0){
            JOptionPane.showMessageDialog(this, "转的钱不可能是负数！");
            jtf_money.requestFocus(true);
            return;
        }
        double d = -money;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn= DBUtils.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            StringBuffer sql = new StringBuffer("select balance from account where username = '" + User.getUsername() + "'");
            rs = stmt.executeQuery(sql.toString());
            rs.next();
            d += rs.getDouble("balance");
            if (d < 0){
                JOptionPane.showMessageDialog(this, "你没有这么多钱！");
                jtf_money.requestFocus(true);
                return;
            }
            sql = new StringBuffer("update account set balance = " + d + " where username = '" + User.getUsername() + "'");
            stmt.executeUpdate(sql.toString());

            sql = new StringBuffer("select balance from account where username = '" + name.get(jcb_name.getSelectedIndex()).toString() + "'");
            rs = stmt.executeQuery(sql.toString());
            rs.next();
            d = rs.getDouble("balance") + money;

            sql = new StringBuffer("update account set balance = " + d + " where username = '" + name.get(jcb_name.getSelectedIndex()).toString() + "'");
            stmt.executeUpdate(sql.toString());

            String datetime = sdf.format(new java.util.Date());
            sql = new StringBuffer("insert into history(userid, datetime, expense, income, sourceid) values('"+User.getUsername()+"','"+datetime+"','"+money+"','0','"+name.get(jcb_name.getSelectedIndex()).toString()+"'), ('"+name.get(jcb_name.getSelectedIndex()).toString()+"','"+datetime+"','0','"+money+"','"+User.getUsername()+"')");
            stmt.executeUpdate(sql.toString());

            JOptionPane.showMessageDialog(this, "转账成功!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ATM系统维护中...");
            if (conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }finally {
            if (conn != null){
                try {
                    conn.commit();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            DBUtils.close(conn, stmt, rs);
        }
    }

    @Override
    public void toEnable() {
        super.toEnable();
        name.removeAll(name);
        name.add("请选择预置账号");
        try {
            Connection conn = DBUtils.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select username from account where username != '"+User.getUsername()+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                name.add(rs.getString("username"));
                System.out.println(rs.getString("username"));
            }
            DBUtils.close(conn, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jcb_name.setModel(new DefaultComboBoxModel(name.toArray()));
        jcb_name.setSelectedIndex(0);
    }
}
