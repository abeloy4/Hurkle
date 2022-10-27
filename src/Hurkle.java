import java.util.Scanner;
import java.util.Arrays;
public class Hurkle {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int idx = (int)((Math.random() * 99) + 1);
        int idx2 = (int)((Math.random() * 99) + 1);
        int idx3 = (int)((Math.random() * 99) + 1);
        int idx4 = (int)((Math.random() * 99) + 1);

        int hurkle = (int)((Math.random() * 99) + 1);

        if (idx == idx2 || idx == idx3 || idx == idx4 || idx == hurkle){
            idx = (int )((Math.random() * 98 ) + 1);
        }if (idx2 == idx3 || idx2 == idx4 || idx2 == hurkle){
            idx2 = (int)((Math.random() * 98) +1);
        }if(idx3 == idx4 || idx3 == hurkle){
            idx3 = (int)((Math.random() * 98) +1);
        }if (idx4 == hurkle){
            idx4 = (int)((Math.random() * 98) + 1);
        }

        int score = 1;
        int playerPosition = 0;
        String hint = "";
        boolean checkHole = false;
        boolean checkHurkle = false;
        int amount;
        int[] pos = new int[2];
        char[] board = new char[100];
        createBoard(board);

        char[] updatedBoard = boardHoles(board,idx, idx2, idx3, idx4);
        displayBoard(updatedBoard, playerPosition);

        
        System.out.print(">");
        char direction = input.next().toLowerCase().charAt(0);


    while(checkHurkle == false){
        if (direction == 'q'){
            System.out.println("You lose!");
            System.exit(0);
        }else {
            amount = input.nextInt();
            for (int i = 0; i < amount; i++){
                playerPosition = movePlayer(playerPosition, direction);
                checkHole = checkForHole(playerPosition, idx, idx2, idx3, idx4);
                if (checkHole == true){
                    playerPosition = 0;
                    break;
                }
                checkHurkle = checkForHurkle(playerPosition, hurkle);
                if (checkHurkle == true){
                    pos = convert1dTo2d(playerPosition);
                    System.out.println("Hurkle found at position:  " + pos[0] + " , " + pos[1] + ".");
                    System.out.println("Score: " + score);
                    System.out.println("You win!");
                    System.exit(0);
                }
            }

            hint = getHint(playerPosition, hurkle);
            if (checkHole == true){
                System.out.println("You fell in a hole!");
                System.out.println("The hurkle is: " + hint);
                System.out.println("Score: " + score);
                System.out.println();
                displayBoard(updatedBoard, playerPosition);

            }else{
                System.out.println("The hurkle is: " + hint);
                System.out.println("Score: " + score);
                System.out.println();
                displayBoard(updatedBoard, playerPosition);

            }

            System.out.print(">");
            direction = input.next().toLowerCase().charAt(0);
            score++;

        }
    }
    }
    public static void createBoard(char[] userBoard){
        char c = '.';
        int i = 0;
        while(i < userBoard.length){
            for (int j = 0; j < 10; j++){
                userBoard[i] = c;
                i++;
            }
        }
    }
    public static char[] boardHoles(char[] userBoard, int idx1, int idx2, int idx3,  int idx4){

        userBoard[idx1] = '0';
        userBoard[idx2] = '0';
        userBoard[idx3] = '0';
        userBoard[idx4] = '0';

        return userBoard;
    }
    public static void displayBoard(char[] userBoard, int userPosition){
        int i = 0;
        while (i < userBoard.length){
            for (int j = 0; j < 10; j++){
                if(i == userPosition){
                    System.out.print("@");
                }else {
                    System.out.print(userBoard[i]);
                }
                i++;
            }
            System.out.println();
        }
    }
    public static int movePlayer(int userPosition, char drx){
        int[] coords = convert1dTo2d(userPosition);

        if(drx == 'e'){
            coords[0] += 1;
            if(coords[0] > 9){
                coords[0] = 9;
            }
        } else if(drx == 'w'){
            coords[0] -= 1;
            if(coords[0] < 0){
                coords[0] = 0;
            }
        } else if(drx == 's'){
            coords[1] += 1;
            if(coords[1] > 9){
                coords[1] = 9;
            }
        } else if(drx == 'n'){
            coords[1] -= 1;
            if(coords[1] < 0){
                coords[1] = 0;
            }
        }
        userPosition = convert2dTo1d(coords);

        return userPosition;
    }
    public static int[] convert1dTo2d(int userPosition ){
        int[] position = new int[2];
        position[0] = userPosition % 10;
        position[1] = userPosition / 10;

        return position;
    }
    public static int convert2dTo1d(int[] boardCoords){
        int index  = (boardCoords[1] * 10) + boardCoords[0];

        return index;
    }
    public static boolean checkForHole(int userPosition, int idx1, int idx2, int idx3, int idx4){
        if (userPosition == idx1 || userPosition == idx2 || userPosition == idx3 || userPosition == idx4){
            return true;
        }

        return false;
    }
    public static boolean checkForHurkle(int userPosition, int hurklePosition){
        if (userPosition == hurklePosition){
            return true;
        }

        return false;
    }
    public static String getHint(int userPosition, int hurklePosition){
        String hint = "";
        int[] choordPos = convert1dTo2d(userPosition);
        int[] choordHurkle = convert1dTo2d(hurklePosition);

        if (choordHurkle[0] < choordPos[0] && choordHurkle[1] < choordPos[1]){
            hint = "NW";
        }else if (choordHurkle[0] > choordPos[0] && choordHurkle[1] < choordPos[1]){
            hint = "NE";
        }else if (choordHurkle[0] < choordPos[0] && choordHurkle[1] > choordPos[1]){
            hint = "SW";
        }else if (choordHurkle[0] > choordPos[0] && choordHurkle[1] > choordPos[1]){
            hint = "SE";
        }else if(choordHurkle[0] < choordPos[0] && choordHurkle[1] == choordPos[1]){
            hint = "W";
        }else if (choordHurkle[0] > choordPos[0] && choordHurkle[1] == choordPos[1]){
            hint = "E";
        }else if (choordHurkle[0] == choordPos[0] && choordHurkle[1] < choordPos[1]){
            hint = "N";
        }else {
            hint = "S";

        }

        return hint;

    }
}
