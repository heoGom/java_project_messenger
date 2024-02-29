import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Login extends JFrame {
	private JTextField textField;
	private JPasswordField textField_1;
	private JButton loginbtn;
	private JButton membershipbtn;
	private Membership membership;

	private MembershipDAO membershipDAO;
	Boolean isRight;
	Boolean isRightPw;
	User user;

	public Login() {
		getContentPane().setBackground(Color.WHITE);
		extracted();
		user = new User();
		membershipDAO = new MembershipDAO();
		allListener();
		showGUI();

	}

	private void allListener() {
		loginbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				isRight = membershipDAO.CheckId(textField.getText());
				isRightPw = membershipDAO.CheckPW(textField.getText(), textField_1.getText());
				if (!isRight && !isRightPw) {
					dispose();
					user = readDB();
					membershipDAO.changeStatus(textField.getText());
					MainPage mainPage = new MainPage(user);
					mainPage.setVisible(true);
					if (user.getImage() != null) {
						ImageIcon icon = user.getImage();
						Image scaledImage = icon.getImage().getScaledInstance(mainPage.picture_lbl.getWidth(),
								mainPage.picture_lbl.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon scaledIcon = new ImageIcon(scaledImage);
						mainPage.picture_lbl.setIcon(scaledIcon);
					}
				} else {
					JOptionPane.showMessageDialog(null, "정보가없습니다");
					loginbtn.setEnabled(false);
					loginbtn.setEnabled(true);
				}
			}
		});

		membershipbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (membership == null || !membership.isVisible()) {
					membership = new Membership();
					membership.setVisible(true);
				} else {
					membership.toFront();
				}
				int currentX = getLocation().x;
				int currentY = getLocation().y;

				// Membership 창을 현재 창의 왼쪽에 띄우기
				int offsetX = -membership.getWidth(); // 왼쪽으로 이동할 거리 (Membership 창의 너비만큼)
				membership.setLocation(currentX + offsetX, currentY);

			}
		});
		textField_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				isRight = membershipDAO.CheckId(textField.getText());
				isRightPw = membershipDAO.CheckPW(textField.getText(), textField_1.getText());
				if (!isRight && !isRightPw) {
					dispose();
					user = readDB();
					membershipDAO.changeStatus(textField.getText());
					MainPage mainPage = new MainPage(user);
					mainPage.setVisible(true);
					if (user.getImage() != null) {
						ImageIcon icon = user.getImage();
						Image scaledImage = icon.getImage().getScaledInstance(mainPage.picture_lbl.getWidth(),
								mainPage.picture_lbl.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon scaledIcon = new ImageIcon(scaledImage);
						mainPage.picture_lbl.setIcon(scaledIcon);
					}
				} else {
					JOptionPane.showMessageDialog(null, "정보가없습니다");
					loginbtn.setEnabled(false);
					loginbtn.setEnabled(true);
				}
			}
		});
	}

	public User readDB() {
		String sql = "select id,password,nickname,profilePhoto,highscore from jae.user where id = ?";
		Blob blob;
		ImageIcon image;
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, textField.getText());

			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					String id = rs.getString("id");
					String pw = rs.getString("password");
					String nick = rs.getString("nickname");
					int highScore = rs.getInt("highscore");

					if (rs.getBlob("profilePhoto") != null) {
						blob = rs.getBlob("profilePhoto");
						image = blobToImageIcon(blob);

						return new User(id, pw, nick, image, highScore);
					} else {
						image = null;
						return new User(id, pw, nick, image, highScore);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ImageIcon blobToImageIcon(Blob blob) throws SQLException {
		if (blob != null) {
			byte[] bytes = blob.getBytes(1, (int) blob.length());
			return new ImageIcon(bytes);
		}
		return null;
	}

	private void showGUI() {
		setSize(462, 491);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);

		textField.requestFocusInWindow();
	}

	private void extracted() {
		getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setForeground(Color.GRAY);
		textField.setBorder(null);
		textField.setOpaque(false);
		textField.setFont(new Font("굴림", Font.PLAIN, 25));
		Insets insetid = new Insets(0, 5, 0, 0); // 여백 설정 (상, 좌, 하, 우)
		textField.setMargin(insetid);
		textField.setBounds(155, 216, 190, 30);
		getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JPasswordField();
		textField_1.setBorder(null);
		textField_1.setOpaque(false);
		textField_1.setFont(new Font("굴림", Font.PLAIN, 25));
		textField_1.setBounds(155, 264, 190, 30);
		Insets insetpw = new Insets(0, 5, 0, 0); // 여백 설정 (상, 좌, 하, 우)
		textField.setMargin(insetpw);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		loginbtn = new JButton("");
		loginbtn.setBorderPainted(false);
		loginbtn.setFocusPainted(false);
		loginbtn.setContentAreaFilled(false);
		loginbtn.setIcon(new ImageIcon(Login.class.getResource("/Image/\uB85C\uADF8\uC778 \uBC84\uD2BC.png")));
		loginbtn.setBounds(96, 329, 97, 30);
		getContentPane().add(loginbtn);

		membershipbtn = new JButton("");
		membershipbtn.setIcon(new ImageIcon(Login.class.getResource("/Image/\uD68C\uC6D0\uAC00\uC785 \uBC84\uD2BC.png")));
		membershipbtn.setBorderPainted(false);
		membershipbtn.setFocusPainted(false);
		membershipbtn.setContentAreaFilled(false);
		membershipbtn.setForeground(Color.BLACK);
		membershipbtn.setBounds(248, 329, 97, 30);
		getContentPane().add(membershipbtn);

		JLabel mainLbl = new JLabel();
		mainLbl.setIcon(new ImageIcon(Login.class.getResource("/Image/\uB85C\uADF8\uC778\uCC3D \uBC30\uACBD.png")));
		mainLbl.setSize(445, 450);
		getContentPane().add(mainLbl);
		
	}

	public static void main(String[] args) {
		new MySqlConnectionProvider();
		new Login();
	}
}
