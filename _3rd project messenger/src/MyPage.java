import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MyPage extends JFrame {
	User user;
	List<User> list = new ArrayList<>();

	public MyPage() {
		getContentPane().setLayout(null);
		
		setTitle("마이 프로필");
		JLabel Myimage = new JLabel("     내 사진");
		Myimage.setBounds(62, 38, 87, 111);
		getContentPane().add(Myimage);

		JLabel UserNick = new JLabel("내 별명");
		UserNick.setBounds(314, 86, 60, 15);
		getContentPane().add(UserNick);

		JButton btnNickCh = new JButton("닉네임 변경");
		btnNickCh.setFont(new Font("굴림", Font.BOLD, 11));
		btnNickCh.setBounds(22, 183, 120, 30);
		getContentPane().add(btnNickCh);
		btnNickCh.setBackground(Color.white);
		btnNickCh.setBorderPainted(false);
		btnNickCh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				JLabel label = new JLabel("변경할 닉네임을 입력해주세요.");
				JTextField tx = new JTextField(20);
				panel.add(label);
				panel.add(tx);
				String[] options = { "적용", "취소" };
				int optionSelect = JOptionPane.showOptionDialog(null, panel, "닉네임 변경", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				if (optionSelect == 0) {
					for (User user : list) {
						if (user.getNick().equals(tx.getText())) {
							JOptionPane.showMessageDialog(null, "이미 사용중인 닉네임 입니다.", "경고", JOptionPane.ERROR_MESSAGE);
							System.out.println("변경되지 않음.");
							break;
						} else {
							user.setNick(tx.getText());
							System.out.println("변경됨");
						}
					}
				}
			}
		});

		JButton btnPwCh = new JButton("비밀번호 변경");
		btnPwCh.setFont(new Font("굴림", Font.BOLD, 11));
		btnPwCh.setBounds(162, 183, 120, 30);
		getContentPane().add(btnPwCh);
		btnPwCh.setBackground(Color.white);
		btnPwCh.setBorderPainted(false);
		btnPwCh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				JLabel label = new JLabel("새 비밀번호를 입력");
				JPasswordField pwfield = new JPasswordField(20);
				JLabel lable2 = new JLabel("새 비밀번호 재확인");
				JPasswordField pwfield2 = new JPasswordField(20);
				panel.add(label);
				panel.add(pwfield);
				panel.add(lable2);
				panel.add(pwfield2);
				String[] options = { "변경", "취소" };
				int optionSelect = JOptionPane.showOptionDialog(null, panel, "비밀번호 변경", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[0]); 
				if (optionSelect == 0) {
					if (pwfield.equals(pwfield2)) {
						user.setPw(pwfield.getText());
						System.out.println(pwfield);
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호가 일치 하지않습니다", "경고", JOptionPane.ERROR_MESSAGE);
						System.out.println(0);
					}
				}
			}
		});

		JButton btnImageCh = new JButton("프로필사진 변경");
		btnImageCh.setFont(new Font("굴림", Font.BOLD, 11));
		btnImageCh.setBounds(305, 183, 125, 30);
		getContentPane().add(btnImageCh);
		btnImageCh.setBackground(Color.white);
		btnImageCh.setBorderPainted(false);

		showGUI();

	}

	private void showGUI() {
		setSize(469, 300);
		setVisible(true);
		
	}
}