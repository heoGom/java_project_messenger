package messenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameScore {
	User user;
	MiniGame minigame;

	public GameScore(User user, MiniGame minigame) {
		this.user = user;
		this.minigame = minigame;
	}

	public void gameScoreDAO(String id, int highscore) {
		// 사용자의 최고 점수를 가져옴
		int userHighScore = getUserHighScore(id);

		// 사용자의 최고 점수가 0이거나 현재 게임의 점수가 최고 점수를 갱신했을 때
		if (userHighScore == 0 || highscore > userHighScore) {
			try (Connection conn = MySqlConnectionProvider.getConnection();
					PreparedStatement stmt = conn.prepareStatement("UPDATE jae.user SET highscore = ? WHERE id = ?")) {
				stmt.setInt(1, highscore);
				stmt.setString(2, id);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private int getUserHighScore(String id) {
		int highScore = 0;
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT highscore FROM jae.user WHERE id = ?")) {
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				highScore = rs.getInt("highscore");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return highScore;
	}
}