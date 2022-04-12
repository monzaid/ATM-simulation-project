import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HistoryUI extends sourceUI{
    private Object[][] msgs = new Object[0][];
    private Object[][] msgsNearby = new Object[0][];
    private String[] titles = {"交易日期", "支出", "收入", "对方账号"};
    private DefaultTableModel tmodel = new DefaultTableModel(msgs, titles);
    private DefaultTableModel tmodelNearby = new DefaultTableModel(msgsNearby, titles);
    private JTable jt_list = new JTable(tmodel);
    private JTable jt_listNearby = new JTable(tmodelNearby);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public HistoryUI(String[] txt, String title, String tip) {
        super(txt, title, tip);
        initUI();
    }

    private void initUI() {
        JScrollPane jsp_list = new JScrollPane(jt_list);
        super.jp_center.add(jsp_list);
        jsp_list.setBounds(20, 30, 480, 310);
//        jt_list.setFont(super.font);
        jt_list.setEnabled(false);
        JScrollPane jsp_listNearby = new JScrollPane(jt_listNearby);
        super.jp_center.add(jsp_listNearby);
        jsp_listNearby.setBounds(20, 30, 480, 310);
//        jt_list.setFont(super.font);
        jt_listNearby.setEnabled(false);
        jsp_listNearby.setVisible(false);
        jt_listNearby.setVisible(false);


        super.jb[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistoryUI.super.jl_txt[3].setText(HistoryUI.super.jl_txt[3].getText() == "<<近一个月" ? "<<全部" : "<<近一个月");
                jt_listNearby.setVisible(!jsp_listNearby.isVisible());
                jsp_listNearby.setVisible(!jsp_listNearby.isVisible());
                jt_list.setVisible(!jsp_listNearby.isVisible());
                jsp_list.setVisible(!jsp_list.isVisible());
            }
        });

        super.jb[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistoryUI.super.type = HistoryUI.super.functionNum[3];
            }
        });
    }

    @Override
    public void toEnable() {
        super.toEnable();
        while (tmodel.getRowCount() != 0)
            tmodel.removeRow(0);
        while (tmodelNearby.getRowCount() != 0)
            tmodelNearby.removeRow(0);
        try {
            Connection conn = DBUtils.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select datetime, income, expense, sourceid from history where userid = '" + User.getUsername() + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Object[] msg = {rs.getString("datetime"), rs.getString("expense"), rs.getString("income"), rs.getString("sourceid")};
                tmodel.addRow(msg);
                long d = new java.util.Date().getTime() - sdf.parse(rs.getString("datetime")).getTime();
                if (Math.abs(d) <= (long)1000 * 60 * 60 * 24 * 30){
                    tmodelNearby.addRow(msg);
                }
            }
            DBUtils.close(conn, stmt, rs);
        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(this, "ATM系统维护中...");
        }
    }
}
