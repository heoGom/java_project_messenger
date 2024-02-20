import javax.swing.*;
import java.awt.*;
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

	public UserList(User user) {
		this.user = user;
		extracted();
		listenerAll();
		showGUI();
		
	}

	private void listenerAll() {
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				user.readAllUser();
				System.out.println("실행중");
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
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

		JLabel lblNewLabel_1 = new JLabel("내 닉네임");
		lblNewLabel_1.setBounds(81, 10, 77, 15);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNick = new JLabel(user.getNick());
		lblNick.setBounds(150, 10, 200, 15);
		getContentPane().add(lblNick);

		JLabel lblNewLabel_2 = new JLabel("가입자 목록 몇명인지?");
		lblNewLabel_2.setBounds(25, 79, 110, 15);
		getContentPane().add(lblNewLabel_2);

		panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(12, 108, 402, 402);
		getContentPane().add(scrollPane);

		
		for (User u : user.list) {
			pnl = new JPanel();
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			lbl = new JLabel(u.getNick());
			pnl.add(lbl);
			panel.add(pnl);
		}

		JButton btnNewButton = new JButton("새로고침");
		btnNewButton.setBounds(317, 75, 97, 23);
		getContentPane().add(btnNewButton);
	}
}