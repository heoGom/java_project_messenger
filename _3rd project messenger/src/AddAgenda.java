import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;

public class AddAgenda extends JFrame {
	private JTextField agendatf;
	private JTextField itemtf;
	private JButton resitagendabtn;
	private JLabel resultagenda;
	private JButton btn2;
	private JPanel panel;
	List<String> agList = new ArrayList<String>();
	User user;
	private JButton btnNewButton_2;
	private JComboBox comboBox;
	private String[] comboBoxLists;
	private Agendas ad;
	private int generatedNo;
	private VoteMainPage voteMainPage;

	public AddAgenda(User user, VoteMainPage voteMainPage) {
		this.user = user;
		this.voteMainPage = voteMainPage;
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
			int count = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (count < 5) {
					if (resultagenda.getText().length() > 1) {
						agList.add(itemtf.getText());
						System.out.println("적재완료");
						int newIndex = agList.size() - 1;
						JLabel lbl = new JLabel(newIndex + 1 + "." + agList.get(newIndex));
						lbl.setLayout(new GridLayout(0, 1));
						panel.add(lbl);
						panel.revalidate();
						panel.repaint();
						count++;
					}
				} else {
					btn2.setEnabled(false);
					btn2.setEnabled(true);
				}
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkvalidate();
			
				
			}
		});
	}

	private void checkvalidate() {
		boolean containsLabel = false;
		Component[] components = panel.getComponents();
		for (Component component : components) {
		    if (component instanceof JLabel) {
		        containsLabel = true;
		        break;
		    }
		}
		
		if (resultagenda.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "투표주제를 설정해주세요");
			btnNewButton_2.setEnabled(false);
			btnNewButton_2.setEnabled(true);

		} else if(!containsLabel) {
			JOptionPane.showMessageDialog(null, "투표항목을 1개이상 등록해주세요");
			btnNewButton_2.setEnabled(false);
			btnNewButton_2.setEnabled(true);
		} else if (comboBox.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "마감시간을 설정해주세요");
			btnNewButton_2.setEnabled(false);
			btnNewButton_2.setEnabled(true);
		}else {
			saveAgenda();
			saveResit();
			dispose();
			voteMainPage.updatePanel();
		}
	}

	public void saveAgenda() {
		System.out.println(user.getId());
		System.out.println(resultagenda.getText());
		String sql1 = "insert into jae.agendas(id,agenda,regist_time,final_time)values(?, ?, ?, ?)";
		LocalDateTime lt = LocalDateTime.now();
		LocalDateTime ltPlusOneHour = lt.plusHours(1);
		LocalDateTime ltPlusTwoHour = lt.plusHours(2);
		Timestamp stamp = Timestamp.valueOf(lt);
		Timestamp stamp2 = Timestamp.valueOf(ltPlusOneHour);
		Timestamp stamp3 = Timestamp.valueOf(ltPlusTwoHour);

		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, user.getId());
			stmt.setString(2, resultagenda.getText());
			stmt.setTimestamp(3, stamp);
			if (comboBox.getSelectedIndex() == 1) {
				stmt.setTimestamp(4, stamp2);
			} else if (comboBox.getSelectedIndex() == 2) {
				stmt.setTimestamp(4, stamp3);
			}
			stmt.executeUpdate();

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				while (generatedKeys.next()) {
					generatedNo = generatedKeys.getInt(1);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void saveResit() {
		String sql2 = "insert into jae.regist_item(agenda_num, id ,item ,agenda_name)values(?, ?, ?, ?)";
		for (int i = 0; i < agList.size(); i++) {
			try (Connection conn = MySqlConnectionProvider.getConnection();
					PreparedStatement stmt = conn.prepareStatement(sql2)) {
				stmt.setInt(1, generatedNo);
				stmt.setString(2, user.getId());
				stmt.setString(3, agList.get(i));
				stmt.setString(4, resultagenda.getText());

				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}


	private void extracted() {
		getContentPane().setLayout(null);

		agendatf = new JTextField();
		agendatf.setBounds(97, 66, 163, 21);
		getContentPane().add(agendatf);
		agendatf.setColumns(10);

		JLabel lblNewLabel = new JLabel("안건 등록");
		lblNewLabel.setBounds(28, 69, 57, 15);
		getContentPane().add(lblNewLabel);

		resitagendabtn = new JButton("확인");
		resitagendabtn.setBounds(289, 65, 97, 23);
		getContentPane().add(resitagendabtn);

		panel = new JPanel();
		panel.setBounds(12, 139, 113, 113);
		getContentPane().add(panel);

		resultagenda = new JLabel("");
		resultagenda.setBounds(12, 120, 224, 15);
		getContentPane().add(resultagenda);

		JLabel lblNewLabel_2 = new JLabel("주제 항목 등록");
		lblNewLabel_2.setBounds(137, 148, 97, 15);
		getContentPane().add(lblNewLabel_2);

		itemtf = new JTextField();
		itemtf.setBounds(134, 185, 107, 21);
		getContentPane().add(itemtf);
		itemtf.setColumns(10);

		btn2 = new JButton("확인");
		btn2.setBounds(263, 184, 97, 23);
		getContentPane().add(btn2);

		btnNewButton_2 = new JButton("완료");
		btnNewButton_2.setBounds(156, 410, 97, 23);
		getContentPane().add(btnNewButton_2);

		comboBoxLists = new String[] { "----", "1시간", "2시간" };
		comboBox = new JComboBox(comboBoxLists);
		comboBox.setBounds(147, 344, 113, 21);
		getContentPane().add(comboBox);

		JLabel lblNewLabel_1 = new JLabel("투표마감시간");
		lblNewLabel_1.setBounds(50, 347, 97, 15);
		getContentPane().add(lblNewLabel_1);
	}
}
