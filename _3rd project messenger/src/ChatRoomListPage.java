import javax.swing.JFrame;
import javax.swing.JLabel;

public class ChatRoomListPage extends JFrame{
	private JLabel testlbl;

	public ChatRoomListPage() {
		extracted();
		showGUI();
	}

	private void extracted() {
		getContentPane().setLayout(null);
		
		testlbl = new JLabel("가입자 전체 채팅방");
		testlbl.setBounds(156, 181, 57, 15);
		getContentPane().add(testlbl);
		testlbl.setEnabled(false);
		
		
	}
	private void showGUI() {
		setSize(462, 491);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		new ChatRoomListPage();
	}
}
