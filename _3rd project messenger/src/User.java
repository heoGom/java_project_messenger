import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class User {
	String id = null;
	String pw = null;
	String nick = null;
	ImageIcon image = null;
	List<User> list = new ArrayList<>();
	public User() {
	}

	public User(String id, String pw, String nick, ImageIcon image) {
		super();
		this.id = id;
		this.pw = pw;
		this.nick = nick;
		this.image = image;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", pw=" + pw + ", nick=" + nick + ", image=" + image + "]";
	}

	public void readAllUser() {
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

				User user = new User();
				user.setId(id);
				user.setPw(pw);
				user.setNick(nick);
				user.setImage(image);

				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (User user : list) {
			System.out.println(user);
		}
	}

}
