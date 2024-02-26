import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class ChatRoomListPage extends JFrame {

	User user;
	List<User> list;

	public ChatRoomListPage(User user) {
		this.user = user;
		list = privateChatUserList(user);
		showGUI();
		extracted();

	}

	private void showGUI() {
		setSize(500, 500);
		setVisible(true);

	}

	private void extracted() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 50));
		panel.setMinimumSize(new Dimension(10, 50));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("\uCC44\uD305\uCC3D \uBAA9\uB85D");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		JLabel labelpb = new JLabel("단체방");

		Dimension preferredSizepb = new Dimension(labelpb.getPreferredSize());
		preferredSizepb.height = 50; // 원하는 세로 크기로 조절
		labelpb.setPreferredSize(preferredSizepb);

		// 가로 길이 창에 맞추기
		labelpb.setMaximumSize(new Dimension(Integer.MAX_VALUE, labelpb.getPreferredSize().height));
		// 테두리 표현
		labelpb.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		labelpb.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(!MainPage.openingPublic) {
				new PublicChatClient(user);
				MainPage.openingPublic = true;
				}
				
			}

		});
		panel_1.add(labelpb);

		for (int i = 0; i < list.size(); i++) {
			final int INDEX = i;
			JLabel label = new JLabel(list.get(i).getNick());

			Dimension preferredSize = new Dimension(label.getPreferredSize());
			preferredSize.height = 50; // 원하는 세로 크기로 조절
			label.setPreferredSize(preferredSize);

			// 가로 길이 창에 맞추기
			label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height));
			// 테두리 표현
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(!MainPage.openingList.contains(list.get(INDEX))){
						new PrivateChatClient(user, list.get(INDEX));
						MainPage.openingList.add(list.get(INDEX));
					}
					
				}
			});

			panel_1.add(label);
		}
	}

	private List<User> privateChatUserList(User user) {
		List<User> list = new ArrayList<>();

		String sql = "SELECT DISTINCT CASE WHEN sender_id " + "= ? THEN receiver_id "
				+ "WHEN receiver_id = ? THEN sender_id" + " END AS partner " + "FROM private_chatlist "
				+ "WHERE sender_id = ? " + "OR receiver_id = ?;";

		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getId());
			stmt.setString(2, user.getId());
			stmt.setString(3, user.getId());
			stmt.setString(4, user.getId());
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String id = rs.getString("partner");
					User a = readDB(conn, id);
					list.add(a);

				}
				return list;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private User readDB(Connection conn, String id) {
		String sql = "select id,password,nickname,profilePhoto from jae.user where id = ?";
		Blob blob;
		ImageIcon image;

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);

			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					String iid = rs.getString("id");
					String pw = rs.getString("password");
					String nick = rs.getString("nickname");
					if (rs.getBlob("profilePhoto") != null) {
						blob = rs.getBlob("profilePhoto");
						image = blobToImageIcon(blob);

						return new User(iid, pw, nick, image);
					} else {
						image = null;
						return new User(iid, pw, nick, image);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ImageIcon blobToImageIcon(Blob blob) throws SQLException {
		if (blob != null) {
			byte[] bytes = blob.getBytes(1, (int) blob.length());
			return new ImageIcon(bytes);
		}
		return null;
	}


}
