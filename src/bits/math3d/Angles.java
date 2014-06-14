package bits.math3d;

/** 
 * @author Philip DeCamp  
 */
public class Angles {

    /**
     * @param angle Angle in radians.
     * @return equivalent angle between -PI inclusive to PI exclusive.
     */
    public static double normalize( double angle ) {
        angle %= Math.PI * 2.0;
        return angle <= -Math.PI ? angle + Math.PI * 2.0 :
               angle >   Math.PI ? angle - Math.PI * 2.0 : angle;
    }
    
    
    public static double asincos( double sinAng, double cosAng ) {
        if( cosAng >= 1.0 ) {
            return cosAng < 1.0 + Tol.SQRT_ABS_ERR ? 0.0 : Double.NaN;
        } else if( cosAng <= -1.0 ) {
            return cosAng > -1.0 - Tol.SQRT_ABS_ERR ? Math.PI : Double.NaN; 
        } else {
            return ( sinAng >= 0.0 ? Math.acos( cosAng ) : -Math.acos( cosAng ) );
        }
    }
    
}
