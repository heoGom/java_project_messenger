import java.awt.Color;
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


	public PastAgendas() {
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
		for(Agendas a : agendas.pastAgendaList) {
			JPanel pnl = new JPanel();
			Dimension preferredSize = new Dimension(panel.getWidth(), 50); // 원하는 크기로 조절
			pnl.setPreferredSize(preferredSize);
			pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JLabel lbl1 = new JLabel(a.getNickname());
			JLabel lbl2 = new JLabel(a.getAgenda());
			JLabel lbl3 = new JLabel("결과 보기");
			lbl3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			pnl.add(lbl1);
			pnl.add(lbl2);
			pnl.add(lbl3);
			
			panel.add(pnl);
		}
	}


	private void extracted() {
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(12, 87, 379, 405);
		getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 108, 402, 402);
		getContentPane().add(scrollPane);
		
		
		JLabel lblNewLabel = new JLabel("종료된 안건들");
		lblNewLabel.setBounds(12, 62, 110, 15);
		getContentPane().add(lblNewLabel);
	}

}
