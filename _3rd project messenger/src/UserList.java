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
import java.util.List;

public class UserList extends JFrame {
	User user;

	private JPanel panel;

	private JPanel pnl;

	private JLabel lbl;
	MainPage mainPage;
	MembershipDAO membershipDAO;

	private JLabel lbl2;

	private List<JLabel> lbl2List = new ArrayList<>();

	public UserList(User user) {
		this.user = user;
		this.membershipDAO = new MembershipDAO();
		extracted();
		showGUI();

	}

	private void showGUI() {
		setSize(441, 553);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void extracted() {
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("내사진");
		lblNewLabel.setBounds(12, 10, 57, 15);
		getContentPane().add(lblNewLabel);

		JLabel lblNick = new JLabel(user.getNick());
		lblNick.setBounds(150, 10, 200, 15);
		getContentPane().add(lblNick);

		JLabel lblNewLabel_2 = new JLabel("가입자 목록 몇명인지?");
		lblNewLabel_2.setBounds(25, 79, 110, 15);
		getContentPane().add(lblNewLabel_2);
		lblNewLabel_2.setText("가입자 수:"+countUser());

		panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));

		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 108, 402, 402);
		getContentPane().add(scrollPane);

		for (User u : user.list) {
			if (!u.getNick().equals(user.getNick())) {
				pnl = new JPanel();
				pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				lbl = new JLabel(u.getNick());
				lbl2 = new JLabel("test");
				lbl2List.add(lbl2);
				pnl.add(lbl);
				pnl.add(lbl2);
				panel.add(pnl);

				pnl.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						new PrivateChatRoom(user, u);
					}
				});
			}
		}

		JButton resetbtn = new JButton("새로고침");
		resetbtn.setBounds(317, 75, 97, 23);
		getContentPane().add(resetbtn);

		resetbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readStatus();
			}
		});
	}
	public void readStatus() {
        String sql = "SELECT * FROM jae.user";
        try (Connection conn = MySqlConnectionProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
	        		int a=   rs.getInt(1); 
	        		return a-1;
	            }
	        } catch (SQLException e) {
				e.printStackTrace();
			}
	        return 0;
	}

}
