package game_model.objects.enemies;

import game_model.objects.bullets.Bullet;
import game_model.objects.bullets.BulletDefault;
import game_model.objects.bullets.BulletDirection;

public class EnemyDefault extends Enemy {
    public static final int enemyDefWidth = 10;
    public static final int enemyDefHeight = 10;

    public EnemyDefault(int x, int y) {
        super(x, y, enemyDefWidth, enemyDefHeight, 10, 0, 1);
    }

    @Override
    public void increaseHealth() {}

    @Override
    public Bullet shoot() {
        return new BulletDefault(this, BulletDirection.DOWN);
    }
}
