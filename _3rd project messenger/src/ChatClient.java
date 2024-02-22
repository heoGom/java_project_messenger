import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Scanner;

public class ChatClient {

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	User user;
	PrivateChatRoom privateChatRoom;

	public ChatClient(int port) {
		Socket socket = null;
		BufferedReader in = null;
		 try {
	            socket = new Socket("localhost", port);
	            System.out.println("[서버와 연결되었습니다]");

	            Thread sendThread = new SendThread(socket);
	            sendThread.start();

	            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	           
	                String inputMsg = in.readLine();
	             
	                System.out.println("From:" + inputMsg);
	                // 수정: PrivateChatRoom에 수신된 메시지 전달
	                privateChatRoom.addChat(inputMsg, false, new Timestamp(System.currentTimeMillis()));
	            
	        } catch (IOException e) {
	            System.out.println("[서버 접속끊김]");
	        } finally {
	            try {
	                socket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        System.out.println("[서버 연결종료]");
	    }
}

class SendThread extends Thread {
	Socket socket = null;
	String name;
	User user;
	PrivateChatRoom privateChatRoom;

	public SendThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			PrintStream out = new PrintStream(socket.getOutputStream());

			privateChatRoom.sendbtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String outputMsg = user.getNick() + ":" + privateChatRoom.textArea.getText();
					out.println(outputMsg);
					out.flush();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
