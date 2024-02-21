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
//		MainPage mainpage;
		User user;
	
		public MyPage(User user) {
			this.user = user;
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
					user.readAllUser2();
					JDialog dialog = new JDialog();
					dialog.setTitle("닉네임 변경");
					dialog.setSize(400, 300);
					dialog.setModal(true);
					JPanel panel = new JPanel();
					panel.setLayout(null);
					JLabel label = new JLabel("현재 닉네임");
					JLabel currentNick = new JLabel(user.getNick());
					JLabel changeNick = new JLabel("변경할 닉네임을 입력해주세요.");
					JTextField changetx = new JTextField(20);
					label.setBounds(100, 30, 200, 50);
					currentNick.setBounds(200, 30, 200, 50);
					changeNick.setBounds(103, 100, 200, 20);
					changetx.setBounds(95, 130, 200, 20);
					panel.add(changeNick);
					panel.add(currentNick);
					panel.add(changetx);
					panel.add(label);
					JButton btnApply = new JButton("적용");
					btnApply.setBounds(40, 200, 100, 30);
					panel.add(btnApply);
					JButton btnCancle = new JButton("취소");
					btnCancle.setBounds(240, 200, 100, 30);
					panel.add(btnCancle);
					dialog.add(panel, BorderLayout.CENTER);
					dialog.setLocationRelativeTo(null);
	
					btnApply.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (changetx.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "닉네임을 입력하세요", "경고", JOptionPane.ERROR_MESSAGE);
							} else {
								boolean Check = false;
								for (User user : user.list) {
									if (user.nick.equals(changetx.getText())) {
										Check = true;
										break;
									}
								}
								if (Check) {
									JOptionPane.showMessageDialog(null, "이미 사용중인 닉네임 입니다.", "경고",
											JOptionPane.ERROR_MESSAGE);
								} else {
									user.setNick(changetx.getText());
//										mainpage.changelbl();										
									MyPageDAO dao = new MyPageDAO();
									dao.changeNick(changetx.getText(), user.id);
									JOptionPane.showMessageDialog(null, "닉네임이 변경되었습니다.", "알림",
											JOptionPane.INFORMATION_MESSAGE);
									dialog.dispose();
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
					
					JDialog dialog = new JDialog();
					dialog.setTitle("비밀번호 변경");
					dialog.setSize(400, 300);
					dialog.setModal(true);
					JPanel panel = new JPanel();
					panel.setLayout(null);
					JLabel label = new JLabel("새 비밀번호를 입력");
					JPasswordField pwfield = new JPasswordField(20);
					JLabel label2 = new JLabel("새 비밀번호 재확인");
					JPasswordField pwfield2 = new JPasswordField(20);
					JButton btnChange = new JButton("변경");
					JButton btnCancle = new JButton("취소");
					
					panel.add(label);
					panel.add(pwfield);
					panel.add(label2);
					panel.add(pwfield2);
					panel.add(btnChange);
					panel.add(btnCancle);
					dialog.add(panel);
					label.setBounds(20, 20, 200, 20);
					pwfield.setBounds(150, 20, 200, 20);
					label2.setBounds(20, 40, 300, 20);
					btnChange.setBounds(40, 200, 100, 30);
					dialog.add(panel, BorderLayout.CENTER);
					dialog.setLocationRelativeTo(null);

					dialog.setVisible(true);
				}
			});
	
			JButton btnImageCh = new JButton("프로필사진 변경");
			btnImageCh.setFont(new Font("굴림", Font.BOLD, 11));
			btnImageCh.setBounds(305, 183, 125, 30);
			getContentPane().add(btnImageCh);
			btnImageCh.setBackground(Color.white);
			btnImageCh.setBorderPainted(false);
	
			showGUI();
			setResizable(false);
			setLocationRelativeTo(null);
		}
	
		private void showGUI() {
			setSize(469, 300);
			setVisible(true);
	
		}
	
	}
