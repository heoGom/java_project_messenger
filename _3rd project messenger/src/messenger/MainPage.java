package messenger;

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
	static ShowProfileImage showProfileImage;

	MembershipDAO mdao = new MembershipDAO();
	private JLabel lblNewLabel;

	public MainPage(User user) {
		getContentPane().setBackground(new Color(250, 255, 243));
		this.user = user;
		setTitle("");
		extracted();
		changelbl();
		listenerAll();
		showGUI();
		openingList = new ArrayList<>();
		openingPublic = false;
		showProfileImage=null;

	}

	private void showGUI() {
		setSize(469, 574);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
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
				System.out.println(userList.lbl2List.toString());
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
				voteMainPage.setLocation(mainPageX + voteMainPage.getWidth() - 30, mainPageY);
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

		userListbtn = new JButton("");
		userListbtn.setIcon(
				new ImageIcon(MainPage.class.getResource("/Image/\uAC00\uC785\uC790 \uBAA9\uB85D \uBC84\uD2BC.png")));
		userListbtn.setBorderPainted(false);
		userListbtn.setFocusPainted(false);
		userListbtn.setContentAreaFilled(false);
		userListbtn.setBounds(143, 97, 160, 60);
		getContentPane().add(userListbtn);

		chatRoomListbtn = new JButton("");
		chatRoomListbtn.setIcon(
				new ImageIcon(MainPage.class.getResource("/Image/\uCC44\uD305\uBC29 \uBAA9\uB85D \uBC84\uD2BC.png")));
		chatRoomListbtn.setBorderPainted(false);
		chatRoomListbtn.setFocusPainted(false);
		chatRoomListbtn.setContentAreaFilled(false);
		chatRoomListbtn.setBounds(143, 188, 160, 60);
		getContentPane().add(chatRoomListbtn);

		votebtn = new JButton("");
		votebtn.setIcon(new ImageIcon(MainPage.class.getResource("/Image/\uD22C\uD45C\uD558\uAE30 \uBC84\uD2BC.png")));
		votebtn.setBorderPainted(false);
		votebtn.setFocusPainted(false);
		votebtn.setContentAreaFilled(false);
		votebtn.setBounds(143, 274, 160, 60);
		getContentPane().add(votebtn);

		minigamebtn = new JButton("");
		minigamebtn
				.setIcon(new ImageIcon(MainPage.class.getResource("/Image/\uBBF8\uB2C8\uAC8C\uC784 \uBC84\uD2BC.png")));
		minigamebtn.setBorderPainted(false);
		minigamebtn.setFocusPainted(false);
		minigamebtn.setContentAreaFilled(false);
		minigamebtn.setBounds(143, 359, 160, 60);
		getContentPane().add(minigamebtn);

		myprofilebtn = new JButton("");
		myprofilebtn.setBorderPainted(false);
		myprofilebtn.setFocusPainted(false);
		myprofilebtn.setContentAreaFilled(false);
		myprofilebtn.setToolTipText("마이프로필");
		myprofilebtn.setIcon(
				new ImageIcon(MainPage.class.getResource("/Image/\uB9C8\uC774\uD504\uB85C\uD544 \uBC84\uD2BC.png")));
		myprofilebtn.setBounds(356, 14, 74, 50);
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
				panel.setBackground(new Color(250, 255, 243));
				panel.setLayout(null);
				JLabel label = new JLabel("");
				label.setIcon(
						new ImageIcon(MainPage.class.getResource("/Image/\uBE44\uBC00\uBC88\uD638\uC785\uB825.png")));
				label.setFont(new Font("한컴 고딕", Font.BOLD, 14));
				label.setBounds(55, 25, 180, 20);
				JPasswordField pwField = new JPasswordField(20);
				pwField.setBounds(70, 60, 150, 20);
				JButton btnOK = new JButton("");
				btnOK.setIcon(new ImageIcon(MainPage.class.getResource("/Image/\uD655\uC778.png")));
				btnOK.setBorderPainted(false);
				btnOK.setFocusPainted(false);
				btnOK.setContentAreaFilled(false);
				btnOK.setBounds(55, 120, 60, 30);
				btnOK.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (user.getPw().equals(pwField.getText())) {
							dialog.setVisible(false);
							MyPage myPage = new MyPage(user, MainPage.this);
							if (user.getImage() != null) { // 사진이 등록이 되어있을때
								ImageIcon icon = user.getImage();
								Image scaledImage2 = icon.getImage().getScaledInstance(myPage.picture.getWidth(),
										myPage.picture.getHeight(), Image.SCALE_SMOOTH);
								ImageIcon scalecIcon2 = new ImageIcon(scaledImage2);
								myPage.picture.setIcon(scalecIcon2);
								int Score = user.getHighScore();
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

				JButton btnCancle = new JButton();
				btnCancle.setIcon(new ImageIcon(MainPage.class.getResource("/Image/\uCDE8\uC18C.png")));
				btnCancle.setBorderPainted(false);
				btnCancle.setFocusPainted(false);
				btnCancle.setContentAreaFilled(false);
				btnCancle.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}
				});

				btnCancle.setBounds(180, 120, 60, 30);
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
		nick_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		nick_lbl.setFont(new Font("한컴 고딕", Font.PLAIN, 18));
		nick_lbl.setBounds(74, 32, 142, 20);
		getContentPane().add(nick_lbl);

		logoutbtn = new JButton("");
		logoutbtn
				.setIcon(new ImageIcon(MainPage.class.getResource("/Image/\uB85C\uADF8\uC544\uC6C3 \uBC84\uD2BC.png")));
		logoutbtn.setBorderPainted(false);
		logoutbtn.setFocusPainted(false);
		logoutbtn.setContentAreaFilled(false);

		logoutbtn.setBounds(12, 438, 68, 70);
		getContentPane().add(logoutbtn);

		lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 449, 439, 70);
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
