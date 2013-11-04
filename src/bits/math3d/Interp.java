package bits.math3d;


/**
 * Methods for interpolation. 
 * 
 * @author decamp
 */
public final class Interp {
    
    
    /**
     * Linear interpolation.
     * 
     * @param x0 Value before sample
     * @param x1 Value after sample
     * @param t  Parameterized position to sample, parameterized between [0,1].  
     * @return   interpolated value
     */
    public static double lerp( double x0, double x1, double t ) {
        return x0 * ( 1.0 - t ) + x1 * t;
    }
    
    /**
     * Cubic spline interpolation.
     *  
     * @param x0 Point before point before sample.
     * @param x1 Point before sample.
     * @param x2 Point after sample.
     * @param x3 Point after point after sample.
     * @param t  Curve position to sample, parameterized between [0,1].
     * @return interpolated point.
     */
    public static double cubic( double x0, double x1, double x2, double x3, double t ) {
        double tt = t * t;
        double a0 = x3 - x2 - x0 + x1;
        double a1 = x0 - x1 - a0;
        double a2 = x2 - x0;
        
        return a0 * t * tt + a1 * tt + a2 * t + x1;
    }
    
    /**
     * Catmull-Rom spline interpolation.
     * <p>
     * Similar to cubic spline, but smoother
     * due to use of derivatives.
     * 
     * @param x0 Point before point before sample.
     * @param x1 Point before sample.
     * @param x2 Point after sample.
     * @param x3 Point after point after sample.
     * @param t  Curve position to sample, parameterized between [0,1].
     * @return interpolated point.
     */
    public static double catmull( double x0, double x1, double x2, double x3, double t ) {
        double tt = t * t;
        double a0 = -0.5 * x0 + 1.5 * x1 - 1.5 * x2 + 0.5 * x3;
        double a1 = x0 - 2.5 * x1 + 2 * x2 - 0.5 * x3;
        double a2 = -0.5 * x0 + 0.5 * x2;
        
        return a0 * t * tt + a1 * tt + a2 * t + x1;
    }

    /**
     * Hermite spline interpolation.
     * 
     * @param x0 Point before point before sample
     * @param x1 Point before sample
     * @param x2 Point after sample
     * @param x3 Point after point after sample
     * @param t  Curve position to sample, parameterized between [0,1].
     * @param tension 1 is high, 0 is normal, -1 is low
     * @param bias    0 is even, positive is towards first segment, negative is towards other.
     * @return
     */
    public static double hermite( double x0,
                                  double x1,
                                  double x2,
                                  double x3,
                                  double t,
                                  double tension,
                                  double bias )
    {
        double tt  = t * t;
        double ttt = tt * t;
        tension = 1.0 - tension;
        
        double m0  = (x1-x0) * (1+bias) * tension / 2 +
                     (x2-x1) * (1-bias) * tension / 2;
        
        double m1 = (x2-x1) * (1+bias) * tension / 2 +
                    (x3-x2) * (1-bias) * tension / 2;
        
        double a0 =  2 * ttt - 3 * tt     + 1;
        double a1 =      ttt - 2 * tt + t    ;
        double a2 =      ttt -     tt        ;
        double a3 = -2 * ttt + 3 * tt        ;
        
        return a0*x1 + a1*m0 + a2*m1 + a3*x2;
    }
    
    
    
    private Interp() {}
    
}
