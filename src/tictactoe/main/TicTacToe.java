/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import tictactoe.data.Player;
import tictactoe.utils.PropertiesService;

/**
 *
 * The TicTacToe game.
 *
 * @author czhiller
 */
public class TicTacToe {

    /**
     * This is where the magic happens, This could be improved by moving most of
     * it to different controllers, but I'm already on a deadline :(.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println();
        System.out.println("Welcome to CZHiller's TicTacToe!");
        System.out.println();
        PropertiesService prop = new PropertiesService();

        /**
         *
         * Getting all the input provided in the properties file.
         *
         */
        int size = Integer.parseInt(prop.getProperty("boardSize"));

        Player firstPlayer = new Player(prop.getProperty("playerOneName"), prop.getProperty("playerOneId").charAt(0), 0);
        char player1 = firstPlayer.getIdentifier();

        Player secondPlayer = new Player(prop.getProperty("playerTwoName"), prop.getProperty("playerTwoId").charAt(0), 1);
        char player2 = firstPlayer.getIdentifier();

        Player computer = new Player(prop.getProperty("computerPlayerName"), prop.getProperty("computerPlayerId").charAt(0), 2);
        char computerPlayer = firstPlayer.getIdentifier();

        /**
         *
         * Creating a list of players in order to be able to switch players
         * during turns.
         *
         */
        List<Player> players = new ArrayList<Player>();

        players.add(firstPlayer);
        players.add(secondPlayer);
        players.add(computer);

        /**
         *
         * Defining msgs like initial input ask and error msgs, These could be
         * extracted to final constants, above the main mehod.
         *
         */
        String msgToAsk = "Enter first player character (only the first char you enter will be used): ";
        String invalidInputErrorMsg = "Invalid input, please read the instructions in the parenthesis. Try again.";
        String alreadyTakenErrorMsg = "That position is already taken! Try again.";

        /**
         *
         * Checking if player1 properties are invalid in the input file, If its
         * identifier is invalid, a new valid input will be asked from the user.
         *
         */
        if (player1 == ' ') {
            System.out.println();
            System.out.println("Invalid char for player1, please change properties before running the game next time.");
            while (true) {
                try {
                    player1 = getLineInput(msgToAsk).charAt(0);
                    if (player1 == ' ') {
                        throw new Exception("Invalid input");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(invalidInputErrorMsg);
                    System.out.println();
                }
            }
        }

        /**
         *
         * Checking if player2 properties are invalid in the input file, If its
         * identifier is invalid, a new valid input will be asked from the user.
         *
         */
        if (player2 == ' ') {
            System.out.println("Invalid char for player2, please change properties before running the game next time.");
            while (true) {
                try {
                    player2 = getLineInput(msgToAsk).charAt(0);
                    if (player2 == ' ') {
                        throw new Exception("Invalid input");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(invalidInputErrorMsg);
                    System.out.println();
                }
            }
        }

        /**
         *
         * Checking if computer properties are invalid in the input file, If its
         * identifier is invalid, a new valid input will be asked from the user.
         *
         */
        if (computerPlayer == ' ') {
            System.out.println("Invalid char for computer, please change properties before running the game next time.");
            while (true) {
                try {
                    computerPlayer = getLineInput(msgToAsk).charAt(0);
                    if (computerPlayer == ' ') {
                        throw new Exception("Invalid input");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(invalidInputErrorMsg);
                    System.out.println();
                }
            }
        }

        /**
         *
         * Checking if the size of the board properties is invalid in the input
         * file, If it is, a new valid input will be asked from the user.
         *
         */
        if (size < 3 || size > 10) {
            msgToAsk = "Enter a size for the board (must be a number between 3 and 10): ";

            System.out.println("Invalid size for board, please change properties before running the game next time.");

            while (true) {
                try {
                    size = getIntInput(msgToAsk);
                    if (size < 3 || size > 10) {
                        throw new Exception("Invalid input");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(invalidInputErrorMsg);
                    System.out.println();
                }
            }
        }

        /**
         *
         * Creating, initializing and printing board with empty spaces.
         *
         */
        char[][] board = makeGrid(size);
        System.out.println();
        System.out.println("Initial board: ");
        drawGrid(board);

        /**
         *
         * Second real msg to ask if everything was OK in the properties file.
         *
         */
        msgToAsk = "please enter the coordinates where you wish to play your turn "
                + "(must be a first number, followed by a second number, "
                + "separated by a comma, e.g. \"1,2\" "
                + "Note that coordinates must fit the size of the board, "
                + "so min is 0, max is " + (size - 1) + "): ";

        /**
         *
         * Initializing game variables and looping for turns.
         *
         */
        int totalTurns = size * size;
        int playCount = 0;
        int playerOrder = 0;

        while (playCount <= totalTurns) {
            String move = "";
            int coordinate1 = 0;
            int coordinate2 = 0;

            /**
             *
             * Deciding which player's turn is, depending on the order
             * attribute.
             *
             */
            Player player = decidePlayer(players, playerOrder);

            /**
             *
             * Asking for the coordinates where each player wants to put its
             * char, Assuming the valid input described in the msgToAsk, looping
             * until a valid input is received and the game can go on.
             *
             */
            while (true) {
                try {
                    move = getLineInput(player.getName() + " (" + player.getIdentifier() + "), " + msgToAsk);
                    String[] realMove = move.split(",");
                    coordinate1 = Integer.parseInt(realMove[0]);
                    coordinate2 = Integer.parseInt(realMove[1]);
                    //Checking coordinates don't go outside the board.
                    if ((coordinate1 < 0 || coordinate1 >= size) || (coordinate2 < 0 || coordinate2 >= size)) {
                        throw new Exception(invalidInputErrorMsg);
                        //Checking if the position isn't already taken.    
                    } else if (checkTakenPosition(board, coordinate1, coordinate2)) {
                        throw new Exception(alreadyTakenErrorMsg);
                    } else {
                        break;
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }

            //playing..
            boolean didWin = play(coordinate1, coordinate2, player, board);

            playCount = playCount + 1;
            playerOrder = playerOrder + 1;

            //results..
            if (didWin) {
                System.out.println();
                System.out.println("Game over --> Congratulations " + player.getName() + " aka " + player.getIdentifier() + ", you won!");
                break;
            } else if (playCount == totalTurns) {
                System.out.println();
                System.out.println("Game over --> No one won :( play again!");
                break;
            }

            //computer move
            if (playerOrder == 2) {
                player = players.get(playerOrder);
                didWin = computerMove(player, board, size);
                playCount = playCount + 1;
                //initializing order again so we can go back to player1
                playerOrder = 0;
                if (didWin) {
                    System.out.println();
                    System.out.println("Ha! The computer won, good luck next time. Play Again!");
                    break;
                } else if (playCount == totalTurns) {
                    System.out.println();
                    System.out.println("Game over --> No one won :( play again!");
                    break;
                }
            }
        }
    }

    /**
     *
     * Creating and initializing the board with empty spaces.
     *
     * @param size
     * @return
     */
    public static char[][] makeGrid(int size) {

        char[][] board = new char[size][size];
        initialize(board);
        return board;
    }

    /**
     *
     * Puts each player's char in the given position, prints the updated board,
     * returns if someone won.
     *
     * @param coordinate1
     * @param coordinate2
     * @param player
     * @param board
     * @return
     */
    public static boolean play(int coordinate1, int coordinate2, Player player, char[][] board) {
        board[coordinate1][coordinate2] = player.getIdentifier();
        drawGrid(board);
        return formedLines(board, player.getIdentifier());
    }

    /**
     *
     * Randomizes the computer's move depending on the position being not being
     * taken, then puts computer's char in the first free position found.
     *
     *
     * @param player
     * @param board
     * @param size
     * @return
     */
    public static boolean computerMove(Player player, char[][] board, int size) {
        Random rand = new Random();

        int coordinate1 = 0;
        int coordinate2 = 0;

        while (checkTakenPosition(board, coordinate1, coordinate2)) {
            coordinate1 = rand.nextInt(size);
            coordinate2 = rand.nextInt(size);
        }

        System.out.println();
        System.out.println("The computer plays: ");

        return play(coordinate1, coordinate2, player, board);
    }

    /**
     * Initializes the board with empty spaces, we use this to assume every free
     * position.
     *
     * @param board
     */
    public static void initialize(char[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }

    }

    /**
     *
     * Draws the board.
     *
     * @param board
     */
    private static void drawGrid(char[][] board) {
        System.out.println(Arrays.stream(board).map(r -> new String(r).replaceAll("", "|"))
                .collect(Collectors.joining("\n")));
    }

    /**
     * Checks if char parameter (player) forms horizontal/vertical/diagonal
     * lines, winning the game.
     *
     * @param board
     * @param player
     * @return
     */
    public static boolean formedLines(char[][] board, char player) {
        boolean lineFormed;

        // Check rows of the board for a line of player's char
        for (int i = 0; i < board.length; i++) {
            lineFormed = true;
            for (int j = 1; j < board[i].length; j++) {
                if (board[i][j - 1] != player || board[i][j] != player) {
                    lineFormed = false;
                }
            }
            if (lineFormed) {
                return true; // Horizontal line formed
            }
        }

        // Check columns
        for (int j = 0; j < board[0].length; j++) {
            lineFormed = true;
            for (int i = 1; i < board.length; i++) {
                if (board[i - 1][j] != player || board[i][j] != player) {
                    lineFormed = false;
                }
            }
            if (lineFormed) {
                return true;
            }
        }

        // Check major diagonal
        lineFormed = true;
        for (int i = 1; i < board.length; i++) {
            if (board[i - 1][i - 1] != player || board[i][i] != player) {
                lineFormed = false;
            }
        }
        if (lineFormed) {
            return true;
        }

        // Check minor diagonal
        lineFormed = true;
        for (int i = 1; i < board.length; i++) {
            if (board[board.length - i][i - 1] != player
                    || board[board.length - i - 1][i] != player) {
                lineFormed = false;
            }
        }

        return lineFormed; //false If none of the lines is formed
    }

    /**
     *
     * Gets int input from the user.
     *
     * @param askMsg
     * @return
     */
    public static int getIntInput(String askMsg) {

        Scanner reader = new Scanner(System.in);
        System.out.println();
        System.out.println(askMsg);
        int myInt = reader.nextInt();
        return myInt;
    }

    /**
     * Gets line input from the user, These last two methods could be unified
     * and do a proper exception handling for input parsing.
     *
     * @param askMsg
     * @return
     */
    public static String getLineInput(String askMsg) {

        Scanner reader = new Scanner(System.in);
        System.out.println();
        System.out.println(askMsg);
        String line = reader.nextLine();
        return line;
    }

    /**
     *
     * Gets the player in given position.
     *
     * @param players
     * @param playerOrder
     * @return
     */
    private static Player decidePlayer(List<Player> players, int playerOrder) {
        return players.get(playerOrder);
    }

    /**
     *
     * Given the coordinates, checks if position is taken in the board.
     *
     * @param board
     * @param coordinate1
     * @param coordinate2
     * @return
     */
    private static boolean checkTakenPosition(char[][] board, int coordinate1, int coordinate2) {
        return (!(board[coordinate1][coordinate2] == ' '));
    }

}
