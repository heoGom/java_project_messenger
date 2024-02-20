import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class ImageChange extends JFrame {
	private JTextField textField;
	public ImageChange() {
		getContentPane().setLayout(null);
		
		JLabel CurrentPhoto = new JLabel("    현재 사진");
		CurrentPhoto.setBounds(50, 50, 85, 100);
		getContentPane().add(CurrentPhoto);
		
		JLabel ChangePhoto = new JLabel("  변경할 사진");
		ChangePhoto.setBounds(290, 50, 85, 100);
		getContentPane().add(ChangePhoto);
		
		JButton btnNewButton = new JButton("변경하기");
		btnNewButton.setBounds(162, 206, 97, 23);
		getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(26, 230, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		setTitle("프로필 사진 변경");
	}
	
}
