import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MiniGame extends JFrame {
	private int angle = 0;
	private int timeElapsed = 0;
	private JLabel timeLabel;
	private Timer timer;
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
			if (angle == -90) {
				timer.stop();
			}
			
			
		}
	}

	public MiniGame() {
		setTitle("막대기 세우기");
		setSize(500, 400);
		setResizable(false);

		JPanel barPanel = new BarPanel();
		add(barPanel, BorderLayout.CENTER);
		JPanel timerPanel = new JPanel();
		add(timerPanel, BorderLayout.NORTH);
		timeLabel = new JLabel();
		timerPanel.add(timeLabel);

		BarPanel(); // BarPanel 생성자 호출

		setFocusable(true);
	}

	public void BarPanel() {
		setFocusable(true); // 키 이벤트를 받을 수 있도록 설정
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_LEFT) {
					angle -= 5; // 왼쪽 화살표를 누르면 반시계 방향으로 5도 회전
					repaint();
					if (!isTimerRunning) {
						startTimer();
						if (angle == -90) {
							timer.stop();
						}
					}
					System.out.println(angle); // -90
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					angle += 5; // 오른쪽 화살표를 누르면 시계 방향으로 5도 회전
					if (!isTimerRunning) {
						startTimer();
					}
					repaint();
					System.out.println(angle); // +90
				}
			}
		});
	}

	private void startTimer() {
		timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timeElapsed++;
				timeLabel.setText("시간 : " + timeElapsed + "초");
			}
		});
		timer.setInitialDelay(0); // 초기 딜레이를 0으로 설정하여 타이머를 바로 시작
		timer.start(); // 타이머 시작
		isTimerRunning = true;
	}
	

	public static void main(String[] args) {
		MiniGame miniGame = new MiniGame();
		miniGame.setVisible(true);
		miniGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
