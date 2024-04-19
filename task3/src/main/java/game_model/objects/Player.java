package game_model.objects;

import game_model.objects.bullets.Bullet;
import game_model.objects.bullets.BulletDefault;
import game_model.objects.bullets.BulletDirection;

public class Player extends Ship {
    public static final int playerWidth = 10;
    public static final int playerHeight = 10;

    public Player(int x, int y) {
        super(x, y, playerWidth, playerHeight, 0, 0, 3);
    }

    @Override
    public Bullet shoot() {
        return new BulletDefault(this, BulletDirection.UP);
    }
}
