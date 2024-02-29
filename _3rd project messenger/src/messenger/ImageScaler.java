package messenger;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageScaler {
	public static ImageIcon getScaledImageIcon(Image o, int width, int height) {
		Image scaledImage = o.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}

	public static void main(String[] args) throws IOException {
		Image o = ImageIO.read(new File("C:\\Users\\GGG\\Desktop\\춘식\\춘식1.png"));
		ImageIcon scaledIcon = ImageScaler.getScaledImageIcon(o, 200, 200);
		System.out.println(scaledIcon.getIconWidth());
		System.out.println(scaledIcon.getIconHeight());
	}
}
