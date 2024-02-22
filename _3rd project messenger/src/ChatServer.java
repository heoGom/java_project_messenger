import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ReceiveThread extends Thread {

	static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());

	Socket socket = null;
	BufferedReader in = null;
	PrintWriter out = null;

	public ReceiveThread(Socket socket) {
		this.socket = socket;
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			list.add(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {
			System.out.println("[새연결생성]");

			while (in != null) {
				String inputMsg = in.readLine();
				if ("quit".equals(inputMsg))
					break;
				sendAll(">>" + inputMsg);
			}
		} catch (IOException e) {
			System.out.println("[ 접속끊김]");
		} finally {
			sendAll("나가셨습니다");
			list.remove(out);
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[연결종료]");

	}

	private void sendAll(String s) {
		for (PrintWriter out : list) {
			out.println(s);
			out.flush();
		}
	}
}

public class ChatServer {

	private ServerSocket serverSocket;
	private Set<ClientHandler> clients;

	public ChatServer() {
	}

	public void start(int port) {
		serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				System.out.println("[클라이언트 연결대기중]");
				socket = serverSocket.accept();

				// client가 접속할때마다 새로운 스레드 생성
				ReceiveThread receiveThread = new ReceiveThread(socket);
				receiveThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
					System.out.println("[서버종료]");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("[서버소켓통신에러]");
				}
			}
		}
	}
	public static void main(String[] args) {
		ChatServer chat = new ChatServer();
		chat.start(12345);
	}
}
