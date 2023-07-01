package Client;

import Controller.Controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket socket;
    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }
    public Client() throws IOException {
        System.out.println("Starting client...");
        Controller.setClient(this);
        this.socket = new Socket("localhost",8001);

        OutputStream outputStream = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println(SaveAsXML.getWriter());
    }
}
