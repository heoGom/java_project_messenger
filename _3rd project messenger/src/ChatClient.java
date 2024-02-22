//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//import javax.swing.SwingUtilities;
//
//public class ChatClient {
//
//	private Socket socket;
//
//
//	public ChatClient(int port) {
//		Socket socket = null;
//		BufferedReader in = null;
//		try {
//			socket = new Socket("localhost", port);
//			System.out.println("[서버와 연결되었습니다]");
//			PrintWriter pw = new PrintWriter(socket.getOutputStream());
//
//			ReadThread a = new ReadThread(socket);
//
//
////			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////			while (in != null) {
////				String inputMsg = in.readLine();
////				if (("님이 나가셨습니다").equals(inputMsg))
////					break;
////				System.out.println("From:" + inputMsg);
////			}
//		} catch (IOException e) {
//			System.out.println("[서버 접속끊김]");
//		} finally {
//			try {
//				socket.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println("[서버 연결종료]");
//	}
//}
//
