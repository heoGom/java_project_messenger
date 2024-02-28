import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;

public class CustomizedOptionPane {
    public CustomizedOptionPane() {
        Font messageFont = new Font("한컴 고딕", Font.BOLD, 14);
        Font buttonFont = new Font("한컴 고딕", Font.BOLD, 13);
        Color customBackgroundColor = Color.PINK;
        Color customButtonColor = Color.PINK;
        Color customBorderColor = Color.BLACK;

        UIManager.put("OptionPane.messageFont", messageFont);
        UIManager.put("OptionPane.buttonFont", buttonFont);
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
