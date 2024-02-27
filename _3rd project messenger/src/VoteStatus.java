import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VoteStatus extends JFrame {
	private JPanel panel;
	private Agendas agendas;
	private JLabel lblNewLabel;
	private VoteMainPage voteMainPage;
	private JPanel panel2;
	private List<Votes> countList = new ArrayList<>();
	private JLabel lbl;
	private JLabel lbl2;
	private Votes votes;

	public VoteStatus(VoteMainPage voteMainPage) {
		this.voteMainPage = voteMainPage;
		showGUI();
		createPanel();
		 createStatus();
		System.out.println(voteMainPage.getSelectedAgendaNo());
		System.out.println();
	}

	private void showGUI() {
		setSize(351, 334);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 53, 159, 242);
		// GridLayout 적용
		panel.setLayout(new GridLayout(0, 1));
		getContentPane().add(panel);

		panel2 = new JPanel();
		panel2.setBounds(163, 53, 169, 242);
		panel2.setLayout(new GridLayout(0, 1));
		getContentPane().add(panel2);

		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(130, 25, 96, 15);
		getContentPane().add(lblNewLabel);

		setVisible(true);
	}
//	public void createCount() {
//		countUsers();
//		for(int i = 0;i<countMap.size();i++) {
//		lbl2 = new JLabel(String.valueOf(countMap.get(lbl)));
//		panel2.add(lbl2);
//		}
//	}

	public void countUsers() {
		String sql = "SELECT vote_item, COUNT(*) AS vote_count FROM jae.votes where agenda_num = ? GROUP BY vote_item;";

		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, voteMainPage.getSelectedAgendaNo());

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt("vote_count");
					String vote_item = rs.getString("vote_item");
					countList.add(new Votes(vote_item, count,true));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createPanel() {
		votes = new Votes();
		countUsers();

		for (int i = 0; i < countList.size(); i++) {
			lbl = new JLabel(countList.get(i).getVote_item());
			lbl2 = new JLabel(String.valueOf(countList.get(i).getCount()) + " 명");
			panel.add(lbl);
			panel2.add(lbl2);
		}
		  for (int j = 0; j < agendas.itemList.size(); j++) {
		        if (agendas.itemList.get(j).getNo() == voteMainPage.getSelectedAgendaNo()
		                && !containsItem(agendas.itemList.get(j).getItem())) {
		            lbl = new JLabel(agendas.itemList.get(j).getItem());
		            lbl2 = new JLabel("0 명");
		            panel.add(lbl);
		            panel2.add(lbl2);
		        }
		    }
		}

	private boolean containsItem(String item) {
	    for (Votes countItem : countList) {
	        if (countItem.getVote_item().equals(item)) {
	            return true; // 아이템이 이미 카운트 리스트에 존재함
	        }
	    }
	    return false; // 아이템이 카운트 리스트에 존재하지 않음
	}
	
	public void createStatus() {
		for (int j = 0; j < agendas.itemList.size(); j++) {
			if (agendas.itemList.get(j).getNo() == voteMainPage.getSelectedAgendaNo()) {
				lblNewLabel.setText(agendas.itemList.get(j).getAgenda());

			}
		}
	}

//	for (int i = 0; i < countList.size(); i++) {
//		if(countList.get(i).getVote_item().equals(lbl)) {
//		lbl2 = new JLabel(countList.get(i) + " 명");
//		}
//		panel.add(lbl2);
//	}

}
