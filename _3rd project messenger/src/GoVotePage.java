import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.util.List;

import javax.swing.JButton;

public class GoVotePage extends JFrame {
	VoteMainPage voteMainPage;
	private JLabel agenda_name;
	private JPanel panel;
	private List<E>

	public GoVotePage(VoteMainPage voteMainPage) {
		this.voteMainPage = voteMainPage;
		extracted();
		changelbl();
		createPanel();
		showGUI();
	}

	private void showGUI() {
		setSize(328, 453);
		setVisible(true);
	}

	private void changelbl() {
		agenda_name.setText(voteMainPage.lbl1.getText());
	}

	private void extracted() {
		getContentPane().setLayout(null);

		agenda_name = new JLabel("투표주제");
		agenda_name.setBounds(111, 61, 112, 15);
		getContentPane().add(agenda_name);

		panel = new JPanel();
		panel.setBounds(45, 98, 208, 184);
		getContentPane().add(panel);

		JButton btnNewButton = new JButton("투표하기");
		btnNewButton.setBounds(108, 311, 97, 23);
		getContentPane().add(btnNewButton);
	}

	private void createPanel() {
		for (int i = 0; i < ; i++) {
			JLabel lbl = new JLabel();
			JRadioButton btn = new JRadioButton();
			panel.add(lbl);
			panel.add(btn);
		}
	}
}
