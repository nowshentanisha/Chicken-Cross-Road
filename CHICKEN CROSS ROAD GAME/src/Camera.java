public class Camera {

    int x;
    int y;

    public Camera() {

        x = 0;
        y = 0;
    }

    public void update(Player player) {

        x = player.x - 400;
        y = player.y - 300;
    }
}