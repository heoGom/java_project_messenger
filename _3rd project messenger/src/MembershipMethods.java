import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MembershipMethods extends JFrame {
	private Membership membership = new Membership();

	public void checkingDB() {
		// 아이디와 닉네임 db에 있는지 체킹하는 메소드
	}

	public void allLabelSet() {
		// 텍스트필드에 들어가는 입력값에따라
		// 밑에 라벨들의 문구 가 변하는 메소드
	}

	public void displayImage(String filePath) {
		try {
			File file = new File(filePath);

			if (!isImageFile(file)) {
				JOptionPane.showMessageDialog(this, "올바른 이미지 파일이 아닙니다.", "에러", JOptionPane.ERROR_MESSAGE);
				return;
			}

			BufferedImage image = ImageIO.read(file);

			if (image != null) {
				ImageIcon icon = new ImageIcon(image);
				// pictureLabel.setIcon(icon);
			} else {
				JOptionPane.showMessageDialog(this, "이미지를 읽을 수 없습니다.", "에러", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isImageFile(File file) {
		try {
			ImageIO.read(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	

}
