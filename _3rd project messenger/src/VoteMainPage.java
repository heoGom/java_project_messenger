import javax.swing.JFrame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class VoteMainPage extends JFrame {
	private Agendas agendas = null;
	private static List<Agendas> agendaList;
	private JPanel pnl;
	private JLabel lbl2;
	private JLabel lbl3;
	private JPanel panel;
	private JLabel lbl1;
	private JLabel lbl4;
	
	public VoteMainPage() {
		extracted();
		createPanel();
		showGUI();
		agendaList = new ArrayList<Agendas>();
	}

	
	private void showGUI() {
		setSize(441, 553);
		setVisible(true);
	}

	private void extracted() {
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("안건 등록");
		btnNewButton.setBounds(322, 10, 97, 23);
		getContentPane().add(btnNewButton);
		
		panel = new JPanel();
		panel.setBounds(12, 84, 407, 426);
		getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("투표 안건 목록");
		lblNewLabel.setBounds(12, 59, 105, 15);
		getContentPane().add(lblNewLabel);
	}
	private void createPanel(){
		for(int i = 0; i<50;i++) {
			pnl = new JPanel();
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			lbl1 = new JLabel("안건주제");
			lbl2 = new JLabel("투표하기");
			lbl3 = new JLabel("현황보기");
			lbl4 = new JLabel("올린사람");
			
			lbl2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			lbl3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			pnl.add(lbl1);
			pnl.add(lbl4);
			pnl.add(lbl2);
			pnl.add(lbl3);
			panel.add(pnl);
			lbl2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			lbl3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});
		}
	}
	public void readAgendas() {
		String sql = "select * from jae.agendas;";
		try(Connection conn = MySqlConnectionProvider.getConnection();
				Statement stmt = conn.createStatement())
	}
}
