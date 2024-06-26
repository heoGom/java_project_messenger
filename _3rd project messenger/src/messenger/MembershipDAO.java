package messenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

	/**
	 * 입력한 아이디가 데이터베이스 있는지 확인
	 * 
	 * @param id 확인하고 싶은 아이디
	 * @return 중복된 아이디가 있다면 false, 없으면 true
	 * 
	 */
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

	public boolean CheckNick(String nickname) {
		String sql = "select * from user where nickname = '" + nickname + "';";
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

	public boolean CheckPW(String id, String pw) {
		String sql = "select * from user where password = '" + pw + "' and id ='" + id + "'";
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

	// 상태 1로
	public void changeStatus(String id) {
		String sql = "UPDATE jae.user SET status = 1 WHERE id = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 상태 0으로
	public void resetStatus(String id) {
		String sql = "UPDATE jae.user SET status = 0 WHERE id = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
