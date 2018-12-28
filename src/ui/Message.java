package ui;

import javax.swing.*;
import java.awt.*;

public class Message extends JPanel {

    public static int LEFT = 0;
    public static int RIGHT = 1;
    public static int CENTER = 2;

    private JPanel lspacer;
    private JButton message;
    private JPanel rspacer;

    public Message(String message, int ali) {
        this.message = new JButton(message);
        this.message.setFocusPainted(false);
        this.message.setRolloverEnabled(false);
        this.message.setBackground(new Color(200,200,200,60));
        ui(ali);
    }

    public void setMessageColor(Color background, Color foreground){
        message.setBackground(background);
        message.setForeground(foreground);
    }

    public void setMessageFont(Font font) {
        message.setFont(font);
    }

    public Font getMessageFont() {
        return message.getFont();
    }

    private void ui(int ali) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);
        switch (ali) {
            case 0 : left(); break;
            case 1 : right(); break;
            case 2 : center(); break;
            default: center();
        }

    }

    private void left() {
        lspacer = new JPanel();
        lspacer.setBackground(new Color(0,0,0,0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(lspacer, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(message, gbc);

        rspacer = new JPanel();
        rspacer.setBackground(new Color(0,0,0,0));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(rspacer, gbc);
    }

    private void right() {
        lspacer = new JPanel();
        lspacer.setBackground(new Color(0,0,0,0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(lspacer, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(message, gbc);

        rspacer = new JPanel();
        rspacer.setBackground(new Color(0,0,0,0));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(rspacer, gbc);
    }

    private void center() {
        lspacer = new JPanel();
        lspacer.setBackground(new Color(0,0,0,0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(lspacer, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(message, gbc);

        rspacer = new JPanel();
        rspacer.setBackground(new Color(0,0,0,0));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(rspacer, gbc);
    }
}
