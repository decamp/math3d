package cogmac.math3d.actors;

import cogmac.math3d.*;

/**
 * @author decamp
 */
public class PovLerper {
    
    private final double[][] mWork = new double[3][3];
    
    public PovLerper() {}
    
    
    public void lerp( Pov a, Pov b, double p, Pov out ) {
        Vectors.lerp(a.posRef(), b.posRef(), p, out.posRef());
        Povs.lerpRots(a.rotRef(), b.rotRef(), p, mWork[0], mWork[1], mWork[2], out.rotRef());
    }
    
}
