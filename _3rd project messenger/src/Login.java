import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.ComponentOrientation;
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
		getContentPane().setBackground(Color.PINK);
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

		JLabel lblNewLabel = new JLabel("424호는 써라");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("한컴 고딕", Font.BOLD, 16));
		lblNewLabel.setBounds(158, 123, 102, 39);
		getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(155, 200, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JPasswordField();
		textField_1.setBounds(155, 271, 116, 21);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		lblNewLabel_1.setBounds(70, 203, 57, 15);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("PW");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		lblNewLabel_2.setBounds(70, 274, 57, 15);
		getContentPane().add(lblNewLabel_2);

		loginbtn = new JButton("로그인");
		loginbtn.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		loginbtn.setBackground(Color.PINK);
		loginbtn.setBorder(new LineBorder(Color.BLACK));
		loginbtn.setBounds(316, 270, 97, 23);
		getContentPane().add(loginbtn);

		membershipbtn = new JButton("회원가입");
		membershipbtn.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		membershipbtn.setForeground(Color.BLACK);
		membershipbtn.setBackground(Color.PINK);
		membershipbtn.setBounds(12, 10, 97, 23);
		getContentPane().add(membershipbtn);
	}

	public static void main(String[] args) {
		new MySqlConnectionProvider();
		new Login();
	}
}
