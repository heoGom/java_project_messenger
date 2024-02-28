import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class showProfileImage extends JFrame {
	private User user;

	public showProfileImage(User user) {
		this.user = user;
		JPanel pnl = new JPanel();
		JLabel lbl = new JLabel();
		lbl.setBounds(0, 0, 600, 600);
		setImageLbl(user, lbl);
		pnl.add(lbl);
		add(pnl);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - 600) / 2;
		int y = (screenSize.height - 600) / 2;
		setLocation(x, y);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		showgui();
	}

	private void showgui() {
		pack();
		setVisible(true);

	}

	private void setImageLbl(User u, JLabel imageLbl) {
		if (u.getImage() != null) {
			ImageIcon icon = u.getImage();
			Image scaledImage = icon.getImage().getScaledInstance(imageLbl.getWidth(), imageLbl.getHeight(),
					Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			imageLbl.setIcon(scaledIcon);
		}
	}

}
