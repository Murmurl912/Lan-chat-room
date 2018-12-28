package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Login extends JDialog {

    private JTextField userNameEntry;
    private JTextField groupNameEntry;
    private JButton loginButton;
    private JLabel titleLabel;
    private JPanel titlePanel;
    private JPanel groupPanel;
    private JPanel loginPanel;
    private JPanel userPanel;
    private JLabel groupNameLabel;
    private JLabel userNameLabel;
    private JButton exitButton;
    private JPanel login;

    private String defaultGroup = "Circle Server";
    private String defaultName = "Murmur";
    private String defaultBroadcastAddress = "224.3.3.149";
    private int defaultBroadcastPort = 12345;
    private int defaultUnicastPort = 23456;

    private boolean active = false;

    public Login() {
        ui();
        action();
        groupNameEntry.setText(defaultGroup);
        userNameEntry.setText(defaultName);
        titleLabel.setText("");
        Image image = Toolkit.getDefaultToolkit().getImage("res/icon.png");
        Image icon = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        titleLabel.setIcon(new ImageIcon(icon));
    }

    private void ui() {

        login = new JPanel();
        this.getContentPane().add(login);
        login.setBackground(Color.WHITE);
        login.setLayout(new GridBagLayout());

        titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        login.add(titlePanel, gbc);

        titleLabel = new JLabel();
        titleLabel.setText("Label");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        titlePanel.add(titleLabel, gbc);

        groupPanel = new JPanel();
        groupPanel.setBackground(Color.WHITE);
        groupPanel.setBorder(BorderFactory.createTitledBorder(""));
        groupPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        login.add(groupPanel, gbc);

        userNameEntry = new JTextField();
        userNameEntry.setBackground(new Color(233, 233, 233, 60));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        groupPanel.add(userNameEntry, gbc);

        userNameLabel = new JLabel();
        userNameLabel.setText("User Name       ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        groupPanel.add(userNameLabel, gbc);

        loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createTitledBorder(""));
        loginPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        login.add(loginPanel, gbc);

        loginButton = new JButton();
        loginButton.setText("Login");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(loginButton, gbc);

        exitButton = new JButton();
        exitButton.setText("Exit");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(exitButton, gbc);

        final JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        loginPanel.add(panel1, gbc);

        userPanel = new JPanel();
        userPanel.setBackground(Color.WHITE);
        userPanel.setBorder(BorderFactory.createTitledBorder(""));
        userPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        login.add(userPanel, gbc);

        groupNameLabel = new JLabel();
        groupNameLabel.setText("Group Name    ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        userPanel.add(groupNameLabel, gbc);

        groupNameEntry = new JTextField();
        groupNameEntry.setBackground(new Color(233, 233, 233, 60));
        groupNameEntry.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        userPanel.add(groupNameEntry, gbc);
    }

    private void action() {

        Login l = this;
        exitButton.addActionListener((e)->{
            System.exit(0);
        });

        loginButton.addActionListener((e)->{
            String group = groupNameEntry.getText();
            String name = userNameEntry.getText();


            if(!check(group, name)) {
                return;
            }

            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            Launcher window = new Launcher();
            window.setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.png"));
            window.setLocation(size.width / 2 - 450, size.height / 2 - 300);;
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(new Dimension(900, 600));
            window.setVisible(true);
            active = true;
            new Thread(()->{
                try {
                    Thread.sleep(100);
                    l.dispose();
                } catch (InterruptedException i) {
                    i.printStackTrace();
                }
            }).start();

            // 启动网络
            try {
                window.execute(group.trim(), name.trim(), 12345, 23456, "224.3.3.149");
            } catch (IOException io) {
                io.printStackTrace();
            }


        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if(!active) {
                    System.exit(0);
                } else {
                    l.dispose();
                }

            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println(l.getSize());
            }
        });
    }

    private boolean check(String group, String name) {

        if(group.trim().equals("")) {
            return false;
        }

        if(name.trim().equals("")) {
            return false;
        }

        if(name.length() > 32) {
            return false;
        }

        if(group.length() > 32) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//            UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
//            UIManager.put("TabbedPane.highlight",        Color.WHITE);
//            UIManager.put("TabbedPane.tabAreaBackground", new ColorUIResource(Color.WHITE));
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception un) {
            un.printStackTrace();
        }
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Login login = new Login();
        login.setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.png"));
        login.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        login.setLocation(size.width / 2 - 200, size.height / 2 - 200);
        login.setSize(331, 549);
        login.setVisible(true);

    }
}
