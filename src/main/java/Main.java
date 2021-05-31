import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        boolean exit = false;

        while(!exit) {
            Game game = new Game();
            Player player = new Player();
            if(!game.start(player)) {
                continue;
            }
            exit = true;
        }
    }
}