import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private int rows;
    private int columns;
    int mines;
    
    private static boolean[][] minefield;
    private static int[][] clueGrid;
    private boolean[][] checked;
    
    public Minesweeper(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        
        minefield = new boolean[rows][columns];
        clueGrid = new int[rows][columns];
        checked = new boolean[rows][columns];
        
        generateMinefield(mines);
        generateClueGrid();
        
        for (int i = 0; i < checked.length; i++) {
            for (int j = 0; j < checked[i].length; j++) {
                checked[i][j] = false;
            }
        }
    }
    
    public static void generateMinefield(int mines) {
        Random random = new Random(System.currentTimeMillis());
        
        for (int i = 0; i < minefield.length; i++) {
            for (int j = 0; j < minefield[i].length; j++) {
                minefield[i][j] = false;
            }
        }
        while (mines > 0) {
            int i = random.nextInt(minefield.length);
            int j = random.nextInt(minefield[i].length);
            
            if (minefield[i][j] == false) {
                minefield[i][j] = true;
                mines--;  
            }
        } 
    }
    
    private void generateClueGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (minefield[i][j])
                    clueGrid[i][j] = -1;
                else 
                    clueGrid[i][j] = countMines(i,j);
            } 
        }
    }
    
    private int countMines(int row,int column) {
        int count = 0;
        for (int r = Math.max(row - 1, 0); r <= Math.min(row + 1, rows - 1); r++) {
            for (int c = Math.max(column - 1, 0); c <= Math.min(column + 1, columns - 1); c++) {
                if (!(r == row && c == column)) {
                    if (minefield[r][c]) 
                        count = count + 1;  
                }
            }
        }
        return count;
    }
    
    private void printBoard() {
        System.out.print("   "); 
        for (int j = 0; j < columns; j++) {
            System.out.print(" " + (j+1));
        }
        System.out.println(); 
        
        System.out.print("  +-"); 
        for (int j = 0; j < columns; j++) {
            System.out.print("--");
        }
        System.out.println(); 
        
        char row_letter = 'A';
        for (int i = 0; i < rows; i++) {
            System.out.print(row_letter + " |"); 
            for (int j = 0; j < columns; j++) { 
                char cell_symbol;
                
                if (!checked[i][j]) 
                    cell_symbol = '?';
                
                else if (minefield[i][j]) 
                    cell_symbol = '*';
                
                else if (clueGrid[i][j] > 0) 
                    cell_symbol = (char)('0' + clueGrid[i][j]);
                
                else  
                    cell_symbol = ' ';
                System.out.print(" " + cell_symbol);  
            }
            System.out.println(); 
            row_letter++;
        }
    }
    
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean win = false;
        
        while (!win) {
            printBoard();
            System.out.print("Check cell? ");
            String line = scanner.nextLine().toUpperCase();
            int row = line.charAt(0)-'A';
            int column = line.charAt(1)-'1';
            if (minefield[row][column]) 
                break;
            else
                checked[row][column] = true;
            win = true;
            for (int i = 0 ; i < rows && win; i++)
                for (int j = 0; j < columns && win; j++)
                if (!checked[i][j] && !minefield[i][j])
                win = false; 
        }
        scanner.close(); 
        
        for (int i = 0 ; i < rows; i++)
            for (int j = 0; j < columns; j++)
            checked[i][j] = true;
        
        printBoard();
        if (win) 
            System.out.println("\nYou win!");
        else 
            System.out.println("\nYou're not very good at this are you?");
    }
    
    public static void main(String[] args) {
        Minesweeper m = new Minesweeper(3, 3, 2);
        m.start();  
    }
}