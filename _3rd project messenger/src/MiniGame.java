import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MiniGame extends JFrame {
   User user;
   private int angle = 0;
   public int timeElapsed = 0;
   public JLabel timeLabel;
   private Timer timer;
   private GameScore gs;
   private boolean isTimerRunning = false;

   class BarPanel extends JPanel {
      @Override
      protected void paintComponent(Graphics g) {
         super.paintComponent(g); // 부모의 paintComponent 호출

         g.setColor(Color.green);
         g.fillRect(0, 300, getWidth(), getHeight());
         Graphics2D g2d = (Graphics2D) g.create();
         g2d.rotate(Math.toRadians(angle), 235, 300);
         g2d.setColor(Color.RED);
         g2d.fillRect(225, 200, 20, 100);
      }
   }

   public MiniGame(User user) {
      this.user = user;
      setTitle("막대기 세우기");
      setSize(500, 400);
      setResizable(false);

      JPanel barPanel = new BarPanel();
      add(barPanel, BorderLayout.CENTER);
      JPanel timerPanel = new JPanel();
      add(timerPanel, BorderLayout.NORTH);
      timeLabel = new JLabel();
      timerPanel.add(timeLabel);

      addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            repaint();
            if (keyCode == KeyEvent.VK_LEFT) {
               angle -= 5; // 왼쪽 화살표를 누르면 반시계 방향으로 5도 회전
               if (!isTimerRunning) {
                  Timer();
               }
               System.out.println(angle); // -90
            } else if (keyCode == KeyEvent.VK_RIGHT) {
               angle += 5; // 오른쪽 화살표를 누르면 시계 방향으로 5도 회전
               if (!isTimerRunning) {
                  Timer();
               }
               System.out.println(angle); // +90
            }
         }
      });
   }

   private void Timer() {
      timer = new Timer(100, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            timeElapsed++;
            timeLabel.setText("시간 : " + timeElapsed + "초");
            if (angle == -90 || angle == 90) {
               System.out.println(timeElapsed + "초");
               gameOver();
               user.setHighScore(timeElapsed);
               gs = new GameScore(user, MiniGame.this);
               gs.gameScoreDAO(user.id, timeElapsed);
            }
         }
      });
      timer.setInitialDelay(0); // 초기 딜레이를 0으로 설정하여 타이머를 바로 시작
      timer.start(); // 타이머 시작
      isTimerRunning = true;
   }

   private void gameOver() {
      if (angle == -90 || angle == 90) {
         removeKeyListener(getKeyListeners()[0]); // 현재 추가된 KeyListener를 제거
         timer.stop();
         JDialog dialog = new JDialog();
         dialog.setTitle("게임 오버");
         dialog.setSize(300,200);
         dialog.setModal(true);
         JPanel panel = new JPanel();
         panel.setLayout(null);
         JLabel label = new JLabel("GAME OVER");
         JLabel label2 = new JLabel("나의 기록 : " + timeElapsed + " 초");
         JButton btn = new JButton("확인");
         btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dialog.setVisible(false);
               setVisible(false);
            }
         });
         panel.add(label);
         panel.add(label2);
         panel.add(btn);
         dialog.getContentPane().add(panel);
         label.setBounds(105, 20, 100, 30);
         label2.setBounds(98, 60, 100, 30);
         btn.setBounds(95, 100, 100, 30);
         dialog.setVisible(true);
      }
   }
}