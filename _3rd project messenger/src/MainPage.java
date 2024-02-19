import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MainPage extends JFrame{
	private JLabel nick_lbl;
	private JLabel picture_lbl;
	User user;
	
	public MainPage(User user) {
		this.user = user;
		extracted();
		changelbl();
		showGUI();
	
	}

	private void showGUI() {
		setSize(469, 574);
		setVisible(true);
		setLocationRelativeTo(null);

	}
	private void changelbl() {
		nick_lbl.setText(user.nick);
		picture_lbl.setIcon(user.image);
	}
	
	private void extracted() {
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("가입자 목록");
		btnNewButton.setBounds(168, 99, 97, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("채팅방 목록");
		btnNewButton_1.setBounds(168, 184, 97, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("투표 하기");
		btnNewButton_2.setBounds(168, 269, 97, 23);
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("미니 게임");
		btnNewButton_3.setBounds(168, 342, 97, 23);
		getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("마이프로필");
		btnNewButton_4.setBounds(326, 10, 97, 23);
		getContentPane().add(btnNewButton_4);
		
		picture_lbl = new JLabel("사진들어갈");
		picture_lbl.setBounds(12, 14, 81, 15);
		getContentPane().add(picture_lbl);
		
		nick_lbl = new JLabel("닉네임 들어갈");
		nick_lbl.setBounds(105, 14, 88, 15);
		getContentPane().add(nick_lbl);
		
		JButton btnNewButton_5 = new JButton("로그아웃 필요 하겠지?");
		btnNewButton_5.setBounds(27, 469, 188, 23);
		getContentPane().add(btnNewButton_5);
	}

}
