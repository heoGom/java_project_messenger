package messenger;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class PastAgendas extends JFrame {
	private JPanel panel;
	private VoteMainPage voteMainPage;
	private VoteStatus voteStatus;
	private int selectedAgendaNo;
	private PastVoteStatus pastVoteStatus;

	public PastAgendas(VoteMainPage voteMainPage) {
		getContentPane().setBackground(new Color(233, 255, 223));
		this.voteMainPage = voteMainPage;
		extracted();
		showGUI();
		createPanel();
	}

	private void showGUI() {
		setSize(441, 553);
		setVisible(true);
		setResizable(false);
	}

	private void createPanel() {
		Agendas agendas = new Agendas();
		agendas.agendaList = new ArrayList<Agendas>();
		agendas.readAgendas();
		agendas.itemList = new ArrayList<Agendas>();
		agendas.readItem2();
		for (int i = 0; i < agendas.agendaList.size(); i++) {
			if (agendas.agendaList.get(i).getProgress_or_not() == 0) {
				JPanel pnl = new JPanel();
				Dimension preferredSize = new Dimension(panel.getWidth(), 75); // 원하는 크기로 조절
				JPanel pnl3 = new JPanel();
				pnl3.setPreferredSize(preferredSize);
				pnl3.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
				
				JLabel lbl1 = new JLabel(agendas.agendaList.get(i).getNickname());
				JLabel lbl2 = new JLabel(agendas.agendaList.get(i).getAgenda());
				JLabel lbl3 = new JLabel("  결과 보기  ");
				lbl3.setForeground(Color.BLUE);
				JLabel lbl4 = new JLabel("의결자: ");
				JLabel lbl5 = new JLabel("투표 종료시간: ");
				JLabel lbl6 = new JLabel(String.valueOf(agendas.agendaList.get(i).getLt()));
				JLabel lbl7 = new JLabel("투표 주제: ");
				lbl7.setForeground(Color.GRAY);
				lbl4.setForeground(Color.GRAY);
				lbl5.setForeground(Color.GRAY);
				lbl3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				int unknown = agendas.agendaList.get(i).getUn();
				int currentAgendaNo = agendas.agendaList.get(i).getNo();
				JPanel pnl2 = new JPanel();
				pnl.add(lbl7);
				pnl.add(lbl2);
				pnl.add(lbl4);
				pnl.add(lbl1);
				pnl.add(lbl3);
				pnl2.add(lbl5);
				pnl2.add(lbl6);
				pnl3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				pnl3.add(pnl);
				pnl3.add(pnl2);
				panel.add(pnl3);

				lbl3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectedAgendaNo = currentAgendaNo;
						if (pastVoteStatus == null || !pastVoteStatus.isVisible()) {
							pastVoteStatus = new PastVoteStatus(PastAgendas.this);
						} else {
							pastVoteStatus.dispose();
							pastVoteStatus = new PastVoteStatus(PastAgendas.this);
						}
						int mainPageX = getX();
						int mainPageY = getY();
						pastVoteStatus.setLocation(mainPageX - pastVoteStatus.getWidth(), mainPageY);
						if (unknown == 1) {
							pastVoteStatus.lblNewLabel_2.setVisible(false);
						} else {
							pastVoteStatus.unknownlbl.setVisible(false);
						}
					}
				});
			}
		}
	}

	public int getSelectedAgendaNo() {
		return selectedAgendaNo;
	}

	private void extracted() {
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(250, 255, 243));
		panel.setBounds(12, 0, 340, 400);
		getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10); // 스크롤바 속도 조절
		scrollPane.setBounds(12, 108, 402, 402);
		getContentPane().add(scrollPane);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(
				new ImageIcon(PastAgendas.class.getResource("/Image/\uC885\uB8CC\uB41C \uC548\uAC74\uB4E4.png")));
		lblNewLabel.setBounds(30, 42, 143, 31);
		getContentPane().add(lblNewLabel);
	}

}
