
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class PublicChatRoom extends JFrame {
	private User user;
	private List<PublicTextDate> TDList;
	private JPanel panel_2;

	public JTextArea sendTextArea;
	public JButton sendbtn;
	private JScrollPane scrollPane;
	public JButton sendFileBtn;

	public PublicChatRoom(User user) {
		this.user = user;
		new TextDate();
		TDList = readDB();
		extracted();
		showGUI();

	}

	private void extracted() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 55));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel another_NN_Lbl = new JLabel("단체방");
		another_NN_Lbl.setBounds(20, 20, 64, 15);
		panel.add(another_NN_Lbl);
		
		JButton btnVote = new JButton("투표 하기");
		btnVote.setBounds(357, 12, 77, 30);
		panel.add(btnVote);
		btnVote.setMargin(new Insets(2, 10, 2, 10));
		btnVote.addActionListener(new ActionListener() {
			private VoteMainPage voteMainPage;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (voteMainPage == null || !voteMainPage.isVisible()) {
					voteMainPage = new VoteMainPage(user);
				} else {
					voteMainPage.toFront();
				}
				int mainPageX = getX();
				int mainPageY = getY();
				voteMainPage.setLocation(mainPageX + voteMainPage.getWidth(), mainPageY);
			}
		});

		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 110));
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(10, 30));
		panel_1.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));

		sendbtn = new JButton("\uC804   \uC1A1");
		panel_3.add(sendbtn, BorderLayout.EAST);

		sendFileBtn = new JButton("\uD30C\uC77C \uBCF4\uB0B4\uAE30");
		sendFileBtn.setMargin(new Insets(2, 10, 2, 10));
		
		panel_3.add(sendFileBtn, BorderLayout.WEST);

		sendTextArea = new JTextArea();
		sendTextArea.setLineWrap(true);
		panel_1.add(sendTextArea, BorderLayout.CENTER);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		panel_2.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(final ContainerEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						scrollDown();
					}
				});

			}
		});
		scrollPane = new JScrollPane(panel_2);
		scrollPane.getVerticalScrollBar().setUnitIncrement(12); // 스크롤바 속도 조절

		getContentPane().add(scrollPane, BorderLayout.CENTER);
		if (TDList != null) {
			for (PublicTextDate sen : TDList) {
				if (sen.getText() != null) {
					addChat(sen.getText(), sen.getSender_id(), user.getId().equals(sen.getSender_id()), sen.getTime());
				} else {
					addFile(sen.getSender_id(), sen.getTime(), sen.getFile_name());

				}
			}
//	         scrollDown();
		}

	}

	private void showGUI() {
		setSize(450, 500);
		setVisible(true);
		setLocationRelativeTo(null);

	}

	private List<PublicTextDate> readDB() {
		List<PublicTextDate> td = new ArrayList<>();
		// 물음표 4개
		String sql = "SELECT * FROM jae.public_chatlist ORDER BY text_time;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String sender_id = rs.getString("sender_id");
					String text = rs.getString("text");
					Timestamp time = rs.getTimestamp("text_time");
					String file_name = rs.getString("file_name");
					PublicTextDate a = new PublicTextDate(sender_id, text, time, file_name);
					td.add(a);
				}
				return td;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void addChat(String message, String sender_id, boolean sentByMe, Timestamp time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		String time1 = sdf.format(time);

		// 문자열을 10자씩 나누어 처리
		int startIndex = 0;
		boolean once = true;

		int[] color = RGBById(sender_id);
		while (startIndex < message.length()) {
			int endIndex = Math.min(startIndex + 10, message.length());
			String subMessage = message.substring(startIndex, endIndex);
			startIndex = endIndex;
			// 새로운 패널을 생성하여 라벨들을 추가
			JPanel messagePanel = new JPanel();
			messagePanel.setBackground(Color.WHITE);
			messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
			// messagePanel.setBorder(BorderFactory.createLineBorder(Color.black));

			// 메시지를 담은 JLabel 생성
			JLabel subMessageLabel = new JLabel(subMessage);
			subMessageLabel.setFont(new Font("굴림", Font.PLAIN, 20));
			if (!sender_id.equals(user.id)) {
				subMessageLabel.setForeground(new Color(color[0], color[1], color[2]));
			}
			// setBlackBorder(subMessageLabel);

			// 패널에 라벨 추가
			// 시간을 담은 JLabel 생성
			JLabel timeLabel = new JLabel("[" + time1 + "]");
			// setBlackBorder(timeLabel);

			// 메시지를 오른쪽에 보내는 경우
			if (sentByMe) {
				messagePanel.add(Box.createHorizontalGlue());
				if (startIndex >= message.length()) {
					messagePanel.add(timeLabel);
				}
				messagePanel.add(Box.createRigidArea(new Dimension(5, 0))); // 간격 조절
				messagePanel.add(subMessageLabel);
			} else { // 메시지를 왼쪽에서 받는 경우
				messagePanel.add(subMessageLabel);
				messagePanel.add(Box.createRigidArea(new Dimension(5, 0))); // 간격 조절
				if (once) {
					JLabel senderIdLbl = new JLabel(getNickFromId(sender_id));
					messagePanel.add(senderIdLbl);
//					senderIdLbl.setFont(new Font("굴림", Font.PLAIN, 20));
					senderIdLbl.setForeground(new Color(color[0], color[1], color[2]));
					messagePanel.add(Box.createRigidArea(new Dimension(5, 0))); // 간격 조절
				}
				once = false;
				if (startIndex >= message.length()) {
					messagePanel.add(timeLabel);
				}
				messagePanel.add(Box.createHorizontalGlue());
			}

			// 현재 텍스트에 추가
			panel_2.add(messagePanel);
			panel_2.revalidate();
			panel_2.repaint();
//	         scrollDown();
			sendTextArea.requestFocusInWindow();
			// 다음 부분 처리를 위해 시작 인덱스 갱신

		}

		// 스크롤을 자동으로 내려가게끔

	}

	public void addFile(String sender_id, Timestamp time, String file_name) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		String time1 = sdf.format(time);

		// 문자열을 10자씩 나누어 처리

		int[] color = RGBById(sender_id);
		// 새로운 패널을 생성하여 라벨들을 추가
		JPanel messagePanel = new JPanel();
		messagePanel.setBackground(Color.WHITE);
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
		// messagePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		// 메시지를 담은 JLabel 생성
		JLabel fileLabel = new JLabel(file_name);
		fileLabel.setBorder(new LineBorder(new Color(0, 0, 0)));

		fileLabel.setFont(new Font("굴림", Font.PLAIN, 12));
		if (!sender_id.equals(user.id)) {
			fileLabel.setForeground(new Color(color[0], color[1], color[2]));
		}
		// setBlackBorder(subMessageLabel);

		// 패널에 라벨 추가
		// 시간을 담은 JLabel 생성
		JLabel timeLabel = new JLabel("[" + time1 + "]");
		// setBlackBorder(timeLabel);

		// 메시지를 오른쪽에 보내는 경우
		if (sender_id.equals(user.id)) {
			messagePanel.add(Box.createHorizontalGlue());
			messagePanel.add(timeLabel);
			messagePanel.add(Box.createRigidArea(new Dimension(5, 0))); // 간격 조절
			messagePanel.add(fileLabel);
		} else { // 메시지를 왼쪽에서 받는 경우
			messagePanel.add(fileLabel);
			messagePanel.add(Box.createRigidArea(new Dimension(5, 0))); // 간격 조절
			JLabel senderIdLbl = new JLabel(getNickFromId(sender_id));
			messagePanel.add(senderIdLbl);
//			senderIdLbl.setFont(new Font("굴림", Font.PLAIN, 12));
			senderIdLbl.setForeground(new Color(color[0], color[1], color[2]));
			messagePanel.add(Box.createRigidArea(new Dimension(5, 0))); // 간격 조절
			messagePanel.add(timeLabel);
			messagePanel.add(Box.createHorizontalGlue());
		}
		fileLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(PublicChatRoom.this, "다운받으시겠습니까?", "파일다운", JOptionPane.INFORMATION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int chooserResult = fileChooser.showSaveDialog(null);
					if (chooserResult == JFileChooser.APPROVE_OPTION) {
						// 사용자가 저장 경로를 선택한 경우
						String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();

						System.out.println("다운로드 시작. 저장 경로: " + selectedFilePath);
						String sql = "SELECT * FROM public_chatlist WHERE " + "text_time = ? " + "AND sender_id = ? "
								+ "AND file_name = ?;";
						try (Connection conn = MySqlConnectionProvider.getConnection();
								PreparedStatement stmt = conn.prepareStatement(sql)) {
							stmt.setTimestamp(1, time);
							stmt.setString(2, sender_id);
							stmt.setString(3, file_name);
							System.out.println("time : " + time);
							System.out.println("sender_id : " + sender_id);
							System.out.println("file_name : " + file_name);

							try (ResultSet rs = stmt.executeQuery()) {
								if (rs.next()) {
									String encoded = rs.getString("file");
									Decoder decoder = Base64.getDecoder();
									byte[] decode = decoder.decode(encoded);
									try {
										Files.write(Paths.get(selectedFilePath + File.separator + file_name), decode);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} 
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else {
						// 사용자가 "아니오"를 선택한 경우 또는 다이얼로그를 닫은 경우
						System.out.println("다운로드 취소");
						// 여기에 취소 또는 다른 처리 코드를 추가
					}
				}
			}
		});

		// 현재 텍스트에 추가
		panel_2.add(messagePanel);
		panel_2.revalidate();
		panel_2.repaint();
//	         scrollDown();
		sendTextArea.requestFocusInWindow();
		// 다음 부분 처리를 위해 시작 인덱스 갱신

	}

	// private void setBlackBorder(JLabel label) {
	// label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	// }

	private void scrollDown() {
	    SwingUtilities.invokeLater(() -> {
	        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
	        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
	    });
	}


	public void addBtnListener(ActionListener listener) {
		sendbtn.addActionListener(listener);
	}

	public void addTextAreaListener(KeyListener listener) {
		sendTextArea.addKeyListener(listener);
	}

	private String getNickFromId(String sender_id) {
		user.readAllUser(user.nick);
		for (User u : user.list) {
			if (u.id.equals(sender_id)) {
				return u.nick;
			}
		}
		return "누구쎄용?";
	}

	private int[] RGBById(String sender_id) {
		int r = Math.abs((sender_id.length() * 60) % 255);
		if (r > 180) {
			r -= 120;
		}
		int g = Math.abs((sender_id.length() * 60) % 255);
		if (g > 180) {
			g -= 120;
		}
		int b = Math.abs((sender_id.length() * 70) % 255);
		if (b > 180) {
			b -= 120;
		}
		int[] a = { r, g, b };
		return a;
	}

}
