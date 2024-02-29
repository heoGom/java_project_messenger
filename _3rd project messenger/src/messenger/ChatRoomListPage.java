package messenger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
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
	private String id;
	private int status;

	public ChatRoomListPage(User user) {
		getContentPane().setBackground(new Color(233, 255, 223));
		this.user = user;
		list = privateChatUserList(user);
		showGUI();
		extracted();

	}

	private void showGUI() {
		setSize(400, 500);
		setVisible(true);

	}

	private void extracted() {
		getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(250, 255, 243));
		panel.setBounds(0, 0, 384, 50);
		panel.setPreferredSize(new Dimension(10, 50));
		panel.setMinimumSize(new Dimension(10, 50));
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("\uCC44\uD305\uCC3D \uBAA9\uB85D");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel, BorderLayout.CENTER);

		JPanel chatPnl = new JPanel();
		chatPnl.setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane(chatPnl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 55, 362, 398);
		getContentPane().add(scrollPane);
		chatPnl.setLayout(new BoxLayout(chatPnl, BoxLayout.Y_AXIS));
		JPanel pbPnl = new JPanel();
		pbPnl.setBackground(Color.WHITE);
		pbPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JLabel labelpb = new JLabel("단체방");
		pbPnl.add(labelpb);
		chatPnl.add(pbPnl);
		labelpb.setHorizontalAlignment(SwingConstants.CENTER);

		Dimension preferredSizepb = new Dimension(pbPnl.getPreferredSize());
		preferredSizepb.height = 50; // 원하는 세로 크기로 조절
		pbPnl.setPreferredSize(preferredSizepb);

		// 가로 길이 창에 맞추기
		pbPnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, pbPnl.getPreferredSize().height));
		// 테두리 표현

		pbPnl.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!MainPage.openingPublic) {
					new PublicChatClient(user);
					MainPage.openingPublic = true;
				}

			}

		});

		for (int i = 0; i < list.size(); i++) {
			readStatus(list.get(i).getId());
			final int INDEX = i;
			JPanel userpnl = new JPanel();
			userpnl.setBackground(Color.WHITE);
			userpnl.setLayout(new BoxLayout(userpnl, BoxLayout.X_AXIS));
			Dimension preferredSize = new Dimension(panel.getWidth(), 50); // 원하는 크기로 조절
			userpnl.setPreferredSize(preferredSize);
			userpnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));

			JLabel label = new JLabel(list.get(i).getNick());
			JLabel imageLbl = new JLabel();
			imageLbl.setBounds(12, 12, 40, 40);
			imageLbl.setMaximumSize(new Dimension(40, 40));
			setImageLbl(list.get(i), imageLbl);
			JLabel emptyLbl = new JLabel();
			emptyLbl.setMaximumSize(new Dimension(10, Integer.MAX_VALUE));
			JLabel emptyLbl2 = new JLabel();
			emptyLbl2.setMaximumSize(new Dimension(20, Integer.MAX_VALUE));
			JLabel emptyLbl3 = new JLabel();
			emptyLbl3.setMaximumSize(new Dimension(20, Integer.MAX_VALUE));
			JLabel lblStatus = new JLabel();
			if (status == 1) {
				lblStatus.setText("접속중");
				lblStatus.setForeground(new Color(50, 205, 50));
			} else {
				lblStatus.setText("비접속");
				lblStatus.setForeground(Color.RED);
			}
			userpnl.add(emptyLbl);
			userpnl.add(imageLbl);
			userpnl.add(emptyLbl2);
			userpnl.add(label);
			userpnl.add(emptyLbl3);
			userpnl.add(lblStatus);

			userpnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			userpnl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!MainPage.openingList.contains(list.get(INDEX))) {
						new PrivateChatClient(user, list.get(INDEX));
						MainPage.openingList.add(list.get(INDEX));
					}

				}
			});
			imageLbl.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (list.get(INDEX).image != null) {
						new showProfileImage(list.get(INDEX));
					}
				}
			});

			chatPnl.add(userpnl);
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

						return new User(iid, pw, nick, image, 0);
					} else {
						image = null;
						return new User(iid, pw, nick, image, 0);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void readStatus(String str) {
		String sql = "SELECT * FROM jae.user where id = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, str);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					id = rs.getString("id");
					status = rs.getInt("status");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private ImageIcon blobToImageIcon(Blob blob) throws SQLException {
		if (blob != null) {
			byte[] bytes = blob.getBytes(1, (int) blob.length());
			return new ImageIcon(bytes);
		}
		return null;
	}

	private void setImageLbl(User u, JLabel imageLbl) {
		if (u.getImage() != null) {
			ImageIcon icon = u.getImage();
			Image scaledImage = icon.getImage().getScaledInstance(imageLbl.getWidth(), imageLbl.getHeight(),
					Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			imageLbl.setIcon(scaledIcon);
		}
	}

}
