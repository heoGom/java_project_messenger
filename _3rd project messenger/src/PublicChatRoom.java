
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

public class PublicChatRoom extends JFrame {
	private User user;
	private List<PublicTextDate> TDList;
	private JPanel panel_2;

	public JTextArea sendTextArea;
	public JButton sendbtn;
	private JScrollPane scrollPane;

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
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(10, 55));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel another_NN_Lbl = new JLabel("단체방");
		another_NN_Lbl.setBounds(20, 20, 64, 15);
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

		getContentPane().add(scrollPane, BorderLayout.CENTER);
		if (TDList != null) {
			for (PublicTextDate sen : TDList) {

				addChat(sen.getText(),sen.getSender_id(), user.getId().equals(sen.getSender_id()), sen.getTime());
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
					PublicTextDate a = new PublicTextDate(sender_id, text, time);
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
			if(!sender_id.equals(user.id)) {
			subMessageLabel.setForeground(new Color(color[0],color[1],color[2]));
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
				if(once) {
					JLabel senderIdLbl = new JLabel(getNickFromId(sender_id));
					messagePanel.add(senderIdLbl);
					senderIdLbl.setFont(new Font("굴림", Font.PLAIN, 20));
					senderIdLbl.setForeground(new Color(color[0],color[1],color[2]));
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
	private String getNickFromId(String sender_id) {
		user.readAllUser(user.nick);
		for(User u : user.list) {
			if(u.id.equals(sender_id)) {
				return u.nick;
			}
		}
		return "누구쎄용?";
	}
	private int[] RGBById(String sender_id) {
		int r = Math.abs((sender_id.length()*60)%255);
		if(r>180) {
			r-=120;
		}
		int g = Math.abs((sender_id.length()*60)%255);
		if(g>180) {
			g-=120;
		}
		int b = Math.abs((sender_id.length()*70)%255);
		if(b>180) {
			b-=120;
		}
		int[] a = {r,g,b};
		return a;
	}
	

}
