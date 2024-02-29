import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CustomizedOptionPane {
    public CustomizedOptionPane() {
        Color customBackgroundColor = new Color(233, 255, 223);
        Color customButtonColor = new Color(127, 237, 75);
        Color customBorderColor = null;

        UIManager.put("OptionPane.background", customBackgroundColor);
        UIManager.put("Button.background", customButtonColor);
        UIManager.put("Button.border", new BorderUIResource.LineBorderUIResource(customBorderColor));
        UIManager.put("Panel.background", customBackgroundColor); // 다이얼로그의 메시지 영역 배경색 변경
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
