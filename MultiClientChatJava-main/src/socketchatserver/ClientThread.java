package socketchatserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread implements Runnable {

    public static ArrayList<ClientThread> alClientThread = new ArrayList<>();
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private String user;
    private int roomConnected;

    public ClientThread(Socket socket, int roomConnected) {
        try {
            this.socket = socket;
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.user = br.readLine();
            alClientThread.add(this);
            this.roomConnected = roomConnected;
            msgBroadcast(user + " connesso alla stanza " + (roomConnected + 1));
        } catch (IOException e) {
            close(socket, br, bw);
        }
    }

    @Override
    public void run() {
        String msgClient;
        while (socket.isConnected()) {
            try {
                msgClient = br.readLine();
                if (msgClient.equals("EXIT")) {
                    close(socket, br, bw);
                } else {
                    msgBroadcast(msgClient);
                }

            } catch (IOException e) {
                close(socket, br, bw);
                break;
            }
        }
    }

    public void msgBroadcast(String msgSend) {
        for (ClientThread clientThread : alClientThread) {
            try {
                if (!clientThread.user.equals(user) && clientThread.roomConnected == roomConnected) {
                    clientThread.bw.write(msgSend);
                    clientThread.bw.newLine();
                    clientThread.bw.flush();
                }
            } catch (IOException e) {
                close(socket, br, bw);
            }
        }
    }

    public void exitClient() {
        alClientThread.remove(this);
        msgBroadcast(user + " si è disconnesso!");
    }

    public void close(Socket s, BufferedReader bur, BufferedWriter buw) {
        exitClient();
        try {
            if (bur != null) {
                bur.close();
            }
            if (buw != null) {
                buw.close();
            }
            if (s != null) {
                s.close();
            }
        } catch (IOException e) {

        }
    }
}
