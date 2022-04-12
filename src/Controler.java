import static java.lang.Thread.sleep;

public class Controler {
    sourceUI[] UI = new sourceUI[8];
    int UINum;  //现在在哪个UI界面上
    int alive = 10;
    public Controler(){
        UI[0] = new WithdrawalsUI(new String[]{"<<500", "<<1000", "<<1500", "<<其他", "100>>", "200>>", "300>>", "返回>>"}, "现金提取窗口", "请选择金额!");
        UI[1] = new DepositUI(new String[]{"", "", "", "<<返回", "", "", "更正>>", "确认>>"}, "存款窗口", "请将整理好的钞票放入存款口（面额必须是100的倍数）!");
        UI[2] = new TransferUI(new String[]{"", "", "", "<<返回", "", "", "更正>>", "确认>>"}, "转账窗口", "请输入要转入的账号和金额!");
        UI[4] = new InquireUI(new String[]{"", "", "", "", "", "", "", "返回>>"}, "余额查询窗口", "");
        UI[5] = new ModifyUI(new String[]{"", "", "", "<<返回", "", "", "", "确认>>"}, "修改密码窗口", "");
        UI[6] = new HistoryUI(new String[]{"", "", "", "<<近一个月", "", "", "", "返回>>"}, "历史数据查询窗口", "");
        UI[7] = new LoginUI(new String[]{"", "", "", "<<取消", "", "", "", "确认>>"}, "登录窗口", "请选择预置账号!");
        UI[3] = new MainUI(new String[]{"<<取款", "<<存款", "<<转账", "", "查询>>", "修改密码>>", "历史数据查询>>", "退出系统>>"}, "主窗口", "请选择相应的功能!");
//        loginUI.toEnable();
        UI[7].toEnable();
        UINum = 7;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (UI[UINum].type != alive){
                        if (UINum == 3){
                            int next = UI[UINum].type;
                            UI[UINum].toDisable();
                            UINum = next;
                            UI[UINum].toEnable();
                        }else{
                            UI[UINum].toDisable();
                            UINum = 3;
                            UI[UINum].toEnable();
                        }
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
