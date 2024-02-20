import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PrivateChatRoom extends JFrame {
	private User user;
	private User another;
	private List<TextDate> TDList;
	
	public PrivateChatRoom(User user, User another) {
		this.user = user;
		this.another = another;
		
		extracted();
		showGUI();
		
	
	}
	private void extracted() {
	getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 55));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		JLabel another_Pt_Lbl = new JLabel("\uC0AC\uC9C4");
		another_Pt_Lbl.setBorder(new LineBorder(new Color(0, 0, 0)));
		another_Pt_Lbl.setBounds(12, 9, 35, 35);
		panel.add(another_Pt_Lbl);
		
		JLabel another_NN_Lbl = new JLabel(another.getNick());
		another_NN_Lbl.setBounds(63, 20, 64, 15);
		panel.add(another_NN_Lbl);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 110));
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(10, 30));
		panel_1.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("\uC804   \uC1A1");
		panel_3.add(btnNewButton, BorderLayout.EAST);
		
		JButton btnNewButton_1 = new JButton("\uD30C\uC77C");
		panel_3.add(btnNewButton_1, BorderLayout.WEST);
		
		JTextArea textArea = new JTextArea();
		panel_1.add(textArea, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
	}
	private void showGUI() {
		setSize(450, 500);
		setVisible(true);

	}
//	private List<TextDate> readDB(){
//		List<TextDate> td = new ArrayList<>();
//		//물음표 4개
//		String sql ="SELECT * FROM private_chatlist "
//				+ "WHERE (sender_id = ? AND receiver_id = ?) "
//				+ "OR (sender_id = ? AND receiver_id = ?) "
//				+ "ORDER BY text_time;";
//		
//		
//		
//	}
	
	
}
