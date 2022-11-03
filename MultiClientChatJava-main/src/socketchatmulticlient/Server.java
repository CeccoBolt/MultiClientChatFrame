package socketchatmulticlient;

import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket sSocket;

    public Server(ServerSocket sSocket) {
        this.sSocket = sSocket;
    }

    public void startServer() {

        try {
            while (!sSocket.isClosed()) {
                Socket socket = sSocket.accept();
                System.out.println("Client connesso");
                ClientThread clientThread = new ClientThread(socket);
                Thread t = new Thread(clientThread);
                t.start();
            }
        } catch (IOException e) {

        }
    }

    public void closeServer() {
        try {
            if (sSocket != null) {
                sSocket.close();
            }
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(3333);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (IOException ex) {
        }
        
    }
}
