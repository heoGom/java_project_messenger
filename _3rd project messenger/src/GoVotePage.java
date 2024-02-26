import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public GoVotePage(VoteMainPage voteMainPage, User user) {
		this.user = user;
		this.voteMainPage = voteMainPage;
		extracted();
		showGUI();
		selectedButtonTextList = new ArrayList<>();
		System.out.println(agendas.getNo());
	}

	private void showGUI() {
		setSize(328, 453);
		setVisible(true);
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
				saveResit();
				System.out.println(selectedButtonTextList.toString());
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
		System.out.println("ㅇㅇ");
	}

	public void createPanel(int no) {
		for (int j = 0; j < agendas.itemList.size(); j++) {
			if (agendas.itemList.get(j).getNo() == no) {
				JLabel lbl = new JLabel(agendas.itemList.get(j).getItem());
				JCheckBox btn = new JCheckBox();

				panel.add(lbl);
				panel.add(btn);
				agenda_name.setText(agendas.itemList.get(j).getAgenda());

				String buttonText = lbl.getText(); // 각 아이템에 대해 새로운 buttonText 생성

				btn.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						System.out.println("이벤트 발생");
						if (btn.isSelected()) {
							selectedButtonTextList.add(buttonText);
						} else {
							selectedButtonTextList.remove(buttonText);
						}
						System.out.println(selectedButtonTextList.toString());
					}
				});
			}
		}
	}
}
