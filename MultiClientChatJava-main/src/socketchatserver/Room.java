package socketchatserver;

import java.net.*;

public class Room {

    public Room() {

    }

    public void addClient(Socket s, int nRoom) {
        ClientThread clientThread = new ClientThread(s, nRoom);
        Thread t = new Thread(clientThread);
        t.start();
    }
    
}
