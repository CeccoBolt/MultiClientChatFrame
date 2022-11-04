package socketchatserver;

import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket sSocket;
    private Room[] roomList;

    public Server(ServerSocket sSocket) {
        this.sSocket = sSocket;
        this.roomList = new Room[5];
        for (Room r : this.roomList) {
            r = new Room();
        }
    }

    public void startServer() {

        try {
            while (!sSocket.isClosed()) {
                Socket socket = sSocket.accept();
                System.out.println("Client connesso");
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int nRoom = dis.readInt();
                System.out.println(nRoom);
                this.roomList[nRoom - 1].addClient(socket, (nRoom - 1));
//                ClientThread clientThread = new ClientThread(socket);
//                Thread t = new Thread(clientThread);
//                t.start();
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
