import java.awt.*;

public class Log {

    int x, y;
    int width = 100;
    int height = 30;

    int speed;
    Color color;

    public Log(int x, int y, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
    }

    public void update() {

        x += speed;

        // ডান দিকে চলে গেলে আবার বাম দিক থেকে আসবে
        if (speed > 0 && x > 800) {
            x = -width;
        }

        // বাম দিকে চলে গেলে আবার ডান দিক থেকে আসবে
        if (speed < 0 && x + width < 0) {
            x = 800;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}