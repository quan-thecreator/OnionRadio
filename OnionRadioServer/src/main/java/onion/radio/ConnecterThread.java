package onion.radio;
import onion.radio.globals.Signals;
import org.apache.commons.io.IOUtils;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class ConnecterThread extends Thread{
    private Socket connection;
    private OutputStream outputStream;
    private PrintWriter writer;
    private BufferedReader reader;
    public ConnecterThread(Socket socket) throws IOException {
        connection = socket;
        outputStream=socket.getOutputStream();
        writer=new PrintWriter(socket.getOutputStream());
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String stringread = reader.readLine();
            System.out.println(stringread);
            int signalCode = Integer.parseInt(stringread);
            switch (signalCode){
                case Signals.PING_SIGNAL:
                    writer.println(Signals.UP_SIGNAL);
                    writer.flush();
                    connection.close();
                    return;
                case Signals.STREAM_SIGNAL:
                    ObjectOutputStream oobOut = new ObjectOutputStream(outputStream);
                    oobOut.writeObject(Main.stationRecord);
                    System.out.println("Copying");
                    AudioInputStream audioInputStream = (AudioInputStream) Main.audioOutBytes;
                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputStream);
                    connection.close();
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
