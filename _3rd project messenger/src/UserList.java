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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserList extends JFrame {
	User user;

	private JPanel panel;

	private JPanel pnl;

	private JLabel lbl;
	MainPage mainPage;
	MembershipDAO membershipDAO;

	private JLabel lbl2;
	
	public UserList(User user) {
		this.user = user;
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

		JButton resetbtn = new JButton("새로고침");
		resetbtn.setBounds(317, 75, 97, 23);
		getContentPane().add(resetbtn);
		/*resetbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int status = 0;
				membershipDAO.readStatus(status);
				if(status == 0) {
					lbl2.setText("비접속");
				}else {
					lbl2.setText("접속중");
				}
			}
		});
			 */

		panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 108, 402, 402);
		getContentPane().add(scrollPane);

		for (User u : user.list) {
			if (!u.getNick().equals(user.getNick())) {
				pnl = new JPanel();
				pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				lbl = new JLabel(u.getNick());
				lbl2 = new JLabel("test");
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

	}
}