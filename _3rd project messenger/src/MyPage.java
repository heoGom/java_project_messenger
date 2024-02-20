import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MyPage extends JFrame {
	User user;
	List<User> userList;

	public MyPage() {
		user = new User();
		user.readAllUser();
		userList = new ArrayList<>();
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
				JDialog dialog = new JDialog();
				dialog.setTitle("닉네임 변경");
				dialog.setSize(400, 300);
				dialog.setModal(true);
				JPanel panel = new JPanel();
				panel.setLayout(null);
				JLabel label = new JLabel("변경할 닉네임을 입력해주세요.");
				JTextField tx = new JTextField(20);
				label.setBounds(100, 70, 200, 20);
				tx.setBounds(100, 90, 200, 20);
				panel.add(label);
				panel.add(tx);
				JButton btnApply = new JButton("적용");
				btnApply.setBounds(40, 200, 100, 30);
				panel.add(btnApply);
				JButton btnCancle = new JButton("취소");
				btnCancle.setBounds(240, 200, 100, 30);
				panel.add(btnCancle);
				dialog.add(panel, BorderLayout.CENTER);

				btnApply.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for (User user : userList) {
							if (user.getNick().equals(tx.getText())) {
								JOptionPane.showMessageDialog(null, "이미 사용중인 닉네임 입니다.", "경고",
										JOptionPane.ERROR_MESSAGE);
								break;
							} else {
								user.setNick(tx.getText());
							}
						}
					}
				});
				btnCancle.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
						System.out.println("뿅뿅");
					}
				});
				dialog.setVisible(true); // 모달이 적용되면 setVisible이 아래흐름으로 안흘러감.
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
				JLabel label2 = new JLabel("새 비밀번호 재확인");
				JPasswordField pwfield2 = new JPasswordField(20);
				panel.add(label);
				panel.add(pwfield);
				panel.add(label2);
				panel.add(pwfield2);
				label.setBounds(20, 20, 100, 20);
//				pwfield.setBounds(x, y, width, height);
				label2.setBounds(20, 40, 100, 20);
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
