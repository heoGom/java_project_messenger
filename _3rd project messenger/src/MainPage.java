import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MainPage extends JFrame{
	public MainPage() {
		extracted();

	
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
		
		JLabel lblNewLabel = new JLabel("사진들어갈");
		lblNewLabel.setBounds(12, 14, 81, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("닉네임 들어갈");
		lblNewLabel_1.setBounds(105, 14, 88, 15);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton_5 = new JButton("로그아웃 필요 하겠지?");
		btnNewButton_5.setBounds(27, 469, 188, 23);
		getContentPane().add(btnNewButton_5);
	}

}
