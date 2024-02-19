import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MembershipDAO {

	private Membership membership;

	public MembershipDAO() {
	}

	public MembershipDAO(Membership membership) {
		super();
		this.membership = membership;
	}

	public boolean CheckId(String id) {
		String sql = "select * from user where id = '" + id + "';";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (!rs.next()) {
				return true;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
