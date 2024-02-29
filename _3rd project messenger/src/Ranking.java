import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ranking extends JFrame {
	User user;
	MiniGame minigame;
	private JLabel lbl_1st;
	private JLabel lbl_2nd;
	private JLabel lbl_3rd;
	private JLabel lbl_4th;
	private JLabel lbl_5th;
	private JLabel lbl_1stImage;
	private JLabel lbl_2ndImage;
	private JLabel lbl_3rdImage;
	private JLabel lbl_4thImage;
	private JLabel lbl_5thImage;
	private JLabel imageLabel;

	public void RankingView(User user) {
		this.user = user;
		JDialog dialog = new JDialog();
		dialog.setTitle("랭킹");
		dialog.setSize(400, 300);
		dialog.setModal(true);
		JPanel panel = new JPanel();
		imageLabel = new JLabel();
		imageLabel.setBounds(0, 0, 400, 300);
		String imagePath = "/picture/등수3.png";
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));
		Image scaledImage = imageIcon.getImage().getScaledInstance(imageLabel.getWidth()
				, imageLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		imageLabel.setIcon(scaledIcon);
		panel.add(imageLabel);
		
		lbl_1st = new JLabel();
		lbl_2nd = new JLabel();
		lbl_3rd = new JLabel();
		lbl_4th = new JLabel();
		lbl_5th = new JLabel();
		lbl_1stImage = new JLabel("");
		lbl_2ndImage = new JLabel("");
		lbl_3rdImage = new JLabel("");
		lbl_4thImage = new JLabel("");
		lbl_5thImage = new JLabel("");
		lbl_1st.setFont(new Font("굴림", Font.BOLD, 14));
		lbl_1st.setForeground(Color.yellow);
		lbl_2nd.setFont(new Font("굴림", Font.BOLD, 14));
		lbl_2nd.setForeground(Color.yellow);
		lbl_3rd.setFont(new Font("굴림", Font.BOLD, 14));
		lbl_3rd.setForeground(Color.yellow);
		lbl_4th.setFont(new Font("굴림", Font.BOLD, 14));
		lbl_4th.setForeground(Color.yellow);
		lbl_5th.setFont(new Font("굴림", Font.BOLD, 14));
		lbl_5th.setForeground(Color.yellow);
		
		imageLabel.add(lbl_1st);
		imageLabel.add(lbl_2nd);
		imageLabel.add(lbl_3rd);
		imageLabel.add(lbl_4th);
		imageLabel.add(lbl_5th);
		imageLabel.add(lbl_1stImage);
		imageLabel.add(lbl_2ndImage);
		imageLabel.add(lbl_3rdImage);
		imageLabel.add(lbl_4thImage);
		imageLabel.add(lbl_5thImage);
		lbl_1stImage.setBounds(85, 63, 40, 40);
		lbl_2ndImage.setBounds(85, 104, 40, 40);
		lbl_3rdImage.setBounds(85, 144, 40, 40);
		lbl_4thImage.setBounds(85, 183, 40, 40);
		lbl_5thImage.setBounds(84, 219, 40, 40);
		lbl_1st.setBounds(140, 72, 150, 20);
		lbl_2nd.setBounds(140, 110, 150, 20);
		lbl_3rd.setBounds(140, 152, 150, 20);
		lbl_4th.setBounds(140, 190, 150, 20);
		lbl_5th.setBounds(140, 228, 150, 20);
		panel.setLayout(null);
		dialog.getContentPane().add(panel);
		dialog.setLocationRelativeTo(null);
		getRankingInfo();
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	public void getRankingInfo() {
		String sql = "SELECT id, nickname, highscore, profilePhoto FROM jae.user ORDER BY highscore DESC LIMIT 5";
		try {
			Connection conn = MySqlConnectionProvider.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			int index = 0;
			while (rs.next()) {
				index++;
				String nickname = rs.getString("nickname");
				int highscore = rs.getInt("highscore");
				ImageIcon profilePhoto = null;
				byte[] imageBytes = rs.getBytes("profilePhoto");
				if (imageBytes != null) {
					profilePhoto = new ImageIcon(imageBytes);
				}
				setLabelInfo(index, nickname, highscore, profilePhoto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setLabelInfo(int rank, String nickname, int highscore, ImageIcon profilePhoto) {
		JLabel label = null;
		JLabel imageLabel = null;
		switch (rank) {
		case 1:
			label = lbl_1st;
			imageLabel = lbl_1stImage;
			break;
		case 2:
			label = lbl_2nd;
			imageLabel = lbl_2ndImage;
			break;
		case 3:
			label = lbl_3rd;
			imageLabel = lbl_3rdImage;
			break;
		case 4:
			label = lbl_4th;
			imageLabel = lbl_4thImage;
			break;
		case 5:
			label = lbl_5th;
			imageLabel = lbl_5thImage;
			break;
		}
		if (label != null && imageLabel != null) {
			label.setText(nickname + " - " + highscore + "초");
			if (profilePhoto != null) {
				Image scaledImage = profilePhoto.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon scaledIcon = new ImageIcon(scaledImage);
				imageLabel.setIcon(scaledIcon);
			}
		}
	}
}
