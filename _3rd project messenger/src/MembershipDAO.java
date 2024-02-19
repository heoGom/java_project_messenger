import java.sql.Connection;
import java.sql.PreparedStatement;

import com.mysql.cj.protocol.Resultset;

public class MembershipDAO {

	public MembershipDAO() {
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
				Resultset rs = stmt.execute();
		}
	}
}
