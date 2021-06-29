import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

final class TicTacToe
{
    private final char[] board;
    private int pos;
    private char winner;
    private int moveCount;
    private boolean end;
    private boolean smart;
    private final boolean[] user;
    private final boolean[] pc;
    
    static void clear() {
        try {
            new ProcessBuilder(new String[] { "cmd", "/c", "cls" }).inheritIO().start().waitFor();
        }
        catch (IOException | InterruptedException ex2) {
            final Exception ex;
            final Exception e1 = ex;
            System.out.println(e1);
        }
    }
    
    TicTacToe() {
        this.board = new char[] { '~', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        this.winner = ' ';
        this.moveCount = 0;
        this.user = new boolean[10];
        this.pc = new boolean[10];
        System.out.println("Welcome to TIC TAC TOE");
        System.out.println("You're symbol is 'O'");
        System.out.println("You're opponent's symbol is 'X'");
        System.out.println("Please Enter one of the digits shown on board");
        this.printBoard();
    }
    
    void start() {
        this.move();
    }
    
    void printBoard() {
        System.out.println();
        int i = 1;
        while (i < 10) {
            System.out.println(this.board[i++] + " | " + this.board[i++] + " | " + this.board[i++]);
            if (i < 9) {
                System.out.println("----------");
            }
        }
        System.out.println();
    }
    
    void chkDraw() {
        if (this.moveCount == 9) {
            this.winner = 'N';
            this.end = true;
        }
    }
    
    void chkWin() {
        if ((this.board[1] == 'X' && this.board[5] == 'X' && this.board[9] == 'X') || (this.board[3] == 'X' && this.board[5] == 'X' && this.board[7] == 'X') || (this.board[2] == 'X' && this.board[5] == 'X' && this.board[8] == 'X') || (this.board[4] == 'X' && this.board[5] == 'X' && this.board[6] == 'X') || (this.board[3] == 'X' && this.board[2] == 'X' && this.board[1] == 'X') || (this.board[1] == 'X' && this.board[4] == 'X' && this.board[7] == 'X') || (this.board[7] == 'X' && this.board[8] == 'X' && this.board[9] == 'X') || (this.board[9] == 'X' && this.board[6] == 'X' && this.board[3] == 'X')) {
            this.winner = 'X';
            this.end = true;
        }
        else if ((this.board[1] == 'O' && this.board[5] == 'O' && this.board[9] == 'O') || (this.board[3] == 'O' && this.board[5] == 'O' && this.board[7] == 'O') || (this.board[2] == 'O' && this.board[5] == 'O' && this.board[8] == 'O') || (this.board[4] == 'O' && this.board[5] == 'O' && this.board[6] == 'O') || (this.board[3] == 'O' && this.board[2] == 'O' && this.board[1] == 'O') || (this.board[1] == 'O' && this.board[4] == 'O' && this.board[7] == 'O') || (this.board[7] == 'O' && this.board[8] == 'O' && this.board[9] == 'O') || (this.board[9] == 'O' && this.board[6] == 'O' && this.board[3] == 'O')) {
            this.winner = 'O';
            this.end = true;
        }
        else {
            this.chkDraw();
        }
    }
    
    void declareWinner() {
        final Scanner sc = new Scanner(System.in);
        if (this.winner == 'X') {
            System.out.println("I won. Let's play again soon.");
        }
        if (this.winner == 'O') {
            System.out.println("Congratulations. You won.");
        }
        if (this.winner == 'N') {
            System.out.println("The game ended with no winner");
        }
        System.out.println("Enter YES or 1 to play again!!!\nEnter anything else to quit");
        final String s = sc.nextLine();
        if (s.equals("YES") || s.equals("yes") || s.equals("1")) {
            clear();
            final TicTacToe t = new TicTacToe();
            t.start();
        }
    }
    
    void move() {
        final Scanner scan = new Scanner(System.in);
        System.out.println("Your move?");
        this.pos = scan.nextInt();
        if (this.pos > 0 && this.pos < 10 && this.board[this.pos] != 'X' && this.board[this.pos] != 'O') {
            this.board[this.pos] = 'O';
            this.user[this.pos] = true;
        }
        else {
            System.out.println("Please Enter one of the digits shown on board");
            this.move();
        }
        ++this.moveCount;
        clear();
        this.printBoard();
        this.chkWin();
        if (this.end) {
            this.declareWinner();
            return;
        }
        this.pcMove();
    }
    
    void pcMove() {
        this.smart = false;
        System.out.println("Thinking. . . .\n");
        if (this.moveCount == 1) {
            if (this.pos == 5) {
                this.board[9] = 'X';
                this.pc[9] = true;
            }
            else {
                this.board[5] = 'X';
                this.pc[5] = true;
            }
        }
        else if (this.moveCount == 3) {
            this.pcSmartMove();
            if (!this.smart) {
                this.pcBlockMove();
            }
            this.pcBooleanFill();
        }
        else if (this.moveCount == 5) {
            this.pcSmartMove();
            if (!this.smart) {
                this.pcBlockMove();
            }
            this.pcBooleanFill();
        }
        else {
            this.pcSmartMove();
            if (!this.smart) {
                this.pcBlockMove();
            }
            this.pcBooleanFill();
        }
        ++this.moveCount;
        System.out.println("Computer's move ::");
        this.printBoard();
        this.chkWin();
        if (this.end) {
            this.declareWinner();
            return;
        }
        this.move();
    }
    
    void pcBooleanFill() {
        for (int i = 1; i < 10; ++i) {
            if (this.board[i] == 'X') {
                this.pc[i] = true;
            }
        }
    }
    
    void pcSmartMove() {
        if (this.board[3] != 'O' && this.board[3] != 'X' && this.board[2] == 'X' && this.board[1] == 'X') {
            this.board[3] = 'X';
            this.smart = true;
        }
        else if (this.board[3] == 'X' && this.board[2] != 'O' && this.board[2] != 'X' && this.board[1] == 'X') {
            this.board[2] = 'X';
            this.smart = true;
        }
        else if (this.board[3] == 'X' && this.board[2] == 'X' && this.board[1] != 'O' && this.board[1] != 'X') {
            this.board[1] = 'X';
            this.smart = true;
        }
        else if (this.board[1] != 'O' && this.board[1] != 'X' && this.board[4] == 'X' && this.board[7] == 'X') {
            this.board[1] = 'X';
            this.smart = true;
        }
        else if (this.board[1] == 'X' && this.board[4] != 'O' && this.board[4] != 'X' && this.board[7] == 'X') {
            this.board[4] = 'X';
            this.smart = true;
        }
        else if (this.board[1] == 'X' && this.board[4] == 'X' && this.board[7] != 'O' && this.board[7] != 'X') {
            this.board[7] = 'X';
            this.smart = true;
        }
        else if (this.board[7] != 'O' && this.board[7] != 'X' && this.board[8] == 'X' && this.board[9] == 'X') {
            this.board[7] = 'X';
            this.smart = true;
        }
        else if (this.board[7] == 'X' && this.board[8] != 'O' && this.board[8] != 'X' && this.board[9] == 'X') {
            this.board[8] = 'X';
            this.smart = true;
        }
        else if (this.board[7] == 'X' && this.board[8] == 'X' && this.board[9] != 'O' && this.board[9] != 'X') {
            this.board[9] = 'X';
            this.smart = true;
        }
        else if (this.board[9] != 'O' && this.board[9] != 'X' && this.board[6] == 'X' && this.board[3] == 'X') {
            this.board[9] = 'X';
            this.smart = true;
        }
        else if (this.board[9] == 'X' && this.board[6] != 'O' && this.board[6] != 'X' && this.board[3] == 'X') {
            this.board[6] = 'X';
            this.smart = true;
        }
        else if (this.board[9] == 'X' && this.board[6] == 'X' && this.board[3] != 'O' && this.board[3] != 'X') {
            this.board[3] = 'X';
            this.smart = true;
        }
        else if (this.board[1] != 'O' && this.board[1] != 'X' && this.board[5] == 'X' && this.board[9] == 'X') {
            this.board[1] = 'X';
            this.smart = true;
        }
        else if (this.board[1] == 'X' && this.board[5] != 'O' && this.board[5] != 'X' && this.board[9] == 'X') {
            this.board[5] = 'X';
            this.smart = true;
        }
        else if (this.board[1] == 'X' && this.board[5] == 'X' && this.board[9] != 'O' && this.board[9] != 'X') {
            this.board[9] = 'X';
            this.smart = true;
        }
        else if (this.board[3] != 'O' && this.board[3] != 'X' && this.board[5] == 'X' && this.board[7] == 'X') {
            this.board[3] = 'X';
            this.smart = true;
        }
        else if (this.board[3] == 'X' && this.board[5] != 'O' && this.board[5] != 'X' && this.board[7] == 'X') {
            this.board[5] = 'X';
            this.smart = true;
        }
        else if (this.board[3] == 'X' && this.board[5] == 'X' && this.board[7] != 'O' && this.board[7] != 'X') {
            this.board[7] = 'X';
            this.smart = true;
        }
        else if (this.board[2] != 'O' && this.board[2] != 'X' && this.board[5] == 'X' && this.board[8] == 'X') {
            this.board[2] = 'X';
            this.smart = true;
        }
        else if (this.board[2] == 'X' && this.board[5] != 'O' && this.board[5] != 'X' && this.board[8] == 'X') {
            this.board[5] = 'X';
            this.smart = true;
        }
        else if (this.board[2] == 'X' && this.board[5] == 'X' && this.board[8] != 'O' && this.board[8] != 'X') {
            this.board[8] = 'X';
            this.smart = true;
        }
        else if (this.board[4] != 'O' && this.board[4] != 'X' && this.board[5] == 'X' && this.board[6] == 'X') {
            this.board[4] = 'X';
            this.smart = true;
        }
        else if (this.board[4] == 'X' && this.board[5] != 'O' && this.board[5] != 'X' && this.board[6] == 'X') {
            this.board[5] = 'X';
            this.smart = true;
        }
        else if (this.board[4] == 'X' && this.board[5] == 'X' && this.board[6] != 'O' && this.board[6] != 'X') {
            this.board[6] = 'X';
            this.smart = true;
        }
    }
    
    void pcBlockMove() {
        if (this.board[3] != 'O' && this.board[3] != 'X' && this.board[2] == 'O' && this.board[1] == 'O') {
            this.board[3] = 'X';
        }
        else if (this.board[3] == 'O' && this.board[2] != 'O' && this.board[2] != 'X' && this.board[1] == 'O') {
            this.board[2] = 'X';
        }
        else if (this.board[3] == 'O' && this.board[2] == 'O' && this.board[1] != 'O' && this.board[1] != 'X') {
            this.board[1] = 'X';
        }
        else if (this.board[1] != 'O' && this.board[1] != 'X' && this.board[4] == 'O' && this.board[7] == 'O') {
            this.board[1] = 'X';
        }
        else if (this.board[1] == 'O' && this.board[4] != 'O' && this.board[4] != 'X' && this.board[7] == 'O') {
            this.board[4] = 'X';
        }
        else if (this.board[1] == 'O' && this.board[4] == 'O' && this.board[7] != 'O' && this.board[7] != 'X') {
            this.board[7] = 'X';
        }
        else if (this.board[7] != 'O' && this.board[7] != 'X' && this.board[8] == 'O' && this.board[9] == 'O') {
            this.board[7] = 'X';
        }
        else if (this.board[7] == 'O' && this.board[8] != 'O' && this.board[8] != 'X' && this.board[9] == 'O') {
            this.board[8] = 'X';
        }
        else if (this.board[7] == 'O' && this.board[8] == 'O' && this.board[9] != 'O' && this.board[9] != 'X') {
            this.board[9] = 'X';
        }
        else if (this.board[9] != 'O' && this.board[9] != 'X' && this.board[6] == 'O' && this.board[3] == 'O') {
            this.board[9] = 'X';
        }
        else if (this.board[9] == 'O' && this.board[6] != 'O' && this.board[6] != 'X' && this.board[3] == 'O') {
            this.board[6] = 'X';
        }
        else if (this.board[9] == 'O' && this.board[6] == 'O' && this.board[3] != 'O' && this.board[3] != 'X') {
            this.board[3] = 'X';
        }
        else if (this.board[1] != 'O' && this.board[1] != 'X' && this.board[5] == 'O' && this.board[9] == 'O') {
            this.board[1] = 'X';
        }
        else if (this.board[1] == 'O' && this.board[5] != 'O' && this.board[5] != 'X' && this.board[9] == 'O') {
            this.board[5] = 'X';
        }
        else if (this.board[1] == 'O' && this.board[5] == 'O' && this.board[9] != 'O' && this.board[9] != 'X') {
            this.board[9] = 'X';
        }
        else if (this.board[3] != 'O' && this.board[3] != 'X' && this.board[5] == 'O' && this.board[7] == 'O') {
            this.board[3] = 'X';
        }
        else if (this.board[3] == 'O' && this.board[5] != 'O' && this.board[5] != 'X' && this.board[7] == 'O') {
            this.board[5] = 'X';
        }
        else if (this.board[3] == 'O' && this.board[5] == 'O' && this.board[7] != 'O' && this.board[7] != 'X') {
            this.board[7] = 'X';
        }
        else if (this.board[2] != 'O' && this.board[2] != 'X' && this.board[5] == 'O' && this.board[8] == 'O') {
            this.board[2] = 'X';
        }
        else if (this.board[2] == 'O' && this.board[5] != 'O' && this.board[5] != 'X' && this.board[8] == 'O') {
            this.board[5] = 'X';
        }
        else if (this.board[2] == 'O' && this.board[5] == 'O' && this.board[8] != 'O' && this.board[8] != 'X') {
            this.board[8] = 'X';
        }
        else if (this.board[4] != 'O' && this.board[4] != 'X' && this.board[5] == 'O' && this.board[6] == 'O') {
            this.board[4] = 'X';
        }
        else if (this.board[4] == 'O' && this.board[5] != 'O' && this.board[5] != 'X' && this.board[6] == 'O') {
            this.board[5] = 'X';
        }
        else if (this.board[4] == 'O' && this.board[5] == 'O' && this.board[6] != 'O' && this.board[6] != 'X') {
            this.board[6] = 'X';
        }
        else if (this.board[1] == 'O' && this.board[5] == 'O' && this.board[7] != 'X' && this.board[7] != 'O') {
            this.board[7] = 'X';
        }
        else if (this.board[1] == 'O' && this.board[5] == 'X' && this.board[9] == 'O' && this.board[8] != 'X' && this.board[8] != 'O') {
            this.board[8] = 'X';
        }
        else if (this.board[3] == 'O' && this.board[5] == 'X' && this.board[7] == 'O' && this.board[4] != 'X' && this.board[4] != 'O') {
            this.board[4] = 'X';
        }
        else if (this.board[1] == 'O' && (this.board[8] == 'O' || this.board[6] == 'O') && this.board[9] != 'X' && this.board[9] != 'O') {
            this.board[9] = 'X';
        }
        else if (this.board[7] == 'O' && (this.board[2] == 'O' || this.board[6] == 'O') && this.board[3] != 'X' && this.board[3] != 'O') {
            this.board[3] = 'X';
        }
        else if (this.board[9] == 'O' && (this.board[2] == 'O' || this.board[4] == 'O') && this.board[1] != 'X' && this.board[1] != 'O') {
            this.board[1] = 'X';
        }
        else if (this.board[3] == 'O' && (this.board[8] == 'O' || this.board[4] == 'O') && this.board[7] != 'X' && this.board[7] != 'O') {
            this.board[7] = 'X';
        }
        else {
            final Random rand = new Random();
            int n;
            do {
                n = 1 + rand.nextInt(8);
            } while (this.board[n] == 'X' || this.board[n] == 'O');
            this.board[n] = 'X';
        }
    }
}