package cogmac.math3d;

/**
 * @author decamp
 */
public class ArrayMath {

    
    public static double sum( double... arr ) {
        return sum( arr, 0, arr.length );
    }
    
    
    public static double sum( double[] arr, int off, int len ) {
        double ret = 0.0;

        for( int i = off; i < off + len; i++ ) {
            ret += arr[i];
        }
        
        return ret;
    }
    
    
    public static double mean( double... arr ) {
        return mean( arr, 0, arr.length );
    }


    public static double mean( double[] arr, int off, int len ) {
        double sum = 0.0;

        for( int i = off; i < off + len; i++ ) {
            sum += arr[i];
        }

        return sum / len;
    }


    public static double variance( double... arr ) {
        return variance( arr, 0, arr.length );
    }


    public static double variance( double[] arr, int off, int len ) {
        return variance( arr, off, len, mean( arr, off, len ) );
    }


    public static double variance( double[] arr, int off, int len, double mean ) {
        double sum = 0.0;

        for( int i = off; i < off + len; i++ ) {
            double v = arr[i] - mean;
            sum += v * v;
        }

        return sum / len;
    }


    public static double min( double... arr ) {
        return min( arr, 0, arr.length );
    }


    public static double min( double[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Double.NaN;
        }

        double ret = arr[off];

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < ret ) {
                ret = arr[i];
            }
        }

        return ret;
    }


    public static double max( double... arr ) {
        return max( arr, 0, arr.length );
    }


    public static double max( double[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Double.NaN;
        }

        double ret = arr[off];

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] > ret ) {
                ret = arr[i];
            }
        }

        return ret;
    }


    public static double[] range( double... arr ) {
        return range( arr, 0, arr.length );
    }


    public static double[] range( double[] arr, int off, int len ) {
        double[] ret = new double[2];
        range( arr, off, len, ret );
        return ret;
    }


    public static void range( double[] arr, double[] out2x1 ) {
        range( arr, 0, arr.length, out2x1 );
    }


    public static void range( double[] arr, int off, int len, double[] out2x1 ) {
        if( len <= 0 ) {
            out2x1[0] = Double.NaN;
            out2x1[1] = Double.NaN;
            return;
        }

        double min = arr[off];
        double max = min;

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < min ) {
                min = arr[i]; 
            }
            if( arr[i] > max ) {
                max = arr[i];
            }
        }

        out2x1[0] = min;
        out2x1[1] = max;
    }


    public static void normalize( double[] arr, int off, int len, double min, double max ) {
        double[] range = range( arr, off, len );
        normalize( arr, off, len, range[0], range[1], min, max );
    }


    public static void normalize( double[] arr, int off, int len, double inMin, double inMax, double outMin, double outMax ) {
        double scale = ( outMax - outMin) / ( inMax - inMin);
        if( Double.isNaN( scale ) ) {
            scale = 0.0;
        }

        double add = outMin - inMin * scale;

        for( int i = off; i < off + len; i++ ) {
            arr[i] = arr[i] * scale + add;
        }
    }


    public static void clamp( double[] arr, int off, int len, double min, double max ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] < min ) {
                arr[i] = min;
            } else if( arr[i] > max ) {
                arr[i] = max;
            }
        }
    }


    public static void clampMin( double[] arr, double min ) {
        clampMin( arr, 0, arr.length, min );
    }


    public static void clampMin( double[] arr, int off, int len, double min ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] < min ) {
                arr[i] = min;
            }
        }
    }


    public static void clampMax( double[] arr, double max ) {
        clampMax( arr, 0, arr.length, max );
    }


    public static void clampMax( double[] arr, int off, int len, double max ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] > max ) {
                arr[i] = max;
            }
        }
    }

    
    public static void pow( double[] arr, double exp ) {
        pow( arr, 0, arr.length, exp );
    }

    
    public static void pow( double[] arr, int off, int len, double exp ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] = Math.pow( arr[i], exp );
        }
    }
    
    
    public static void mult( double[] arr, double scale ) {
        mult( arr, 0, arr.length, scale );
    }
    
    
    public static void mult( double[] arr, int off, int len, double scale ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] *= scale;
        }
    }
    

    public static void add( double[] arr, double amount ) {
        add( arr, 0, arr.length, amount );
    }
    
    
    public static void add( double[] arr, int off, int len, double amount ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] += amount;
        }
    }

}
