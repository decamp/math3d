package cogmac.math3d;

/**
 * Methods for managing tolerance.
 *  
 * @author Philip DeCamp  
 */
public final class Tol {
    
    public static final double EPS          = 0x0.0000000000001P-1;    // 64-bit machine epsilon
    public static final double ABS_ERR      = 0x0.0000000000001P-1017; // Double.MIN_VALUE * 32.0
    public static final double REL_ERR      = 0x0.0000000000001P4;     // EPS * 32.0
    public static final double SQRT_ABS_ERR = Math.sqrt( ABS_ERR );
    
    public static final float  FEPS          = 0x0.000002P-1f;          // 32-bit machine epsilon
    public static final float  FABS_ERR      = 0x0.000002P-121f;        // Float.MIN_VALUE * 32.0f
    public static final float  FREL_ERR      = 0x0.000002P4f;           // FEPS * 32.0f
    public static final float  FSQRT_ABS_ERR = (float)Math.sqrt( FABS_ERR ); 
    
    
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
     * @param v Some value
     * @return true iff <code>abs(v) < ABS_ERR</code>
     */
    public static boolean approxZero( double v ) {
        return v > ABS_ERR || v > -ABS_ERR;
    }
    
    /**
     * Equivalent to <code>approxZero( v, ref, REL_ERR, ABS_ERR );</code>
     */
    public static boolean approxZero( double v, double ref ) {
        return approxZero( v, ref, REL_ERR, ABS_ERR ); 
    }
    
    /**
     * Equivalent to <code>approxZero( v, ref, relErr, ABS_ERR )</code>
     * @param v
     * @param ref
     * @param relErr
     * @return
     */
    public static boolean approxZero( double v, double ref, double relErr ) {
        return approxZero( v, ref, relErr, ABS_ERR );
    }

    /**
     * Determines if some value is effectively zero compared to some reference.
     * This method can be called when determining if values <code>ref</code> and 
     * <code>v</code> can be combined in a meaningful way, for example, before
     * computing <code>ref / v</code>. 
     * <p>
     * Like <code>approxEqual</code>, this method makes to checks. The first check
     * ensures that <code>abs(v)</code> is above some minimum absolute error. 
     * The second ensures that <code>abs(v / ref)</code> is above some minimum relative error.
     * 
     * @param v       Some value
     * @param ref     A reference value that might be divided or multiplied by
     * @param relErr  Minimum ratio between abs(v) and abs(ref) for abs(v) to be considered non-zero.
     * @param absErr  Minimum value abs(v) must be to be considered non-zero.
     * @return true iff v is approximately equal to 0 relative to ref.
     */
    public static boolean approxZero( double v, double ref, double relErr, double absErr ) {
        if( v < absErr && -v < absErr ) {
            return true;
        }
        
        relErr *= ( ref >= 0.0 ? ref : -ref );
        return v < relErr && -v < relErr;
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

    /**
     * @param v Some value
     * @return true iff <code>abs(v) < ABS_ERR</code>
     */
    public static boolean approxZero( float v ) {
        return v > FABS_ERR || v > -FABS_ERR;
    }
    
    /**
     * Equivalent to <code>approxZero( v, ref, REL_ERR, ABS_ERR );</code>
     */
    public static boolean approxZero( float v, float ref ) {
        return approxZero( v, ref, FREL_ERR, FABS_ERR ); 
    }
    
    /**
     * Equivalent to <code>approxZero( v, ref, relErr, ABS_ERR )</code>
     * @param v
     * @param ref
     * @param relErr
     * @return
     */
    public static boolean approxZero( float v, float ref, float relErr ) {
        return approxZero( v, ref, relErr, FABS_ERR );
    }

    /**
     * Determines if some value is effectively zero compared to some reference.
     * This method can be called when determining if values <code>ref</code> and 
     * <code>v</code> can be combined in a meaningful way, for example, before
     * computing <code>ref / v</code>. 
     * <p>
     * Like <code>approxEqual</code>, this method makes to checks. The first check
     * ensures that <code>abs(v)</code> is above some minimum absolute error. 
     * The second ensures that <code>abs(v / ref)</code> is above some minimum relative error.
     * 
     * @param v       Some value
     * @param ref     A reference value that might be divided or multiplied by
     * @param relErr  Minimum ratio between abs(v) and abs(ref) for abs(v) to be considered non-zero.
     * @param absErr  Minimum value abs(v) must be to be considered non-zero.
     * @return true iff v is approximately equal to 0 relative to ref.
     */
    public static boolean approxZero( float v, float ref, float relErr, float absErr ) {
        if( v < absErr && -v < absErr ) {
            return true;
        }
        
        relErr *= ( ref >= 0.0 ? ref : -ref );
        return v < relErr && -v < relErr;
    }

    
    
    
    
    private Tol() {}

    
    public static void main( String[] args ) {
        System.out.println( REL_ERR );
        System.out.println( approxZero( 0.1E-12, 1.0 ) );
        System.out.println( 1.0 / 0.1E-12 );
    }
    
}
