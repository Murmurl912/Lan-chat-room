package main;

import ui.Launcher;
import ui.Single;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArraySet;

public class Network {

    public CopyOnWriteArraySet<User> users; // 用户

    public Broadcast broadcast;
    public int broadcastReceivePort;
    public InetAddress broadcastAddress;
    public Unicast unicast;
    public int unicastReceivePort;
    public InetAddress address;

    public String groupname;
    public String username;

    public Launcher launcher;
    /**
     * 广播消息
     * @param message 消息内容
     * @param flag 消息类型
     */
    public void broadcast(String message, String flag) {

        try {
            byte[] bytes = Coder.encode(flag, InetAddress.getLocalHost(),
                    Calendar.getInstance().getTime().toString(), groupname, username, message);
            broadcast.broadcast(bytes);
        } catch (UnknownHostException un) {
            un.printStackTrace();
        }

    }

    /**
     * 处理收到的广播包
     * 只处理标志位为 1-5的包
     * 其它的丢掉
     */
    public void receiveBroadcast() {

        while (true) {
            // queue 存储接受的包
            if(!broadcast.queue.isEmpty()) {
                DatagramPacket packet = broadcast.queue.remove();
                // System.out.println("\n------------------------------------------------------------------------------------------------");
                System.out.println("Decode broadcast packet: " + new String(packet.getData(), StandardCharsets.UTF_8));
                String[] results = Coder.decode(packet);

                // 消息长度不够
                System.out.println("Message length: " + results.length);
                if(results.length != 6) {
                    System.out.print("Decode failed: " );
                    for(String str : results) {
                        System.out.print(str + "\t");
                    }
                    System.out.println();
                    return;
                }

                // 通过接受的广播包 获取对方的ip地址 原来消息里面的ip地址字段不再使用
                // ********* 消息里面的ip 地址字段不再使用 但消息仍需要填入ip 地址 ************
                User user = new User(packet.getAddress(), results[3], results[4]);
                user.latestOnlineTime = System.currentTimeMillis();

                // 标志位为 1 处理报道
                if(results[0].equals(Coder.BOOKING)) {
                    // System.out.println("Message Flag: Booking");

                    // 如果 用户不在列表中 更新列表
                    // 具体的方法根据自己的界面采取具体的实现
                    new Thread(()->{
                        if(!users.contains(user)) {
                            users.add(user);
                            launcher.update(users);
                        }

                        // 这个的目的是更新在线时间
                        // 具体的可以自己实现自己的更新时间的方法
                        users.remove(user);
                        users.add(user);

                        // 发送回执
                        broadcast("Booking Ack", Coder.BOOKING_ACK);
                        // System.out.println("Broadcast Reply Message Flag Booking Ack");
                    }).start();

                    continue;
                }

                // 标志位位2 处理报道答复
                if(results[0].equals(Coder.BOOKING_ACK)) {
                    // System.out.println("Message Flag: Booking Ack");
                    // 如果 用户不在列表中 更新列表
                    // 具体的方法根据自己的界面采取具体的实现
                    new Thread(()->{
                        if(!users.contains(user)) {
                            users.add(user);
                            launcher.update(users);
                        }

                        // 这个的目的是更新在线时间
                        // 具体的可以自己实现自己的更新时间的方法
                        users.remove(user);
                        users.add(user);
                    }).start();

                    continue;
                }

                // 标志位为3 处理在线检测
                if(results[0].equals(Coder.ONLINE_CHECK)) {
                    // System.out.println("Message Flag: Online Check");
                    // 如果 用户不在列表中 更新列表
                    // 具体的方法根据自己的界面采取具体的实现

                    new Thread(()->{
                        if(!users.contains(user)) {
                            users.add(user);
                            launcher.update(users);
                        }

                        // 这个的目的是更新在线时间
                        // 具体的可以自己实现自己的更新时间的方法
                        users.remove(user);
                        users.add(user);
                    }).start();

                    continue;
                }

                // 标志位为4 处理群聊消息
                if(results[0].equals(Coder.GROUP_CHAT)) {
                    // System.out.println("Message Flag: Group Chat");

                    // 屏蔽掉自己的消息

                    new Thread(()->{
                        if(user.group.equals(groupname) && user.member.equals(username)) {
                            return;
                        }

                        // 如果 用户不在列表中 更新列表
                        // 具体的方法根据自己的界面采取具体的实现
                        if(!users.contains(user)) {
                            users.add(user);
                            launcher.update(users);
                        }

                        // 这个的目的是更新在线时间
                        // 具体的可以自己实现自己的更新时间的方法
                        users.remove(user);
                        users.add(user);

                        launcher.receiveGroup(results, user);
                    }).start();


                    continue;
                }

                // System.out.println("Message Flag: " + results[0]);
                // System.out.println("Invalid Message");
                // System.out.println("------------------------------------------------------------------------------------------------\n");

            }

        }
    }

    /**
     * 单播消息
     * @param message 内容
     * @param flag 类型
     * @param destinationAddress 目的地址
     */
    public void unicast(String message, String flag, InetAddress destinationAddress) {

        try {
            byte[] bytes = Coder.encode(flag, InetAddress.getLocalHost(),
                    Calendar.getInstance().getTime().toString(), groupname, username, message);
            unicast.send(bytes, destinationAddress);
        } catch (UnknownHostException un) {
            un.printStackTrace();
        }

    }

    /**
     * 处理单播接受端包
     * 只处理标志位为6的包
     * 其它的丢掉
     */
    public void receiveUnicast() {

        while (true) {
            // queue 存储接受的包
            if(!unicast.queue.isEmpty()) {
                DatagramPacket packet = unicast.queue.remove();

                System.out.println("Decode unicast packet: " + new String(packet.getData(), StandardCharsets.UTF_8));
                String[] results = Coder.decode(packet);

                // 消息长度不够
                if(results.length != 6) {
                    System.out.print("Decode failed: " );
                    for(String str : results) {
                        System.out.print(str + "\t");

                    }
                    System.out.println();
                    return;
                }

                // 通过接受的广播包 获取对方的ip地址 原来消息里面的ip地址字段不再使用
                // ********* 消息里面的ip 地址字段不再使用 但消息仍需要填入ip 地址 ************
                User user = new User(packet.getAddress(), results[3], results[4]);
                user.latestOnlineTime = System.currentTimeMillis();

                // 标志位为5 私聊消息
                if(results[0].equals(Coder.PRIVATE_CHAT)) {
                    // System.out.println("Message Flag: Private Chat");

                    // 如果 用户不在列表中 更新列表
                    // 具体的方法根据自己的界面采取具体的实现
                    new Thread(()->{
                        if(!users.contains(user)) {
                            users.add(user);
                            launcher.update(users);
                        }

                        // 这个的目的是更新在线时间
                        // 具体的可以自己实现自己的更新时间的方法
                        users.remove(user);
                        users.add(user);

                        // 接受到消息传递到界面上
                        launcher.receivePrivate(results, user);
                    }).start();

                    continue;
                }

                // System.out.println("Message Flag: " + results[0]);
                // System.out.println("Invalid Message");

            }

        }
    }

    /**
     * 检测列表是否有人不在线
     */
    public void onlineCheck() {
        for(User user : users) {

            if(System.currentTimeMillis() - user.latestOnlineTime > 10000) {
                // 用户超过10秒没有发送任何包 表明不在线剔除
                users.remove(user);
                // 同时更新界面上的列表
                // 具体的实现根据自己的界面确定
                launcher.update(users);
            }
        }
    }

    /**
     * 发送自己在线的消息
     */
    public void stateOnline() {
        broadcast("Online", Coder.ONLINE_CHECK);
    }

    /**
     * 启动网络部分
     * @throws IOException
     */
    public void execute() throws IOException {

        broadcast = new Broadcast(broadcastAddress, broadcastReceivePort); // 广播
        unicast = new Unicast(unicastReceivePort); // 单播

        // 启动广播监听 监听  roadcastReceivePort = 12345 端口-------------------------------------
        broadcast.execute();
        // 启动处理广播消息的线程
        Thread a = new Thread(()->{
            receiveBroadcast();
        });

        a.setPriority(Thread.MAX_PRIORITY);
        a.start();

        // 启动单播监听 监听    unicastReceivePort = 23456 端口-------------------------------
        unicast.execute();
        // 启动单播消息处理程序
        Thread b = new Thread(()->{
            receiveUnicast();
        });
        b.setPriority(Thread.MAX_PRIORITY);
        b.start();

        // 发送报道 --------------------------------------------------------------------------------------
        try {

            Thread.sleep(500);
            broadcast("Hello?", Coder.BOOKING);

            // 启动刷新在线用户的线程---------------------------------------------------------------------------
            Thread c = new Thread(()->{
                while (true) {
                    try {
                        onlineCheck();
                        Thread.sleep(10000);
                    } catch (InterruptedException i) {
                        i.printStackTrace();
                    }

                }
            });
            c.setPriority(Thread.MIN_PRIORITY);
            c.start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 启动发送自己在线消息的线程----------------------------------------------------------------
        Thread d = new Thread(()->{
            while (true) {
                try {
                    stateOnline();
                    Thread.sleep(9000);
                } catch (InterruptedException i) {
                    i.printStackTrace();
                }

            }
        });
        d.setPriority(Thread.MIN_PRIORITY);
        d.start();

//        new Thread(()->{
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                    unicast("Hello: " + System.currentTimeMillis(), Coder.PRIVATE_CHAT, InetAddress.getLocalHost());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }).start();

    }

    /**
     * 创建 初始化的网络
     * @param launcher ui 界面
     */
    public Network(Launcher launcher, String username, String groupname,
                   int broadcastReceivePort, int unicastReceivePort, InetAddress broadcastAddress) {

        try {
            this.launcher = launcher;
            this.broadcastReceivePort = broadcastReceivePort;
            this.unicastReceivePort = unicastReceivePort;
            this.broadcastAddress = broadcastAddress;

            users = new CopyOnWriteArraySet<>();
            this.groupname = groupname;
            this.username = username;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}

/*
A : ALPHA （埃尔发） -a er fa B : BRAVO （布拉瓦沃）-bu rua wo
C : CHARLIE（恰丽儿） D : DELTA （德儿塔）
E : ECHO （爱柯喔）ai kao F : FOXTROT（佛克斯抓特）-fou k si chao te
G : GOLF （高尔夫） H : HOTEL（侯太儿）-hao tai ao
I : INDIA （印地安）yin di ya J : JULIET（朱丽叶）-jiu li te
K : KILO（克依勒欧）kai lou L : LIMA（利马）
M : MIKE（迈克）-mai ke N : NOVEMBER（楠部）-nao wen bo
O : OSCAR（奥斯卡） P : PAPA （帕帕）
Q : QUEBEC（魁北克）-kui bai ke R : ROMEO（罗密欧）ruo mi ou
S : SIEARRA（斯依也路阿）s\' a er rua T : TANGO（探戈） -tan gao
U : UNIFORM（尤尼佛姆） V : VICTOR （维克托）
W : WHISKEY（威士忌）-wei si gei X : X _RAY（埃克斯瑞）ai\' ke si rui
Y : YANKEE（严克依）-yan k\'ei Z : ZULU（祖鲁）
 */

