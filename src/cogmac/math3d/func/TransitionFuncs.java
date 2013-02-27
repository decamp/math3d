package cogmac.math3d.func;

/**
 * Convenience factory for generating transition curves, which are
 * typically functions that follow f(0) -> 0 and f(1) -> 1.  That 
 * is, these functions remap a normalized time range onto an
 * altered but still normalized time range.
  *  
 * @author decamp
 * @deprecated Use cogmac.math3d.func.Ease. 
 */
public class TransitionFuncs {
    
    public static Function11 identity() {
        return IDENTITY;
    }
    
    public static Function11 smoothStep() {
        return SMOOTH_STEP;
    }
    
    public static Function11 smootherStep() {
        return SMOOTHER_STEP;
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
    
    public static Function11 sigmoid( double tol ) {
        return Sigmoid.newClampedSigmoid(0, 0, 1, 1, tol);
    }
    
    public static Function11 exp( double coef ) {
        return ExpFunc.newClampedExpFunc(0.0, 1.0, 0.0, 1.0, coef);
    }
    
    public static Function13 composite( final Function13 a, final Function11 b ) {
        return new Function13() {
            public void apply( double t, double[] out3x1 ) {
                a.apply( b.apply(t), out3x1 );
            }
        };
    }
    
    

    private static final Function11 IDENTITY = new Function11() {
        public double apply( double t ) {
            return t;
        }
    };
    
    private static final Function11 COS = new Function11() {
        public double apply( double t ) {
            return ( 1.0 - Math.cos( t * Math.PI ) ) * 0.5;
        }
    };
    
    private static final Function11 COS_IN = new Function11() {
        public double apply( double t ) {
            return 1.0 - Math.cos( t * Math.PI * 0.5 );
        }
    };
    
    private static final Function11 COS_OUT = new Function11() {
        public double apply( double t ) {
            return -Math.cos( ( t * 0.5 + 0.5 ) * Math.PI );
        }
    };
    
    private static final Function11 SIN = new Function11() {
        public double apply( double t ) {
            double r = Math.sin( t * Math.PI ) * 0.5;
            return t < 0.5 ? r : 1.0 - r;
        }
    };
    
    private static final Function11 SMOOTH_STEP = new Function11() {
        public double apply( double t ) {
            return t * t * ( 3.0 - 2.0 * t );
        }
    };
    
    private static final Function11 SMOOTHER_STEP = new Function11() {
        public double apply( double t ) {
            return t * t * t * ( t * ( t * 6 - 15 ) + 10 );
        }
    };
    
}
