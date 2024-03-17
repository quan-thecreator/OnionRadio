package onion.radio;

import onion.radio.globals.Signals;
import onion.radio.globals.Station;

import javax.sound.sampled.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            File stationsTxt = new File(args[0]);
            if(!stationsTxt.exists()) throw new RuntimeException();
            Scanner readStations = new Scanner(new FileInputStream(stationsTxt));
            ArrayList<InetSocketAddress> stations = new ArrayList<>();
            while(readStations.hasNext()){

                //add proxy funtionality later
                Socket testConnection = new Socket();
                String[] line = readStations.nextLine().split(":");
                InetSocketAddress address = new InetSocketAddress(line[0], Integer.parseInt(line[1]));

                testConnection.connect(address);
                PrintWriter writer = new PrintWriter(testConnection.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));
                writer.println(Signals.PING_SIGNAL);writer.flush();
                try {
                    if (Integer.parseInt(reader.readLine()) == Signals.UP_SIGNAL){
                        System.out.println("station found!");
                        stations.add(address);
                    }
                }catch (Exception e){}
            }
            listenFirst(stations);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Ye done goofed up lad.");
        }
    }
    //listen to first station available, go to section 2.2 https://www.baeldung.com/java-play-sound
    private static void listenFirst(ArrayList<InetSocketAddress> lsit) throws IOException, UnsupportedAudioFileException, LineUnavailableException, ClassNotFoundException {
        InetSocketAddress firstStation = lsit.get(0);
        Socket connection = new Socket();
        connection.connect(firstStation);
        PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
        printWriter.println(Signals.STREAM_SIGNAL);printWriter.flush();
        ObjectInputStream oobIn = new ObjectInputStream(connection.getInputStream());
        Station thisStation = (Station) oobIn.readObject();
        System.out.println(thisStation.name()); System.out.println(thisStation.description());
        InputStream bufferedIn = new BufferedInputStream(connection.getInputStream());
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        Mixer.Info info = mixerInfo[0];

        System.out.println(String.format("Name [%s] \n Description [%s]\n\n", info.getName(), info.getDescription()));
        System.out.println(info.getDescription());
        Clip clip = AudioSystem.getClip(info);
        clip.open(audioInputStream);
        clip.start();

    }
}
/*
AudioFormat audioFormat = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();
        final int BUFFER_SIZE = 4096;
        byte[] bufferBytes = new byte[BUFFER_SIZE];
        int readBytes = -1;
        while ((readBytes = audioInputStream.read(bufferBytes)) != -1) {
            sourceDataLine.write(bufferBytes, 0, readBytes);
        }
        sourceDataLine.drain();
        sourceDataLine.close();
        audioInputStream.close();
        connection.close();
 */