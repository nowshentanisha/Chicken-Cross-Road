import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    final int WIDTH = 800;
    final int HEIGHT = 600;

    Player player = new Player();
    TileManager tileM = new TileManager();

    Collision collision = new Collision();
    GameManager gamemanager = new GameManager();

    Score score = new Score();
    LevelGenerator levelGenerator = new LevelGenerator();

    KeyHandler keyH = new KeyHandler();

    ArrayList<Car> cars = new ArrayList<>();
    ArrayList<Log> logs = new ArrayList<>();

    Thread gameThread;
    boolean gameRunning = true;


    // =========================
    // CONSTRUCTOR
    // =========================

    public GamePanel() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setBackground(Color.BLACK);

        setFocusable(true);

        addKeyListener(keyH);

        createObjects();
    }


    // =========================
    // CREATE OBJECTS
    // =========================

    private void createObjects() {

        cars.clear();
        logs.clear();

        for (int row = 0; row < 10; row++) {

            int type = tileM.getTileType(row);

            int y = row * 50 + 10;


            // ROAD
            if (type == 1) {

                int x = 100 + (row * 180) % 550;

                Color color;

                if (row % 3 == 0) {
                    color = Color.BLUE;
                }
                else if (row % 3 == 1) {
                    color = Color.RED;
                }
                else {
                    color = Color.GREEN;
                }

                cars.add(
                    new Car(
                        x,
                        y,
                        2 + (row % 3),
                        color
                    )
                );
            }


            // RIVER
            else if (type == 2) {

                int x = 100 + (row * 200) % 500;

                logs.add(
                    new Log(
                        x,
                        y,
                        -2,
                        new Color(139, 69, 19)
                    )
                );
            }
        }
    }


    // =========================
    // START
    // =========================

    public void startGameThread() {

        gameThread = new Thread(this);

        gameThread.start();
    }


    // =========================
    // GAME LOOP
    // =========================

    @Override
    public void run() {

        while (gameRunning) {

            update();

            repaint();

            try {

                Thread.sleep(16);

            }
            catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }


    // =========================
    // UPDATE
    // =========================

    public void update() {

        // =========================
        // PLAYER MOVEMENT
        // =========================

        if (keyH.up) {

            player.y -= 50;

            score.increaseScore();

            keyH.up = false;
        }

        if (keyH.down) {

            player.y += 50;

            keyH.down = false;
        }

        if (keyH.left) {

            player.x -= 50;

            keyH.left = false;
        }

        if (keyH.right) {

            player.x += 50;

            keyH.right = false;
        }


        // =========================
        // X BOUNDARY
        // =========================

        if (player.x < 0) {
            player.x = 0;
        }

        if (player.x > WIDTH - 40) {
            player.x = WIDTH - 40;
        }


        // =========================
        // SCROLL MAP
        // =========================

        if (player.y < 250) {

            int section =
                levelGenerator.generateSection();

            tileM.generateSection(section);

            // Move all objects down
            for (Car car : cars) {
                car.y += 50;
            }

            for (Log log : logs) {
                log.y += 50;
            }

            // Move player back to center
            player.y = 300;

            // Create ONLY the new top row
            createTopObjects(section);
        }


        // =========================
        // UPDATE CARS
        // =========================

        for (Car car : cars) {

            car.update();
        }


        // =========================
        // UPDATE LOGS
        // =========================

        for (Log log : logs) {

            log.update();
        }


        // =========================
        // REMOVE OLD OBJECTS
        // =========================

        cars.removeIf(
            car -> car.y > HEIGHT
        );

        logs.removeIf(
            log -> log.y > HEIGHT
        );


        // =========================
        // CAR COLLISION
        // =========================

        for (Car car : cars) {

            if (
                player.getBounds()
                .intersects(car.getBounds())
            ) {

                gamemanager.playerDied();

                player.x = 380;
                player.y = 300;

                score.resetScore();

                return;
            }
        }


        // =========================
        // RIVER
        // =========================

        if (tileM.isRiverAt(player.y)) {

            boolean onLog = false;

            for (Log log : logs) {

                if (
                    player.getBounds()
                    .intersects(log.getBounds())
                ) {

                    onLog = true;

                    player.x += log.speed;

                    break;
                }
            }

            if (!onLog) {

                gamemanager.playerDied();

                player.x = 380;
                player.y = 300;

                score.resetScore();

                return;
            }
        }


        // =========================
        // FINAL X BOUNDARY
        // =========================

        if (player.x < 0) {
            player.x = 0;
        }

        if (player.x > WIDTH - 40) {
            player.x = WIDTH - 40;
        }
    }


    // =========================
    // CREATE NEW TOP OBJECT
    // =========================

    private void createTopObjects(int section) {

        if (section == 0) {

            // ROAD

            cars.add(
                new Car(
                    100,
                    10,
                    2,
                    Color.BLUE
                )
            );

        }
        else {

            // RIVER

            logs.add(
                new Log(
                    300,
                    10,
                    -2,
                    new Color(
                        139,
                        69,
                        19
                    )
                )
            );
        }
    }


    // =========================
    // DRAW
    // =========================

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 =
            (Graphics2D) g;


        // MAP
        tileM.draw(g2);


        // CARS
        for (Car car : cars) {

            g2.setColor(car.color);

            g2.fillRect(
                car.x,
                car.y,
                car.width,
                car.height
            );
        }


        // LOGS
        for (Log log : logs) {

            g2.setColor(log.color);

            g2.fillRect(
                log.x,
                log.y,
                log.width,
                log.height
            );
        }


        // PLAYER
        g2.setColor(Color.WHITE);

        g2.fillRect(
            player.x,
            player.y,
            40,
            40
        );


        // SCORE
        g2.setColor(Color.WHITE);

        g2.setFont(
            new java.awt.Font(
                "Arial",
                java.awt.Font.BOLD,
                24
            )
        );

        g2.drawString(
            "Score: " + score.getScore(),
            20,
            35
        );

        g2.dispose();
    }
}