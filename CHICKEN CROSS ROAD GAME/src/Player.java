import java.awt.Rectangle;

public class Player {

    int x;
    int y;
    
    final int tileSize = 50;

    public Player() {

        x = 380;
        y = 300;
    }
public Rectangle getBounds(){
    return new Rectangle(x,y,40,40);
}
}