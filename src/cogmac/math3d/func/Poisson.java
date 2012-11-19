package cogmac.math3d.func;

import java.util.Random;

/**
 * Generates a Poisson process.
 */
public class Poisson {

    
    private final Random mRand;

    
    public Poisson() {
        this(new Random());
    }
    
    public Poisson(Random rand) {
        mRand = rand;
    }

   
    /**
     * @param time       Time of previous event
     * @param intensity  Rate of event occurences.
     * @return Time of the next event
     */
    public double nextEvent(double time, double intensity) {
        return time - Math.log(1 - mRand.nextDouble()) / intensity;
    }
    
}
