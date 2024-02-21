import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
