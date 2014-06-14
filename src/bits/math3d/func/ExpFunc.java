package bits.math3d.func;

/**
 * @author decamp
 */
public class ExpFunc implements Function11 {
    

    public static Function11 createClamped( double x0, double x1, double y0, double y1, double coef ) {
        double v0 = Math.exp( coef * x0 );
        double v1 = Math.exp( coef * x1 );
        double scale = ( y1 - y0 ) / ( v1 - v0 );
        double off   = y0 - v0 * scale;
        return new ExpFunc( scale, coef, off );
    }


    private final double mCoef;
    private final double mMult;
    private final double mAdd;


    public ExpFunc( double mult, double coef, double add ) {
        mCoef = coef;
        mMult = mult;
        mAdd  = add;
    }


    public double apply( double t ) {
        return mMult * Math.exp( mCoef * t ) + mAdd;
    }

    @Deprecated
    public static Function11 newClampedExpFunc( double x0, double x1, double y0, double y1, double coef ) {
        return createClamped( x0, x1, y0, y1, coef );
    }

}
