import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserList extends JFrame {
    User user;
  
    List<User> userList;

    public UserList(User user, List<User> userList) {
        this.user = user;
        this.userList = userList; // 추가: userList 초기화
        extracted();
        showGUI();
    }

    private void showGUI() {
        setSize(441, 553);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void extracted() {
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("내사진");
        lblNewLabel.setBounds(12, 10, 57, 15);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("내 닉네임");
        lblNewLabel_1.setBounds(81, 10, 77, 15);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNick = new JLabel(user.getNick());
        lblNick.setBounds(150, 10, 200, 15);
        getContentPane().add(lblNick);

        JLabel lblNewLabel_2 = new JLabel("가입자 목록 몇명인지?");
        lblNewLabel_2.setBounds(25, 79, 110, 15);
        getContentPane().add(lblNewLabel_2);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(12, 108, 402, 402);
        getContentPane().add(scrollPane);

        // 수정: userList에서 닉네임을 뽑아내어 각각의 JLabel에 설정
        for (User u : userList) {
            JPanel pnl = new JPanel();
            pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel lbl = new JLabel(u.getNick());
            pnl.add(lbl);
            panel.add(pnl);
        }

        JButton btnNewButton = new JButton("새로고침");
        btnNewButton.setBounds(317, 75, 97, 23);
        getContentPane().add(btnNewButton);
    }
}