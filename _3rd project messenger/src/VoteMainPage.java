import javax.swing.JFrame;

import java.awt.Color;
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
	public VoteMainPage(User user ) {
		this.user = user;
		extracted();
		createPanel();
		lisetnerAll();
		showGUI();
		
	}

	
	private void showGUI() {
		setSize(441, 553);
		setVisible(true);
	}

	private void extracted() {
		getContentPane().setLayout(null);
		
		btnNewButton = new JButton("안건 등록");
		btnNewButton.setBounds(322, 10, 97, 23);
		getContentPane().add(btnNewButton);
		
		panel = new JPanel();
		panel.setBounds(12, 84, 407, 426);
		getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("투표 안건 목록");
		lblNewLabel.setBounds(12, 59, 105, 15);
		getContentPane().add(lblNewLabel);
		
		btnNewButton_1 = new JButton("종료된 안건 보기");
		btnNewButton_1.setBounds(12, 10, 158, 23);
		getContentPane().add(btnNewButton_1);
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
				AddAgenda aa = new AddAgenda(user);
				aa.setVisible(true);
			}
		});
	}
	
	private void createPanel(){
		Agendas agendas = new Agendas();
		agendas.agendaList = new ArrayList<Agendas>();
		agendas.readAgendas();
		
		for(int i =0; i<agendas.agendaList.size();i++) {
			pnl = new JPanel();
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			lbl1 = new JLabel(agendas.agendaList.get(i).getAgenda());
			lbl2 = new JLabel("투표하기");
			lbl3 = new JLabel("현황보기");
			lbl4 = new JLabel(agendas.agendaList.get(i).getNickname());
			int n = agendas.agendaList.get(i).getNo();
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
					goVotePage = new GoVotePage(VoteMainPage.this, user);
					goVotePage.createPanel(n);
					goVotePage.setVisible(true);
				}
			});
			lbl3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			});
		}
	}
	
}
