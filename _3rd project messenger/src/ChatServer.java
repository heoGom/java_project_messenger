import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {

    private ServerSocket serverSocket;
    private Connection dbConnection;
    private Set<ClientHandler> clients;

    public ChatServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jae", "root", "root");;
            clients = new HashSet<>();

            while (true) {
            	System.out.println("연결 대기중");
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, dbConnection, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
                System.out.println("연결완료");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public static void main(String[] args) {
        int port = 12345;
        new ChatServer(port);
    }
}