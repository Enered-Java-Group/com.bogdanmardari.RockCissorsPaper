import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] computerString = {"rock", "scissors", "paper"};
        String personAnswer = " ";
        String playersName;
        System.out.println(" Please enter your name :");
        playersName = scanner.nextLine();
        System.out.println(" Hello "+playersName+" Hope you will have a nice game! See our best players: ");
        DisplayTopScorer(loadRecordGame());
        Gameresult player1 = new Gameresult(playersName);
        Gameresult computer = new Gameresult("Computer");
        Gameresult[] game = new Gameresult[0];
        for (int i = 0; i < 5; i++) {
            boolean flag = false;
            do {
                System.out.println(" Please choose one of 3 words:\n " +
                        "rock or scissors or paper");
                personAnswer = scanner.nextLine();
                if (personAnswer.toUpperCase().equals("ROCK") || personAnswer.toUpperCase().equals("SCISSORS") || personAnswer.toUpperCase().equals("PAPER")) {
                    flag = true;
                }

            } while (!flag);
            System.out.println("game " + (i + 1));

            int temp = generateRandom();
            game = calculateResult(personAnswer, computerString[temp], player1, computer);
            System.out.println(" Player defeats:" + game[0].getDefeat() + " - draw:" + game[0].getDraws() + " - wins: " + game[0].getWins());
            System.out.println(" Computer defeats:" + game[1].getDefeat() + " - draw:" + game[1].getDraws() + " - wins: " + game[1].getWins());

        }

        System.out.println(playersName);
        System.out.println(displayResult(game));
        recordGameScore(player1);
        DisplayTopScorer(loadRecordGame());

    }

    public static void recordGameScore(Gameresult playerG) {
        LinkedList<Gameresult> recordGame = loadRecordGame();
        recordGame.add(playerG);
        Collections.sort(recordGame, new PlayerComparator());

        Path pathFile = Paths.get("Scoreboard.txt");
        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(pathFile)))) {
            for (Gameresult gamer : recordGame) {
                locFile.writeObject(gamer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static LinkedList<Gameresult> loadRecordGame() {
        Path pathFile = Paths.get("Scoreboard.txt");

        LinkedList<Gameresult> gamesRecords = new LinkedList<>();
        try (ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(pathFile)))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Gameresult gameresult = (Gameresult) locFile.readObject();
                    gamesRecords.add(gameresult);
                } catch (EOFException e) {
                    eof=true;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gamesRecords;
    }

    public static String displayResult(Gameresult[] gameresult) {
        if (gameresult[0].getWins() > gameresult[1].getWins()) {
            String string = "Players " + gameresult[0].getName() + " wins! Congratulation!! ";
            return string;
        } else if (gameresult[0].getWins() < gameresult[1].getWins()) {
            String string2 = gameresult[1].getName() + " wins! Better luck next time!!! ";
            return string2;
        } else {
            return " It's a TIE !!";
        }
    }

    public static int generateRandom() {
        return (int) (Math.random() * 3);
    }

    public static Gameresult[] calculateResult(String playersAnswer, String computerAnswer, Gameresult player, Gameresult computer) {
        Gameresult[] listGame = {player, computer};
        if (playersAnswer.equals("rock")) {
            if (computerAnswer.equals("rock")) {
                listGame[0].addDraw(1);
                listGame[1].addDraw(1);
            } else if (computerAnswer.equals("paper")) {
                listGame[0].addDefeat(1);
                listGame[1].addVictory(1);
            } else {
                listGame[0].addVictory(1);
                listGame[1].addDefeat(1);
            }

        } else if (playersAnswer.equals("paper")) {
            if (computerAnswer.equals("rock")) {
                listGame[0].addVictory(1);
                listGame[1].addDefeat(1);
            } else if (computerAnswer.equals("paper")) {
                listGame[0].addDraw(1);
                listGame[1].addDraw(1);
            } else {
                listGame[0].addDefeat(1);
                listGame[1].addVictory(1);
            }

        } else {
            if (computerAnswer.equals("scissors")) {
                listGame[0].addDraw(1);
                listGame[1].addDraw(1);
            } else if (computerAnswer.equals("rock")) {
                listGame[0].addDefeat(1);
                listGame[1].addVictory(1);
            } else {
                listGame[0].addVictory(1);
                listGame[1].addDefeat(1);
            }
        }

        return listGame;
    }

    public static void DisplayTopScorer(LinkedList<Gameresult> results){
        System.out.println("====== THIS IS THE TOP SCORE =======");
        for(Gameresult games:results){
            System.out.println(games.getName()+" with: "+games.getWins()+" wins and "+games.getDraws()+" draws" );
        }
    }
}
