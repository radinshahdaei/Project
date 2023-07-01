package Client;

import Controller.Controller;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }
    public Client() throws IOException {
        System.out.println("Starting client...");
        Controller.setClient(this);
        this.socket = new Socket("localhost",8000);
    }
}
