import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolverGUI extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private JButton solveButton;
    
    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 600);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 20));

                boolean isShaded = ((row / 3) + (col / 3)) % 2 == 0;
                cells[row][col].setBackground(isShaded ? new Color(220, 220, 220) : Color.WHITE);

                int top = row % 3 == 0 ? 4 : 1;
                int left = col % 3 == 0 ? 4 : 1;
                int bottom = row == SIZE - 1 ? 4 : 1;
                int right = col == SIZE - 1 ? 4 : 1;
                cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                gridPanel.add(cells[row][col]);
            }
        }

        solveButton = new JButton("Solve Sudoku");
        solveButton.setFont(new Font("Arial", Font.BOLD, 16));
        solveButton.addActionListener(new SolveButtonListener());

        add(gridPanel, BorderLayout.CENTER);
        add(solveButton, BorderLayout.SOUTH);
    }

    private class SolveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int[][] grid = new int[SIZE][SIZE];
            
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    String text = cells[row][col].getText();
                    grid[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
                }
            }

            if (solveSudoku(grid)) {
                // Update the grid with the solved values
                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        cells[row][col].setText(String.valueOf(grid[row][col]));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No solution exists for the provided Sudoku puzzle.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Helper function to solve Sudoku using backtracking
    private boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isSafe(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solveSudoku(grid)) return true;
                            grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSafe(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i] == num || grid[i][col] == num) return false;
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + startRow][j + startCol] == num) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuSolverGUI frame = new SudokuSolverGUI();
            frame.setVisible(true);
        });
    }
}
