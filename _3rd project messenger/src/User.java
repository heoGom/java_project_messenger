import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class User {
	String id = null;
	String pw = null;
	String nick = null;
	ImageIcon image = null;
	int highScore = 0;
	static List<User> list = new ArrayList<>();
	int status;
	

	public User() {
	}

	public User(String id, String pw, String nick, ImageIcon image, int highScore) {
		super();
		this.id = id;
		this.pw = pw;
		this.nick = nick;
		this.image = image;
		this.highScore = highScore;
	}

	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", pw=" + pw + ", nick=" + nick + ", image=" + image + ", highScore=" + highScore
				+ ", status=" + status + "]";
	}

	public void readAllUser(String nickname) {
	    String sql = "SELECT * FROM jae.user WHERE nickname != ?";
	    try (Connection conn = MySqlConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, nickname); // 물음표에 해당하는 값을 설정

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                String id = rs.getString("id");
	                String pw = rs.getString("password");
	                String nick = rs.getString("nickname");
	                byte[] imageBytes = rs.getBytes("profilePhoto");
	                ImageIcon image = (imageBytes != null) ? new ImageIcon(imageBytes) : null;
	                int highScore = rs.getInt("highscore");
	                
	                User user = new User();
	                user.setId(id);
	                user.setPw(pw);
	                user.setNick(nick);
	                user.setImage(image);
	                user.setHighScore(highScore);

	                list.add(user);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	}

	public void readAllUser2() {
	      String sql = "select * from jae.user;";
	      try (Connection conn = MySqlConnectionProvider.getConnection();
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql)) {

	         while (rs.next()) {
	            String id = rs.getString("id");
	            String pw = rs.getString("password");
	            String nick = rs.getString("nickname");
	            byte[] imageBytes = rs.getBytes("profilePhoto");
	            ImageIcon image = (imageBytes != null) ? new ImageIcon(imageBytes) : null;
	            int highScore = rs.getInt("highscore");

	            User user = new User();
	            user.setId(id);
	            user.setPw(pw);
	            user.setNick(nick);
	            user.setImage(image);
	            user.setHighScore(highScore);

	            list.add(user);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	   }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((pw == null) ? 0 : pw.hashCode());
		result = prime * result + status;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		if (pw == null) {
			if (other.pw != null)
				return false;
		} else if (!pw.equals(other.pw))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
}
		