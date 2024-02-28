import java.awt.Color;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class MainPage extends JFrame {
	public JLabel nick_lbl;
	public JLabel picture_lbl;
	User user;
	Agendas agendas;
	GoVotePage goVotePage;

	static List<User> openingList;
	static boolean openingPublic;

	public JButton userListbtn;
	private JButton chatRoomListbtn;
	private JButton votebtn;
	private JButton minigamebtn;
	private JButton myprofilebtn;
	private JButton logoutbtn;

	MembershipDAO mdao = new MembershipDAO();
	private JLabel lblNewLabel;

	public MainPage(User user) {
		getContentPane().setBackground(Color.WHITE);
		this.user = user;
		setTitle("");
		extracted();
		changelbl();
		listenerAll();
		showGUI();
		openingList = new ArrayList<>();
		openingPublic = false;

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
			private ChatRoomListPage chatRoomListPage;

			@Override
			public void actionPerformed(ActionEvent e) {
				// chatRoomListPage가 null이거나 숨겨져 있는 경우에만 새로운 창을 생성하고 보여줍니다.
				if (chatRoomListPage == null || !chatRoomListPage.isVisible()) {
					chatRoomListPage = new ChatRoomListPage(user);
					chatRoomListPage.setVisible(true);
				} else {
					// chatRoomListPage가 이미 열려 있는 경우 해당 페이지를 활성화합니다.
					chatRoomListPage.toFront();
				}
				int mainPageX = getX();
				int mainPageY = getY();
				chatRoomListPage.setLocation(mainPageX - chatRoomListPage.getWidth(), mainPageY);
			}
		});

		userListbtn.addActionListener(new ActionListener() {
			private UserList userList;

			@Override
			public void actionPerformed(ActionEvent e) {
				User.list.clear();
				user.readAllUser(user.getNick());
				if (userList == null || !userList.isVisible()) {
					userList = new UserList(user);
					userList.setVisible(true);
				} else {
					userList.toFront();
				}
				int mainPageX = getX();
				int mainPageY = getY();
				userList.setLocation(mainPageX - userList.getWidth(), mainPageY);
				userList.readStatus();
				if (user.getImage() != null) {
					ImageIcon icon = user.getImage();
					Image scaledImage = icon.getImage().getScaledInstance(userList.lblNewLabel.getWidth(),
							userList.lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon scalecIcon = new ImageIcon(scaledImage);
					userList.lblNewLabel.setIcon(scalecIcon);
				}
			}
		});

		votebtn.addActionListener(new ActionListener() {
			private VoteMainPage voteMainPage;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (voteMainPage == null || !voteMainPage.isVisible()) {
					voteMainPage = new VoteMainPage(user);
				} else {
					voteMainPage.toFront();
				}
				int mainPageX = getX();
				int mainPageY = getY();
				voteMainPage.setLocation(mainPageX + voteMainPage.getWidth(), mainPageY);
			}
		});

		minigamebtn.addActionListener(new ActionListener() {
			private MiniGame minigame;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (minigame == null || !minigame.isVisible()) {
					minigame = new MiniGame(user);
					minigame.setVisible(true);
				} else {
					minigame.toFront();
				}
			}
		});

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				for (Window window : Window.getWindows()) {
					if (window != MainPage.this && window.isDisplayable()) {
						window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
						window.dispose(); // 창 닫기
					}
				}
				mdao.resetStatus(user.id);
				System.out.println("프로그램 종료");
				System.exit(0);
			}
		});
	}

	private void extracted() {
		getContentPane().setLayout(null);

		userListbtn = new JButton("가입자 목록");
		userListbtn.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		userListbtn.setBounds(160, 103, 113, 23);
		getContentPane().add(userListbtn);

		chatRoomListbtn = new JButton("채팅방 목록");
		chatRoomListbtn.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		chatRoomListbtn.setBounds(160, 183, 113, 23);
		getContentPane().add(chatRoomListbtn);

		votebtn = new JButton("투표 하기");
		votebtn.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		votebtn.setBounds(160, 270, 113, 23);
		getContentPane().add(votebtn);

		minigamebtn = new JButton("미니 게임");
		minigamebtn.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		minigamebtn.setBounds(160, 345, 113, 23);
		getContentPane().add(minigamebtn);

		myprofilebtn = new JButton("ㅁㄴㅇㄹ");
		myprofilebtn.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		myprofilebtn.setBounds(375, 14, 50, 50);
		getContentPane().add(myprofilebtn);

		myprofilebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setSize(300, 200);
				dialog.setTitle("비밀번호 입력");
				dialog.setResizable(false);
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				JPanel panel = new JPanel();
				panel.setLayout(null);
				JLabel label = new JLabel("비밀번호를 입력해주세요.");
				label.setFont(new Font("한컴 고딕", Font.BOLD, 14));
				label.setBounds(70, 25, 180, 20);
				JPasswordField pwField = new JPasswordField(20);
				pwField.setBounds(70, 60, 150, 20);
				JButton btnOK = new JButton("확인");
				btnOK.setFont(new Font("한컴 고딕", Font.BOLD, 13));
				btnOK.setBounds(50, 100, 60, 30);
				btnOK.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (user.getPw().equals(pwField.getText())) {
							MyPage myPage = new MyPage(user, MainPage.this);
							if (user.getImage() != null) { // 사진이 등록이 되어있을때
								dialog.dispose();
								ImageIcon icon = user.getImage();
								Image scaledImage2 = icon.getImage().getScaledInstance(myPage.picture.getWidth(),
										myPage.picture.getHeight(), Image.SCALE_SMOOTH);
								ImageIcon scalecIcon2 = new ImageIcon(scaledImage2);
								myPage.picture.setIcon(scalecIcon2);
								int Score = user.getHighScore();
								System.out.println(Score);
//								myPage.score.setText(Score + " 초");	
								myPage.showGUI();
							} else { // 사진이 등록 되어있지않을때
								int Score = user.getHighScore();
								myPage.score.setText(Score + " 초");
								myPage.showGUI();
							}
						} else {
							JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "경고", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				pwField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							btnOK.doClick(); // 버튼 클릭 동작을 호출합니다.
						}
					}
				});

				JButton btnCancle = new JButton("취소");
				btnCancle.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}
				});

				btnCancle.setFont(new Font("한컴 고딕", Font.BOLD, 13));
				btnCancle.setBounds(180, 100, 60, 30);
				panel.add(label);
				panel.add(pwField);
				panel.add(btnOK);
				panel.add(btnCancle);
				dialog.getContentPane().add(panel);
				dialog.setVisible(true);
			}
		});
		picture_lbl = new JLabel();
		picture_lbl.setBounds(12, 14, 50, 50);
		getContentPane().add(picture_lbl);

		nick_lbl = new JLabel("닉네임 들어갈");
		nick_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		nick_lbl.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		nick_lbl.setBounds(98, 11, 88, 20);
		getContentPane().add(nick_lbl);

		logoutbtn = new JButton("로그아웃");
		logoutbtn.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		logoutbtn.setBounds(320, 469, 97, 23);
		getContentPane().add(logoutbtn);

		lblNewLabel = new JLabel("사진");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 418, 296, 70);
		String imagePath2 = "/picture/하트일러.png";
		ImageIcon icon2 = new ImageIcon(getClass().getResource(imagePath2));
		Image image2 = icon2.getImage();
		Image scaledImage2 = image2.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(),
				Image.SCALE_SMOOTH);
		ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
		lblNewLabel.setIcon(scaledIcon2);
		getContentPane().add(lblNewLabel);
		logoutbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Window window : Window.getWindows()) {
					if (window.isDisplayable()) {
						window.dispose(); // 창 닫기
					}
				}
				Login login = new Login();
				login.setVisible(true);
				mdao.resetStatus(user.id);
			}
		});
	}

}
