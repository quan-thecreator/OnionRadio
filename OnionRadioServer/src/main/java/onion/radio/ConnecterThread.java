package onion.radio;
import onion.radio.globals.Signals;
import org.apache.commons.io.IOUtils;

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
            int signalCode = Integer.parseInt(reader.readLine());
            switch (signalCode){
                case Signals.PING_SIGNAL:
                    writer.println(Signals.UP_SIGNAL);
                    writer.flush();
                    connection.close();
                    return;
                case Signals.STREAM_SIGNAL:
                    ObjectOutputStream oobOut = new ObjectOutputStream(outputStream);
                    oobOut.writeObject(Main.stationRecord);
                    IOUtils.copyLarge(Main.audioOutBytes, outputStream);
                    connection.close();
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
