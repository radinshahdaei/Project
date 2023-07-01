package Server;

import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    public ArrayList<Socket> clients = new ArrayList<>();
    public static void main(String[] args) {
        Server server = new Server(8001);
    }
    public Server(int port) {
        System.out.println("Starting server...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("connected: "+socket.toString());
                Runnable runnable = () -> {
                    try {
                        clients.add(socket);
                        runClient(socket);
                    } catch (IOException | JAXBException e) {
                        throw new RuntimeException(e);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void runClient(Socket socket) throws IOException, JAXBException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        Scanner scanner = new Scanner(new InputStreamReader(inputStream));
        String line;

        while (true){
            StringBuilder xmlBuilder = new StringBuilder();
            while (!(line = in.readLine()).contains("<<CLASS>>==")) {
                xmlBuilder.append(line);
            }
            String xmlData = xmlBuilder.toString();
            String function = line.replaceAll("<<CLASS>>==","").replaceAll("\"","");
            if (function.equals("updateChat")) handleChat(xmlData);
            else if (function.equals("nextTurn")) handleNextTurn(xmlData);
        }

    }

    public void handleChat(String xmlData) throws IOException {
        for (Socket socket:clients){
            System.out.println("Chat handled for "+socket.toString());
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println(xmlData);
            out.println("<<CLASS>>==\"updateChat\"");
        }
    }

    public void handleNextTurn(String xmlData){

    }

    private static Class getClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
