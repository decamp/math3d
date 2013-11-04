package bits.math3d.func;

/**
 * @author decamp
 */
public class ExpFunc implements Function11 {
    
    
    public static Function11 newClampedExpFunc( double x0, double x1, double y0, double y1, double coef ) {
        double v0 = Math.exp( coef * x0 );
        double v1 = Math.exp( coef * x1 );
        
        double scale = ( y1 - y0 ) / ( v1 - v0 );
        double off   = y0 - v0 * scale;
        
        return new ExpFunc( coef, scale, off );
    }
    
    
    private final double mCoef;
    private final double mScale;
    private final double mOff;
    
    
    private ExpFunc( double coef, double scale, double off ) {
        mCoef  = coef;
        mScale = scale;
        mOff   = off;
    }
    
    
    public double apply( double t ) {
        return mScale * Math.exp( mCoef * t ) + mOff;
    }
    
    

}
