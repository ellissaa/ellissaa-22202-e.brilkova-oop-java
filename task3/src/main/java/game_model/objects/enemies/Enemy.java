package game_model.objects.enemies;

import game_model.objects.Ship;

public abstract class Enemy extends Ship {
    protected Enemy(int x, int y, int width, int height,
                    int speedX, int speedY, int maxHealth) {
        super(x, y, width, height, speedX, speedY, maxHealth);
    }
}
