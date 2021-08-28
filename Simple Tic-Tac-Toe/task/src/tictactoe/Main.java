package tictactoe;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
          make global scanner because of the 'Exception in thread “main” java.util.NoSuchElementException'
           You close the second Scanner which closes the underlying InputStream,
           therefore the first Scanner can no longer read from the same InputStream and a NoSuchElementException results.
           The solution: For console apps, use a single Scanner to read from System.in.
         */
        Scanner scanner = new Scanner(System.in);
        String input = "_________";
        /*
        One of the biggest confusions in Java is whether it is pass by value or pass by reference.
        Java is always a pass by value; but, there are a few ways to achieve pass by reference:
        -Making a public member variable in a class
        -Return a value and update it
        -Create a single element array
        here is why we use single element array instead of just a boolean or an integer number to
        track the turn of each players
         */
        int[] turn = {0};
        boolean askAgain = true;
        printEmptyGrid();
        while (askAgain) {
            input = checkCoordinates(input, scanner, turn);
            try {
                printGrid(splitInput(input));
                analyzeTheGameState(input);
            } catch (IOException e) {
                askAgain = false;
            } catch (IllegalStateException ise) {
                askAgain = false;
            }
        }

    }

    public static void updateTurn(int[] turn) {
        if (turn[0] == 0) {
            turn[0] = 1;
        } else if (turn[0] == 1) {
            turn[0] = 2;
        } else if (turn[0] == 2) {
            turn[0] = 1;
        }
    }

    /**
     * In this method fist we ask the user to enter the coordinates of where he want to put his x or or o
     * after that first we check if it is not less than 1 or greater than 3
     *
     * @param input
     * @param scanner
     * @param turn
     * @return
     */
    public static String checkCoordinates(String input, Scanner scanner, int[] turn) {

        boolean askAgain = true;
        String newInput = "";
        while (askAgain) {
            try {
                System.out.print("Enter Coordinates: ");
                int row = scanner.nextInt();
                int column = scanner.nextInt();

                if (row < 1 || row > 3 || column < 1 || column > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    newInput = input;
                    break;
                }

                String key = isOccupied(input, row, column);

                newInput = buildNewInput(input, key, turn).toString();

                askAgain = newInput.equals(input);

            } catch (InputMismatchException ime) {
                scanner.nextLine();
                System.out.println("You should enter numbers!");
            }
        }

        return newInput;

    }

    public static StringBuilder buildNewInput(String input, String key, int[] turn) {
        StringBuilder newInput = new StringBuilder(input);
        if (key != null) {
            updateTurn(turn);
            if (turn[0] == 1) {
                switch (key) {
                    case "(1, 1)":
                        newInput.setCharAt(0, 'X');
                        break;
                    case "(1, 2)":
                        newInput.setCharAt(1, 'X');
                        break;
                    case "(1, 3)":
                        newInput.setCharAt(2, 'X');
                        break;
                    case "(2, 1)":
                        newInput.setCharAt(3, 'X');
                        break;
                    case "(2, 2)":
                        newInput.setCharAt(4, 'X');
                        break;
                    case "(2, 3)":
                        newInput.setCharAt(5, 'X');

                        break;
                    case "(3, 1)":
                        newInput.setCharAt(6, 'X');

                        break;
                    case "(3, 2)":
                        newInput.setCharAt(7, 'X');

                        break;
                    case "(3, 3)":
                        newInput.setCharAt(8, 'X');
                        break;
                }
            } else if (turn[0] == 2) {
                switch (key) {
                    case "(1, 1)":
                        newInput.setCharAt(0, 'O');
                        break;
                    case "(1, 2)":
                        newInput.setCharAt(1, 'O');
                        break;
                    case "(1, 3)":
                        newInput.setCharAt(2, 'O');
                        break;
                    case "(2, 1)":
                        newInput.setCharAt(3, 'O');
                        break;
                    case "(2, 2)":
                        newInput.setCharAt(4, 'O');
                        break;
                    case "(2, 3)":
                        newInput.setCharAt(5, 'O');
                        break;
                    case "(3, 1)":
                        newInput.setCharAt(6, 'O');
                        break;
                    case "(3, 2)":
                        newInput.setCharAt(7, 'O');
                        break;
                    case "(3, 3)":
                        newInput.setCharAt(8, 'O');
                        break;
                }
            }
        }
        return newInput;
    }

    private static String isOccupied(String input, int row, int column) {
        int[] emptyCells = new int[input.length()];
        if (input.contains("_")) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '_') {
                    emptyCells[i] = 0;
                } else {
                    emptyCells[i] = 1;
                }
            }
        }
        Map<String, Integer> emptyCellsWithCoordinate = new HashMap<>();
        for (int i = 0; i < emptyCells.length; i++) {
            if (emptyCells[i] == 0) {
                switch (i) {
                    case 0:
                        emptyCellsWithCoordinate.put("(1, 1)", 0);
                        break;
                    case 1:
                        emptyCellsWithCoordinate.put("(1, 2)", 0);
                        break;
                    case 2:
                        emptyCellsWithCoordinate.put("(1, 3)", 0);
                        break;
                    case 3:
                        emptyCellsWithCoordinate.put("(2, 1)", 0);
                        break;
                    case 4:
                        emptyCellsWithCoordinate.put("(2, 2)", 0);
                        break;
                    case 5:
                        emptyCellsWithCoordinate.put("(2, 3)", 0);
                        break;
                    case 6:
                        emptyCellsWithCoordinate.put("(3, 1)", 0);
                        break;
                    case 7:
                        emptyCellsWithCoordinate.put("(3, 2)", 0);
                        break;
                    case 8:
                        emptyCellsWithCoordinate.put("(3, 3)", 0);
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        emptyCellsWithCoordinate.put("(1, 1)", 1);
                        break;
                    case 1:
                        emptyCellsWithCoordinate.put("(1, 2)", 1);
                        break;
                    case 2:
                        emptyCellsWithCoordinate.put("(1, 3)", 1);
                        break;
                    case 3:
                        emptyCellsWithCoordinate.put("(2, 1)", 1);
                        break;
                    case 4:
                        emptyCellsWithCoordinate.put("(2, 2)", 1);
                        break;
                    case 5:
                        emptyCellsWithCoordinate.put("(2, 3)", 1);
                        break;
                    case 6:
                        emptyCellsWithCoordinate.put("(3, 1)", 1);
                        break;
                    case 7:
                        emptyCellsWithCoordinate.put("(3, 2)", 1);
                        break;
                    case 8:
                        emptyCellsWithCoordinate.put("(3, 3)", 1);
                        break;
                }
            }
        }
        String key = "(" + row + ", " + column + ")";
        int value = 0;
        if (emptyCellsWithCoordinate.containsKey(key)) {
            value = emptyCellsWithCoordinate.get(key);
        }
        if (value == 1) {
            System.out.println("This cell is occupied! Choose another one!");
            return null;
        } else {
            return key;
        }
    }

    public static String getInput(Scanner scanner) {
        System.out.println("Enter cells: ");
        String input = scanner.nextLine();
        return input.trim();
    }

    public static String[] splitInput(String input) {
        String row1 = input.substring(0, 3);
        String row2 = input.substring(3, 6);
        String row3 = input.substring(6);
        return new String[]{row1, row2, row3};
    }

    public static void printGrid(String[] rows) {
        System.out.println("---------");
        System.out.println("| " + rows[0].charAt(0) + " " + rows[0].charAt(1) + " " + rows[0].charAt(2) + " |");
        System.out.println("| " + rows[1].charAt(0) + " " + rows[1].charAt(1) + " " + rows[1].charAt(2) + " |");
        System.out.println("| " + rows[2].charAt(0) + " " + rows[2].charAt(1) + " " + rows[2].charAt(2) + " |");
        System.out.println("---------");
    }

    public static void printEmptyGrid() {
        System.out.println("---------");
        System.out.println("| " + " " + " " + " " + " " + " " + " |");
        System.out.println("| " + " " + " " + " " + " " + " " + " |");
        System.out.println("| " + " " + " " + " " + " " + " " + " |");
        System.out.println("---------");
    }

    public static void analyzeTheGameState(String input) throws IOException, IllegalStateException {
        String[] array = extractArray(input);
        if ((xHasThreeInARow(array) && oHasThreeInARow(array)) || hasMoreXThanO_OrViceVersa(array)) {
            System.out.println("Impossible");
        } else if (!xHasThreeInARow(array) && !oHasThreeInARow(array)) {
            if (hasEmptyCells(array)) {
                System.out.println("Game not finished");
            } else {
                System.out.println("Draw");
                throw new IllegalStateException();
            }
        } else if (xHasThreeInARow(array)) {
            System.out.println("X wins");
            throw new IOException();
        } else if (oHasThreeInARow(array)) {
            System.out.println("O wins");
            throw new IOException();
        }
    }

    private static String[] extractArray(String input) {
        String[] array = new String[input.length()];
        for (int i = 0; i < input.length(); i++) {
            array[i] = Character.toString(input.charAt(i));
            array[i].trim();
        }
        return array;
    }

    public static boolean xHasThreeInARow(String[] array) {
        if (array[0].equalsIgnoreCase("x")
                && array[1].equalsIgnoreCase("x")
                && array[2].equalsIgnoreCase("x")) {
            return true;
        } else if (array[3].equalsIgnoreCase("x")
                && array[4].equalsIgnoreCase("x")
                && array[5].equalsIgnoreCase("x")) {
            return true;
        } else if (array[6].equalsIgnoreCase("x")
                && array[7].equalsIgnoreCase("x")
                && array[8].equalsIgnoreCase("x")) {
            return true;
        } else if (array[0].equalsIgnoreCase("x")
                && array[4].equalsIgnoreCase("x")
                && array[8].equalsIgnoreCase("x")) {
            return true;
        } else if (array[2].equalsIgnoreCase("x")
                && array[4].equalsIgnoreCase("x")
                && array[6].equalsIgnoreCase("x")) {
            return true;
        } else if (array[2].equalsIgnoreCase("x")
                && array[5].equalsIgnoreCase("x")
                && array[8].equalsIgnoreCase("x")) {
            return true;
        } else if (array[0].equalsIgnoreCase("x")
                && array[3].equalsIgnoreCase("x")
                && array[6].equalsIgnoreCase("x")) {
            return true;
        } else return array[1].equalsIgnoreCase("x")
                && array[4].equalsIgnoreCase("x")
                && array[7].equalsIgnoreCase("x");
    }

    public static boolean oHasThreeInARow(String[] array) {
        if (array[0].equalsIgnoreCase("o")
                && array[1].equalsIgnoreCase("o")
                && array[2].equalsIgnoreCase("o")) {
            return true;
        } else if (array[3].equalsIgnoreCase("o")
                && array[4].equalsIgnoreCase("o")
                && array[5].equalsIgnoreCase("o")) {
            return true;
        } else if (array[6].equalsIgnoreCase("o")
                && array[7].equalsIgnoreCase("o")
                && array[8].equalsIgnoreCase("o")) {
            return true;
        } else if (array[0].equalsIgnoreCase("o")
                && array[4].equalsIgnoreCase("o")
                && array[8].equalsIgnoreCase("o")) {
            return true;
        } else if (array[2].equalsIgnoreCase("o")
                && array[4].equalsIgnoreCase("o")
                && array[6].equalsIgnoreCase("o")) {
            return true;
        } else if (array[2].equalsIgnoreCase("o")
                && array[5].equalsIgnoreCase("o")
                && array[8].equalsIgnoreCase("o")) {
            return true;
        } else if (array[0].equalsIgnoreCase("o")
                && array[3].equalsIgnoreCase("o")
                && array[6].equalsIgnoreCase("o")) {
            return true;
        } else return array[1].equalsIgnoreCase("o")
                && array[4].equalsIgnoreCase("o")
                && array[7].equalsIgnoreCase("o");
    }

    public static boolean hasEmptyCells(String[] array) {
        boolean result = false;
        for (String arr : array) {
            if (arr.equalsIgnoreCase("_")) {
                result = true;
            }
        }
        return result;
    }

    public static boolean hasMoreXThanO_OrViceVersa(String[] array) {
        int countX = 0;
        int countO = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase("x")) {
                countX++;
            } else if (array[i].equalsIgnoreCase("o")) {
                countO++;
            }
        }
        if (countX - countO > 1) {
            return true;
        } else return countO - countX > 1;
    }


}
