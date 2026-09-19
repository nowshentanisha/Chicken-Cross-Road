package tile;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class TileManager {

    int tileSize = 50;

    Random random = new Random();

    int[][] map = {

        // Grass
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},

        // Road
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},

        // Grass
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},

        // Road
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},

        // Grass
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},

        // River
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},

        // Grass
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };


    // =========================
    // DRAW MAP
    // =========================

    public void draw(Graphics g) {

        for (int row = 0; row < map.length; row++) {

            for (int col = 0; col < map[row].length; col++) {

                int x = col * tileSize;
                int y = row * tileSize;


                // =========================
                // GRASS
                // =========================

                if (map[row][col] == 0) {

                    g.setColor(
                        new Color(75, 170, 70)
                    );

                    g.fillRect(
                        x,
                        y,
                        tileSize,
                        tileSize
                    );

                    g.setColor(
                        new Color(45, 125, 45)
                    );

                    g.drawLine(
                        x + 8,
                        y + 42,
                        x + 11,
                        y + 33
                    );

                    g.drawLine(
                        x + 18,
                        y + 35,
                        x + 20,
                        y + 27
                    );

                    g.drawLine(
                        x + 35,
                        y + 43,
                        x + 38,
                        y + 34
                    );

                    g.drawLine(
                        x + 42,
                        y + 25,
                        x + 45,
                        y + 18
                    );
                }


                // =========================
                // ROAD
                // =========================

                else if (map[row][col] == 1) {

                    g.setColor(
                        new Color(55, 55, 55)
                    );

                    g.fillRect(
                        x,
                        y,
                        tileSize,
                        tileSize
                    );

                    g.setColor(
                        new Color(100, 100, 100)
                    );

                    g.fillRect(
                        x,
                        y,
                        tileSize,
                        3
                    );

                    g.fillRect(
                        x,
                        y + tileSize - 3,
                        tileSize,
                        3
                    );

                    g.setColor(Color.WHITE);

                    if (col % 2 == 0) {

                        g.fillRect(
                            x + 5,
                            y + 23,
                            25,
                            4
                        );
                    }
                }


                // =========================
                // RIVER
                // =========================

                else if (map[row][col] == 2) {

                    g.setColor(
                        new Color(35, 145, 215)
                    );

                    g.fillRect(
                        x,
                        y,
                        tileSize,
                        tileSize
                    );

                    g.setColor(
                        new Color(120, 205, 240)
                    );

                    g.drawLine(
                        x + 5,
                        y + 12,
                        x + 22,
                        y + 12
                    );

                    g.drawLine(
                        x + 30,
                        y + 25,
                        x + 47,
                        y + 25
                    );

                    g.drawLine(
                        x + 8,
                        y + 40,
                        x + 25,
                        y + 40
                    );

                    g.setColor(
                        new Color(25, 115, 180)
                    );

                    g.drawLine(
                        x + 25,
                        y + 7,
                        x + 42,
                        y + 7
                    );
                }
            }
        }
    }


    // =========================
    // GENERATE NEW SECTION
    // =========================

    public void generateSection(int section) {

        // Move old rows down

        for (
            int row = map.length - 1;
            row > 0;
            row--
        ) {

            for (
                int col = 0;
                col < map[row].length;
                col++
            ) {

                map[row][col] =
                    map[row - 1][col];
            }
        }


        // section 0 = Road
        // section 1 = River

        int newType;

        if (section == 0) {

            newType = 1;
        }
        else {

            newType = 2;
        }


        // Create new row at top

        for (
            int col = 0;
            col < map[0].length;
            col++
        ) {

            map[0][col] = newType;
        }
    }


    // =========================
    // CHECK RIVER
    // =========================

    public boolean isRiverAt(int y) {

        int startRow = y / tileSize;

        int endRow =
            (y + 39) / tileSize;


        for (
            int row = startRow;
            row <= endRow;
            row++
        ) {

            if (
                row >= 0 &&
                row < map.length
            ) {

                if (map[row][0] == 2) {

                    return true;
                }
            }
        }

        return false;
    }


    // =========================
    // GET TILE TYPE
    // =========================

    public int getTileType(int row) {

        if (
            row < 0 ||
            row >= map.length
        ) {

            return -1;
        }

        return map[row][0];
    }
}