package model;

import model.objects.Player;
import model.objects.Ship;
import model.objects.bullets.Bullet;
import model.objects.enemies.EnemyDefault;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private int score = 0;
    public static final int fieldWidth = 1000;
    public static final int fieldHeight = 600;

    private final Player player;
    private List<Ship> ships;
    private List<Bullet> bullets;
    private GameState gameState = GameState.RUN;
    private GameDifficulty difficulty;

    private ModelUpload upload;

    public Model(GameDifficulty difficulty) {
        this.difficulty = difficulty;

        int shootTimeout = switch (difficulty) {
            case LEVEL1 -> 700;
            case LEVEL2 -> 900;
            case LEVEL3 -> 1100;
        };

        player = new Player(fieldWidth / 2 - Player.playerWidth / 2,
                fieldHeight - Player.playerHeight, shootTimeout);

        ships = new ArrayList<>();
        bullets = new ArrayList<>();
        spawnEnemies();
        ships.add(player);
    }

    public void spawnEnemies() {
        int offset = 25;
        int gap = 15;
        int enemyWidth = EnemyDefault.enemyDefWidth + gap;

        int shootFreqModifier = switch (difficulty) {
            case LEVEL1 -> 1500;
            case LEVEL2 -> 800;
            case LEVEL3 -> 400;
        };

        for (int i = offset; i < (fieldWidth - offset); i += enemyWidth) {
            ships.add(new EnemyDefault(i, 10, shootFreqModifier));
        }
    }

    public void update() throws InterruptedException {
        if (gameState == GameState.DEAD) {
            notifyUpload();
            return;
        }

        checkCollisions();

        if (canMove(player)) player.move();
        int speedModifier = moveEnemies() ? 1 : -1;
        for (Ship ship : ships) {
            if (ship == player) continue;
            ship.setSpeedX(ship.getSpeedX() * speedModifier);
            ship.move();

            Bullet bullet = ship.shoot();
            if (bullet != null) {
                bullets.add(bullet);
            }
        }

        if (ships.size() == 1) {
            spawnEnemies();
            player.increaseHealth();
        }
        notifyUpload();
    }

    private void checkCollisions() {
        List<Bullet> bulletsCopy = new ArrayList<>(bullets);
        List<Ship> shipsCopy = new ArrayList<>(ships);

        for (Bullet bullet : bullets) {
            for (Ship ship : ships) {
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
                    score += switch (difficulty) {
                        case LEVEL1 -> 10;
                        case LEVEL2 -> 15;
                        case LEVEL3 -> 20;
                    };
                }
            }
            moveBullet(bullet, bulletsCopy);
        }
        ships = shipsCopy;
        bullets = bulletsCopy;
    }

    private boolean canMove(Ship ship) {
        int newX = ship.getX() + ship.getSpeedX();
        int newY = ship.getY() + ship.getSpeedY();
        return 0 <= newX && newX + ship.getWidth() <= fieldWidth
                && 0 <= newY && newY + ship.getHeight() <= fieldHeight;
    }

    private boolean moveEnemies() {
        boolean allCanMove = true;
        for (Ship ship : ships) {
            if (ship == player) continue;
            allCanMove = allCanMove && canMove(ship);
        }
        return allCanMove;
    }

    private void moveBullet(Bullet bullet, List<Bullet> bulletsList) {
        bullet.move();
        int bottomY = bullet.getY() + bullet.getHeight();
        if (bottomY < 0 || bullet.getY() >= fieldHeight)
            bulletsList.remove(bullet);
    }

    public void playerShoot() {
        Bullet newBullet = player.shoot();
        if (newBullet == null) return;
        bullets.add(newBullet);
    }

    public void updatePlayerSpeed(int newSpeed) {
        player.setSpeedX(newSpeed);
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

    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    private void notifyUpload() {
        if (upload != null)
            upload.modelChange();
    }

    public void setUpload(ModelUpload upload) {
        this.upload = upload;
    }
}