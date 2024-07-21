package gui.over.base;

import gui.start.StartGame;
import loader.ResourceLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class ResultGUI {
    private JFrame frame;
    private String title;
    private String imagePath;
    private JButton backToMainButton;

    public ResultGUI(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(1100, 700);
        frame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(ResourceLoader.class.getResource(imagePath));
                g.drawImage(background.getImage(), 0, 0, null);
            }
        };
        backgroundPanel.setLayout(null);
        frame.add(backgroundPanel);

        backToMainButton = new JButton("回到游戏");
        backgroundPanel.add(backToMainButton);

        backToMainButton.addActionListener(e -> {
            frame.dispose();
            new StartGame();
        });

        backToMainButton.setBounds(700, 550, 100, 40);
        backToMainButton.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int closeCode = JOptionPane.showConfirmDialog(
                    frame,
                    "确定要结束游戏吗？",
                    title,
                    JOptionPane.YES_NO_OPTION
                );
                if (closeCode == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public void setVisible(boolean isVisible) {
        if (isVisible) {
            frame.setVisible(isVisible);
        } else {
            frame.dispose();
        }
    }

    public abstract void displayResultMessage();
}