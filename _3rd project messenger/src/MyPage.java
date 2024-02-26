import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics2D;

public class MyPage {
	User user;
	MainPage mainPage;
	private File selectedImageFile;
	private JLabel nick;
	public JLabel picture;
	public JLabel currentPhoto;
	private ImageIcon currentSelectedIcon;
	private MyPageDAO dao;
	private Image image;
	public ImageIcon scaledIcon;
	public ImageIcon scaledIcon2;
	private JDialog mainDL;
	private JLabel userScore;
	public JLabel score;

	public MyPage(User user, MainPage mainPage) {
		this.user = user;
		this.mainPage = mainPage;
		
		mainDL = new JDialog();
		mainDL.setTitle("마이 프로필");
		mainDL.setModal(true);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JLabel myImage = new JLabel("현재 사진");
		myImage.setBounds(333, 24, 60, 24);
		panel.add(myImage);
		picture = new JLabel(user.getImage());
		picture.setBounds(285, 60, 150, 150);
		panel.add(picture);

		JLabel userNick = new JLabel("내 별명");
		userNick.setBounds(58, 29, 60, 15);
		panel.add(userNick);
		nick = new JLabel(user.getNick());
		nick.setFont(new Font("굴림", Font.BOLD, 16));
		nick.setBounds(58, 95, 91, 30);
		panel.add(nick);
		
		userScore = new JLabel("내 점수");
		userScore.setFont(new Font("굴림", Font.BOLD, 12));
		userScore.setBounds(189, 29, 60, 15);
		panel.add(userScore);
		score = new JLabel(user.getHighScore() + "초");
		score.setFont(new Font("굴림", Font.PLAIN, 16));
		score.setBounds(175, 103, 75, 15);
		panel.add(score);

		JButton btnNickCh = new JButton("닉네임 변경");
		btnNickCh.setFont(new Font("굴림", Font.BOLD, 11));
		btnNickCh.setBounds(20, 220, 120, 30);
		panel.add(btnNickCh);
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
				dialog.getContentPane().add(panel, BorderLayout.CENTER);
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
								mainPage.nick_lbl.setText(changetx.getText());
								nick.setText(changetx.getText());
								dao = new MyPageDAO();
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
					}
				});
				dialog.setVisible(true); // 모달이 적용되면 setVisible이 아래흐름으로 안흘러감.
			}
		});

		JButton btnPwCh = new JButton("비밀번호 변경");
		btnPwCh.setFont(new Font("굴림", Font.BOLD, 11));
		btnPwCh.setBounds(160, 220, 120, 30);
		panel.add(btnPwCh);
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
				dialog.getContentPane().add(panel);
				label.setBounds(20, 60, 150, 20);
				pwfield.setBounds(150, 60, 200, 20);
				label2.setBounds(20, 120, 150, 20);
				pwfield2.setBounds(150, 120, 200, 20);
				btnChange.setBounds(40, 200, 100, 30);
				btnCancle.setBounds(240, 200, 100, 30);
//				dialog.getContentPane().add(panel, BorderLayout.CENTER);
				dialog.setLocationRelativeTo(null);

				btnChange.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String password1 = new String(pwfield.getPassword());
						String password2 = new String(pwfield2.getPassword());
						if (password1.isEmpty() || password2.isEmpty()) {
							JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요", "경고", JOptionPane.ERROR_MESSAGE);
						} else if (user.getPw().equals(password2)) {
							JOptionPane.showMessageDialog(null, "전에 비밀번호와 다른비밀번호를 입력하세요.", "경고",
									JOptionPane.ERROR_MESSAGE);
						} else {
							if (password1.equals(password2)) {
								JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다.", "알림",
										JOptionPane.INFORMATION_MESSAGE);
								dao = new MyPageDAO();
								dao.changePW(password2, user.id);
								user.setPw(password2);
								dialog.dispose();
							} else {
								JOptionPane.showMessageDialog(null, "비밀번호가 맞지않습니다.", "경고", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});

				btnCancle.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}
				});

				dialog.setVisible(true);
			}
		});

		JButton btnImageCh = new JButton("프로필사진 변경");
		btnImageCh.setFont(new Font("굴림", Font.BOLD, 11));
		btnImageCh.setBounds(300, 220, 125, 30);
		panel.add(btnImageCh);
		btnImageCh.setBackground(Color.white);
		btnImageCh.setBorderPainted(false);

		btnImageCh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				JFrame frame = new JFrame();
				dialog.setTitle("프로필 사진 변경");
				dialog.setSize(400, 300);
				dialog.setModal(true);
				JPanel panel = new JPanel();
				panel.setLayout(null);
				JLabel label = new JLabel("현재 사진");
				currentPhoto = new JLabel(user.image);
				panel.add(label);
				panel.add(currentPhoto);
				JButton btnfindPhoto = new JButton("사진 찾기");
				JButton btnOK = new JButton("적용");
				JButton btnReturn = new JButton("되돌리기");
				JButton btnDelete = new JButton("기본 사진으로 설정");
				panel.add(btnfindPhoto);
				panel.add(btnOK);
				panel.add(btnReturn);
				panel.add(btnDelete);
				label.setBounds(65, 0, 100, 100);
				currentPhoto.setBounds(20, 70, 150, 150);
				btnfindPhoto.setBounds(190, 75, 100, 30);
				btnOK.setBounds(190, 120, 90, 30);
				btnReturn.setBounds(190, 160, 90, 30);
				btnDelete.setBounds(190, 200, 150, 30);
				dialog.getContentPane().add(panel);
				dialog.setLocationRelativeTo(null);
				if (user.getImage() == null) {
					currentPhoto.setText("사진이 없습니다.");
				} else {
					ImageIcon icon = user.getImage();
					Image scaledImage = icon.getImage().getScaledInstance(currentPhoto.getWidth(),
							currentPhoto.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon scaledIcon = new ImageIcon(scaledImage);
					currentPhoto.setIcon(scaledIcon);

				}
				btnfindPhoto.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser fileChooser = new JFileChooser();
						FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "jpeg", "gif");
						fileChooser.setFileFilter(filter);
						fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fileChooser.setMultiSelectionEnabled(true);
						int result = fileChooser.showOpenDialog(frame);
						if (result == JFileChooser.APPROVE_OPTION) {
							File[] selectedFiles = fileChooser.getSelectedFiles();
							for (File file : selectedFiles) {
								System.out.println("Selected File: " + file.getAbsolutePath());
								label.setText("바뀐 사진");
								// 이미지 파일을 ImageIcon으로 읽어옵니다.
								ImageIcon selectedIcon = new ImageIcon(file.getAbsolutePath());
								// JLabel에 이미지를 설정합니다.
								currentPhoto.setIcon(selectedIcon);
								// 사용자가 선택한 이미지를 현재 선택된 아이콘으로 설정합니다.
								currentSelectedIcon = selectedIcon;
							}
							Image scaledImage = currentSelectedIcon.getImage().getScaledInstance(
									currentPhoto.getWidth(), currentPhoto.getHeight(), Image.SCALE_SMOOTH);
							ImageIcon scaledIcon = new ImageIcon(scaledImage);
							currentPhoto.setIcon(scaledIcon);
						}
					}
				});

				btnOK.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (currentSelectedIcon != null) {
							if (!(currentSelectedIcon.equals(user.getImage()))) {
								image = currentSelectedIcon.getImage();
								String userId = user.getId();
								scaledIcon = ImageScaler.getScaledImageIcon(image, 50, 50);
								mainPage.picture_lbl.setIcon(scaledIcon);
								scaledIcon2 = ImageScaler.getScaledImageIcon(image, 150, 150);
								user.setImage(scaledIcon2);
								picture.setIcon(scaledIcon2);
								dao = new MyPageDAO();
								dao.changeImage(image, userId);
								JOptionPane.showMessageDialog(null, "이미지 변경이 완료되었습니다.", "성공",
										JOptionPane.INFORMATION_MESSAGE);
								dialog.dispose();
							}
						} else {
							JOptionPane.showMessageDialog(null, "이미지를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
						}
					}
				});

				btnDelete.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						currentPhoto.setIcon(null);
						currentPhoto.setText("기본사진");
						dao = new MyPageDAO();
						dao.deleteImage(user.id);
						JOptionPane.showMessageDialog(null, "이미지가 성공적으로 삭제되었습니다.", "성공",
								JOptionPane.INFORMATION_MESSAGE);
						user.setImage(null);
						mainPage.picture_lbl.setIcon(currentPhoto.getIcon());
						picture.setIcon(currentPhoto.getIcon());
						dialog.dispose();
					}
				});

				btnReturn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						label.setText("현재 사진");
						ImageIcon icon = user.getImage();
						Image scaledImage = icon.getImage().getScaledInstance(currentPhoto.getWidth(),
								currentPhoto.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon scaledIcon = new ImageIcon(scaledImage);
						currentPhoto.setIcon(scaledIcon);
					}
				});
				dialog.setVisible(true);
			}

		});
		mainDL.getContentPane().add(panel);
		mainDL.setSize(469, 300);
		mainDL.setLocationRelativeTo(mainPage);
		mainDL.setResizable(false);
	}
	
	public void showGUI() {
		mainDL.setVisible(true);
	}
}	