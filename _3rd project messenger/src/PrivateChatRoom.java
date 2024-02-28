import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class PrivateChatRoom extends JFrame {
	private User user;
	private User another;
	private List<TextDate> TDList;
	private JPanel panel_2;

	public JTextArea sendTextArea;
	public JButton sendbtn;
	private JScrollPane scrollPane;
	private JLabel another_Pt_Lbl;

	public PrivateChatRoom(User user, User another) {
		this.user = user;
		this.another = another;
		new TextDate();
		TDList = readDB();
		extracted();
		showGUI();

	}

	private void extracted() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
		panel.setPreferredSize(new Dimension(10, 55));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		another_Pt_Lbl = new JLabel();
		another_Pt_Lbl.setBounds(12, 9, 35, 35);
		panel.add(another_Pt_Lbl);
		if (another.getImage() != null) {
			ImageIcon icon = another.getImage();
			Image scaledImage = icon.getImage().getScaledInstance(another_Pt_Lbl.getWidth(), another_Pt_Lbl.getHeight(),
					Image.SCALE_SMOOTH);
			ImageIcon scalecIcon = new ImageIcon(scaledImage);
			another_Pt_Lbl.setIcon(scalecIcon);
		}

		JLabel another_NN_Lbl = new JLabel(another.getNick());
		another_NN_Lbl.setFont(new Font("한컴 고딕", Font.BOLD, 13));
		another_NN_Lbl.setBounds(63, 20, 64, 15);
		panel.add(another_NN_Lbl);

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

		sendTextArea = new JTextArea();
		sendTextArea.setLineWrap(true);
		panel_1.add(sendTextArea, BorderLayout.CENTER);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.PINK);
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
			for (TextDate sen : TDList) {

				addChat(sen.getText(), user.getId().equals(sen.getSender_id()), sen.getTime());
			}
//         scrollDown();
		}

	}

	private void showGUI() {
		setSize(450, 500);
		setVisible(true);
		setLocationRelativeTo(null);

	}

	private List<TextDate> readDB() {
		List<TextDate> td = new ArrayList<>();
		// 물음표 4개
		String sql = "SELECT * FROM private_chatlist " + "WHERE (sender_id = ? AND receiver_id = ?) "
				+ "OR (sender_id = ? AND receiver_id = ?) " + "ORDER BY text_time;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getId());
			stmt.setString(2, another.getId());
			stmt.setString(3, another.getId());
			stmt.setString(4, user.getId());
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String sender_id = rs.getString("sender_id");
					String receiver_id = rs.getString("receiver_id");
					String text = rs.getString("text");
					Timestamp time = rs.getTimestamp("text_time");
					TextDate a = new TextDate(sender_id, receiver_id, text, time);
					td.add(a);
				}
				return td;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void addChat(String message, boolean sentByMe, Timestamp time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		String time1 = sdf.format(time);

		// 문자열을 10자씩 나누어 처리
		int startIndex = 0;
		int[] color = RGBById(another.id);
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
			if (!sentByMe) {
				subMessageLabel.setForeground(new Color(color[0], color[1], color[2]));
			}

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
				if (startIndex >= message.length()) {
					messagePanel.add(timeLabel);
				}
				messagePanel.add(Box.createHorizontalGlue());
			}

			// 현재 텍스트에 추가
			panel_2.add(messagePanel);
			panel_2.revalidate();
			panel_2.repaint();
			messagePanel.scrollRectToVisible(messagePanel.getBounds());
//         scrollDown();
			sendTextArea.requestFocusInWindow();
			// 다음 부분 처리를 위해 시작 인덱스 갱신

		}

		// 스크롤을 자동으로 내려가게끔

	}

	// private void setBlackBorder(JLabel label) {
	// label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	// }

	private void scrollDown() {
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.setValue(verticalScrollBar.getMaximum());
	}

	public void addBtnListener(ActionListener listener) {
		sendbtn.addActionListener(listener);
	}

	public void addTextAreaListener(KeyListener listener) {
		sendTextArea.addKeyListener(listener);
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