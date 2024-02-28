import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ranking {
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
	
	
	public void RankingView(User user) {
		this.user = user;
	    JDialog dialog = new JDialog();
	    dialog.setTitle("랭킹");
	    dialog.setSize(300, 300);
	    dialog.setModal(true);
	    JPanel panel = new JPanel();
	    panel.setLayout(null);
	    JLabel lbl1st = new JLabel("1등 : ");
	    JLabel lbl2nd = new JLabel("2등 : ");
	    JLabel lbl3rd = new JLabel("3등 : ");
	    JLabel lbl4th = new JLabel("4등 : ");
	    JLabel lbl5th = new JLabel("5등 : ");
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
	    lbl1st.setFont(new Font("굴림", Font.BOLD, 16));
	    lbl2nd.setFont(new Font("굴림", Font.BOLD, 16));
	    lbl3rd.setFont(new Font("굴림", Font.BOLD, 16));
	    lbl4th.setFont(new Font("굴림", Font.BOLD, 16));
	    lbl5th.setFont(new Font("굴림", Font.BOLD, 16));
	    lbl_1st.setFont(new Font("굴림", Font.BOLD, 16));
	    lbl_2nd.setFont(new Font("굴림", Font.BOLD, 16));
	    lbl_3rd.setFont(new Font("굴림", Font.BOLD, 16));
	    lbl_4th.setFont(new Font("굴림", Font.BOLD, 16));
	    lbl_5th.setFont(new Font("굴림", Font.BOLD, 16));

	    dialog.getContentPane().add(panel);
	    panel.add(lbl1st);
	    panel.add(lbl2nd);
	    panel.add(lbl3rd);
	    panel.add(lbl4th);
	    panel.add(lbl5th);
	    panel.add(lbl_1st);
	    panel.add(lbl_2nd);
	    panel.add(lbl_3rd);
	    panel.add(lbl_4th);
	    panel.add(lbl_5th);
	    panel.add(lbl_1stImage);
	    panel.add(lbl_2ndImage);
	    panel.add(lbl_3rdImage);
	    panel.add(lbl_4thImage);
	    panel.add(lbl_5thImage);
	    lbl1st.setBounds(20, 40, 100, 30);
	    lbl2nd.setBounds(20, 85, 100, 30);
	    lbl3rd.setBounds(20, 130, 100, 30);
	    lbl4th.setBounds(20, 175, 100, 30);
	    lbl5th.setBounds(20, 220, 100, 30);
	    lbl_1stImage.setBounds(60, 40, 30, 30);
	    lbl_2ndImage.setBounds(60, 85, 30, 30);
	    lbl_3rdImage.setBounds(60, 130, 30, 30);
	    lbl_4thImage.setBounds(60, 175, 30, 30);
	    lbl_5thImage.setBounds(60, 220, 30, 30);
	    lbl_1st.setBounds(100, 40, 150, 30);
	    lbl_2nd.setBounds(100, 85, 150, 30);
	    lbl_3rd.setBounds(100, 130, 150, 30);
	    lbl_4th.setBounds(100, 175, 150, 30);
	    lbl_5th.setBounds(100, 220, 150, 30);

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
