import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingUtilities;

public class PrivateChatClient {
	private PrivateChatRoom pr;
	private Socket socket;

	public PrivateChatClient(User nser, User another) {
		Socket socket = null;
		PrintWriter in = null;
		pr = new PrivateChatRoom(nser, another);
		try {
			socket = new Socket("localhost", 12345);
			System.out.println("[서버와 연결되었습니다]");
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			ReadThread a = new ReadThread(pr,socket);

			a.start();
			pr.addBtnListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					pw.println("전송!!");
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
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						System.out.println(message);
					}
				});

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
