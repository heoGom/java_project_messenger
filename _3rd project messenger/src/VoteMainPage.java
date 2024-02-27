import javax.swing.JFrame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VoteMainPage extends JFrame {
	private JPanel pnl;
	private JLabel lbl2;
	private JLabel lbl3;
	public JPanel panel;
	public JLabel lbl1;
	private JLabel lbl4;
	private JButton btnNewButton_1;
	private JButton btnNewButton;
	User user;
	GoVotePage goVotePage;
	VoteStatus voteStatus;
	private List<Votes> voteId;

	private int selectedAgendaNo;

	public VoteMainPage(User user) {
		this.user = user;
		extracted();
		createPanel();
		lisetnerAll();
		showGUI();
		voteId = new ArrayList<>();
		System.out.println(selectedAgendaNo);

	}

	private void showGUI() {
		setSize(495, 559);
		setVisible(true);
	}

	private void extracted() {
		getContentPane().setLayout(null);

		btnNewButton = new JButton("안건 등록");
		btnNewButton.setBounds(370, 10, 97, 23);
		getContentPane().add(btnNewButton);

		panel = new JPanel();
		panel.setBounds(12, 84, 455, 426);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1));

		JLabel lblNewLabel = new JLabel("투표 안건 목록");
		lblNewLabel.setBounds(185, 59, 105, 15);
		getContentPane().add(lblNewLabel);

		btnNewButton_1 = new JButton("종료된 안건 보기");
		btnNewButton_1.setBounds(12, 10, 158, 23);
		getContentPane().add(btnNewButton_1);
	}

	private void readVoteId() {
		String sql = "select * from jae.votes;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String id = rs.getString("id");
					int agend_num = rs.getInt("agenda_num");
					voteId.add(new Votes(id, agend_num));

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void lisetnerAll() {
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PastAgendas pa = new PastAgendas();
				pa.setVisible(true);
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddAgenda aa = new AddAgenda(user, VoteMainPage.this);
				aa.setVisible(true);
			}
		});
	}

	private void createPanel() {
		Agendas agendas = new Agendas();
		agendas.agendaList = new ArrayList<Agendas>();
		agendas.readAgendas();
		agendas.itemList = new ArrayList<Agendas>();
		agendas.readItem2();
		
		for (int i = 0; i < agendas.agendaList.size(); i++) {
			pnl = new JPanel();
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			lbl1 = new JLabel(agendas.agendaList.get(i).getAgenda());
			lbl2 = new JLabel("투표하기");
			lbl3 = new JLabel("현황보기");
			lbl4 = new JLabel(agendas.agendaList.get(i).getNickname());

			int currentAgendaNo = agendas.agendaList.get(i).getNo();

			lbl2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			lbl3.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			pnl.add(lbl1);
			pnl.add(lbl2);
			pnl.add(lbl3);
			pnl.add(lbl4);
			panel.add(pnl);
			lbl2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Votes votes = new Votes();
					readVoteId();
					System.out.println(voteId.toString());
					boolean hasVoted = false;
					for (int i = 0; i < voteId.size(); i++) {
						  System.out.println("비교 중: " + voteId.get(i).getAgend_num() + " 와 " +currentAgendaNo);
						    System.out.println("ID: " + voteId.get(i).getId() + " 와 " + user.getId());
						if (voteId.get(i).getAgend_num() == currentAgendaNo
								&& voteId.get(i).getId().contains(user.getId())) {
							// 이미 투표한 경우
							JOptionPane.showMessageDialog(null, "이미 투표하셨습니다");
							lbl2.setEnabled(false);
							lbl2.setEnabled(true);
							hasVoted = true;
							panel.revalidate();
							panel.repaint();
							break; // 중복 투표가 확인되면 루프 탈출
						}
					}
					
					if (!hasVoted) {
						selectedAgendaNo = currentAgendaNo;
						goVotePage = new GoVotePage(VoteMainPage.this, user);
						goVotePage.createPanel(selectedAgendaNo);
					}
				}
			});
			lbl3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selectedAgendaNo = currentAgendaNo;
					voteStatus = new VoteStatus(VoteMainPage.this);
				}
			});
		}
	}
	public void updatePanel() {
	    panel.removeAll(); // 패널의 모든 컴포넌트를 제거
	    createPanel(); // 패널을 다시 생성
	    panel.revalidate();
	    panel.repaint();
	}

	public int getSelectedAgendaNo() {
		return selectedAgendaNo;
	}
}
