import javax.swing.JFrame;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class MainPage extends JFrame {
	public JLabel nick_lbl;
	public JLabel picture_lbl;
	User user;
	Agendas agendas;

	public JButton userListbtn;
	private JButton chatRoomListbtn;
	private JButton votebtn;
	private JButton minigame;
	private JButton myprofilebtn;
	private JButton logoutbtn;

	MembershipDAO mdao = new MembershipDAO();

	public MainPage(User user) {
		this.user = user;
		setTitle("");
		extracted();
		changelbl();
		listenerAll();
		showGUI();

	}

	private void showGUI() {
		setSize(469, 574);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void changelbl() {
		nick_lbl.setText(user.getNick());
		picture_lbl.setIcon(user.image);
	}

	private void listenerAll() {
		chatRoomListbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChatRoomListPage chatRoomListPage = new ChatRoomListPage(user);
				chatRoomListPage.setVisible(true);
			}
		});

		userListbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User.list.clear();
				user.readAllUser(user.getNick());
				UserList userList = new UserList(user);
				int mainPageX = getX();
				int mainPageY = getY();
				userList.setLocation(mainPageX - userList.getWidth(), mainPageY);
				userList.setVisible(true);
				userList.readStatus();
			}
		});

		votebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				VoteMainPage voteMainPage = new VoteMainPage(user);
				int mainPageX = getX();
				int mainPageY = getY();
				voteMainPage.setLocation(mainPageX + voteMainPage.getWidth(), mainPageY);
				voteMainPage.setVisible(true);
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

		myprofilebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				JLabel label = new JLabel("비밀번호를 입력해주세요.");
				JPasswordField pwField = new JPasswordField(20);
				panel.add(label);
				panel.add(pwField);
				String[] options = { "확인", "취소" };
				while (true) {
					int optionSelect = JOptionPane.showOptionDialog(null, panel, "비밀번호 입력", JOptionPane.NO_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
					if (optionSelect == 0) {
						if (user.getPw().equals(pwField.getText())) {
							MyPage myPage = new MyPage(user, MainPage.this);
							if (user.getImage() != null) { // 사진이 등록이 되어있을때
								ImageIcon icon = user.getImage();
								Image scaledImage2 = icon.getImage().getScaledInstance(myPage.picture.getWidth(),
										myPage.picture.getHeight(), Image.SCALE_SMOOTH);
								ImageIcon scalecIcon2 = new ImageIcon(scaledImage2);
								myPage.picture.setIcon(scalecIcon2);
								myPage.setVisible(true);
							} else { // 사진이 등록 되어있지않을때
								myPage.setVisible(true);
							}
							break;
						} else {
							JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "경고", JOptionPane.ERROR_MESSAGE);
						}
					}
					if (optionSelect == 1) {
						setVisible(true);
						break;
					}
					if (optionSelect == JOptionPane.CLOSED_OPTION) {
						setVisible(true);
						break;
					}
				}
			}
		});

		picture_lbl = new JLabel("사진들어갈");
		picture_lbl.setBounds(12, 14, 50, 50);
		getContentPane().add(picture_lbl);

		nick_lbl = new JLabel("닉네임 들어갈");
		nick_lbl.setBounds(105, 14, 88, 20);
		getContentPane().add(nick_lbl);

		logoutbtn = new JButton("로그아웃");
		logoutbtn.setBounds(27, 469, 256, 23);
		getContentPane().add(logoutbtn);
		logoutbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login login = new Login();
				login.setVisible(true);
				mdao.resetStatus(user.id);
			}
		});
	}

}
