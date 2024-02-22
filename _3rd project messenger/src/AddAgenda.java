import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AddAgenda extends JFrame {
	private JTextField agendatf;
	private JTextField itemtf;
	private JButton resitagendabtn;
	private JLabel resultagenda;
	private JButton btn2;
	private JPanel panel;
	List<String> agList = new ArrayList<String>();
	public AddAgenda() {
		extracted();
		listenerAll();
		showGUI();
	
	}
	private void showGUI() {
		setSize(441, 553);
		setVisible(true);
	}
	private void listenerAll() {
		resitagendabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultagenda.setText(agendatf.getText());
				resultagenda.getText();
			}
		});
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				agList.add(itemtf.getText());
				System.out.println("적재완료");
				 int newIndex = agList.size() - 1;
			        JLabel lbl = new JLabel(newIndex+1 + "." + agList.get(newIndex));
			        panel.add(lbl);
			        panel.revalidate();
			        panel.repaint();
			}
		});
		resitagendabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	public void saveAgenda() {
		String sql = "insert into jae.regist_item(id, password, nickname, profilephoto)values(?, ?, ?, ?)";
	}
	private void extracted() {
		getContentPane().setLayout(null);
		
		agendatf = new JTextField();
		agendatf.setBounds(97, 66, 163, 21);
		getContentPane().add(agendatf);
		agendatf.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("안건 등록");
		lblNewLabel.setBounds(12, 69, 57, 15);
		getContentPane().add(lblNewLabel);
		
		resitagendabtn = new JButton("확인");
		resitagendabtn.setBounds(289, 65, 97, 23);
		getContentPane().add(resitagendabtn);
		
		panel = new JPanel();
		panel.setBounds(12, 139, 412, 113);
		getContentPane().add(panel);
		
		resultagenda = new JLabel("등록한 주제가 적힘");
		resultagenda.setBounds(136, 123, 224, 15);
		getContentPane().add(resultagenda);
		
		JLabel lblNewLabel_2 = new JLabel("주제 항목 등록");
		lblNewLabel_2.setBounds(12, 271, 97, 15);
		getContentPane().add(lblNewLabel_2);
		
		itemtf = new JTextField();
		itemtf.setBounds(97, 268, 163, 21);
		getContentPane().add(itemtf);
		itemtf.setColumns(10);
		
		btn2 = new JButton("확인");
		btn2.setBounds(289, 267, 97, 23);
		getContentPane().add(btn2);
		
		JButton btnNewButton_2 = new JButton("완료");
		btnNewButton_2.setBounds(157, 341, 97, 23);
		getContentPane().add(btnNewButton_2);
	}
}
