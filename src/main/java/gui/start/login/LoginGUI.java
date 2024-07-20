package gui.start.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import game.RunGame;
import gui.start.StartGame;

public class LoginGUI extends JFrame {
    private static boolean isLoggedIn = false;
    //String accountName = null; // 用于存储从数据库中获取的账号名
    private  final Integer WIDTH=600;
    private  final Integer HEIGHT=400;


    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPasswordField upwdInput;
    private JFormattedTextField uidInput;
    private JLabel label1;
    private JLabel uid;
    private JLabel upwd;
    private JButton btnLogin;
    private JButton btnRgster;
    private JPanel buttonBar;

    public LoginGUI() {
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        upwdInput = new JPasswordField();
        uidInput = new JFormattedTextField();
        label1 = new JLabel();
        uid = new JLabel();
        upwd = new JLabel();
        btnLogin = new JButton();
        btnRgster = new JButton();
        buttonBar = new JPanel();

        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);
                contentPanel.add(upwdInput);
                upwdInput.setBounds(95, 120, 165, 30);
                contentPanel.add(uidInput);
                uidInput.setBounds(95, 85, 165, uidInput.getPreferredSize().height);

                //---- label1 ----
                label1.setText("\u8bf7\u5148\u767b\u5f55\uff01");
                label1.setFont(label1.getFont().deriveFont(Font.BOLD));
                label1.setDisplayedMnemonicIndex(1);
                contentPanel.add(label1);
                label1.setBounds(15, 10, 65, 45);

                //---- uid ----
                uid.setText("\u8d26\u53f7\uff1a");
                contentPanel.add(uid);
                uid.setBounds(new Rectangle(new Point(50, 85), uid.getPreferredSize()));

                //---- upwd ----
                upwd.setText("\u5bc6\u7801\uff1a");
                contentPanel.add(upwd);
                upwd.setBounds(new Rectangle(new Point(50, 120), upwd.getPreferredSize()));

                //---- btnLogin ----
                btnLogin.setText("\u767b\u5f55");
                btnLogin.setForeground(Color.red);
                contentPanel.add(btnLogin);
                btnLogin.setBounds(new Rectangle(new Point(195, 170), btnLogin.getPreferredSize()));

                //---- btnRgster ----
                btnRgster.setText("\u6ce8\u518c");
                contentPanel.add(btnRgster);
                btnRgster.setBounds(new Rectangle(new Point(95, 170), btnRgster.getPreferredSize()));


                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for (int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 85, 80};
                ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0, 0.0};
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //添加登录按钮监听器
        btnLogin.addActionListener(e -> {
            String getInputUid = uidInput.getText();
            String getInputUpwd = upwdInput.getText();

            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = JdbcUtils.getConnection();
                String sql = "SELECT * FROM userinfo WHERE Account=? AND Password=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, getInputUid);
                pstmt.setString(2, getInputUpwd);
                rs = pstmt.executeQuery();

                //创建弹窗
                JDialog dialog = new JDialog();
                dialog.setLocationRelativeTo(null);
                dialog.setSize(200, 200);
                if (rs.next()) {
                    isLoggedIn = true; // 设置登录成功标志
                    dialog.add(new JLabel("登录成功！"));

                   // accountName = rs.getString("Account"); // 获取账号名
                    this.dispose(); // 关闭登录窗口

                    // 使用Timer来实现弹窗一秒后自动关闭
                    new Timer(1000, event -> dialog.dispose()).start();

                    // 登录成功后，启动游戏
                    new StartGame();

                } else {
                    isLoggedIn = false; // 重置登录失败标志
                    dialog.add(new JLabel("登录失败！"));
                }
                dialog.setVisible(true);


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                JdbcUtils.release(conn, pstmt, rs);
            }


        });
        setVisible(true);
        

        //添加注册按钮监听器

        btnRgster.addActionListener(e -> {
            String getInputUid = uidInput.getText(); // 获取用户输入的账号
            String getInputUpwd = upwdInput.getText(); // 获取用户输入的密码

            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = JdbcUtils.getConnection(); // 获取数据库连接
                String sql = "INSERT INTO userinfo (Account, Password) VALUES (?, ?)"; // 准备SQL语句
                pstmt = conn.prepareStatement(sql); // 创建PreparedStatement
                pstmt.setString(1, getInputUid); // 设置账号参数
                pstmt.setString(2, getInputUpwd); // 设置密码参数

                int affectedRows = pstmt.executeUpdate(); // 执行更新操作

                // 创建弹窗
                JDialog dialog = new JDialog();
                dialog.setLocationRelativeTo(null);
                dialog.setSize(200, 200);
                if (affectedRows > 0) {
                    dialog.add(new JLabel("注册成功！快试试登录吧!"));
                } else {
                    dialog.add(new JLabel("注册失败！"));
                }
                dialog.setVisible(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                JdbcUtils.release(conn, pstmt, null); // 释放资源
            }
        });

    }
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    //测试时运行loginGUI即可
    public static void main(String[] args) {
        new LoginGUI();
    }


}
