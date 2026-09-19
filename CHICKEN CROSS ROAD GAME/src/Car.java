import java.awt.Rectangle;
import java.awt.Color;

public class Car {

    int x;
    int y;

    int width = 60;
    int height = 30;

    int speed;
    Color color;

    public Car(int x, int y, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void update() {

    x += speed;

    if (x > 800) {
        x = -width;
    }
  }
  public Rectangle getBounds() {
    return new Rectangle(x, y, width, height);
}
}