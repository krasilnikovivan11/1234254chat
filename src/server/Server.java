package server;

import database.ConnectionDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class Server {
    private Vector<ServerWrapper> clients;
    private ConnectionDB connectionDB;

    public Server() {
        try {
            connectionDB = new ConnectionDB();
            ServerSocket serverSocket = new ServerSocket(8189);
            clients = new Vector<>();

            new Thread(() -> {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        ServerWrapper serverWrapper = new ServerWrapper(this, socket);
                        clients.add(serverWrapper);
                        System.out.println("Connected");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendBroadcast(String msg) {
        for (ServerWrapper client : clients) {
            client.sendMsg(msg);
        }
    }
}
