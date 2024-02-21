import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public ChatClient(String serverAddress, int port) {
		try {
			socket = new Socket(serverAddress, port);
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());

			new ReceiveThread(ois).start();
			System.out.println("연결완료");

			while (true) {
				
				oos.writeObject("뭔갈적자");
				oos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class ReceiveThread extends Thread {

	private ObjectInputStream ois;

	public ReceiveThread(ObjectInputStream ois) {
		this.ois = ois;
	}

	@Override
	public void run() {
		try {
			while (true) {
				String receivedMessage = (String) ois.readObject();
				System.out.println("Received: " + receivedMessage);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}