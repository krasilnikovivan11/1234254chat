package server;

import server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static database.ConnectionDB.searchClient;

public class ServerWrapper {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    String msg;

    public ServerWrapper(Server server, Socket sock) {
        this.socket = sock;
        try {
            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (true) {
                try {
                    msg = in.readUTF();
                    if(msg.startsWith("/auth")){
                        String[] elements = msg.split(" ");
                        if(searchClient(elements[1],elements[2])){
                            server.sendBroadcast("new Connection");
                        }
                        else {
                            socket.close();
                        }
                    }
                    else
                    server.sendBroadcast(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
