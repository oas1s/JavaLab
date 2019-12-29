package main.java.servers;

import main.java.utills.MD5Utill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyServer {
    // список клиентов
    private List<ClientHandler> clients;

    public MyServer() {
        // Список для работы с многопоточностью
        clients = new CopyOnWriteArrayList<>();
    }

    public void start(int port) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        // запускаем бесконечный цикл
        while (true) {
            try {
                // запускаем обработчик сообщений для каждого подключаемого клиента
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class ClientHandler extends Thread {
        // связь с одним клиентом
        private Socket clientSocket;
        private BufferedReader in;
        private int id;


        ClientHandler(Socket socket) {
            this.clientSocket = socket;
            // добавляем текущее подключение в список
            clients.add(this);
            System.out.println("New client");
        }

        public void run() {
            try {
                // получем входной поток для конкретного клиента
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        // бегаем по всем клиентам и обовещаем их о событии
                        for (ClientHandler client : clients) {
                            PrintWriter out = new PrintWriter(client.clientSocket.getOutputStream(), true);
                            out.println("bye");
                        }
                        break;
                    }
                    else if(inputLine.startsWith("reg")) {
                        ConnectionToDB connectionToDB = new ConnectionToDB();
                        Connection connection = connectionToDB.getInstance();
                        String sql = "INSERT INTO javalab.users(`name`,`password`) VALUES(?,?)";
                        String sql2 = "SELECT * FROM javalab.users WHERE (name = ?)";
                        PreparedStatement stmt = connection.prepareStatement(sql);
                        PreparedStatement stmt2 = connection.prepareStatement(sql2);
                        String password = inputLine.split(" ")[2];
                        MD5Utill md5Utill = new MD5Utill();
                        password = md5Utill.md5Custom(password);
                        stmt.setString(1 , inputLine.split(" ")[1]);
                        stmt.setString(2 , password);
                        stmt2.setString(1 , inputLine.split(" ")[1]);
                        stmt.executeUpdate();
                        ResultSet resultSet = stmt2.executeQuery();
                        if (resultSet.next()) {
                            this.id = resultSet.getInt("id");
                        }
                    }
                    else {
                        ConnectionToDB connectionToDB = new ConnectionToDB();
                        Connection connection = connectionToDB.getInstance();
                        String sql = "INSERT INTO javalab.messages(`text`,`id_from`,`date`) VALUES(?, ?, ?)";
                        PreparedStatement stmt = connection.prepareStatement(sql);
                        Date date = new Date(new java.util.Date().getTime());
                        stmt.setString(1 , inputLine);
                        stmt.setInt(2 , id);
                        stmt.setDate(3, date);
                        stmt.executeUpdate();
                        for (ClientHandler client : clients) {
                            PrintWriter out = new PrintWriter(client.clientSocket.getOutputStream(), true);
                            out.println(inputLine);
                        }
                    }
                }
                in.close();
                clientSocket.close();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
