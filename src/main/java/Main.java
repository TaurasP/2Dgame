import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Game game = new Game();
        Player player = new Player();
        game.start(player, game);
    }
}