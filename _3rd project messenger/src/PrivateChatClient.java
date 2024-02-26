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

public class PrivateChatClient {
	private PrivateChatRoom pr;
	private Socket socket;

	public PrivateChatClient(User user, User another) {

		Socket socket = null;
		pr = new PrivateChatRoom(user, another);
		try {
			socket = new Socket("192.168.0.100", 12345);
			System.out.println("[서버와 연결되었습니다]");
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println(user.getId());
			pw.flush();

			ReadThread a = new ReadThread(pr, socket);

			a.start();
			pr.addBtnListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					LocalDateTime currentTime = LocalDateTime.now();
					pw.println(
							user.getId() + "/" + another.getId() + "/" + currentTime + "/" + pr.sendTextArea.getText());
					pw.flush();
					Timestamp time = Timestamp.valueOf(currentTime);
					pr.addChat(pr.sendTextArea.getText(), true, time);
					pr.sendTextArea.setText("");
					
				}
			});
			pr.sendTextArea.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed (KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER) {
						e.consume();
						pr.sendbtn.doClick();
					}
				}
			});
			 pr.addWindowListener(new java.awt.event.WindowAdapter() {
	                @Override
	                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	                	a.interrupt();
	                	MainPage.openingList.remove(another);
	                	System.out.println("갠톡창닫히나?");
	                	pw.println("Bye Bye");
	                	pw.flush();
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
				if (message == null || rawtime == null) {
					return;
				}
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
