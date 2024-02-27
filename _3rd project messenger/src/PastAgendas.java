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
import javax.swing.JLabel;

public class PastAgendas extends JFrame {
	private JPanel panel;
	private VoteMainPage voteMainPage;
	private VoteStatus voteStatus;
	private int selectedAgendaNo;

	public PastAgendas(VoteMainPage voteMainPage) {
		this.voteMainPage = voteMainPage;
		extracted();
		showGUI();
		createPanel();
	}
	private void showGUI() {
		setSize(441, 553);
		setVisible(true);
	}
	private void createPanel() {
		Agendas agendas = new Agendas();
		agendas.pastAgendaList = new ArrayList<Agendas>();
		agendas.readAgendas();
		for(int i = 0; i < agendas.pastAgendaList.size(); i++) {
			JPanel pnl = new JPanel();
			Dimension preferredSize = new Dimension(panel.getWidth(), 50); // 원하는 크기로 조절
			pnl.setPreferredSize(preferredSize);
			pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JLabel lbl1 = new JLabel(agendas.pastAgendaList.get(i).getNickname());
			JLabel lbl2 = new JLabel(agendas.pastAgendaList.get(i).getAgenda());
			JLabel lbl3 = new JLabel("결과 보기");
			lbl3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			int currentAgendaNo = agendas.pastAgendaList.get(i).getNo();
			System.out.println(agendas.pastAgendaList.get(i).getNo());
			pnl.add(lbl1);
			pnl.add(lbl2);
			pnl.add(lbl3);
			
			panel.add(pnl);
			
			lbl3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selectedAgendaNo = currentAgendaNo;
					voteStatus = new VoteStatus(voteMainPage);
					
				}
			});
		}
	}


	private void extracted() {
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(12, 87, 379, 405);
		getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("종료된 안건들");
		lblNewLabel.setBounds(12, 62, 110, 15);
		getContentPane().add(lblNewLabel);
	}

}
