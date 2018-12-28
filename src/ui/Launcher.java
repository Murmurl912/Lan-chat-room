package ui;

import main.Coder;
import main.Network;
import main.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.plaf.basic.BasicTableUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;

public class Launcher extends JFrame {

    private JPanel basePanel;
    public JTabbedPane chatTabbedPanel;
    private JList<String> onlineMemberList;
    private JPanel titlePanel;
    private JPanel toolPanel;
    private JPanel bodyPanel;
    private JSplitPane panelSplitor;
    private JPanel statusPanel;
    private JButton titleLabel;
    private JLabel toolLabel;
    private JScrollPane onlineMembersScrollPane;
    private JList<String> messagesList;
    private JPanel listPanel;

    private JButton statusLabel;

    public Group group;
    public boolean groupClosed = false;

    public ArrayBlockingQueue<Single> singles;
    public ArrayBlockingQueue<Single> recycle;
    public ArrayBlockingQueue<Single> closed;

    public HashMap<User, Integer> unreads;
    public String[] messages;
    public User[] users;
    public String[] names;

    public Network network;

    public Launcher() {
        ui();
        place();
        action();
        init();
    }

    private void ui() {

        // base panel ----------------------------------------
        this.setBackground(Color.WHITE);
        basePanel = new JPanel();
        basePanel.setBackground(Color.WHITE);
        basePanel.setLayout(new GridBagLayout());
        setContentPane(basePanel);

        // title panel ---------------------------------------
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setLayout(new GridBagLayout());

        // title label --------------------------------------
        titleLabel = new JButton();
        titleLabel.setFocusPainted(false);
        titleLabel.setBorderPainted(false);
        titleLabel.setHorizontalAlignment(JButton.CENTER);
        titleLabel.setRolloverEnabled(false);
        titleLabel.setBackground(new Color(233, 233, 233, 20));
        titleLabel.setText("Lan Chat Room");

        toolPanel = new JPanel();
        toolPanel.setLayout(new GridBagLayout());

        // toolLabel = new JLabel();
        // toolLabel.setText("Tools");

        bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.WHITE);
        bodyPanel.setLayout(new GridBagLayout());

        panelSplitor = new JSplitPane();
        panelSplitor.setBackground(Color.WHITE);
        panelSplitor.setDividerSize(0);
        panelSplitor.setDividerLocation(250);


        onlineMembersScrollPane = new JScrollPane();
        onlineMembersScrollPane.setBackground(new Color(233, 233, 233, 60));;
        panelSplitor.setLeftComponent(onlineMembersScrollPane);
        onlineMembersScrollPane.setBorder(BorderFactory.createTitledBorder("Online Members"));

        listPanel = new JPanel();
        listPanel.setLayout(new GridBagLayout());
        listPanel.setBackground(new Color(255, 255, 255));
        onlineMembersScrollPane.setViewportView(listPanel);

        onlineMemberList = new JList<>();
        onlineMemberList.setBackground(Color.WHITE);

        chatTabbedPanel = new JTabbedPane();
        chatTabbedPanel.setBorder(BorderFactory.createTitledBorder("Opened Chat rooms"));
        chatTabbedPanel.setBackground(new Color(233, 233, 233, 60));
        chatTabbedPanel.setFocusable(false);
        chatTabbedPanel.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

        panelSplitor.setRightComponent(chatTabbedPanel);




        statusPanel = new JPanel();
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setLayout(new GridBagLayout());

        statusLabel = new JButton();
        statusLabel.setBackground(new Color(233, 233, 233, 20));
        statusLabel.setBorderPainted(true);
        statusLabel.setFocusPainted(false);
        statusLabel.setRolloverEnabled(false);
        statusLabel.setContentAreaFilled(true);
        statusLabel.setEnabled(true);
        statusLabel.setHorizontalAlignment(JButton.LEFT);
        statusLabel.setText("Ready!");


        messagesList = new JList<>();

    }

    private void place() {

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        basePanel.add(titlePanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        // gbc.insets = new Insets(5, 5, 5, 5);
        titlePanel.add(titleLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        basePanel.add(toolPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        // toolPanel.add(toolLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        basePanel.add(bodyPanel, gbc);



        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        bodyPanel.add(panelSplitor, gbc);


        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        basePanel.add(statusPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        statusPanel.add(statusLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        listPanel.add(onlineMemberList, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        listPanel.add(messagesList, gbc);
    }

    private void action() {
        Launcher launcher = this;
        onlineMemberList.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // double clicked
                if(e.getClickCount() == 2) {
                    int index = onlineMemberList.getSelectedIndex();
                    // no item selected
                    if(index == -1) {
                        return;
                    }

                    // not in the right position
                    Rectangle rectangle = onlineMemberList.getCellBounds(index, index + 1);
                    if(!rectangle.contains(e.getPoint())) {
                        return;
                    }

                    // group chat
                    if(index == 0) {
                        if(groupClosed) {
                            chatTabbedPanel.insertTab("Group Chat", null, group, "Group Chat", 0);
                            group.unreads = 0;
                        }

                        chatTabbedPanel.setSelectedComponent(group);
                        return;
                    }

                    // private chat
                    // error in users
                    if(users.length <= 0) {
                        return;
                    }

                    // get user
                    User user = users[index - 1];
                    if(unreads.containsKey(user)) {
                        unreads.replace(user, unreads.get(user), 0);
                    } else {
                        unreads.put(user, 0);
                    }

                    // opened tab
                    for(Single single : singles) {
                        System.out.println(single.user.member + " " + single.user.group + " " + single.user.address);
                        System.out.println(single.user.equals(user));
                        if(single.user.equals(user)) {
                            chatTabbedPanel.setSelectedComponent(single);
                            update(launcher.network.users);
                            return;
                        }
                    }

                    // closed tab
                    for(Single single : closed) {
                        if(user.equals(single.user)) {
                            closed.remove(single);
                            singles.add(single);
                            chatTabbedPanel.insertTab(user.member, null, single,
                                    user.member + " from group " + user.group, chatTabbedPanel.getTabCount() - 1);
                            chatTabbedPanel.setSelectedComponent(single);
                            update(launcher.network.users);
                            return;
                        }
                    }

                    // not int the list
                    System.out.println("Not no the list");
                    Single single = new Single(user, launcher);
                    singles.add(single);
                    chatTabbedPanel.addTab(user.member, null, single, user.member + " from group " + user.group);
                    chatTabbedPanel.setSelectedComponent(single);
                    update(launcher.network.users);
                }

            }
        });

    }

    private void init() {
        singles = new ArrayBlockingQueue<>(1024);
        recycle = new ArrayBlockingQueue<>(1024);
        closed = new ArrayBlockingQueue<>(1024);
        group = new Group(this);
        chatTabbedPanel.addTab("Group Chat", group);
        unreads = new HashMap<>();
        // toolLabel.setText("This is a toolbar area for further development");
    }

    public void removeTab(JPanel panel) {
        chatTabbedPanel.remove(panel);
    }


    /**
     * 更新用户列表
     * @param userlist 用户列表
     */
    public void update(CopyOnWriteArraySet<User> userlist) {

        users = new User[userlist.size()];
        userlist.toArray(users);
        names = new String[users.length + 1];
        messages = new String[users.length + 1];
        // onlines.addAll(Arrays.asList(users));

        int count = 0;
        names[count] = "Group Chat";
        messages[count] = Integer.toString(group.unreads);
        count++;

        for(User user : users) {
            names[count] = user.member + "-" + user.group;
            messages[count] = Integer.toString(unreads.getOrDefault(user, 0));
            count++;
        }

        onlineMemberList.setListData(names);
        messagesList.setListData(messages);
        group.setChatTitleLabel(users.length + " Member(s) Online");

    }

    /**
     * 启动网络通讯
     */
    public void execute(String groupname, String membername, int broadcastReceivePort,
                        int unicastReceivePort, String broadcastAddress) throws IOException {
        network = new Network(this, membername, groupname, broadcastReceivePort,
                unicastReceivePort, InetAddress.getByName(broadcastAddress));
        network.execute();
    }

    /**
     * message[0]标志位
     * message[1]ip地址
     * message[2]时间
     * message[3]组名
     * message[4]成员名
     * 收到群消息 显示到群聊的界面上
     * @param message 消息内容 这是个数组 直接将解码后的结果传入
     * @param user 消息来源 （谁发的）
     */
    public void receiveGroup(String[] message, User user) {
        if(groupClosed) {
            group.unreads++;
        }

        group.receive(message, user);
    }

    /**
     * message[0]标志位
     * message[1]ip地址
     * message[2]时间
     * message[3]组名
     * message[4]成员名
     * message[5]消息内容
     * 收到私聊消息 显示到具体私聊的界面
     * @param messages 消息内容 这是个数组 直接将解码后的结果传入
     * @param user 来源
     */
    public void receivePrivate(String[] messages, User user) {

        // opened tab
        for(Single single : singles) {
            if(single.user.equals(user)) {
                single.receive(messages);
                return;
            }
        }

        //  closed tab
        for(Single single : closed) {
            if(single.user.equals(user)) {
                if(unreads.containsKey(user)) {
                    unreads.replace(user, unreads.get(user), unreads.get(user) + 1);
                } else {
                    unreads.put(user, 1);
                }
                single.receive(messages);
                update(network.users);
                return;
            }
        }

        // recycled tab
        for(Single single : recycle) {
            if(single.user.equals(user)) {

                if(unreads.containsKey(user)) {
                    unreads.replace(user, unreads.get(user), unreads.get(user) + 1);
                } else {
                    unreads.put(user, 1);
                }

                recycle.remove(single);
                closed.add(single);
                single.receive(messages);
                update(network.users);
                return;
            }
        }

        // tab not found
        if(unreads.containsKey(user)) {
            unreads.replace(user, unreads.get(user), unreads.get(user) + 1);
        } else {
            unreads.put(user, 1);
        }
        Single panel = new Single(user, this);
        closed.add(panel); // add to closed tab
        panel.receive(messages);
        update(network.users);

    }

    /**
     * 群发消息
     * @param message 消息内容 仅仅是文字 不包含组名啥的
     */
    public void sendGroup(String message) {
        network.broadcast(message, Coder.GROUP_CHAT);
    }

    /**
     * 私发消息
     * @param message 消息内容 仅仅是文字 不包含组名啥的
     * @param destinationAddress 目的地址
     */
    public void sendPrivate(String message, InetAddress destinationAddress) {
        network.unicast(message, Coder.PRIVATE_CHAT, destinationAddress);
    }

    public void setStatus(String str, Color color) {
        statusLabel.setText(str);
        statusLabel.setForeground(color);
    }

//    public static void main(String[] args) {
//
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
////            UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
////            UIManager.put("TabbedPane.highlight",        Color.WHITE);
////            UIManager.put("TabbedPane.tabAreaBackground", new ColorUIResource(Color.WHITE));
//            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//
//        } catch (Exception un) {
//            un.printStackTrace();
//        }
//        Launcher window = new Launcher();
//
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setSize(new Dimension(900, 600));
//        window.setVisible(true);
//
//        // 启动网络
//        try {
//            window.execute("Mur", "Mur", 12345, 23456, "224.3.3.149");
//        } catch (IOException io) {
//            io.printStackTrace();
//        }
//
//
//    }

}
