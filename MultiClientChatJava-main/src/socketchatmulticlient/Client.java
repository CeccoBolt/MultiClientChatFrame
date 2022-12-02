package socketchatmulticlient;

import java.io.*;
import java.net.Socket;
import javax.swing.*;

public class Client {

    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private String user;
    private JTextArea atxMsg;
    private JLabel lblUsers;

    public Client(Socket socket, String user) {
        try {
            this.socket = socket;
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.user = user;
            bw.write(user);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            close(socket, br, bw);
        }
    }

    public void sendMsg(String msgInput) {
        try {

            if (socket.isConnected()) {
                bw.write(user + ": " + msgInput);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            close(socket, br, bw);
        }
    }

    public void listenMsg(JTextArea atxMsgs) {
        this.atxMsg = atxMsgs;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgChat;
                while (socket.isConnected()) {
                    try {
                        msgChat = br.readLine();
                        atxMsg.append(msgChat + "\n");
                    } catch (IOException e) {
                        close(socket, br, bw);
                    }
                }
            }
        }).start();
    }

//    public void listenUsersList(JLabel lblUser) {
//        lblUsers = lblUser;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String readList;
//                while (socket.isConnected()) {
//                    try {
//                        readList = br.readLine();
//                        if (readList.contains("|")) {
//                            String[] list = readList.split("|");
//                            readList = "";
//                            for (int i = 0; i < list.length; i++) {
//                                readList += list[i] + "\n";
//                            }
//                            lblUsers.setText(readList);
//                        }
//                    } catch (IOException e) {
//                        close(socket, br, bw);
//                    }
//                }
//            }
//        }).start();
//    }

//    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Inserisci username: ");
//        String user = scanner.next();
//        Socket socket = new Socket("localhost", 1234);
//        Client client = new Client(socket, user);
//        client.listenMsg();
//        client.sendMsg();
//    }
    public void close(Socket socket, BufferedReader br, BufferedWriter bw) {
        try {
            if (br != null) {
                br.close();
            }
            if (bw != null) {
                bw.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() throws IOException {
        //close(socket, br, bw);
        try {

            if (socket.isConnected()) {
                bw.write("EXIT");
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            close(socket, br, bw);
        }
    }
}
