import java.awt.Rectangle;

public class RiverSystem {

    public boolean isInRiver(Player player) {

        Rectangle riverArea = new Rectangle(
            0,
            350,
            800,
            100
        );

        return player.getBounds().intersects(riverArea);
    }
}