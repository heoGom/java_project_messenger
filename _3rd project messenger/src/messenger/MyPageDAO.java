package messenger;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;

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

	public void changeImage(Image image, String id) {
		String sql = "UPDATE jae.user SET profilePhoto = ? WHERE id = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			BufferedImage bufferedImage = toBufferedImage(image);
			byte[] imageBytes = imageToByteArray(bufferedImage);

			stmt.setBytes(1, imageBytes);
			stmt.setString(2, id);
			stmt.executeUpdate();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteImage(String id) {
		String sql = "UPDATE jae.user SET profilePhoto = null WHERE id = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 이미지를 BufferedImage로 변환하는 메서드
	private BufferedImage toBufferedImage(Image icon) {
		if (icon instanceof BufferedImage) {
			return (BufferedImage) icon;
		}
		// BufferedImage로 변환합니다.
		BufferedImage bufferedImage = new BufferedImage(icon.getWidth(null), icon.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(icon, 0, 0, null); // 원본 이미지를 그립니다.
		g2.dispose(); // 그래픽 컨텍스트를 해제합니다.
		return bufferedImage;
	}

	// BufferedImage를 byte[]로 변환하는 메서드
	private byte[] imageToByteArray(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		return baos.toByteArray();
	}
}
