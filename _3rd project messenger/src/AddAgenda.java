import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import javax.swing.JCheckBox;

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
	private JPanel pnl = new JPanel();;
	private JLabel lbl;
	private JLabel lblNewLabel_3;
	public JCheckBox chckbxNewCheckBox;
	private VoteStatus voteStatus;
	private PastAgendas pastAgendas;
	private JLabel zz;

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
		setResizable(false);
	}

	private void listenerAll() {

		resitagendabtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (agendatf.getText().length() >= 1 && agendatf.getText().length() < 15) {
					resultagenda.setText(agendatf.getText());
					resultagenda.getText();
					zz.setText("");
					zz.getText();
					itemtf.requestFocusInWindow();
				} else {
					zz.setText("앗 주제 길이가 너무 길어버렷..!!");
					agendatf.setText("");
					agendatf.requestFocusInWindow();
					resitagendabtn.setEnabled(false);
					resitagendabtn.setEnabled(true);
				}
			}
		});
		agendatf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (agendatf.getText().length() >= 1 && agendatf.getText().length() < 15) {
					resultagenda.setText(agendatf.getText());
					resultagenda.getText();
					zz.setText("");
					zz.getText();
					itemtf.requestFocusInWindow();
				} else {
					zz.setText("앗 주제 길이가 너무 길어버렷..!!");
					agendatf.setText("");
					agendatf.requestFocusInWindow();
					resitagendabtn.setEnabled(false);
					resitagendabtn.setEnabled(true);
				}
			}
		});

		btn2.addActionListener(new ActionListener() {
			int count = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (count < 5) {
					if (itemtf.getText().length() >= 1 && itemtf.getText().length() < 15) {
						agList.add(itemtf.getText());
						System.out.println("적재완료");
						int newIndex = agList.size() - 1;
						lbl = new JLabel(newIndex + 1 + "." + agList.get(newIndex));
						lbl.setLayout(new GridLayout(0, 1));
						pnl = new JPanel();
						pnl.add(lbl);
						Dimension preferredSize = new Dimension(panel.getWidth(), 25); // 원하는 크기로 조절
						pnl.setPreferredSize(preferredSize);
						pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
						pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						panel.add(pnl);
						panel.revalidate();
						panel.repaint();
						itemtf.setText("");
						count++;
					}else {
						itemtf.setText("");
						itemtf.requestFocusInWindow();
						btn2.setEnabled(false);
						btn2.setEnabled(true);
					}
				} 
			}
		});
		
		itemtf.addActionListener(new ActionListener() {
			int count = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (count < 5) {
					if (itemtf.getText().length() >= 1 && itemtf.getText().length() < 15) {
						agList.add(itemtf.getText());
						System.out.println("적재완료");
						int newIndex = agList.size() - 1;
						lbl = new JLabel(newIndex + 1 + "." + agList.get(newIndex));
						lbl.setLayout(new GridLayout(0, 1));
						pnl = new JPanel();
						pnl.add(lbl);
						Dimension preferredSize = new Dimension(panel.getWidth(), 25); // 원하는 크기로 조절
						pnl.setPreferredSize(preferredSize);
						pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
						pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						panel.add(pnl);
						panel.revalidate();
						panel.repaint();
						itemtf.setText("");
						count++;
					}else {
						itemtf.setText("");
						itemtf.requestFocusInWindow();
						btn2.setEnabled(false);
						btn2.setEnabled(true);
					}
				} 
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkvalid();
			}
		});

	}

	private void checkvalid() {

		boolean containsLabel = false;
		Component[] components = pnl.getComponents();
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

		} else if (!containsLabel) {
			JOptionPane.showMessageDialog(null, "투표항목을 1개이상 등록해주세요");
			btnNewButton_2.setEnabled(false);
			btnNewButton_2.setEnabled(true);
		} else if (comboBox.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "마감시간을 설정해주세요");
			btnNewButton_2.setEnabled(false);
			btnNewButton_2.setEnabled(true);
		} else {
			saveAgenda();
			saveResit();
			dispose();
			voteMainPage.updatePanel();
		}
	}

	public void saveAgenda() {
		System.out.println(user.getId());
		System.out.println(resultagenda.getText());
		String sql1 = "insert into jae.agendas(id,agenda,regist_time,final_time,unknown)values(?, ?, ?, ?, ?)";
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
			if (chckbxNewCheckBox.isSelected()) {
				stmt.setInt(5, 1);
			} else {
				stmt.setInt(5, 0);
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
		panel.setBounds(12, 139, 180, 171);
		getContentPane().add(panel);

		resultagenda = new JLabel("");
		resultagenda.setBounds(77, 120, 236, 15);
		getContentPane().add(resultagenda);

		JLabel lblNewLabel_2 = new JLabel("주제 항목 등록 (5개까지 입력가능)");
		lblNewLabel_2.setBounds(204, 145, 198, 15);
		getContentPane().add(lblNewLabel_2);

		itemtf = new JTextField();
		itemtf.setBounds(238, 192, 107, 21);
		getContentPane().add(itemtf);
		itemtf.setColumns(10);

		btn2 = new JButton("확인");
		btn2.setBounds(238, 238, 107, 23);
		getContentPane().add(btn2);

		btnNewButton_2 = new JButton("완료");
		btnNewButton_2.setBounds(145, 426, 97, 23);
		getContentPane().add(btnNewButton_2);

		comboBoxLists = new String[] { "----", "1시간", "2시간" };
		comboBox = new JComboBox(comboBoxLists);
		comboBox.setBounds(200, 344, 113, 21);
		getContentPane().add(comboBox);

		JLabel lblNewLabel_1 = new JLabel("투표마감시간");
		lblNewLabel_1.setBounds(95, 347, 97, 15);
		getContentPane().add(lblNewLabel_1);

		lblNewLabel_3 = new JLabel("투표주제:");
		lblNewLabel_3.setBounds(12, 120, 57, 15);
		getContentPane().add(lblNewLabel_3);

		chckbxNewCheckBox = new JCheckBox("익명 투표");
		chckbxNewCheckBox.setBounds(145, 381, 115, 23);
		getContentPane().add(chckbxNewCheckBox);
		
		zz = new JLabel("");
		zz.setBounds(72, 94, 241, 15);
		getContentPane().add(zz);

	}
}
