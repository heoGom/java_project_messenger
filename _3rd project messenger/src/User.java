import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;

public class User {
	String id = null;
	String pw = null;
	String nick = null;
	ImageIcon image = null;

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

}
