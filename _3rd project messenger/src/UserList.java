import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserList extends JFrame {
	User user;

	private JPanel panel;

	private JPanel pnl;

	private JLabel lbl;
	MainPage mainPage;
	MembershipDAO membershipDAO;

	public JLabel lbl2;

	public List<JLabel> lbl2List = new ArrayList<>();

	private JLabel lblNewLabel_2;

	public UserList(User user) {
		this.user = user;
		this.membershipDAO = new MembershipDAO();
		extracted();
		createPanel();
		showGUI();
		System.out.println(user.getNick());

	}

	private void showGUI() {
		setSize(441, 553);
		setVisible(true);
		
	}

	private void extracted() {
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("내사진");
		lblNewLabel.setBounds(12, 10, 57, 15);
		getContentPane().add(lblNewLabel);

		JLabel lblNick = new JLabel(user.getNick());
		lblNick.setBounds(150, 10, 200, 15);
		getContentPane().add(lblNick);

		lblNewLabel_2 = new JLabel("가입자 목록 몇명인지?");
		lblNewLabel_2.setBounds(25, 79, 110, 15);
		getContentPane().add(lblNewLabel_2);
		lblNewLabel_2.setText("가입자 수:" + countUser());

		panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));

		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 108, 402, 402);
		getContentPane().add(scrollPane);

		JButton resetbtn = new JButton("새로고침");
		resetbtn.setBounds(317, 75, 97, 23);
		getContentPane().add(resetbtn);

		resetbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
				mainPage = new MainPage(user);
				mainPage.dispose();
				mainPage.userListbtn.doClick();
			}
		});
	}

	public void createPanel() {
		for (User u : user.list) {

			pnl = new JPanel();
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			lbl = new JLabel(u.nick);
			lbl2 = new JLabel("test");
			lbl2List.add(lbl2);
			pnl.add(lbl);
			pnl.add(lbl2);
			panel.add(pnl);

			pnl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(!ChatRoomListPage.openingList.contains(u)){
						new PrivateChatClient(user, u);
						ChatRoomListPage.openingList.add(u);
						
					}
				}
			});

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
					} else {
						lbl2List.get(index).setText("접속중");
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
