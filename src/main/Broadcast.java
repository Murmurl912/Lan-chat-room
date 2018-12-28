package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.concurrent.ArrayBlockingQueue;

public class Broadcast {

    public MulticastSocket broadcastSocket;
    public MulticastSocket receiveSocket; // listening
    public int receivePort;
    public InetAddress groupBroadcastAddress;
    public int size;

    public ArrayBlockingQueue<DatagramPacket> queue;

    /**
     *
     * @param groupBroadcastAddress the group broadcast address
     * @param receivePort the port where others send messages to you
     */
    public Broadcast(InetAddress groupBroadcastAddress, int receivePort) {
        this.groupBroadcastAddress = groupBroadcastAddress;
        this.receivePort = receivePort;
        size = 4096;
        queue = new ArrayBlockingQueue<>(2 * size);
    }

    /**
     * start listening
     * @throws IOException
     */
    public void execute() throws IOException {

        System.out.println("\n----Broadcast Services Execute at " + Calendar.getInstance().getTime() + "----\n");
        broadcastSocket = new MulticastSocket();
        broadcastSocket.joinGroup(groupBroadcastAddress);
        // bind receive socket to a group address
        // in order to receive broadcast messages
        receiveSocket = new MulticastSocket(receivePort);
        receiveSocket.joinGroup(groupBroadcastAddress);
        receive();
    }

    /**
     * broadcast packed message
     * @param message message to be broadcast
     */
    public void broadcast(byte[] message) {

        new Thread(()->{

            try {
                System.out.println("\n------------------------------------------------------------------------------------------------");
                System.out.println("Sending Broadcast Message: " + new String(message, StandardCharsets.UTF_8));
                System.out.println("Broadcast Destination port: " + receivePort);
                System.out.println("Broadcast Destination Address: " + groupBroadcastAddress.getHostAddress());
                System.out.println("------------------------------------------------------------------------------------------------\n");
                broadcastSocket.send(new DatagramPacket(message, message.length, groupBroadcastAddress, receivePort));
            } catch (IOException io) {
                io.printStackTrace();
            }

        }).start();

    }

    private void receive() {
        new Thread(()->{
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(new byte[size], size);
                    receiveSocket.receive(packet);
                    System.out.println("\n------------------------------------------------------------------------------------------------");
                    System.out.println("Broadcast packet received: " + packet.getAddress());
                    System.out.println("Packet source address: " + packet.getAddress());
                    System.out.println("Packet source port: " + packet.getPort());
                    System.out.println("------------------------------------------------------------------------------------------------\n");
                    queue.add(packet);
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }).start();
    }

}
