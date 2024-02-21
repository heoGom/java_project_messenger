import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class MyPageDAO {
	public void changeNick(String nick, String id) {
		String sql = "UPDATE jae.user SET nickname = ? WHERE id = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, nick);
			stmt.setString(2, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void changePW(String pw, String id) {
		String sql = "UPDATE jae.user SET password = ? WHERE id = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, pw);
			stmt.setString(2, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void changeImage(String icon, String id) {
		String sql = "UPDATE jae.user SET profilePhoto = ? WHERE id = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, icon);
			stmt.setString(2, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
