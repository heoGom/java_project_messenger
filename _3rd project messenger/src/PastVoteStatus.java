import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PastVoteStatus extends JFrame {
	private JPanel panel;
	private Agendas agendas;
	private JLabel lblNewLabel;
	private VoteMainPage voteMainPage;
	private JPanel panel2;
	private List<Votes> countList = new ArrayList<>();
	private JLabel lbl;
	private JLabel lbl2;
	private Votes votes;
	private PastAgendas pastAgendas;
	private JLabel lblNewLabel_1;
	private JPanel panel_1;
	private JPanel panel_2;

	public PastVoteStatus(PastAgendas pastAgendas) {
		this.pastAgendas = pastAgendas;
		showGUI();
		createPanel();
		createStatus();
		System.out.println(pastAgendas.getSelectedAgendaNo());
		System.out.println();
	}

	private void showGUI() {
		setSize(657, 527);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(12, 53, 119, 435);
		// GridLayout 적용
		panel.setLayout(new GridLayout(0, 1));
		getContentPane().add(panel);

		panel2 = new JPanel();
		panel2.setBounds(130, 53, 126, 435);
		panel2.setLayout(new GridLayout(0, 1));
		getContentPane().add(panel2);


		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(24, 28, 220, 15);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("투표자");
		lblNewLabel_1.setBounds(421, 28, 57, 15);
		getContentPane().add(lblNewLabel_1);

		panel_1 = new JPanel();
		panel_1.setBounds(264, 53, 365, 435);
		getContentPane().add(panel_1);

		panel_2 = new JPanel();
		panel_2.setBounds(256, 0, 5, 488);
		getContentPane().add(panel_2);
		panel_2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel_2.setBackground(Color.black);
		
		setVisible(true);
	}

	public void countUsers() {
		String sql = "SELECT agenda_num,vote_item,COUNT(*) AS vote_item_count,GROUP_CONCAT(nickname) AS voted_nicknames FROM jae.votesuser where agenda_num = ? group BY agenda_num, vote_item;";

		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, pastAgendas.getSelectedAgendaNo());

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt("vote_item_count");
					String vote_item = rs.getString("vote_item");
					String nickname = rs.getString("voted_nicknames");
					countList.add(new Votes(vote_item, count, nickname));
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
			JPanel Listpnl = new JPanel();
			JPanel Listpnl2 = new JPanel();
			JPanel pnl = new  JPanel();
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JLabel lbl3 = new JLabel(countList.get(i).getVote_item() + " : ");
			JLabel lbl4 = new JLabel(countList.get(i).getNickname());
			
			Dimension preferredSize = new Dimension(panel2.getWidth(), 40); // 원하는 크기로 조절
		//	panel2.getWidth(), 25
			Listpnl.setPreferredSize(preferredSize);
			Listpnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));

			Listpnl2.setPreferredSize(preferredSize);
			Listpnl2.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
			
			Listpnl.add(lbl3);
			Listpnl2.add(lbl4);
			pnl.add(Listpnl);
			pnl.add(Listpnl2);
			
			panel.add(lbl);
			panel2.add(lbl2);
			panel_1.add(pnl);
		}
		for (int j = 0; j < agendas.itemList.size(); j++) {
			if (agendas.itemList.get(j).getNo() == pastAgendas.getSelectedAgendaNo()
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
			if (agendas.itemList.get(j).getNo() == pastAgendas.getSelectedAgendaNo()) {
				lblNewLabel.setText(agendas.itemList.get(j).getAgenda());

			}
		}
	}
}
