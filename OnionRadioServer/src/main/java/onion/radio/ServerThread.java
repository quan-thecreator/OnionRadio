package onion.radio;

import javax.sound.sampled.Clip;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    private ServerSocket socketServer;
    public ServerThread(int _port) throws IOException {
        socketServer = new ServerSocket(_port);
    }
    @Override
    public void run() {
        while(true){
            Socket acceptedSocket = null;
            try {
                acceptedSocket = socketServer.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Thread clientThread;
            try {
                clientThread = new ConnecterThread(acceptedSocket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clientThread.start();
        }
    }
}
