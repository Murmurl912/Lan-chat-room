package main;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class Coder {

    public static final String UTF_8_ENCODE = "utf-8";
    public static final String GBK_ENCODE = "gbk";
    public static final String BOOKING = new String("1".getBytes(), StandardCharsets.UTF_8);
    public static final String BOOKING_ACK = new String("2".getBytes(), StandardCharsets.UTF_8);
    public static final String ONLINE_CHECK = new String("3".getBytes(), StandardCharsets.UTF_8);
    public static final String GROUP_CHAT = new String("4".getBytes(), StandardCharsets.UTF_8);
    public static final String PRIVATE_CHAT = new String("5".getBytes(), StandardCharsets.UTF_8);
    public static final String UNIT_SEPARATOR = "\u2593";

    /**
     * 打包自己的消息 以utf-8重新编码
     * @param flag 消息的标志位
     * @param address 自己的ip地址 可以由InetAddress.getLocalHost()获取
     * @param time 发送时间
     * @param group 组名
     * @param member 成员名
     * @param message 发送的消息
     * @return 包装好的字节数组
     */
    public static byte[] encode(String flag, InetAddress address, String time, String group, String member, String message) {

        StringBuilder builder = new StringBuilder();

        // flag 0
        builder.append(flag); builder.append(UNIT_SEPARATOR);

        // local address, your host address 1
        String a = new String(address.getHostAddress().getBytes(), StandardCharsets.UTF_8); // 以utf-8重新编码
        builder.append(a); builder.append(UNIT_SEPARATOR);

        // time 2
        String t = new String(time.getBytes(), StandardCharsets.UTF_8);
        builder.append(t); builder.append(UNIT_SEPARATOR);

        // group name 3
        String g = new String(group.getBytes(), StandardCharsets.UTF_8);
        builder.append(group); builder.append(UNIT_SEPARATOR);

        // member name 4
        String m = new String(member.getBytes(), StandardCharsets.UTF_8);
        builder.append(m); builder.append(UNIT_SEPARATOR);

        // message 5
        String mess = new String(message.getBytes(), StandardCharsets.UTF_8);
        builder.append(mess);

        // return byte[]
        byte[] bytes = builder.toString().getBytes(StandardCharsets.UTF_8);
        return  bytes;
    }

    /**
     * 编码都以UTF_8, 从接受端包中提取消息
     * @param packet 要提取信息的DatagramPacket
     * @return 返回分开的数组
     */
    public static String[] decode(DatagramPacket packet) {
        byte[] bytes = packet.getData();
        String message = new String(bytes, StandardCharsets.UTF_8);
        return message.split(UNIT_SEPARATOR);
    }

}
