package game_model;

import game_model.objects.Player;
import game_model.objects.Ship;
import game_model.objects.bullets.Bullet;
import game_model.objects.enemies.EnemyDefault;

import java.util.ArrayList;
import java.util.List;

public class Model implements AutoCloseable {
    private int score = 0;
    public static final int fieldWidth = 1000;
    public static final int fieldHeight = 600;

    private final Player player;
    private List<Ship> ships;
    private List<Bullet> bullets;
    private GameState gameState = GameState.RUN;

    private final Thread tick;
    private ModelUpload upload;

    public Model() {
        player = new Player(fieldWidth / 2 - Player.playerWidth / 2,
                fieldHeight - Player.playerHeight);
        ships = new ArrayList<>();
        bullets = new ArrayList<>();
        initialize();

        tick = new Ticker(this);

    }

    public void initialize() {
        int offset = 25;
        int gap = 15;
        int enemyWidth = EnemyDefault.enemyDefWidth + gap;

        for (int i = offset; i < (fieldWidth - offset); i += enemyWidth) {
            ships.add(new EnemyDefault(i, 10));
        }
        ships.add(player);
    }

    public void start() {
        tick.start();
    }

    public void update() throws InterruptedException {
        if (gameState == GameState.DEAD) {
            notifyUpload();
            close();
            return;
        }
        checkCollisions();

        if (moveShip(player)) player.move();
        int speedModifier = moveEnemies() ? 1 : -1;
        for (var ship : ships) {
            if (ship == player) continue;
            ship.setSpeedX(ship.getSpeedX() * speedModifier);
            ship.move();

            Bullet bullet = ship.shoot();
            if (bullet != null) {
                bullets.add(bullet);
            }
        }

        if (ships.size() == 1) {
            initialize();
            player.increaseHealth();
        }
        notifyUpload();
    }

    private void checkCollisions() {
        List<Bullet> bulletsCopy = new ArrayList<>(bullets);
        List<Ship> shipsCopy = new ArrayList<>(ships);

        for (var bullet : bullets) {
            for (var ship : ships) {
                if (!bulletsCopy.contains(bullet) || !ships.contains(ship) ||
                        !bullet.collide(ship)) continue;
                ship.decreaseHealth();

                bulletsCopy.remove(bullet);
                if (ship.getHealth() == 0) {
                    if (ship == player) {
                        gameState = GameState.DEAD;
                        return;
                    }
                    shipsCopy.remove(ship);
                    score += 10;
                }
            }
            moveBullet(bullet, bulletsCopy);
        }
        ships = shipsCopy;
        bullets = bulletsCopy;
    }

    private boolean moveShip(Ship ship) {
        int newX = ship.getX() + ship.getSpeedX();
        int newY = ship.getY() + ship.getSpeedY();
        return 0 <= newX && newX + ship.getWidth() <= fieldWidth
                && 0 <= newY && newY + ship.getHeight() <= fieldHeight;
    }

    private boolean moveEnemies() {
        boolean allCanMove = true;
        for (Ship ship : ships) {
            if (ship == player) continue;
            allCanMove = allCanMove && moveShip(ship);
        }
        return allCanMove;
    }

    private void moveBullet(Bullet bullet, List<Bullet> bulletsList) {
        bullet.move();
        int bottomY = bullet.getY() + bullet.getHeight();
        if (bottomY < 0 || bullet.getY() >= fieldHeight)
            bulletsList.remove(bullet);
    }

    public Player getPlayer() {
        return player;
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<Ship> getShip() {
        return ships;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public int getScore() {
        return score;
    }

    public void playerShoot() {
        bullets.add(player.shoot());
        notifyUpload();
    }

    private void notifyUpload() {
        if (upload != null)
            upload.modelChange();
    }

    public void setUpload(ModelUpload upload) {
        this.upload = upload;
    }

    @Override
    public void close() throws InterruptedException {
        tick.interrupt();
        tick.join();
    }
}