package game_model.objects.bullets;

import game_model.objects.Entity;
import game_model.objects.Ship;

public abstract class Bullet extends Entity {
    protected Bullet(Ship shooter, int width, int height, int speed, BulletDirection direct) {
        super(width, height);
        int bulletX = shooter.getX() + shooter.getWidth() / 2 - width / 2;
        int bulletY, speedY;

        if (direct == BulletDirection.UP) {
            bulletY = shooter.getY() + height;
            speedY = -speed;
        } else {
            bulletY = shooter.getY() + shooter.getHeight();
            speedY = speed;
        }

        setX(bulletX);
        setY(bulletY);
        setSpeedY(speedY);
    }
}
