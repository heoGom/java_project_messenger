import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
	private MembershipMethods membershipMethods;
	
	public Membership() {
		extracted();
		frame = this;
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
						membershipMethods.displayImage(file.getAbsolutePath());
					}
				}
			}
		});

		confirmbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if(레이블문구 문제없으면) {
				// db에 저장;
				// 회원가입성공했다 다이얼로그(이건 옵션)
				// dispose();
				// } else{
				// 위에 다이얼로그 할거면 여기도
				// confirmbtn.setEnabled(false);
				// confirmbtn.setEnabled(true);
				// }
			}
		});
		idDupbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		nickDupbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
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

		JLabel lblNewLabel_4 = new JLabel("수정");
		lblNewLabel_4.setBounds(156, 116, 57, 15);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("해야");
		lblNewLabel_5.setBounds(156, 172, 57, 15);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("하는");
		lblNewLabel_6.setBounds(156, 226, 57, 15);
		getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("라벨");
		lblNewLabel_7.setBounds(156, 282, 57, 15);
		getContentPane().add(lblNewLabel_7);
		
		idDupbtn = new JButton("중복확인");
		idDupbtn.setBounds(273, 84, 97, 23);
		getContentPane().add(idDupbtn);
		
		nickDupbtn = new JButton("중복 확인");
		nickDupbtn.setBounds(273, 250, 97, 23);
		getContentPane().add(nickDupbtn);
	}
}
