import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
	User user;
	private JButton btnNewButton_2;

	public AddAgenda(User user) {
		this.user = user;
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
				saveAgenda();
			}
		});
	}

	public void saveAgenda() {
		System.out.println(user.getId());
		System.out.println(resultagenda.getText());
		String sql1 = "insert into jae.agendas(id,agenda,regist_time)values(?, ?, ?)";
		LocalDateTime lt = LocalDateTime.now();
		Timestamp stamp = Timestamp.valueOf(lt);

		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql1)) {
			stmt.setString(1, user.getId());
			stmt.setString(2, resultagenda.getText());
			stmt.setTimestamp(3, stamp);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void readagenda() {
		String sql = "SELECT agendas.*, jae.user.nickname FROM agendas JOIN jae.user ON agendas.id = jae.user.id;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				String agenda = rs.getString("agenda");
				String id = rs.getString("id");
				int no = rs.getInt("no");
			
			
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

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
//		for(int i =0;i <agList.size();i++) {
//		try (Connection conn = MySqlConnectionProvider.getConnection()) {
//		    conn.setAutoCommit(false);
//
//		    try (PreparedStatement stmt = conn.prepareStatement(sql1);
//		         PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
//
//		        stmt.setString(1, user.getId());
//		        stmt.setString(2, resultagenda.getText());
//		        stmt.setTimestamp(3, stamp);
//
//		        stmt2.setInt(1, agenda.getNo());
//		        stmt2.setString(2, user.getId());
//		        stmt2.setString(3, agList.get(i));
//		        stmt2.setString(4, resultagenda.getText());
//
//		        stmt.executeUpdate();
//		        stmt2.executeUpdate();
//
//		        conn.commit();
//		    } catch (SQLException e) {
//		        conn.rollback();
//		        e.printStackTrace();
//		    }
//		} catch (SQLException e) {
//		    e.printStackTrace();
//		}
//		}

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

		resultagenda = new JLabel("");
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

		btnNewButton_2 = new JButton("완료");
		btnNewButton_2.setBounds(157, 341, 97, 23);
		getContentPane().add(btnNewButton_2);
	}
}