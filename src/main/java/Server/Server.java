package Server;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.*;
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
                System.out.println("connected");


                InputStream inputStream = socket.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder xmlBuilder = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    xmlBuilder.append(line);
                }
                String xmlData = xmlBuilder.toString();
                JAXBContext context = JAXBContext.newInstance(Person.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                StringReader reader = new StringReader(xmlData);
                Person person = (Person) unmarshaller.unmarshal(reader);

                System.out.println(person.getName());
            }
        } catch (IOException  e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }
}
