package Projects.P02;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class TicTacToe extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 305, HEIGTH = 230;
	private static final String[] VICTORY_ORDER = { "012", "036", "048", "147", "246", "258", "345", "678" };
	private JLabel score, xScore, oScore;
	private int xPoint, oPoint;
	private JButton clean, reset;
	private JButton[] buttons;
	private boolean[] mouseClicks;
	private boolean signal;

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton action = (JButton) e.getSource();

		if (action == clean) {
			clean();
		} else if (action == reset) {
			xPoint = 0;
			oPoint = 0;
			update();
			clean();
		}

		for (int i = 0; i < 9; i++) {
			if (action == buttons[i]) {
				if (!mouseClicks[i]) {
					mouseClicks[i] = true;
					change(buttons[i]);
				}
			}
		}
	}

	public TicTacToe() {
		/* JANELA */
		this.setTitle("Tic Tac Toe");
		this.setLayout(null);
		this.setSize(WIDTH, HEIGTH);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true); // Trecho de código transferido do main()

		/* RÓTULOS */
		score = new JLabel("SCORE");
		score.setBounds(190, 15, 100, 30);
		this.add(score);

		xScore = new JLabel("X: " + xPoint);
		xScore.setBounds(190, 36, 100, 30);
		this.add(xScore);

		oScore = new JLabel("O: " + oPoint);
		oScore.setBounds(225, 36, 100, 30);
		this.add(oScore);

		/* BOTÕES */
		clean = new JButton("Clean");
		clean.setBounds(190, 105, 80, 30);
		clean.addActionListener(this);
		this.add(clean);

		reset = new JButton("Reset");
		reset.setBounds(190, 142, 80, 30);
		reset.addActionListener(this);
		this.add(reset);

		buttons = new JButton[9];
		int xCoordenate, yCoordenate;
		int actualPosition = 0;
		for (int i = 0; i < 3; i++) {
			xCoordenate = ((51 * i) + 20);

			for (int j = 0; j < 3; j++) {
				yCoordenate = ((51 * j) + 20);
				buttons[actualPosition] = new JButton();
				buttons[actualPosition].setBounds(xCoordenate, yCoordenate, 50, 50);
				buttons[actualPosition].setFont(new Font("Tw Cen MT", Font.BOLD, 22));
				buttons[actualPosition].addActionListener(this);
				this.add(buttons[actualPosition]);
				actualPosition++;
			}
		}

		mouseClicks = new boolean[9];
		for (int i = 0; i < 9; i++) {
			mouseClicks[i] = false;
		}
	}

	public void update() {
		xScore.setText("X: " + xPoint);
		oScore.setText("O: " + oPoint);
	}

	public void change(JButton button) {
		if (signal) {
			button.setText("O");
			signal = false;
		} else {
			button.setText("X");
			signal = true;
		}
		checkWinner();
	}

	public void clean() {
		int counter = 0;
		do {
			if (mouseClicks[counter]) {
				mouseClicks[counter] = false;
				buttons[counter].setText(null);
			}
			counter++;
		} while (counter < 9);

		signal = false;
	}

	public void checkWinner() {
		int selectedPositions = 0;
		for (int i = 0; i < 9; i++) {
			if (mouseClicks[i]) {
				selectedPositions++;
			}
		}

		if (selectedPositions == 9) {
			JOptionPane.showMessageDialog(null, "No winner!", "Draw", JOptionPane.INFORMATION_MESSAGE);
			clean();
		} else if (selectedPositions >= 5) {
			for (int i = 0; i < VICTORY_ORDER.length; i++) {
				int p0 = Character.getNumericValue(VICTORY_ORDER[i].charAt(0));
				int p1 = Character.getNumericValue(VICTORY_ORDER[i].charAt(1));
				int p2 = Character.getNumericValue(VICTORY_ORDER[i].charAt(2));

				if (buttons[p0].getText() == "X" || buttons[p0].getText() == "O") {
					if ((buttons[p0].getText() == buttons[p1].getText()
							&& buttons[p1].getText() == buttons[p2].getText())) {
						showWinner(buttons[p0].getText());
					}
				}
			}
		}
	}

	public void showWinner(String winner) {
		JOptionPane.showMessageDialog(null, (winner + " won!"), "Winner", JOptionPane.INFORMATION_MESSAGE);
		if (winner == "X") {
			xPoint++;
		} else if (winner == "O") {
			oPoint++;
		}
		update();
		clean();
	}

	public static void main(String[] args) {
		new TicTacToe();
	}
}