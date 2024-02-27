import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

	public void sendMessageToAll(String sender_id, String time, String message) {
		synchronized (list) {
			for (ClientPerThread ct : list) {
				if (ct.getTId().equals(sender_id)) {

				} else {
					ct.getPw().println(sender_id);
					ct.getPw().println(message);
					ct.getPw().println(time);
					ct.getPw().flush();
				}
			}
		}
	}
	public void sendFileToAll(String sender_id, String time, String file_name) {
		synchronized (list) {
			for (ClientPerThread ct : list) {
				if (ct.getTId().equals(sender_id)) {

				} else {
					ct.getPw().println("파일 전송");
					ct.getPw().println(sender_id);
					ct.getPw().println(file_name);
					ct.getPw().println(time);
					ct.getPw().flush();
				}
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
			pw = new PrintWriter(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			tid = br.readLine();
			session.addClient(this);
			System.out.println(tid+"접속");

			while (go && !isInterrupted()) {
				String fromClient = br.readLine();
				System.out.println(fromClient);
				if (fromClient.equals("Bye Bye")) {
					System.out.println(tid + "연결종료");
					throw new InterruptedException("종료");
				} else {
					if (fromClient.equals("파일 전송")) {
						String sender_id = br.readLine();
						String time = br.readLine();
						String file_name = br.readLine();
						String file = br.readLine();
						String sqlp = "insert into public_chatlist (sender_id, text, text_time,file_name,file) values(?,null,?,?,?);";
						try (Connection conn = MySqlConnectionProvider.getConnection();
								PreparedStatement stmt = conn.prepareStatement(sqlp)) {
							stmt.setString(1, sender_id);
							stmt.setString(2, time);
							stmt.setString(3, file_name);
							stmt.setString(4, file);
							stmt.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						session.sendFileToAll(sender_id, time, file_name);

					} else {
						// 개인 메세지의 경우
						// 유저아이디//받는사람아이디//시간// 내용순으로 들어오고 배열로 자름
						// 단체 메세지의 경우
						// 유저아이디// ALLChat//시간//내용
						String[] a = splitString(fromClient);
						String sql = "insert into private_chatlist (sender_id, receiver_id, text ,text_time, file) values(?, ?, ?, ?,null);";
						String sqlp = "insert into public_chatlist (sender_id, text, text_time,file) values(?,?,?,null);";
						LocalDateTime dateTime = LocalDateTime.parse(a[2]);
						Timestamp time = Timestamp.valueOf(dateTime);
						if (a[1].equals("AllChat")) {
							try (Connection conn = MySqlConnectionProvider.getConnection();
									PreparedStatement stmt = conn.prepareStatement(sqlp)) {
								stmt.setString(1, a[0]);
								stmt.setString(2, a[3]);
								stmt.setTimestamp(3, time);
								stmt.executeUpdate();

							} catch (SQLException e) {
								e.printStackTrace();
							}

						} else {
							try (Connection conn = MySqlConnectionProvider.getConnection();
									PreparedStatement stmt = conn.prepareStatement(sql)) {
								stmt.setString(1, a[0]);
								stmt.setString(2, a[1]);
								stmt.setString(3, a[3]);
								stmt.setTimestamp(4, time);
								stmt.executeUpdate();

							} catch (SQLException e) {
								e.printStackTrace();
							}

						}

//            session.sendMessageToAll(fromClient+"이건전체메세지");
						if (a[1].equals("AllChat")) {
							session.sendMessageToAll(a[0], a[2], a[3]);
							if (fromClient.equals("Bye Bye")) {
								throw new InterruptedException("종료");
							}

						} else {
							session.sendPrivateMessage(a[1], a[2], a[3]);
							if (fromClient.equals("Bye Bye")) {
								throw new InterruptedException("종료");
							}
						}
					}
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
		int thirdIndex = input.indexOf('/', secondIndex + 1);

		if (firstIndex != -1 && secondIndex != -1 && thirdIndex != -1) {
			return new String[] { input.substring(0, firstIndex), input.substring(firstIndex + 1, secondIndex),
					input.substring(secondIndex + 1, thirdIndex), input.substring(thirdIndex + 1) };
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