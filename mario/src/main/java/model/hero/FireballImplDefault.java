package model.hero;

public class FireballImplDefault implements FireballImpl {
    @Override
    public void moveFireball(Fireball fireball) {
        //comportement pas défaut
        if (fireball.isToRight()) {
            fireball.setVelX(10);
        } else {
            fireball.setVelX(-5);
        }
    }
}
