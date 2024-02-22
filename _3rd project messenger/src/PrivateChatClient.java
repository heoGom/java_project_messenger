import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.swing.SwingUtilities;

public class PrivateChatClient {
	private PrivateChatRoom pr;
	private Socket socket;

	public PrivateChatClient(User user, User another) {

		LocalDateTime currentTime = LocalDateTime.now();

		Socket socket = null;
		PrintWriter in = null;
		pr = new PrivateChatRoom(user, another);
		try {
			socket = new Socket("localhost", 12345);
			System.out.println("[서버와 연결되었습니다]");
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println(user.getId());
			pw.flush();
			System.out.println("클라이언트에서 아이디를 송출합니다");

			ReadThread a = new ReadThread(pr, socket);

			a.start();
			pr.addBtnListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					pw.println(
							user.getId() + "/" + another.getId() + "/" + currentTime + "/" + pr.snedTextArea.getText());
					pw.flush();
					Timestamp time = Timestamp.valueOf(currentTime);
					pr.addChat(pr.snedTextArea.getText(), true, time);
					pr.snedTextArea.setText("");
					
				}
			});

		} catch (IOException e) {
			System.out.println("[서버 접속끊김]");
		}
	}

}

class ReadThread extends Thread {

	Socket socket = null;
	private BufferedReader br;
	private boolean go = true;
	private PrivateChatRoom pr;

	public ReadThread(PrivateChatRoom pr, Socket socket) throws IOException {
		this.socket = socket;
		this.pr = pr;
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	}

	public void setGo(boolean go) {
		this.go = go;
	}

	@Override
	public void run() {
		try {
			while (go && !isInterrupted()) {

				String message = br.readLine();
				String rawtime = br.readLine();
				System.out.println(message);
				System.out.println(rawtime);
				LocalDateTime dateTime = LocalDateTime.parse(rawtime);
				Timestamp time = Timestamp.valueOf(dateTime);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						pr.addChat(message, false, time);
					}
				});

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
