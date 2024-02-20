import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class PrivateChatRoom extends JFrame {
	public PrivateChatRoom() {
		extracted();
		showGUI();
		
	
	}
	private void extracted() {
	getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 55));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uC0AC\uC9C4");
		lblNewLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel.setBounds(12, 9, 35, 35);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\uC0C1\uB300 \uB2C9\uB124\uC784");
		lblNewLabel_1.setBounds(63, 20, 64, 15);
		panel.add(lblNewLabel_1);
		
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}
	public static void main(String[] args) {
		new PrivateChatRoom();
	}
}
