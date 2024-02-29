package messenger;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MiniGame extends JFrame {
	User user;
	Ranking ranking;
	MainPage mainPage;
	private int angle = 0;
	public int timeElapsed = 0;
	public JLabel timeLabel;
	private Timer timer;
	private Timer timer2;
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
		setLocationRelativeTo(null);
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
					// 각도를 무작위로 변경
					angle -= (int) (Math.random() * 40);
					angle = Math.max(angle, -90); // 최대 -90도까지만 허용
					if (!isTimerRunning) {
						Timer();
						BarTimer();
					}
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					// 각도를 무작위로 변경
					angle += (int) (Math.random() * 40);
					angle = Math.min(angle, 90); // 최대 90도까지만 허용
					if (!isTimerRunning) {
						BarTimer();
						Timer();
					}
				}
			}
		});
	}

	private void Timer() {
		timer = new Timer(1000, new ActionListener() {
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

	private void BarTimer() {
		timer2 = new Timer(300, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int randomAngle = (int) (Math.random() * 31); // -10부터 10까지의 랜덤 값을 생성
				int direction = angle < 0 ? -1 : 1; // 막대의 현재 기울기에 따라 방향 결정
				angle += randomAngle * direction; // 방향에 따라 랜덤 각도 적용
				angle = Math.max(Math.min(angle, 90), -90); // 최대 90도, 최소 -90도까지 허용
				repaint();
				if (angle <= -90 || angle >= 90) {
					gameOver();
				}
				int currentDelay = timer2.getDelay();
				if (currentDelay > 100) { // 최소 딜레이 값을 지정하여 무한히 빠르게 가지 않도록 설정
					timer2.setDelay(currentDelay - 1); // 감소할 딜레이 값 지정
				}
			}
		});
		timer2.setInitialDelay(0); // 초기 딜레이를 0으로 설정하여 타이머를 바로 시작
		timer2.start(); // 타이머 시작
		isTimerRunning = true;
	}

	private void gameOver() {
		if (angle == -90 || angle == 90) {
			timer.stop();
			if (user.highScore > timeElapsed) {
				user.setHighScore(user.highScore);
			} else {
				user.setHighScore(timeElapsed);
			}
			gs = new GameScore(user, MiniGame.this);
			gs.gameScoreDAO(user.id, timeElapsed);
			timer2.stop();
			removeKeyListener(getKeyListeners()[0]); // 현재 추가된 KeyListener를 제거
			JDialog dialog = new JDialog();
			dialog.setTitle("게임 오버");
			dialog.setSize(300, 200);
			dialog.setModal(true);
			JPanel panel = new JPanel();
			panel.setLayout(null);
			JLabel label = new JLabel("GAME OVER");
			JLabel label2 = new JLabel("나의 기록 : " + timeElapsed + " 초");
			JButton btn = new JButton("확인");
			JButton btn2 = new JButton("랭킹 보기");
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
					setVisible(false);
				}
			});
			btn2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
					setVisible(false);
					ranking = new Ranking();
					ranking.RankingView(user);
				}
			});

			panel.add(label);
			panel.add(label2);
			panel.add(btn);
			panel.add(btn2);
			dialog.getContentPane().add(panel);
			label.setBounds(105, 20, 100, 30);
			label2.setBounds(98, 60, 150, 30);
			btn.setBounds(35, 100, 100, 30);
			btn2.setBounds(155, 100, 100, 30);
			dialog.setLocationRelativeTo(null);
			dialog.setResizable(false);
			dialog.setVisible(true);
		}
	}
}