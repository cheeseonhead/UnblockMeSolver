import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;

public class puzzleInput extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel[][] boardCells;
	private JButton solveButton;
	private JButton resetButton;
	private JPanel boardPanel;
	private JRadioButton redPieceSelect;
	private JRadioButton otherPieceSelect;
	private ButtonGroup pieceSelectionGroup;
	private JPanel toolPanel;
	private JPanel pieceSelectionPanel;
	private JButton previousStep;
	private JButton nextStep;
	private JLabel steps;

	// private Board b;

	public puzzleInput() {

		// initialize Window
		super("Unblock Me Solver");
		setLayout(new FlowLayout());
		setSize(450, 325);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// initialize boardPanel
		boardPanel = new JPanel();
		boardPanel.setLayout(new FlowLayout());
		boardPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		boardPanel.setPreferredSize(new Dimension(280, 280));
		add(boardPanel);

		// initialize toolPanel
		toolPanel = new JPanel();
		toolPanel.setLayout(new FlowLayout());
		toolPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		toolPanel.setPreferredSize(new Dimension(130, 230));
		add(toolPanel);

		// initialize pieceSelectionPanel
		pieceSelectionPanel = new JPanel();
		pieceSelectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pieceSelectionPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1,
				1, Color.BLACK));
		pieceSelectionPanel.setPreferredSize(new Dimension(120, 70));
		toolPanel.add(pieceSelectionPanel);

		// initialize pieceSelectionGroup
		redPieceSelect = new JRadioButton("Red Piece", true);
		otherPieceSelect = new JRadioButton("Other Piece", false);
		pieceSelectionPanel.add(redPieceSelect);
		pieceSelectionPanel.add(otherPieceSelect);
		pieceSelectionGroup = new ButtonGroup();
		pieceSelectionGroup.add(redPieceSelect);
		pieceSelectionGroup.add(otherPieceSelect);

		// Place all the cells on the window
		boardCells = new JPanel[6][6];
		// boardCells = new BoardCell[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				boardCells[i][j] = new JPanel();
				boardCells[i][j].setBackground(Color.white);
				boardCells[i][j].setPreferredSize(new Dimension(40, 40));
				boardPanel.add(boardCells[i][j]);
			}
		}

		// Tool buttons
		solveButton = new JButton("Solve");
		resetButton = new JButton("Reset");
		resetButton.setEnabled(false);
		toolPanel.add(solveButton);
		toolPanel.add(resetButton);

		// Step buttons
		previousStep = new JButton("<");
		previousStep.setPreferredSize(new Dimension(50, 25));
		previousStep.setEnabled(false);
		nextStep = new JButton(">");
		nextStep.setPreferredSize(new Dimension(50, 25));
		nextStep.setEnabled(false);
		toolPanel.add(previousStep);
		toolPanel.add(nextStep);

		// Step label
		steps = new JLabel("NONE");
		steps.setPreferredSize(new Dimension(50, 50));
		steps.setHorizontalAlignment(SwingConstants.CENTER);
		toolPanel.add(steps);

		// Add cellHandler to cells
		Handler handler = new Handler();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				boardCells[i][j].addMouseListener(handler);
				boardCells[i][j].addMouseMotionListener(handler);
			}
		}

		// Add Handler to Button
		resetButton.addActionListener(handler);
		solveButton.addActionListener(handler);
		previousStep.addActionListener(handler);
		nextStep.addActionListener(handler);

		// Add handler to button
		solveButton.addKeyListener(handler);
		resetButton.addKeyListener(handler);
		previousStep.addKeyListener(handler);
		nextStep.addKeyListener(handler);
		redPieceSelect.addKeyListener(handler);
		otherPieceSelect.addKeyListener(handler);

	}

	private class Handler implements MouseListener, MouseMotionListener,
			ActionListener, KeyListener {

		private boolean mousePressedOnWhite = false;
		Color[] colorOrder = new Color[] { Color.black, Color.blue, Color.cyan,
				Color.darkGray, Color.gray, Color.green, Color.lightGray,
				Color.magenta, Color.orange, Color.pink, Color.yellow,
				new Color(139, 69, 19), new Color(0, 128, 0),
				new Color(128, 0, 128), new Color(64, 224, 208),
				new Color(184, 134, 11) };
		private int maxColorCount = 16;
		private boolean[] isColorUsed = new boolean[maxColorCount];
		private int currentColor = 0;

		public void mouseClicked(MouseEvent event) {

		}

		@Override
		public void mouseDragged(MouseEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent event) {
			// TODO Auto-generated method stub

			resetButton.setEnabled(true);
			if (event.getComponent().getBackground() == Color.WHITE) {
				if (redPieceSelect.isSelected()) {
					event.getComponent().setBackground(Color.RED);
				} else if (otherPieceSelect.isSelected()) {
					event.getComponent()
							.setBackground(colorOrder[currentColor]);
				}
				mousePressedOnWhite = true;
			} else if (event.getComponent().getBackground() == Color.RED) {
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 6; j++) {
						if (boardCells[i][j].getBackground() == Color.RED) {
							boardCells[i][j].setBackground(Color.WHITE);
						}
					}
				}
			} else {
				int colorCancelIndex = 0;
				for (int i = 0; i < maxColorCount; i++) {
					if (colorOrder[i] == event.getComponent().getBackground()) {
						colorCancelIndex = i;
						break;
					}
				}
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 6; j++) {
						if (boardCells[i][j].getBackground() == colorOrder[colorCancelIndex]) {
							boardCells[i][j].setBackground(Color.WHITE);
						}
					}
				}
				isColorUsed[colorCancelIndex] = false;
			}
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			// TODO Auto-generated method stub
			mousePressedOnWhite = false;
			if (event.getComponent().getBackground() == colorOrder[currentColor]) {
				isColorUsed[currentColor] = true;
			}
			for (int i = 0; i < maxColorCount; i++) {
				if (!isColorUsed[i]) {
					currentColor = i;
					break;
				}
			}
			if (redPieceSelect.isSelected()) {
				otherPieceSelect.setSelected(true);
				redPieceSelect.setSelected(false);
			}
		}

		@Override
		public void mouseEntered(MouseEvent event) {
			// TODO Auto-generated method stub

			if (mousePressedOnWhite) {
				if (redPieceSelect.isSelected()) {
					event.getComponent().setBackground(Color.RED);
				} else if (otherPieceSelect.isSelected()) {
					event.getComponent()
							.setBackground(colorOrder[currentColor]);
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent event) {
			// TODO Auto-generated method stub
		}

		int nextOrder = 0;
		int currentOrder = 0;
		Color[][][] solveOrder = new Color[100][6][6];

		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == resetButton) {
				Arrays.fill(isColorUsed, false);
				currentColor = 0;
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 6; j++) {
						boardCells[i][j].setBackground(Color.WHITE);
					}
				}
				resetButton.setEnabled(false);
				solveButton.setEnabled(true);
				nextStep.setEnabled(false);
				previousStep.setEnabled(false);
				steps.setText("NONE");
				redPieceSelect.setSelected(true);
				otherPieceSelect.setSelected(false);
			} else if (event.getSource() == solveButton) {

				Board originalBoard = new Board(boardCells);
				originalBoard.puzzleIn();
				originalBoard.puzzleRun();
				currentOrder = 0;
				nextOrder = 0;

				while (originalBoard.solvingSteps.peek() != null) {
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 6; j++) {
							int pieceID = originalBoard.solvingSteps.peek().bcur[i + 1][j + 1];
							if (pieceID != 0) {
								solveOrder[nextOrder][i][j] = originalBoard.pieceList[pieceID].pieceColor;
							} else if (pieceID == 0) {
								solveOrder[nextOrder][i][j] = Color.white;
							}
						}
					}
					nextOrder++;
					originalBoard.solvingSteps.remove();
				}
				nextOrder--;
				solveButton.setEnabled(false);
				resetButton.setEnabled(true);
				if (nextOrder > 0) {
					nextStep.setEnabled(true);
					steps.setText(String.format("%d", currentOrder));
				} else {
					steps.setText("Impossible");
				}
				
				System.out.println(originalBoard.bid);

			} else if (event.getSource() == previousStep) {
				if (currentOrder > 0) {
					currentOrder--;
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 6; j++) {
							boardCells[i][j]
									.setBackground(solveOrder[currentOrder][i][j]);
						}
					}
				}
				if (currentOrder == 0) {
					previousStep.setEnabled(false);
				}
				nextStep.setEnabled(true);
				steps.setText(String.format("%d", currentOrder));
			} else if (event.getSource() == nextStep) {
				if (currentOrder < nextOrder) {
					currentOrder++;
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 6; j++) {
							boardCells[i][j]
									.setBackground(solveOrder[currentOrder][i][j]);
						}
					}
				}
				if (currentOrder == nextOrder) {
					nextStep.setEnabled(false);
				}
				previousStep.setEnabled(true);
				steps.setText(String.format("%d", currentOrder));
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == 37 && previousStep.isEnabled()) {
				if (currentOrder > 0) {
					currentOrder--;
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 6; j++) {
							boardCells[i][j]
									.setBackground(solveOrder[currentOrder][i][j]);
						}
					}
				}
				if (currentOrder == 0) {
					previousStep.setEnabled(false);
				}
				nextStep.setEnabled(true);
				steps.setText(String.format("%d", currentOrder));
			} else if (e.getKeyCode() == 39 && nextStep.isEnabled()) {
				if (currentOrder < nextOrder) {
					currentOrder++;
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 6; j++) {
							boardCells[i][j]
									.setBackground(solveOrder[currentOrder][i][j]);
						}
					}
				}
				if (currentOrder == nextOrder) {
					nextStep.setEnabled(false);
				}
				previousStep.setEnabled(true);
				steps.setText(String.format("%d", currentOrder));
			} else if (e.getKeyCode() == 82 && resetButton.isEnabled()) {
				Arrays.fill(isColorUsed, false);
				currentColor = 0;
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 6; j++) {
						boardCells[i][j].setBackground(Color.WHITE);
					}
				}
				resetButton.setEnabled(false);
				solveButton.setEnabled(true);
				nextStep.setEnabled(false);
				previousStep.setEnabled(false);
				steps.setText("NONE");
				redPieceSelect.setSelected(true);
				otherPieceSelect.setSelected(false);
			} else if (e.getKeyCode() == 83 && solveButton.isEnabled()) {
				Board originalBoard = new Board(boardCells);
				originalBoard.puzzleIn();
				originalBoard.puzzleRun();
				currentOrder = 0;
				nextOrder = 0;

				while (originalBoard.solvingSteps.peek() != null) {
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 6; j++) {
							int pieceID = originalBoard.solvingSteps.peek().bcur[i + 1][j + 1];
							if (pieceID != 0) {
								solveOrder[nextOrder][i][j] = originalBoard.pieceList[pieceID].pieceColor;
							} else if (pieceID == 0) {
								solveOrder[nextOrder][i][j] = Color.white;
							}
						}
					}
					nextOrder++;
					originalBoard.solvingSteps.remove();
				}
				nextOrder--;
				solveButton.setEnabled(false);
				resetButton.setEnabled(true);
				if (nextOrder > 0) {
					nextStep.setEnabled(true);
					steps.setText(String.format("%d", currentOrder));
				} else {
					steps.setText("Impossible");
				}
				System.out.println(originalBoard.bid);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}
}
