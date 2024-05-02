package game_model.objects.enemies;

import game_model.objects.bullets.Bullet;
import game_model.objects.bullets.BulletDefault;
import game_model.objects.bullets.BulletDirection;

import java.util.Random;

public class EnemyDefault extends Enemy {
    public static final int enemyDefWidth = 45;
    public static final int enemyDefHeight = 34;
    private final Random random = new Random();


    public EnemyDefault(int x, int y) {
        super(x, y, enemyDefWidth, enemyDefHeight, 1, 0, 1);
    }

    @Override
    public void increaseHealth() {}

    @Override
    public Bullet shoot() {
        int randomInt = random.nextInt(2000);
        if (randomInt < 4) return new BulletDefault(this, BulletDirection.DOWN);
        else return null;
    }
}
