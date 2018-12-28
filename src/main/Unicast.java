package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.concurrent.ArrayBlockingQueue;

public class Unicast {

    public DatagramSocket sendSocket;
    public DatagramSocket receiveSocket; // listening
    public int receivePort; // listening port
    public int size;
    public ArrayBlockingQueue<DatagramPacket> queue;

    /**
     *
     * @param receivePort the port where you receive others packets
     */
    public Unicast(int receivePort){
        this.receivePort = receivePort;
        size = 4096;
        queue = new ArrayBlockingQueue<>(2 * size);
    }

    /**
     * start to listening
     * @throws IOException
     */
    public void execute() throws IOException {
        System.out.println("\n----Unicast Service Execute at " + Calendar.getInstance().getTime() + "----\n");
        sendSocket = new DatagramSocket();
        receiveSocket = new DatagramSocket(receivePort);

        receive();

    }

    /**
     * send packed message
     * @param message packed message to be sent
     * @param address destination address
     */
    public void send(byte[] message, InetAddress address) {

        new Thread(()->{
            try {

                System.out.println("\n------------------------------------------------------------------------------------------------");
                System.out.println("Sending Unicast Message: " + new String(message, StandardCharsets.UTF_8));
                System.out.println("Uincast Destination port: " + receivePort);
                System.out.println("Uincast Destination Address: " + address.getHostAddress());
                System.out.println("------------------------------------------------------------------------------------------------\n");

                DatagramPacket packet = new DatagramPacket(message, message.length, address, receivePort);
                sendSocket.send(packet);

            } catch (IOException io) {
                io.printStackTrace();
            }
        }).start();
    }

    /**
     * receive message
     */
    private void receive() {

        new Thread(()->{
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(new byte[size], size);
                    receiveSocket.receive(packet);
                    System.out.println("\n------------------------------------------------------------------------------------------------");
                    System.out.println("Uincast packet received: " + packet.getAddress());
                    System.out.println("Packet source address: " + packet.getAddress());
                    System.out.println("Packet spurce port: " + packet.getPort());
                    System.out.println("-----------------------------------------------------------------------------------------------\n");

                    queue.add(packet);
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }).start();
    }


}
