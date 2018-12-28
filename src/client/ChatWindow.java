package client;

import javax.swing.*;
import java.awt.*;

public class ChatWindow extends JFrame {
    private JPanel top;
    private JPanel bott;
    private JTextArea chathistory;
    private JButton send, log, reg;
    private JScrollPane jScrollPane;
    private JTextField login, pass, message;

    public ChatWindow() {
        Client client = new Client(this);
        setTitle("client.ChatWindow");
        setSize(400, 400);
        setVisible(true);

        top = new JPanel();
        login = new JTextField();
        login.setPreferredSize(new Dimension(100, 30));
        top.add(login, BorderLayout.WEST);
        pass = new JTextField();
        pass.setPreferredSize(new Dimension(100, 30));
        top.add(pass, BorderLayout.CENTER);
        log = new JButton("Login");
        log.setSize(60, 30);
        log.addActionListener(e->{
           String log = login.getText();
           String password = pass.getText();
           client.sendMsg("/auth " + log + " " + password);
        });
        top.add(log, BorderLayout.EAST);

        bott = new JPanel();
        message = new JTextField();
        message.setPreferredSize(new Dimension(200, 30));
        bott.add(message, BorderLayout.WEST);
        send = new JButton("Send");
        send.setSize(60, 30);
        send.addActionListener(e -> {
            String msg = message.getText();
            client.sendMsg(msg);
            message.setText("");
        });
        bott.add(send, BorderLayout.EAST);

        chathistory = new JTextArea();
        chathistory.setEditable(false);
        chathistory.setLineWrap(true);
        jScrollPane = new JScrollPane(chathistory);

        add(top, BorderLayout.NORTH);
        add(bott, BorderLayout.SOUTH);
        add(jScrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChatWindow();
    }

    public void showMsg(String msg) {
        chathistory.append(msg + "\n");
    }
}
