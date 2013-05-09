package cogmac.math3d;

/**
 * @author decamp
 */
public final class Arr {
    
    
    public static float[] wrap( float... vals ) {
        return vals;
    }
    
    
    public static void mult( float[] arr, float scale ) {
        mult( arr, 0, arr.length, scale );
    }
    
    
    public static void mult( float[] arr, int off, int len, float scale ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] *= scale;
        }
    }
    
    
    public static void mult( float[] a, float[] b, float[] out ) {
        mult( a, 0, b, 0, a.length, out, 0 );
    }
    
    
    public static void mult( float[] a, int aOff, float[] b, int bOff, int len, float[] out, int outOff ) {
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = a[aOff+i] * b[bOff+i];
        }
    }
    

    public static void add( float[] arr, float amount ) {
        add( arr, 0, arr.length, amount );
    }
    
    
    public static void add( float[] arr, int off, int len, float amount ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] += amount;
        }
    }
    

    public static void add( float[] a, float[] b, float[] out ) {
        add( a, 0, b, 0, a.length, out, 0 );
    }
    
    
    public static void add( float[] a, int aOff, float[] b, int bOff, int len, float[] out, int outOff ) {
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = a[aOff+i] + b[bOff+i];
        }
    }
    

    public static float dot( float[] a, float[] b ) {
        return dot( a, 0, b, 0, a.length );
    }
    
    
    public static float dot( float[] a, int aOff, float[] b, int bOff, int len ) {
        float sum = 0f;
        for( int i = 0; i < len; i++ ) {
            sum += a[aOff+i] * b[bOff+i];
        }
        return sum;
    }
    
    
    public static void multAdd( float[] arr, float scale, float add ) {
        multAdd( arr, 0, arr.length, scale, add );
    }
    
    
    public static void multAdd( float[] arr, int off, int len, float scale, float add ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] = arr[i] * scale + add;
        }
    }
    

    public static void lerp( float[] a, float[] b, float p, float[] out ) {
        final int len = a.length;
        final float q = 1.0f - p;
        
        for( int i = 0; i < len; i++ ) {
            out[i] = q * a[i] + p * b[i]; 
        }
    }
    
    
    public static void lerp( float[] a, 
                             int aOff, 
                             float[] b, 
                             int bOff, 
                             int len, 
                             float p, 
                             float[] out, 
                             int outOff ) 
    {
        final float q = 1.0f - p;
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = q * a[aOff+i] + p * b[bOff+i]; 
        }
    }

    
    public static float len( float... arr ) {
        return (float)Math.sqrt( lenSquared( arr, 0, arr.length ) );
    }
    
    
    public static float len( float[] arr, int off, int len ) {
        return (float)Math.sqrt( lenSquared( arr, off, len ) );
    }
    
    
    public static float lenSquared( float... arr ) {
        return lenSquared( arr, 0, arr.length );
    }
    
    
    public static float lenSquared( float[] arr, int off, int len ) {
        float sum = 0;
        for( int i = off; i < off + len; i++ ) {
            sum += arr[i] * arr[i];
        }
        
        return sum;
    }
    
    
    public static float sum( float... arr ) {
        return sum( arr, 0, arr.length );
    }
    
    
    public static float sum( float[] arr, int off, int len ) {
        float ret = 0.0f;

        for( int i = off; i < off + len; i++ ) {
            ret += arr[i];
        }
        
        return ret;
    }
    
    
    public static float mean( float... arr ) {
        return mean( arr, 0, arr.length );
    }


    public static float mean( float[] arr, int off, int len ) {
        float sum = 0.0f;

        for( int i = off; i < off + len; i++ ) {
            sum += arr[i];
        }

        return sum / len;
    }


    public static float variance( float... arr ) {
        return variance( arr, 0, arr.length );
    }


    public static float variance( float[] arr, int off, int len ) {
        return variance( arr, off, len, mean( arr, off, len ) );
    }


    public static float variance( float[] arr, int off, int len, float mean ) {
        float sum = 0.0f;

        for( int i = off; i < off + len; i++ ) {
            float v = arr[i] - mean;
            sum += v * v;
        }

        return sum / len;
    }


    public static float min( float... arr ) {
        return min( arr, 0, arr.length );
    }


    public static float min( float[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Float.NaN;
        }

        float ret = arr[off];

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] < ret ) {
                ret = arr[i];
            }
        }

        return ret;
    }


    public static float max( float... arr ) {
        return max( arr, 0, arr.length );
    }


    public static float max( float[] arr, int off, int len ) {
        if( len <= 0.0 ) {
            return Float.NaN;
        }

        float ret = arr[off];

        for( int i = off + 1; i < off + len; i++ ) {
            if( arr[i] > ret ) {
                ret = arr[i];
            }
        }

        return ret;
    }


    public static float[] range( float... arr ) {
        return range( arr, 0, arr.length );
    }


    public static float[] range( float[] arr, int off, int len ) {
        float[] ret = new float[2];
        range( arr, off, len, ret );
        return ret;
    }


    public static void range( float[] arr, float[] out2x1 ) {
        range( arr, 0, arr.length, out2x1 );
    }


    public static void range( float[] arr, int off, int len, float[] out2x1 ) {
        if( len <= 0 ) {
            out2x1[0] = Float.NaN;
            out2x1[1] = Float.NaN;
            return;
        }

        float min = arr[off];
        float max = min;

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


    public static void normalize( float[] arr, int off, int len, float min, float max ) {
        float[] range = range( arr, off, len );
        normalize( arr, off, len, range[0], range[1], min, max );
    }


    public static void normalize( float[] arr, int off, int len, float inMin, float inMax, float outMin, float outMax ) {
        float scale = ( outMax - outMin) / ( inMax - inMin);
        if( Float.isNaN( scale ) ) {
            scale = 0.0f;
        }

        float add = outMin - inMin * scale;

        for( int i = off; i < off + len; i++ ) {
            arr[i] = arr[i] * scale + add;
        }
    }


    public static void clamp( float[] arr, int off, int len, float min, float max ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] < min ) {
                arr[i] = min;
            } else if( arr[i] > max ) {
                arr[i] = max;
            }
        }
    }


    public static void clampMin( float[] arr, float min ) {
        clampMin( arr, 0, arr.length, min );
    }


    public static void clampMin( float[] arr, int off, int len, float min ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] < min ) {
                arr[i] = min;
            }
        }
    }


    public static void clampMax( float[] arr, float max ) {
        clampMax( arr, 0, arr.length, max );
    }


    public static void clampMax( float[] arr, int off, int len, float max ) {
        for( int i = off; i < off + len; i++ ) {
            if( arr[i] > max ) {
                arr[i] = max;
            }
        }
    }

    
    public static void pow( float[] arr, float exp ) {
        pow( arr, 0, arr.length, exp );
    }

    
    public static void pow( float[] arr, int off, int len, float exp ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] = (float)Math.pow( arr[i], exp );
        }
    }

    
    public static void exp( float[] arr, float base ) {
        exp( arr, 0, arr.length, base );
    }
    
    
    public static void exp( float[] arr, int off, int len, float base ) {
        double s = 1.0 / Math.exp( base );
        for( int i = off; i < off + len; i++ ) {
            arr[i] = (float)( Math.exp( base ) * s );
        }
    }
    

    
    
    public static double[] wrap( double... vals ) {
        return vals;
    }
    
    
    public static void mult( double[] arr, double scale ) {
        mult( arr, 0, arr.length, scale );
    }
    
    
    public static void mult( double[] arr, int off, int len, double scale ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] *= scale;
        }
    }
    
    
    public static void mult( double[] a, double[] b, double[] out ) {
        mult( a, 0, b, 0, a.length, out, 0 );
    }
    
    
    public static void mult( double[] a, int aOff, double[] b, int bOff, int len, double[] out, int outOff ) {
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = a[aOff+i] * b[bOff+i];
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
    

    public static void add( double[] a, double[] b, double[] out ) {
        add( a, 0, b, 0, a.length, out, 0 );
    }
    
    
    public static void add( double[] a, int aOff, double[] b, int bOff, int len, double[] out, int outOff ) {
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = a[aOff+i] + b[bOff+i];
        }
    }
    

    public static double dot( double[] a, double[] b ) {
        return dot( a, 0, b, 0, a.length );
    }
    
    
    public static double dot( double[] a, int aOff, double[] b, int bOff, int len ) {
        double sum = 0f;
        for( int i = 0; i < len; i++ ) {
            sum += a[aOff+i] * b[bOff+i];
        }
        return sum;
    }
    
    
    public static void multAdd( double[] arr, double scale, double add ) {
        multAdd( arr, 0, arr.length, scale, add );
    }
    
    
    public static void multAdd( double[] arr, int off, int len, double scale, double add ) {
        for( int i = off; i < off + len; i++ ) {
            arr[i] = arr[i] * scale + add;
        }
    }
    

    public static void lerp( double[] a, double[] b, double p, double[] out ) {
        final int len = a.length;
        final double q = 1.0f - p;
        
        for( int i = 0; i < len; i++ ) {
            out[i] = q * a[i] + p * b[i]; 
        }
    }
    
    
    public static void lerp( double[] a, 
                             int aOff, 
                             double[] b, 
                             int bOff, 
                             int len, 
                             double p, 
                             double[] out, 
                             int outOff ) 
    {
        final double q = 1.0f - p;
        for( int i = 0; i < len; i++ ) {
            out[outOff+i] = q * a[aOff+i] + p * b[bOff+i]; 
        }
    }

    
    public static double len( double... arr ) {
        return Math.sqrt( lenSquared( arr, 0, arr.length ) );
    }
    
    
    public static double len( double[] arr, int off, int len ) {
        return Math.sqrt( lenSquared( arr, off, len ) );
    }
    
    
    public static double lenSquared( double... arr ) {
        return lenSquared( arr, 0, arr.length );
    }
    
    
    public static double lenSquared( double[] arr, int off, int len ) {
        double sum = 0;
        for( int i = off; i < off + len; i++ ) {
            sum += arr[i] * arr[i];
        }
        
        return sum;
    }
    
    
    public static double sum( double... arr ) {
        return sum( arr, 0, arr.length );
    }
    
    
    public static double sum( double[] arr, int off, int len ) {
        double ret = 0.0f;

        for( int i = off; i < off + len; i++ ) {
            ret += arr[i];
        }
        
        return ret;
    }
    
    
    public static double mean( double... arr ) {
        return mean( arr, 0, arr.length );
    }


    public static double mean( double[] arr, int off, int len ) {
        double sum = 0.0f;

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
        double sum = 0.0f;

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
            scale = 0.0f;
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

    
    public static void exp( double[] arr, double base ) {
        exp( arr, 0, arr.length, base );
    }
    
    
    public static void exp( double[] arr, int off, int len, double base ) {
        double s = 1.0 / Math.exp( base );
        for( int i = off; i < off + len; i++ ) {
            arr[i] = ( Math.exp( base ) * s );
        }
    }
    
    
    
    private Arr() {}
    
}