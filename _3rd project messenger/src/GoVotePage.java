import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

public class GoVotePage extends JFrame {
	VoteMainPage voteMainPage;
	private JLabel agenda_name;
	private JPanel panel;
	Agendas agendas;
	private JButton votebtn;
	private JLabel lbl;
	private JRadioButton btn;
	private User user;

	public GoVotePage(VoteMainPage voteMainPage, User user) {
		this.user = user;
		this.voteMainPage = voteMainPage;
		extracted();
		changelbl();
		showGUI();
	}

	private void showGUI() {
		setSize(328, 453);
		setVisible(true);
	}

	private void changelbl() {
		agenda_name.setText(voteMainPage.lbl1.getText());
	}

	private void extracted() {
		getContentPane().setLayout(null);

		agenda_name = new JLabel("투표주제");
		agenda_name.setBounds(111, 61, 112, 15);
		getContentPane().add(agenda_name);

		panel = new JPanel();
		panel.setBounds(45, 98, 208, 184);
		getContentPane().add(panel);

		votebtn = new JButton("투표하기");
		votebtn.setBounds(108, 311, 97, 23);
		getContentPane().add(votebtn);
		votebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btn.isSelected()) {
					saveResit();
					dispose();
				}
			}
		});
	}

	public void saveResit() {
		Agendas agendas = new Agendas();
		agendas.itemList = new ArrayList<Agendas>();
		agendas.agendaList = new ArrayList<Agendas>();
		agendas.readAgendas();
		agendas.readItem2();
		String sql2 = "insert into jae.vote(agenda_num, id ,item )values(?, ?, ?)";
		for (int j = 0; j < agendas.agendaList.size(); j++) {
			for (int i = 0; i < agendas.itemList.size(); i++) {
				if (agendas.itemList.get(i).getNo() == agendas.agendaList.get(j).getNo()) {
					try (Connection conn = MySqlConnectionProvider.getConnection();
							PreparedStatement stmt = conn.prepareStatement(sql2)) {
						stmt.setInt(1, agendas.itemList.get(i).getNo());
						stmt.setString(2, user.getId());
						stmt.setString(3, lbl.getText());

						stmt.executeUpdate();
						System.out.println("ㅇㅇ");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public void createPanel(int no) {
		Agendas agendas = new Agendas();
		agendas.itemList = new ArrayList<Agendas>();
		agendas.readItem(no);
		for (int j = 0; j < agendas.itemList.size(); j++) {
			if (agendas.itemList.get(j).getNo() == no) {
				lbl = new JLabel(agendas.itemList.get(j).getItem());
				btn = new JRadioButton();

				panel.add(lbl);
				panel.add(btn);
			}
		}
	}
}
