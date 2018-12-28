package ui;

import main.Coder;
import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;

public class Group extends JPanel {

    private JPanel chatPanel;
    private JPanel chatTitlePanel;
    private JScrollPane messageScrollPane;
    private JPanel toolPanel;
    private JScrollPane entryScrollPane;
    private JPanel controlPanel;
    private JLabel chatTitleLabel;
    private JLabel toolLabel;
    private JTextArea entry;
    private JButton closeButton;
    private JButton sendButton;
    private JPanel messagePanel;
    private Font timeFont;

    private int messageCount;

    public int unreads;

    public Launcher launcher;
    public int messageLength;
    public boolean layout = false;
    public int maxLineLength = 25;

    /**
     * 群聊界面
     * @param launcher 所在界面
     */
    public Group(Launcher launcher) {

        this.launcher = launcher;
        ui();
        place();
        action();
        toolLabel.setText(" ");
        timeFont = new Font("time", Font.ITALIC, 10);
    }

    private void ui() {

        chatPanel = this;
        this.setBackground(Color.WHITE);
        chatPanel.setLayout(new GridBagLayout());
        chatTitlePanel = new JPanel();
        chatTitlePanel.setBackground(Color.WHITE);
        chatTitlePanel.setLayout(new GridBagLayout());

        chatTitleLabel = new JLabel();
        chatTitleLabel.setText("Label");

        messageScrollPane = new JScrollPane();
        messageScrollPane.setBorder(BorderFactory.createTitledBorder(""));

        messagePanel = new JPanel();
        messagePanel.setLayout(new GridBagLayout());
        messagePanel.setBackground(Color.WHITE);
//        messagePanel.setLayout(new GridLayout(10000,1));
        messageScrollPane.setViewportView(messagePanel);

        toolPanel = new JPanel();
        toolPanel.setBackground(Color.WHITE);
        toolPanel.setLayout(new GridBagLayout());


        toolLabel = new JLabel();
        toolLabel.setText("Label");

        entryScrollPane = new JScrollPane();
        entryScrollPane.setBorder(BorderFactory.createTitledBorder(""));

        entry = new JTextArea();
        entry.setLineWrap(true);
        entryScrollPane.setViewportView(entry);

        controlPanel = new JPanel();
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setLayout(new GridBagLayout());

        closeButton = new JButton();
        closeButton.setText("Close");


        sendButton = new JButton();
        sendButton.setText("Send");

    }

    private void place() {

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        chatPanel.add(chatTitlePanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        chatTitlePanel.add(chatTitleLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        chatPanel.add(messageScrollPane, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        chatPanel.add(toolPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        toolPanel.add(toolLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        chatPanel.add(entryScrollPane, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        chatPanel.add(controlPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(closeButton, gbc);

        final JPanel spacer1 = new JPanel();
        spacer1.setBackground(Color.WHITE);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(spacer1, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(sendButton, gbc);

        final JPanel spacer2 = new JPanel();
        spacer2.setBackground(Color.WHITE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(spacer2, gbc);
    }

    private void action() {
        Group group = this;

        sendButton.addActionListener((e)->{
            sendBroadcast(entry.getText());
            entry.setText("");
            entry.grabFocus();
        });

        entry.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER && (e.isControlDown())) {
                    sendBroadcast(entry.getText());
                    entry.setText("");
                    entry.grabFocus();
                }
            }
        });

        closeButton.addActionListener((e)->{
            launcher.removeTab(group);
            launcher.groupClosed = true;
        });

    }

    private void addMessage(Message message) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = messageCount++;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        messagePanel.add(message, gbc);

    }

    private String nextline(String str) {

        str = str.trim();
        StringBuilder messages = new StringBuilder("<html>");
        String[] strings = str.split("\n");

        int count = 0;
        for(String s : strings) {

            String sub = "";
            while (!s.equals("") && s.length() > maxLineLength) {
                sub = s.substring(0, maxLineLength);
                s = s.substring(maxLineLength);
                messages.append(sub);
                messages.append("<br/>");
            }
            messages.append(s);
            if(strings.length - 1 != count){
                messages.append("<br/>");
            }
            count++;
        }

        messages.append("</html>");

        return messages.toString();
    }

    /**
     * 发送广播
     * @param message 消息内容
     */
    public void sendBroadcast(String message) {

        message = message.equals("") ? " " : message;
        message.trim();

        launcher.sendGroup(message);
        Beep.play(Beep.SEND_SUCCESS);

        launcher.setStatus("Message send successful! [" + Calendar.getInstance().getTime() + "]", Color.BLACK);

        Calendar calendar = Calendar.getInstance();
        String t = calendar.get(Calendar.HOUR_OF_DAY) + " : "
                + calendar.get(Calendar.MINUTE) + " : "
                + calendar.get(Calendar.SECOND) + " ["
                + launcher.network.username + "-"
                + launcher.network.groupname + "]";
        Message time = new Message(t, Message.RIGHT);
        time.setMessageColor(new Color(0,0,0,0), Color.DARK_GRAY);
        time.setMessageFont(timeFont);
        time.setForeground(Color.DARK_GRAY);

        Message label = new Message(nextline(message), Message.RIGHT);

        messageCount++;
        addMessage(time);
        messageCount++;
        addMessage(label);


        messagePanel.revalidate();
        messageScrollPane.revalidate();
        launcher.chatTabbedPanel.revalidate();


        new Thread(()->{

            try {
                Thread.sleep(100);
            } catch (InterruptedException i) {
                i.printStackTrace();
            }


            JScrollBar bar = messageScrollPane.getVerticalScrollBar();

            if(bar != null) {

////                System.out.println("Label position: " + label.getLocation());
//                messageLength += label.getHeight() + time.getHeight();
//
//                if(label.getLocation().y >=  entry.getLocation().y - 4 * label.getHeight()) {
//                    bar.setValue(messageLength - messageScrollPane.getHeight() + 2 * label.getHeight() + 2 * time.getHeight());
//                }
                bar.setValue(bar.getMaximum());
//                System.out.println("Bar position: " + bar.getValue());
            }
        }).start();

    }

    /**
     * message[0]标志位
     * message[1]ip地址
     * message[2]时间
     * message[3]组名
     * message[4]成员名
     * message[5]消息内容
     * @param message 消息数组
     * @param user 消息来源
     */
    public void receive(String[] message, User user) {

        Beep.play(Beep.RECEIVE_GROUP);

        String re = message[5].trim();
        re = re.equals("") ? " " : re;


        Calendar calendar = Calendar.getInstance();
        String t = calendar.get(Calendar.HOUR_OF_DAY) + " : "
                + calendar.get(Calendar.MINUTE) + " : "
                + calendar.get(Calendar.SECOND) + " ["
                + user.member + "-"
                + user.group + "]";
        Message time = new Message(t, Message.RIGHT);
        time.setMessageColor(new Color(0,0,0,0), Color.DARK_GRAY);
        time.setMessageFont(timeFont);
        time.setForeground(Color.DARK_GRAY);

        Message label = new Message(nextline(re), Message.RIGHT);

        messageCount++;
        addMessage(time);
        messageCount++;
        addMessage(label);

        messagePanel.revalidate();
        messageScrollPane.revalidate();
        launcher.chatTabbedPanel.revalidate();

        new Thread(()->{

            try {
                Thread.sleep(100);
            } catch (InterruptedException i) {
                i.printStackTrace();
            }


            JScrollBar bar = messageScrollPane.getVerticalScrollBar();

            if(bar != null) {

////                System.out.println("Label position: " + label.getLocation());
//                messageLength += label.getHeight() + time.getHeight();
//
//                if(label.getLocation().y >=  entry.getLocation().y - 4 * label.getHeight()) {
//                    bar.setValue(messageLength - messageScrollPane.getHeight() + 2 * label.getHeight() + 2 * time.getHeight());
//                }
                bar.setValue(bar.getMaximum());

//                System.out.println("Bar position: " + bar.getValue());
            }
        }).start();

    }

    /**
     * 改变标题栏
     * @param text 显示的内容
     */
    public void setChatTitleLabel(String text) {
        chatTitleLabel.setText(text);
    }

}
