package fapra.magenta.data.pickups;

import fapra.magenta.data.Point;

public class CoinPickUp extends PickUpGameObject {

    public CoinPickUp(Point position, float radius) {
        super(position, radius);
        coinValue = value;
    }

    public static int value = 10;
    
    public int coinValue;
    
    @Override
    public void update(float delta) {
    }

    @Override
    public boolean isAlive() {
        // directly consumed, so no isAlive state needed
        return false;
    }

    public static void setValue(int value) {
        CoinPickUp.value = value;
    }
}
