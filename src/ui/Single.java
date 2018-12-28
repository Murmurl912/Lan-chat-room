package ui;

import main.Coder;
import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;

public class Single extends JPanel {

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

    public User user;
    public Launcher launcher;
    public int messageLength;

    public boolean layout = false;
    public boolean online = true;
    public int maxLineLength = 25;

    public int messageCount;

    public int unread;

    /**
     *
     * @param user 聊天的对象
     * @param launcher 所在的界面 用来调用它的发送接口
     */
    public Single(User user, Launcher launcher) {
        this.user = user;
        this.launcher = launcher;
        ui();
        place();
        action();
        chatTitleLabel.setText(user.member);
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
        messageScrollPane.setBackground(Color.WHITE);
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
        entryScrollPane.setBackground(Color.WHITE);
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

        Single single = this;
        sendButton.addActionListener((e)->{
            send(entry.getText());
            entry.setText(""); //
            entry.grabFocus();
        });

        entry.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER && (e.isControlDown())) {
                    send(entry.getText());
                    entry.setText("");
                    entry.grabFocus();
                }
            }
        });

        closeButton.addActionListener((e)->{

            launcher.removeTab(single);
            launcher.singles.remove(single);
            launcher.closed.add(single);
        });
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

    private void addMessage(Message message) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = messageCount++;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        messagePanel.add(message, gbc);
    }

    /**
     * 发送消息
     * @param message 消息内容
     */
    public void send(String message) {


        message = message.equals("") ? " " : message;
        message = message.trim();

        String display = nextline(message);
        Message label = new Message(display, Message.RIGHT);

        boolean flag = true;
        for(int i = 0; i < launcher.users.length; i++) {
            if(launcher.users[i].equals(user)) {
                launcher.setStatus("Message send successful! [" + Calendar.getInstance().getTime() + "]", Color.BLACK);
                Beep.play(Beep.SEND_SUCCESS);
                flag = false;
                break;
            }
        }

        if(flag) {
            Beep.play(Beep.SEND_FAILED);
            label.setMessageColor(Color.WHITE, Color.RED);
            launcher.setStatus("Message send failed! The peer is not online [" + Calendar.getInstance().getTime() + "]", Color.RED);
        }

        Calendar calendar = Calendar.getInstance();
        String t = calendar.get(Calendar.HOUR_OF_DAY) + " : "
                + calendar.get(Calendar.MINUTE) + " : "
                + calendar.get(Calendar.SECOND);
        Message time = new Message(t, Message.RIGHT);
        time.setMessageColor(new Color(0,0,0,0), Color.DARK_GRAY);
        time.setMessageFont(timeFont);
        time.setForeground(Color.DARK_GRAY);

        messageCount++;
        addMessage(time);
        messageCount++;
        addMessage(label);
//        messagePanel.add(label);

        messagePanel.revalidate();
        messageScrollPane.revalidate();
        launcher.chatTabbedPanel.revalidate();


        new Thread(()->{

            try {
                Thread.sleep(50);
            } catch (InterruptedException i) {
                i.printStackTrace();
            }


            JScrollBar bar = messageScrollPane.getVerticalScrollBar();

            if(bar != null) {
//
//                System.out.println("Label position: " + label.getLocation());
//                messageLength += label.getHeight();
//
//                if(label.getLocation().y >=  entry.getLocation().y - 2 * label.getHeight()) {
//                    bar.setValue(messageLength - messageScrollPane.getHeight() + 2 * label.getHeight());
//                }

                bar.setValue(bar.getMaximum());
//                System.out.println("Bar position: " + bar.getValue());
            }
        }).start();

        launcher.sendPrivate(message, user.address);
    }

    /**
     * message[0]标志位
     * message[1]ip地址
     * message[2]时间
     * message[3]组名
     * message[4]成员名
     * message[5] 消息内容
     * 显示消息
     * @param message 消息数组
     */
    public void receive(String[] message) {

        Beep.play(Beep.RECEIVE_PRIVATE);

        Calendar calendar = Calendar.getInstance();
        String t = calendar.get(Calendar.HOUR_OF_DAY) + " : "
                + calendar.get(Calendar.MINUTE) + " : "
                + calendar.get(Calendar.SECOND);
        Message time = new Message(t, Message.LEFT);
        time.setMessageColor(new Color(0,0,0,0), Color.DARK_GRAY);
        time.setMessageFont(timeFont);
        time.setForeground(Color.DARK_GRAY);

        messageCount++;
        addMessage(time);
        String display = nextline(message[5]);
        messageCount ++;
        Message label = new Message(display, Message.LEFT);
        addMessage(label);
//        messagePanel.add(label);

        messagePanel.revalidate();
        messageScrollPane.revalidate();
        launcher.chatTabbedPanel.revalidate();

        new Thread(()->{

            try {
                Thread.sleep(50);
            } catch (InterruptedException i) {
                i.printStackTrace();
            }


            JScrollBar bar = messageScrollPane.getVerticalScrollBar();

            if(bar != null) {
//
//                System.out.println("Label position: " + label.getLocation());
//                messageLength += label.getHeight();
//
//                if(label.getLocation().y >=  entry.getLocation().y - 2 * label.getHeight()) {
//                    bar.setValue(messageLength - messageScrollPane.getHeight() + label.getHeight());
//                }

                bar.setValue(bar.getMaximum());

//                System.out.println("Bar position: " + bar.getValue());
            }
        }).start();

    }


}
