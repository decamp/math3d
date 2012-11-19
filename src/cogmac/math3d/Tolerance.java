package cogmac.math3d;

/** 
 * @author Philip DeCamp  
 */
public final class Tolerance {
    
    public static final double EPS         = 0x0.0000000000001P-1;    // 64-bit machine epsilon
    public static final double ABS_ERR     = 0x0.0000000000001P-1017; // Double.MIN_VALUE * 32.0
    public static final double REL_ERR     = 0x0.0000000000001P4;     // EPS * 32.0
    
    public static final float  FEPS        = 0x0.000002P-1f;          // 32-bit machine epsilon
    public static final float  FABS_ERR    = 0x0.000002P-121f;        // Float.MIN_VALUE * 32.0f
    public static final float  FREL_ERR    = 0x0.000002P4f;           // FEPS * 32.0f
    
    
    @Deprecated 
    public static final double TOL = 1E-10;
    
    @Deprecated
    public static final float FTOL = 1E-5f;
    
    
    
    /**
     * Equivalent to <code>approxError( a, b, REL_ERR, ABS_ERR );</code>
     */
    public static boolean approxEqual( double a, double b ) {
        return approxEqual( a, b, REL_ERR, ABS_ERR );
    }
    
    /**
     * Equivalent to <code>approxError( a, b, maxRelError, ABS_ERR );</code>
     */
    public static boolean approxEqual( double a, double b, double maxRelError ) {
        return approxEqual( a, b, maxRelError, ABS_ERR );
    }
    
    /**
     * Computes if two numbers are approximately equal, protecting around rounding 
     * errors. This method will compute both absolute and relative differences, and
     * return true if either difference is below a threshold.
     * <p>
     * The absolute error is simply the magnitude of the difference between <code>a</code> and <code>b</code>:
     * <code>Math.abs(a-b)</code>.
     * The absolute error is normally very small. The default value is ABS_ERR. 
     * <p>
     * The relative error is the absolute error divided by the larger of the two magnitudes of <code>a</code> and
     * <code>b</code>: <code>abs(a-b)/max(abs(a),abs(b))</code>. A functional maxRelError should be no smaller than
     * Tolerance.EPS.  The default value is REL_ERR.
     * 
     * @param a
     * @param b
     * @param maxRelError   Maximum relative error.
     * @param maxAbsError   Maximum absolute error.
     * @return true if <code>a</code> and <code>b</code> are approximately equal.
     */
    public static boolean approxEqual( double a, double b, double maxRelError, double maxAbsError ) {
        double diff = a - b;
        
        if( diff < 0.0 )
            diff = -diff;
        
        if( diff < maxAbsError )
            return true;
        
        if( a < 0.0 )
            a = -a;
        
        if( b < 0.0 )
            b = -b;
        
        if( a < b ) {
            diff /= b;
        }else{
            diff /= a;
        }
        
        return diff < maxRelError;
    }

    /**
     * Equivalent to <code>approxError( a, b, FREL_ERR, FABS_ERR );</code>
     */
    public static boolean approxEqual( float a, float b ) {
        return approxEqual( a, b, FREL_ERR, FABS_ERR );
    }
    
    /**
     * Equivalent to <code>approxError( a, b, maxRelError, FABS_ERR );</code>
     */
    public static boolean approxEqual( float a, float b, float maxRelError ) {
        return approxEqual( a, b, maxRelError, FABS_ERR );
    }
    
    /**
     * Computes if two numbers are approximately equal, protecting around rounding 
     * errors. This method will compute both absolute and relative differences, and
     * return true if either difference is below a threshold.
     * <p>
     * The absolute error is simply the magnitude of the difference between <code>a</code> and <code>b</code>:
     * <code>Math.abs(a-b)</code>.
     * The absolute error is normally very small. The default is FABS_ERR.
     * <p>
     * The relative error is the absolute error divided by the larger of the two magnitudes of <code>a</code> and
     * <code>b</code>: <code>abs(a-b)/max(abs(a),abs(b))</code>. A functional maxRelError should be no smaller than
     * Tolerance.FEPS. The default value is FREL_ERR.
     * 
     * @param a
     * @param b
     * @param maxRelError   Maximum relative error.
     * @param maxAbsError   Maximum absolute error.
     * @return true if <code>a</code> and <code>b</code> are approximately equal.
     */
    public static boolean approxEqual( float a, float b, float maxRelError, float maxAbsError ) {
        float diff = a - b;
        
        if( diff < 0.0f )
            diff = -diff;
        
        if( diff < maxAbsError )
            return true;
        
        if( a < 0.0f )
            a = -a;
        
        if( b < 0.0f )
            b = -b;
        
        if( a < b ) {
            diff /= b;
        }else{
            diff /= a;
        }
        
        return diff < maxRelError;
        
    }
    
    
    private Tolerance() {}

    
    public static void main( String[] args ) {
                
    }
    
}
