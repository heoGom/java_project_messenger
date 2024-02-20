import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MainPage extends JFrame {
	private JLabel nick_lbl;
	private JLabel picture_lbl;
	User user;
	private List<User> userList;
	private JButton userListbtn;
	private JButton chatRoomListbtn;
	private JButton votebtn;
	private JButton minigame;
	private JButton myprofilebtn;
	private JButton logoutbtn;

	public MainPage(User user, List<User> userList) {
		this.user = user;
		this.userList = userList;
		extracted();
		changelbl();
		listenerAll();
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

	private void listenerAll() {

		chatRoomListbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChatRoomListPage chatRoomListPage = new ChatRoomListPage();
				chatRoomListPage.setVisible(true);
			}
		});

		userListbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				user.readAllUser();
				UserList userList = new UserList(user, user.list);
				userList.setVisible(true);
			}
		});
		
		myprofilebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	

	private void extracted() {
		getContentPane().setLayout(null);

		userListbtn = new JButton("가입자 목록");
		userListbtn.setBounds(143, 103, 113, 23);
		getContentPane().add(userListbtn);

		chatRoomListbtn = new JButton("채팅방 목록");
		chatRoomListbtn.setBounds(143, 183, 113, 23);
		getContentPane().add(chatRoomListbtn);

		votebtn = new JButton("투표 하기");
		votebtn.setBounds(143, 270, 113, 23);
		getContentPane().add(votebtn);

		minigame = new JButton("미니 게임");
		minigame.setBounds(143, 345, 113, 23);
		getContentPane().add(minigame);

		myprofilebtn = new JButton("마이프로필");
		myprofilebtn.setBounds(326, 10, 97, 23);
		getContentPane().add(myprofilebtn);

		picture_lbl = new JLabel("사진들어갈");
		picture_lbl.setBounds(12, 14, 81, 15);
		getContentPane().add(picture_lbl);

		nick_lbl = new JLabel("닉네임 들어갈");
		nick_lbl.setBounds(105, 14, 88, 15);
		getContentPane().add(nick_lbl);

		logoutbtn = new JButton("로그아웃 필요 하겠지?");
		logoutbtn.setBounds(27, 469, 256, 23);
		getContentPane().add(logoutbtn);
	}

}
