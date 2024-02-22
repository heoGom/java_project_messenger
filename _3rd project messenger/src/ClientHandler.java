import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private Connection dbConnection;
    private ChatServer chatServer;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientHandler(Socket clientSocket, Connection dbConnection, ChatServer chatServer) {
        this.clientSocket = clientSocket;
        this.dbConnection = dbConnection;
        this.chatServer = chatServer;

        try {
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void run() {
//        try {
//            while (true) {
//                String message = (String) ois.readObject();
//                saveMessageToDatabase(message);
//                chatServer.broadcastMessage(message, this);
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            closeConnection();
//        }
    }

    public void sendMessage(String message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMessageToDatabase(String message) {
        try {
            String sql = "INSERT INTO chat_messages (message) VALUES (?)";
            try (PreparedStatement stmt = dbConnection.prepareStatement(sql)) {
                stmt.setString(1, message);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            ois.close();
            oos.close();
            clientSocket.close();
            chatServer.removeClient(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}