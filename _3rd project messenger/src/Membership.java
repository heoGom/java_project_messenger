import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javax.swing.JTextField;

public class Membership extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton pictuerebtn;
	private JButton confirmbtn;
	private JFrame frame;
	private JLabel pictureLabel;
	private JButton idDupbtn;
	private JButton nickDupbtn;

	private MembershipDAO membershipdao;
	JLabel id_lbl;
	
	Boolean isRightId;
	private JLabel pw_lbl;
	private JLabel pw_lbl2;
	private JLabel nick_lbl;

	public Membership() {
		extracted();
		frame = this;
		membershipdao = new MembershipDAO();
		listenerAll();
		showGUI();
		

	}

	private void listenerAll() {
		pictuerebtn.addActionListener(new ActionListener() {
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
					}
				}
			}
		});

		confirmbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = "사용가능";
				if(id_lbl.getText().equals(s) && pw_lbl.getText().equals(s) &&  pw_lbl2.getText().equals(s)&& nick_lbl.getText().equals(s)) {
				
					String query= "insert into jae.user(id, password, nickname, profilephoto)values(?, ?, ?, ?)";
					try(Connection conn = MySqlConnectionProvider.getConnection();
							PreparedStatement stmt = conn.prepareStatement(query)){
						stmt.setString(1,id_lbl.getText());
						stmt.setString(2, pw_lbl2.getText());
						stmt.setString(3, nick_lbl.getText());
						//stmt.setString(4, pictureLabel.createImage(producer));
					//	ResultSet rs = stmt.executeQuery()
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
		
			}
		});
		idDupbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isRightId = null;
				isRightId = membershipdao.CheckId(textField.getText());
				if(isRightId) {
					JOptionPane.showMessageDialog(null, "사용가능한 아이디입니다.");
					id_lbl.setText("사용가능");
				}else {
					JOptionPane.showMessageDialog(null, "사용불가한 아이디입니다.");
					id_lbl.setText("중복된 아이디 입니다.");
				}

			}
		});

		nickDupbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

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

		textField = new JTextField();
		textField.setBounds(128, 85, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				String str = textField.getText();
				boolean isValid = isValidPattern(str);
				if (isValid) {
					id_lbl.setText("사용가능");
					idDupbtn.setEnabled(true);
				} else {
					id_lbl.setText("사용불가");
					idDupbtn.setEnabled(false);
				}
				if (textField.getText().equals("")) {
					id_lbl.setText("");
				}

			}

		});

		textField_1 = new JTextField();
		textField_1.setBounds(128, 141, 116, 21);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(128, 195, 116, 21);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(128, 251, 116, 21);
		getContentPane().add(textField_3);
		textField_3.setColumns(10);

		pictuerebtn = new JButton("프로필 사진등록");
		pictuerebtn.setBounds(116, 310, 155, 36);
		getContentPane().add(pictuerebtn);

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

		pw_lbl = new JLabel("해야");
		pw_lbl.setBounds(156, 172, 57, 15);
		getContentPane().add(pw_lbl);

		pw_lbl2 = new JLabel("하는");
		pw_lbl2.setBounds(156, 226, 57, 15);
		getContentPane().add(pw_lbl2);

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

	private static boolean isValidPattern(String input) {
		String pattern = "^[a-zA-Z0-9]{2,10}$";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(input);
		return matcher.matches();
	}
}
