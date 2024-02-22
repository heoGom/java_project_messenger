import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JLabel lbl1 = new JLabel(a.getId());
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
		
		JLabel lblNewLabel = new JLabel("종료된 안건들");
		lblNewLabel.setBounds(12, 62, 110, 15);
		getContentPane().add(lblNewLabel);
	}

}
