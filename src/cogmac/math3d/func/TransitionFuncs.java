package cogmac.math3d.func;

/**
 * Convenience factory for generating transition curves, which are
 * typically functions that follow f(0) -> 0 and f(1) -> 1.  That 
 * is, these functions remap a normalized time range onto an
 * altered but still normalized time range.
 *  
 * @author decamp
 */
public class TransitionFuncs {

    
    public static Function11 identity() {
        return IDENTITY;
    }

    public static Function11 cos() {
        return COS;
    }

    public static Function11 sin() {
        return SIN;
    }
    
    public static Function11 easeInCos() {
        return COS_IN;
    }
    
    public static Function11 easeOutCos() {
        return COS_OUT;
    }
    
    public static Function11 sigmoid(double tol) {
        return Sigmoid.newClampedSigmoid(0, 0, 1, 1, tol);
    }
    
    public static Function11 exp(double coef) {
        return ExpFunc.newClampedExpFunc(0.0, 1.0, 0.0, 1.0, coef);
    }
    
    public static Function13 composite(final Function13 a, final Function11 b) {
        return new Function13() {
            public void apply(double t, double[] out3x1) {
                a.apply(b.apply(t), out3x1);
            }
        };
    }
    
    

    private static final Function11 IDENTITY = new Function11() {
        public double apply(double x) {
            return x;
        }
    };
    
    private static final Function11 COS = new Function11() {
        public double apply(double val) {
            return (1.0 - Math.cos(val * Math.PI)) * 0.5;
        }
    };
    
    private static final Function11 COS_IN = new Function11() {
        public double apply(double val) {
            return 1.0 - Math.cos( val * Math.PI * 0.5 );
        }
    };
    
    private static final Function11 COS_OUT = new Function11() {
        public double apply(double val) {
            return -Math.cos( ( val * 0.5 + 0.5 ) * Math.PI );
        }
    };
    
    private static final Function11 SIN = new Function11() {
        public double apply( double val ) {
            double r = Math.sin( val * Math.PI ) * 0.5;
            return val < 0.5 ? r : 1.0 - r;
        }
    };


}
