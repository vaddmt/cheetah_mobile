package cheetah.cheetah_LGC;

import java.util.Random;

public class GameGenerator {

    Random rnd;

    public GameGenerator() {
        rnd = new Random();
    }

    public int generateValue(int min, int max) {
        return min + rnd.nextInt(Math.abs(max + 1 - min));
    }
}
