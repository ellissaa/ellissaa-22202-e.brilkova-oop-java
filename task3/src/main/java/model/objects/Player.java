package model.objects;

import model.objects.bullets.Bullet;
import model.objects.bullets.BulletDefault;
import model.objects.bullets.BulletDirection;

public class Player extends Ship {
    public static final int playerWidth = 60;
    public static final int playerHeight = 40;
    public static final int playerSpeed = 4;

    private final int shootTimeout;
    private long lastShotTime;

    public Player(int x, int y, int shootTimeout) {
        super(x, y, playerWidth, playerHeight,
                0, 0, 3);
        this.shootTimeout = shootTimeout;
    }

    @Override
    public Bullet shoot() {
        long currTime = System.currentTimeMillis();
        if (currTime - lastShotTime > shootTimeout) {
            lastShotTime = currTime;
            return new BulletDefault(this, BulletDirection.UP);
        }
        return null;
    }
}
