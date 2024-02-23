
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.swing.SwingUtilities;

public class PublicChatClient {
	private PublicChatRoom pr;
	private Socket socket;

	public PublicChatClient(User user) {

		LocalDateTime currentTime = LocalDateTime.now();

		Socket socket = null;
		PrintWriter in = null;
		pr = new PublicChatRoom(user);
		try {
			socket = new Socket("localhost", 12345);
			System.out.println("[서버와 연결되었습니다]");
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println(user.getId());
			pw.flush();
			System.out.println("클라이언트에서 아이디를 송출합니다");

			PublicReadThread a = new PublicReadThread(pr, socket);

			a.start();
			pr.addBtnListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					pw.println(
							user.getId() + "/" + "AllChat" + "/" + currentTime + "/" + pr.sendTextArea.getText());
					pw.flush();
					Timestamp time = Timestamp.valueOf(currentTime);
					pr.addChat(pr.sendTextArea.getText(), true, time);
					pr.sendTextArea.setText("");

				}
			});
			pr.sendTextArea.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						e.consume();
						pr.sendbtn.doClick();
					}
				}
			});
			pr.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                	ChatRoomListPage.openingPublic=false;
                }
            });

		} catch (IOException e) {
			System.out.println("[서버 접속끊김]");
		}
	}

}

class PublicReadThread extends Thread {

	Socket socket = null;
	private BufferedReader br;
	private boolean go = true;
	private PublicChatRoom pr;

	public PublicReadThread(PublicChatRoom pr, Socket socket) throws IOException {
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
				String sender_id = br.readLine();
				String message = br.readLine();
				String rawtime = br.readLine();
				LocalDateTime dateTime = LocalDateTime.parse(rawtime);
				Timestamp time = Timestamp.valueOf(dateTime);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						pr.addChatPr(sender_id, message, time);
					}
				});

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
