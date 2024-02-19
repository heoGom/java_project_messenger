import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Login extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JButton loginbtn;
	private JButton membershipbtn;
	private Membership membership;
	
	public Login() {
		extracted();
		allListener();
		showGUI();
	
	}
	
	
	private void allListener() {
		loginbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * if(db에 일치하는 아이디 비번이 존재할시에 ){
				 * dispose;
				 * 메인화면창 띄우기 ;
				 * }
				 * else{
				 * 쇼다이얼로그 띄우던가 안띄우던가
				 * loginbtn.setenable(false);
				 * loginbtn.setenable(true);
				 * }
				 */
				
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
	}
	
	private void showGUI() {
		setSize(462, 491);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
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
		
		textField_1 = new JTextField();
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
