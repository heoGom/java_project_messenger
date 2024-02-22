import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class ClientsList {
	private List<ClientPerThread> list = new ArrayList<>();

	public void addClient(ClientPerThread CT) {
		synchronized (list) {
			list.add(CT);
		}
	}

	public boolean removeClient(ClientPerThread CT) {
		synchronized (list) {
			return list.remove(CT);
		}
	}

	public void sendMessageToAll(String message) {
		synchronized (list) {
			for (ClientPerThread ct : list) {
				ct.getPw().println(message);
				ct.getPw().flush();
			}
		}
	}

	public void sendPrivateMessage(String receiver_id, String time, String message) {
		synchronized (list) {
			for (ClientPerThread ct : list) {
				if (ct.getTId().equals(receiver_id)) {
					ct.getPw().println(message);
					ct.getPw().println(time);
					ct.getPw().flush();

				}
			}
		}

	}
}

class ClientPerThread extends Thread {
	private ClientsList session;
	private Socket socket;
	private boolean go = true;
	private String tid;
	private PrintWriter pw;
	private BufferedReader br;
	
	public ClientPerThread(Socket socket, ClientsList session) {
		this.socket = socket;
		this.session = session;
	}


	@Override
	public void run() {
		pw = null;
		br = null;
		try {
			System.out.println("1");
			pw = new PrintWriter(socket.getOutputStream());
			System.out.println("2");
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("3");

			tid = br.readLine();
			System.out.println("아이디를 입력함");
			session.addClient(this);

			while (go && !isInterrupted()) {
				String fromClient = br.readLine();
				String[] a = splitString(fromClient);

//				session.sendMessageToAll(fromClient+"이건전체메세지");
				session.sendPrivateMessage(a[1], a[2], a[3]);
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
			session.removeClient(this);
		}
	}

	private String[] splitString(String input) {
		int firstIndex = input.indexOf('/');
		int secondIndex = input.indexOf('/', firstIndex + 1);
		int thirdIndex = input.indexOf('/', secondIndex+1);

		if (firstIndex != -1 && secondIndex != -1&&thirdIndex !=-1) {
			return new String[] { input.substring(0, firstIndex), input.substring(firstIndex + 1, secondIndex),
					input.substring(secondIndex + 1,thirdIndex),input.substring(thirdIndex+1) };
		} else {
			return new String[] { input };
		}
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	public String getTId() {
		return tid;
	}

	public void setId(String id) {
		this.tid = id;
	}

}

public class ChatServer {
	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(12345);) {
			ClientsList session = new ClientsList();
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