package com.aleksandrphilimonov.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    static int counter;
    static final String query = "/stat";
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Сервер запущен на порту 8189. Ожидание подключения клиента...");
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Клиент подключился");

            String msg;
            while (true) {
                msg = in.readUTF();
                if(msg.equals(query)){
                    msg = "Количество сообщений - " + counter;
                }
                System.out.print(msg);
                out.writeUTF(msg);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}