package messenger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class GoVotePage extends JFrame {
	VoteMainPage voteMainPage;
	private JLabel agenda_name;
	private JPanel panel;
	Agendas agendas = new Agendas();
	public JButton votebtn;
	private JLabel lbl;
	private JCheckBox btn;
	private User user;

	private List<String> selectedButtonTextList;
	private JLabel lbl2;

	public GoVotePage(VoteMainPage voteMainPage, User user) {
		this.user = user;
		this.voteMainPage = voteMainPage;
		extracted();
		showGUI();
		selectedButtonTextList = new ArrayList<>();

	}
	
	

	private void showGUI() {
		setSize(328, 493);
		setVisible(true);
		setResizable(false);
	}

	private void extracted() {
		getContentPane().setLayout(null);

		agenda_name = new JLabel("투표주제");
		agenda_name.setBounds(68, 61, 166, 15);
		getContentPane().add(agenda_name);

		panel = new JPanel();
		panel.setBounds(45, 98, 208, 285);
		getContentPane().add(panel);

		votebtn = new JButton("투표하기");
		votebtn.setBounds(98, 393, 97, 23);
		getContentPane().add(votebtn);
		votebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveResit();
				dispose();
			}
		});

	}

	public void saveResit() {
		String sql2 = "insert into jae.votes(agenda_num, id ,vote_item )values(?, ?, ?)";
		for (String buttonText : selectedButtonTextList) {
			try (Connection conn = MySqlConnectionProvider.getConnection();
					PreparedStatement stmt = conn.prepareStatement(sql2)) {
				stmt.setInt(1, voteMainPage.getSelectedAgendaNo());
				stmt.setString(2, user.getId());
				stmt.setString(3, buttonText);

				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public void createPanel(int no) {
		int count = 1;
		for (int j = 0; j < agendas.itemList.size(); j++) {
			if (agendas.itemList.get(j).getNo() == no) {
				JLabel lbl = new JLabel(agendas.itemList.get(j).getItem());
				JCheckBox btn = new JCheckBox();
				JPanel pnl = new JPanel();
				Dimension preferredSize = new Dimension(panel.getWidth(), 50);

				lbl2 = new JLabel(String.valueOf(count) + "번 항목: ");
				count++;
				pnl.setPreferredSize(preferredSize);
				pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
				pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				pnl.add(lbl2);
				pnl.add(lbl);
				pnl.add(btn);

				panel.add(pnl);
				agenda_name.setText("투표 주제: " + agendas.itemList.get(j).getAgenda());

				String buttonText = lbl.getText(); // 각 아이템에 대해 새로운 buttonText 생성

				btn.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (btn.isSelected()) {
							selectedButtonTextList.add(buttonText);
						} else {
							selectedButtonTextList.remove(buttonText);
						}
					}
				});
			}
		}
	}
}
