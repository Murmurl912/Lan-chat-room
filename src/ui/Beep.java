package ui;

import javax.sound.sampled.*;
import java.io.File;

public class Beep {

    public static File SEND_SUCCESS = new File("res/button-47.wav");
    public static File SEND_FAILED = new File("res/failed.wav");
    public static File RECEIVE_GROUP = new File("res/receiveGroup.wav");
    public static File RECEIVE_PRIVATE = new File("res/button-46.wav");

    public static long latestBeepTime;

    public static void play(File file) {

        if(System.currentTimeMillis() - latestBeepTime < 200) {
            latestBeepTime = System.currentTimeMillis();
            return;
        }
        latestBeepTime = System.currentTimeMillis();

        new Thread(()-> {
            try {

                AudioInputStream stream;
                AudioFormat format;
                DataLine.Info info;
                Clip clip;

                stream = AudioSystem.getAudioInputStream(file);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);
                clip.start();
                // 等待播放完成
                Thread.sleep(500);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }).start();


    }
}


