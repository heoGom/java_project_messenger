import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class PastAgendas extends JFrame {
	private JPanel panel;
	private VoteMainPage voteMainPage;
	private VoteStatus voteStatus;
	private int selectedAgendaNo;
	private PastVoteStatus pastVoteStatus;

	public PastAgendas(VoteMainPage voteMainPage) {
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
				Dimension preferredSize = new Dimension(panel.getWidth(), 50); // 원하는 크기로 조절
				pnl.setPreferredSize(preferredSize);
				pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
				pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				JLabel lbl1 = new JLabel(agendas.agendaList.get(i).getNickname());
				JLabel lbl2 = new JLabel(agendas.agendaList.get(i).getAgenda());
				JLabel lbl3 = new JLabel("  결과 보기  ");
				JLabel lbl4 = new JLabel("투표 의결자: ");
				JLabel lbl5 = new JLabel("투표 종료시간: ");
				JLabel lbl6 = new JLabel(String.valueOf(agendas.agendaList.get(i).getLt()));
				JLabel lbl7 = new JLabel("투표 주제: ");
				lbl3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				int unknown = agendas.agendaList.get(i).getUn();
				int currentAgendaNo = agendas.agendaList.get(i).getNo();
				System.out.println(agendas.agendaList.get(i).getNo());
				pnl.add(lbl7);
				pnl.add(lbl2);
				pnl.add(lbl4);
				pnl.add(lbl1);
				pnl.add(lbl3);
				pnl.add(lbl5);
				pnl.add(lbl6);

				panel.add(pnl);

				lbl3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectedAgendaNo = currentAgendaNo;
						if (pastVoteStatus == null || !pastVoteStatus.isVisible()) {
							pastVoteStatus = new PastVoteStatus(PastAgendas.this);
							if (unknown == 1) {
								pastVoteStatus.lblNewLabel_2.setVisible(false);
							}
						} else {
							pastVoteStatus.toFront();
						}
						int mainPageX = getX();
						int mainPageY = getY();
						pastVoteStatus.setLocation(mainPageX-pastVoteStatus.getWidth() , mainPageY);
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
		panel.setBounds(1, 1, 340, 400);
		getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10); // 스크롤바 속도 조절
		scrollPane.setBounds(12, 108, 402, 402);
		getContentPane().add(scrollPane);

		JLabel lblNewLabel = new JLabel("종료된 안건들");
		lblNewLabel.setBounds(12, 62, 110, 15);
		getContentPane().add(lblNewLabel);
	}

}
