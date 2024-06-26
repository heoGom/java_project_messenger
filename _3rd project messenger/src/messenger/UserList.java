package messenger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JButton;

public class UserList extends JFrame {
	User user;

	private JPanel panel;

	private JPanel pnl;

	private JLabel lbl;
	MainPage mainPage;
	MembershipDAO membershipDAO;

	public JLabel lbl2;

	public List<JLabel> lbl2List = new ArrayList<>();
	public List<Integer> lbl2List2 = new ArrayList<>();

	private JLabel lblNewLabel_2;

	public JLabel lblNewLabel;

	public UserList(User user) {
		getContentPane().setBackground(new Color(250, 255, 243));
		this.user = user;
		this.membershipDAO = new MembershipDAO();
		extracted();
		createPanel();
		readStatus();
		showGUI();

	}

	private void showGUI() {
		setSize(441, 553);
		setVisible(true);
		setResizable(false);
	}

	private void extracted() {
		getContentPane().setLayout(null);
		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(12, 10, 50, 50);
		getContentPane().add(lblNewLabel);

		JLabel lblNick = new JLabel(user.getNick());
		lblNick.setBounds(83, 10, 200, 15);
		getContentPane().add(lblNick);

		lblNewLabel_2 = new JLabel("가입자 목록 몇명인지?");
		lblNewLabel_2.setBounds(25, 79, 110, 15);
		getContentPane().add(lblNewLabel_2);
		lblNewLabel_2.setText("가입자 수:" + countUser());

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 108, 402, 402);
		getContentPane().add(scrollPane);
	}

	public void updatePanel() {
		panel.removeAll(); // 패널의 모든 컴포넌트를 제거
		lbl2List.clear();
		readStatus();
		createPanel(); // 패널을 다시 생성
		panel.revalidate();
		panel.repaint();
	}
	public void createPanel() {
		for (User u : user.list) {

			pnl = new JPanel();
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			pnl.setBackground(Color.WHITE);
			Dimension preferredSize = new Dimension(panel.getWidth(), 50); // 원하는 크기로 조절
			pnl.setPreferredSize(preferredSize);
			pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
			pnl.setLayout(new BoxLayout(pnl, BoxLayout.X_AXIS));
			JLabel imageLbl = new JLabel();
			imageLbl.setMaximumSize(new Dimension(40, 40));
			imageLbl.setBounds(12, 12, 40, 40);
			lbl = new JLabel(u.nick);
			lbl2 = new JLabel("test");
			lbl2List.add(lbl2);
			JLabel emptyLbl = new JLabel();
			emptyLbl.setMaximumSize(new Dimension(10, Integer.MAX_VALUE));
			JLabel emptyLbl2 = new JLabel();
			emptyLbl2.setMaximumSize(new Dimension(20, Integer.MAX_VALUE));
			JLabel emptyLbl3 = new JLabel();
			emptyLbl3.setMaximumSize(new Dimension(20, Integer.MAX_VALUE));

			pnl.add(emptyLbl);
			pnl.add(imageLbl);
			setImageLbl(u, imageLbl);
			pnl.add(emptyLbl2);
			pnl.add(lbl);
			pnl.add(emptyLbl3);
			pnl.add(lbl2);
			panel.add(pnl);

			imageLbl.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (u.image != null) {
						if (MainPage.showProfileImage == null) {
							MainPage.showProfileImage = new ShowProfileImage(u);
						} else {
							MainPage.showProfileImage.dispose();;
							MainPage.showProfileImage = new ShowProfileImage(u);
						}
					}
				}

			});

			pnl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!MainPage.openingList.contains(u)) {
						new PrivateChatClient(user, u);
						MainPage.openingList.add(u);

					}
				}
			});

		}
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

	public void readStatus() {
		String sql = "SELECT * FROM jae.user where nickname != ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, user.getNick()); // 파라미터 값 설정

			try (ResultSet rs = stmt.executeQuery()) {
				int index = 0; // lbl2List의 인덱스

				while (rs.next() && index < lbl2List.size()) {
					int userStatus = rs.getInt("status");
					if (userStatus == 0) {
						lbl2List.get(index).setText("비접속");
						lbl2List.get(index).setForeground(Color.RED);
					} else {
						lbl2List.get(index).setText("접속중");
						lbl2List.get(index).setForeground(new Color(50, 205, 50));
					}

					index++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	
	public int countUser() {
		String sql = "select count(*) from jae.user;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				int a = rs.getInt(1);
				return a - 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
