import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
	private List<User> list;

	public Login() {
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
				isRightPw = membershipDAO.CheckPW(textField.getText(),textField_1.getText());
				if (!isRight && !isRightPw) {
					dispose();
					user = readDB();
					membershipDAO.changeStatus(textField.getText());
					MainPage mainPage = new MainPage(user);
					mainPage.setVisible(true);
					System.out.println(user.nick);
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
				membership = new Membership();
				int currentX = getLocation().x;
				int currentY = getLocation().y;

				// Membership 창을 현재 창의 왼쪽에 띄우기
				int offsetX = -membership.getWidth(); // 왼쪽으로 이동할 거리 (Membership 창의 너비만큼)
				membership.setLocation(currentX + offsetX, currentY);

				membership.setVisible(true);
			}
		});
		textField_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				isRight = membershipDAO.CheckId(textField.getText());
				isRightPw = membershipDAO.CheckPW(textField.getText(),textField_1.getText());
				if (!isRight && !isRightPw) {
					dispose();
					user = readDB();
					membershipDAO.changeStatus(textField.getText());
					MainPage mainPage = new MainPage(user);
					mainPage.setVisible(true);
					System.out.println(user.nick);
				} else {
					JOptionPane.showMessageDialog(null, "정보가없습니다");
					loginbtn.setEnabled(false);
					loginbtn.setEnabled(true);
				}
			}
		});
	}
	

	public User readDB() {
		String sql = "select id,password,nickname,profilePhoto from jae.user where id = ?";
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
					if (rs.getBlob("profilePhoto") != null) {
						blob = rs.getBlob("profilePhoto");
						image = blobToImageIcon(blob);

						return new User(id, pw, nick, image);
					} else {
						image = null;
						return new User(id, pw, nick, image);
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

		JLabel lblNewLabel = new JLabel("메신저 로그인");
		lblNewLabel.setBounds(169, 123, 89, 39);
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
		lblNewLabel_1.setBounds(70, 203, 57, 15);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("PW");
		lblNewLabel_2.setBounds(70, 274, 57, 15);
		getContentPane().add(lblNewLabel_2);

		loginbtn = new JButton("로그인");
		loginbtn.setBounds(316, 270, 97, 23);
		getContentPane().add(loginbtn);

		membershipbtn = new JButton("회원가입");
		membershipbtn.setBounds(12, 10, 97, 23);
		getContentPane().add(membershipbtn);
	}

	public static void main(String[] args) {
		new MySqlConnectionProvider();
		new Login();
	}
}
