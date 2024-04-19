package game_model;

import game_model.objects.Player;
import game_model.objects.Ship;
import game_model.objects.bullets.Bullet;
import game_model.objects.enemies.EnemyDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model implements AutoCloseable {
    private int score = 0;
    public static final int fieldWidth = 400;
    public static final int fieldHeight = 300;

    private final Player player;
    private final List<Ship> ships;
    private final List<Bullet> bullets;
    private GameState gameState = GameState.RUN;

    private final Random random = new Random();
    private final Thread tick;
    private ModelUpload upload;

    public Model() {
        player = new Player(fieldWidth / 2 - Player.playerWidth / 2,
                fieldHeight - Player.playerHeight);
        ships = new ArrayList<>();
        bullets = new ArrayList<>();
        initialize();

        tick = new Ticker(this);
        tick.start();
    }

    public void initialize() {
        int offset = 25;
        int gap = 5;
        int enemyWidth = EnemyDefault.enemyDefWidth + gap;
        int enemyAmount = (fieldWidth - offset * 2 + gap) / enemyWidth;

        for (int i = 0; i < enemyAmount; i++) {
            ships.add(new EnemyDefault(offset + i * enemyWidth, 10));
        }
        ships.add(player);
    }

    public void update() {
        checkCollisions();

        for (var ship : ships) {
            moveShip(ship);
            if (ship == player) continue;
            int randomInt = random.nextInt(10);
            if (randomInt < 2) bullets.add(ship.shoot());
        }

        for (var bullet : bullets) {
            moveBullet(bullet);
        }
        notifyUpload();
    }

    private void checkCollisions() {
        for (var bullet : bullets) {
            for (var ship : ships) {
                if (!bullet.collide(ship)) continue;

                ship.decreaseHealth();
                bullets.remove(bullet);
                if (ship.getHealth() == 0) {
                    if (ship == player) {
                        gameState = GameState.DEAD;
                        return;
                    }
                    ships.remove(ship);
                    score += 10;
                }
            }
        }
    }

    private void moveShip(Ship ship) {
        int newX = ship.getX() + ship.getSpeedX();
        int newY = ship.getY() + ship.getSpeedY();
        if (0 <= newX && newX < fieldWidth && 0 <= newY && newY < fieldHeight)
            ship.move();
    }

    private void moveBullet(Bullet bullet) {
        bullet.move();
        int bottomY = bullet.getY() + bullet.getHeight();
        if (bottomY < 0 || bullet.getY() >= fieldHeight)
            bullets.remove(bullet);
    }

    public Player getPlayer() {
        return player;
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