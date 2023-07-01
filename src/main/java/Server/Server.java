package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        Server server = new Server(8001);
    }
    public Server(int port) {
        System.out.println("Starting server...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("okay");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
