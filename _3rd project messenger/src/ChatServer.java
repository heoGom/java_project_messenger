import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class SharedSessionWithSynchedBlock {
	private List<PrintWriter> list = new ArrayList<>();

	public void addClient(PrintWriter pw) {
		synchronized (list) {
			list.add(pw);
		}
	}

	public boolean removeClient(PrintWriter pw) {
		synchronized (list) {
			return list.remove(pw);
		}
	}

	public void sendMessageToAll(String message) {
		synchronized (list) {
			for (PrintWriter pw : list) {
				pw.println(message);
				pw.flush();
			}
		}
	}
}

class ClientPerThread extends Thread {
	private SharedSessionWithSynchedBlock session;
	private Socket socket;
	private boolean go = true;

	public ClientPerThread(Socket socket, SharedSessionWithSynchedBlock session) {
		this.socket = socket;
		this.session = session;
	}

	@Override
	public void run() {
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			pw = new PrintWriter(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			session.addClient(pw);

			while (go && !isInterrupted()) {
				String fromClient = br.readLine();
				session.sendMessageToAll(fromClient);
				if (fromClient.equals("Bye Bye")) {
					throw new InterruptedException("종료");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {

		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			session.removeClient(pw);
		}
	}
}

public class ChatServer {
	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(12345);) {
			SharedSessionWithSynchedBlock session = new SharedSessionWithSynchedBlock();

			while (true) {
				System.out.println("클라이언트 접속 대기중");
				Socket socket = server.accept();

				Thread thread = new ClientPerThread(socket, session);
				thread.start();
				System.out.println("클라이언트 접속 완료");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}