import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Membership extends JFrame {
	private JTextField id_tf;
	private JPasswordField password_pf;
	private JPasswordField password_pf2;
	private JTextField textField_3;
	private JButton picturebtn;
	private JButton confirmbtn;
	private JFrame frame;
	private JLabel pictureLabel;
	private JButton idDupbtn;
	private JButton nickDupbtn;

	private MembershipDAO membershipdao;
	JLabel id_lbl;
	JLabel password_lbl;
	JLabel password_lbl2;

	Boolean isRightId;
	Boolean isRightNick;
	JLabel nick_lbl;
	File filePath;

	public Membership() {
		extracted();
		frame = this;
		membershipdao = new MembershipDAO();
		listenerAll();
		showGUI();

	}

	private void listenerAll() {
		picturebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setMultiSelectionEnabled(true);

				int result = fileChooser.showOpenDialog(frame);

				if (result == JFileChooser.APPROVE_OPTION) {
					File[] selectedFiles = fileChooser.getSelectedFiles();
					for (File file : selectedFiles) {
						System.out.println("Selected File: " + file.getAbsolutePath());
						displayImage(file.getAbsolutePath());
						filePath = file.getAbsoluteFile();
					}
				}
			}
		});

		confirmbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = "사용가능";
				if (id_lbl.getText().equals(s) && password_lbl.getText().equals(s) && password_lbl2.getText().equals(s)
						&& nick_lbl.getText().equals(s)) {

					String query = "insert into jae.user(id, password, nickname, profilephoto)values(?, ?, ?, ?)";
					try (Connection conn = MySqlConnectionProvider.getConnection();
							PreparedStatement stmt = conn.prepareStatement(query)) {
						stmt.setString(1, id_tf.getText());
						stmt.setString(2, password_pf2.getText());
						stmt.setString(3, textField_3.getText());
						
						byte[] imageData = Files.readAllBytes(filePath.toPath());
						stmt.setBytes(4, imageData);

						stmt.executeUpdate();

					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
					
				}

			}
		});
		idDupbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				isRightId = null;
				isRightId = membershipdao.CheckId(id_tf.getText());
				if (isRightId) {
					JOptionPane.showMessageDialog(null, "사용가능한 아이디입니다.");
				} else {

					JOptionPane.showMessageDialog(null, "사용불가한 아이디입니다.");
					id_lbl.setText("중복된 아이디 입니다.");
				}

			}
		});

		nickDupbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isRightNick = membershipdao.CheckNick(textField_3.getText());
				if (isRightNick) {
					JOptionPane.showMessageDialog(null, "사용가능한 닉네임입니다.");
					nick_lbl.setText("사용가능");
					
					
				} else {
					JOptionPane.showMessageDialog(null, "사용불가능한 닉네임입니다.");
					nick_lbl.setText("사용불가");
				}
			}
		});

		id_tf.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				String str = id_tf.getText();
				boolean isValid = isValidIdPattern(str);
				if (isValid) {
					id_lbl.setText("사용가능");
				} else {
					id_lbl.setText("사용불가");
				}
				if (id_tf.getText().equals("")) {
					id_lbl.setText("");
				}

			}

		});
		password_pf.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				char[] row = password_pf.getPassword();
				String password = new String(row);

				boolean isValid = isValidPasswordPattern(password);
				if (isValid) {
					password_lbl.setText("사용가능");
				} else {
					password_lbl.setText("사용불가");
				}

			}

		});

		password_pf2.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				char[] origin = password_pf.getPassword();
				String sOrigin = new String(origin);
				char[] copy = password_pf2.getPassword();
				String sCopy = new String(copy);
				if (sOrigin.equals(sCopy)) {
					password_lbl2.setText("사용가능");
				} else {
					password_lbl2.setText("사용불가");
				}
			}
		});

	}

	public void displayImage(String filePath) {
		try {
			File file = new File(filePath);

			if (!isImageFile(file)) {
				JOptionPane.showMessageDialog(this, "올바른 이미지 파일이 아닙니다.", "에러", JOptionPane.ERROR_MESSAGE);
				return;
			}

			BufferedImage image = ImageIO.read(file);

			if (image != null) {
				ImageIcon icon = new ImageIcon(image);
				// pictureLabel.setIcon(icon);
			} else {
				JOptionPane.showMessageDialog(this, "이미지를 읽을 수 없습니다.", "에러", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isImageFile(File file) {
		try {
			ImageIO.read(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private void showGUI() {
		setSize(416, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);

	}

	private void extracted() {
		getContentPane().setLayout(null);

		pictureLabel = new JLabel();
		pictureLabel.setText("임시로넣어둔");
		pictureLabel.setBounds(12, 294, 200, 200);
		getContentPane().add(pictureLabel);

		id_tf = new JTextField();
		id_tf.setBounds(128, 85, 116, 21);
		getContentPane().add(id_tf);
		id_tf.setColumns(10);

		password_pf = new JPasswordField();
		password_pf.setBounds(128, 141, 116, 21);
		getContentPane().add(password_pf);
		password_pf.setColumns(10);

		password_pf2 = new JPasswordField();
		password_pf2.setBounds(128, 195, 116, 21);
		getContentPane().add(password_pf2);
		password_pf2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(128, 251, 116, 21);
		getContentPane().add(textField_3);
		textField_3.setColumns(10);

		picturebtn = new JButton("프로필 사진등록");
		picturebtn.setBounds(116, 310, 155, 36);
		getContentPane().add(picturebtn);

		confirmbtn = new JButton("확인");
		confirmbtn.setBounds(291, 411, 97, 23);
		getContentPane().add(confirmbtn);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(41, 88, 57, 15);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("PW");
		lblNewLabel_1.setBounds(41, 144, 57, 15);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("PW확인");
		lblNewLabel_2.setBounds(41, 198, 57, 15);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("닉네임");
		lblNewLabel_3.setBounds(41, 254, 57, 15);
		getContentPane().add(lblNewLabel_3);

		id_lbl = new JLabel("2~10자 내외,특수문자불가");
		id_lbl.setBounds(138, 116, 133, 15);
		getContentPane().add(id_lbl);

		password_lbl = new JLabel("20자리 이하");
		password_lbl.setBounds(128, 172, 116, 15);
		getContentPane().add(password_lbl);

		password_lbl2 = new JLabel("하는");
		password_lbl2.setBounds(156, 226, 57, 15);
		getContentPane().add(password_lbl2);

		nick_lbl = new JLabel("라벨");
		nick_lbl.setBounds(156, 282, 57, 15);
		getContentPane().add(nick_lbl);

		idDupbtn = new JButton("중복확인");
		idDupbtn.setBounds(273, 84, 97, 23);
		getContentPane().add(idDupbtn);

		nickDupbtn = new JButton("중복 확인");
		nickDupbtn.setBounds(273, 250, 97, 23);
		getContentPane().add(nickDupbtn);

	}

	private boolean isValidIdPattern(String input) {
		String pattern = "^[a-zA-Z0-9]{2,10}$";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(input);
		return matcher.matches();
	}

	private boolean isValidPasswordPattern(String input) {
		String pattern = "^[a-zA-Z0-9]{1,20}$";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(input);
		return matcher.matches();
	}
}
