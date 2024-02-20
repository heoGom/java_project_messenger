import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PrivateChatRoom extends JFrame {
	private User user;
	private User another;
	private List<TextDate> TDList;

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
		panel.setPreferredSize(new Dimension(10, 55));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel another_Pt_Lbl = new JLabel("\uC0AC\uC9C4");
		another_Pt_Lbl.setBorder(new LineBorder(new Color(0, 0, 0)));
		another_Pt_Lbl.setBounds(12, 9, 35, 35);
		panel.add(another_Pt_Lbl);

		JLabel another_NN_Lbl = new JLabel(another.getNick());
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

		JButton btnNewButton = new JButton("\uC804   \uC1A1");
		panel_3.add(btnNewButton, BorderLayout.EAST);

		JButton btnNewButton_1 = new JButton("\uD30C\uC77C");
		panel_3.add(btnNewButton_1, BorderLayout.WEST);

		JTextArea textArea = new JTextArea();
		panel_1.add(textArea, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		if (TDList != null) {
			for (int i = 0; i < TDList.size(); i++) {
				JLabel label = new JLabel(TDList.get(i).getText());
				if (TDList.get(i).getSender_id().equals(user.getId())) {
					label.setHorizontalAlignment(SwingConstants.RIGHT);
				}

				Dimension preferredSize = new Dimension(label.getPreferredSize());
				// preferredSize.height = 200; // 원하는 세로 크기로 조절
				label.setPreferredSize(preferredSize);

				// 가로 길이 창에 맞추기
				label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height));
				// 테두리 표현
				label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

				panel_2.add(label);
			}
		}
	}

	private void showGUI() {
		setSize(450, 500);
		setVisible(true);

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
					System.out.println(a);
				}
				return td;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	 

}
