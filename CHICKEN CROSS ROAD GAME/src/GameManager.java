public class GameManager {

    boolean gameOver = false;

    public void playerDied() {
        gameOver = true;
        System.out.println("Game Over!");
    }

    public void restartGame() {
        gameOver = false;
        System.out.println("Game Restarted!");
    }
}