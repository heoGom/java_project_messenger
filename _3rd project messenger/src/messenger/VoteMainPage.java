package messenger;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

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
	Agendas agendas;
	PastAgendas pastAgendas;
	private List<Votes> voteId;
	private int selectedAgendaNo;
	private AddAgenda addAgenda;

	public VoteMainPage(User user) {
		getContentPane().setBackground(new Color(233, 255, 223));
		this.user = user;
		showGUI();
		extracted();
		createPanel();
		lisetnerAll();
		updateProgress_or_Not();
		updatePanel();
		voteId = new ArrayList<>();
	}

	private void showGUI() {
		setSize(482, 559);
		setVisible(true);
		setResizable(false);
	}

	private void extracted() {
		getContentPane().setLayout(null);

		btnNewButton = new JButton("");
		btnNewButton.setIcon(
				new ImageIcon(VoteMainPage.class.getResource("/Image/\uC548\uAC74 \uB4F1\uB85D \uBC84\uD2BC.png")));
		btnNewButton.setBounds(362, 10, 100, 30);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setContentAreaFilled(false);
		getContentPane().add(btnNewButton);

		panel = new JPanel();
		panel.setBackground(new Color(250, 255, 243));
		panel.setBounds(12, 84, 400, 426);
		getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(6); // 스크롤바 속도 조절
		scrollPane.setBounds(12, 84, 450, 426);
		getContentPane().add(scrollPane);

		JLabel lblNewLabel = new JLabel("투표 안건 목록");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("한컴 고딕", Font.BOLD, 14));
		lblNewLabel.setBounds(185, 59, 105, 15);
		getContentPane().add(lblNewLabel);

		btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(VoteMainPage.class
				.getResource("/Image/\uC885\uB8CC\uB41C \uC548\uAC74 \uBCF4\uAE30 \uBC84\uD2BC.png")));
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBounds(12, 10, 160, 30);
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
				if (pastAgendas == null || !pastAgendas.isVisible()) {
					pastAgendas = new PastAgendas(VoteMainPage.this);
				} else {
					pastAgendas.toFront();
				}
				pastAgendas.setVisible(true);
				int mainPageX = getX();
				int mainPageY = getY();
				pastAgendas.setLocation(mainPageX - pastAgendas.getWidth(), mainPageY);
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (addAgenda == null || !addAgenda.isVisible()) {
					addAgenda = new AddAgenda(user, VoteMainPage.this);
				} else {
					addAgenda.toFront();
				}
				addAgenda.setVisible(true);
				int mainPageX = getX();
				int mainPageY = getY();
				addAgenda.setLocation(mainPageX - addAgenda.getWidth(), mainPageY);
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
			if (agendas.agendaList.get(i).getProgress_or_not() == 1) {
				pnl = new JPanel();
				Dimension preferredSize = new Dimension(panel.getWidth(), 80); // 원하는 크기로 조절
				pnl.setPreferredSize(preferredSize);
				pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
				pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				lbl1 = new JLabel(agendas.agendaList.get(i).getAgenda());
				lbl2 = new JLabel("투표하기");
				lbl2.setForeground(Color.BLUE);
				lbl3 = new JLabel("현황보기");
				lbl3.setForeground(Color.BLUE);
				lbl4 = new JLabel(agendas.agendaList.get(i).getNickname());
				JLabel lbl5 = new JLabel("투표 주제: ");
				lbl5.setForeground(Color.GRAY);
				JLabel lbl6 = new JLabel("의결자: ");
				lbl6.setForeground(Color.GRAY);
				JLabel lbl7 = new JLabel("투표 종료시간: ");
				lbl7.setForeground(Color.GRAY);
				JLabel lbl8 = new JLabel(String.valueOf(agendas.agendaList.get(i).getLt()));
				JPanel pnl2 = new JPanel();
				JPanel pnl3 = new JPanel();
				int currentAgendaNo = agendas.agendaList.get(i).getNo();
				int unknown = agendas.agendaList.get(i).getUn();
				lbl2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				lbl3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				pnl2.add(lbl5);
				pnl2.add(lbl1);
				pnl2.add(lbl6);
				pnl2.add(lbl4);
				pnl2.add(lbl2);
				pnl2.add(lbl3);
				pnl3.add(lbl7);
				pnl3.add(lbl8);
				pnl.add(pnl2);
				pnl.add(pnl3);
				panel.add(pnl);

				lbl2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Votes votes = new Votes();
						readVoteId();
						boolean hasVoted = false;
						for (int i = 0; i < voteId.size(); i++) {
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
							if (goVotePage == null || !goVotePage.isVisible()) {
								goVotePage = new GoVotePage(VoteMainPage.this, user);
								goVotePage.createPanel(selectedAgendaNo);
							} else {
								goVotePage.dispose();
								goVotePage = new GoVotePage(VoteMainPage.this, user);
								goVotePage.createPanel(selectedAgendaNo);
							}
							int mainPageX = getX();
							int mainPageY = getY();
							goVotePage.setLocation(mainPageX - goVotePage.getWidth(), mainPageY);
						}
					}
				});
				lbl3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectedAgendaNo = currentAgendaNo;
						if (voteStatus == null || !voteStatus.isVisible()) {
							voteStatus = new VoteStatus(VoteMainPage.this, pastAgendas);
						} else {
							voteStatus.dispose();
							voteStatus = new VoteStatus(VoteMainPage.this, pastAgendas);
						}
						int mainPageX = getX();
						int mainPageY = getY();
						voteStatus.setLocation(mainPageX, mainPageY);
						if (unknown == 1) {
							voteStatus.showlbl.setVisible(false);
						} else {
							voteStatus.unknownlbl.setVisible(false);
						}
					}
				});
			}
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

	private void updateProgress_or_Not() {
		agendas = new Agendas();
		String sql = "update jae.agendas set progress_or_not =0 where agenda = ?";
		LocalDateTime currentTime = LocalDateTime.now();
		Timestamp stamp = Timestamp.valueOf(currentTime);
		agendas.readAgendas();
		for (int i = 0; i < agendas.agendaList.size(); i++) {
			if (agendas.agendaList.get(i).getLt().before(stamp)) {
				try (Connection conn = MySqlConnectionProvider.getConnection();
						PreparedStatement stmt = conn.prepareStatement(sql)) {
					stmt.setString(1, agendas.agendaList.get(i).getAgenda());
					stmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
