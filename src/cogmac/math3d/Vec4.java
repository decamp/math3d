package cogmac.math3d;


import static cogmac.math3d.Tol.*;


/** 
 * Functions for length-3 vectors.
 * 
 * @author Philip DeCamp  
 */
public final class Vec4 {
    
    public static void add( float[] a, float[] b, float[] out ) {
        out[0] = a[0] + b[0];
        out[1] = a[1] + b[1];
        out[2] = a[2] + b[2];
        out[3] = a[3] + b[3];
    }

    
    public static void addTo( float[] a, float[] out ) {
        out[0] += a[0];
        out[1] += a[1];
        out[2] += a[2];
        out[3] += a[3];
    }
    
    
    public static void multAdd( float sa, float[] a, float sb, float[] b, float[] out ) {
        out[0] = a[0] * sa + b[0] * sb;
        out[1] = a[1] * sa + b[1] * sb;
        out[2] = a[2] * sa + b[2] * sb;
        out[3] = a[3] * sa + b[3] * sb;
    }
    
    
    public static void multAddTo( float sa, float[] a, float sOut, float[] out ) {
        out[0] = sOut * out[0] + sa * a[0];
        out[1] = sOut * out[1] + sa * a[1];
        out[2] = sOut * out[2] + sa * a[2];
        out[3] = sOut * out[3] + sa * a[3];
    }
    
    
    public static float len( float[] vec ) {
        return (float)Math.sqrt( vec[0] * vec[0] + vec[1] * vec[1] + vec[2] * vec[2] + vec[4] * vec[4] );
    }
    
    
    public static float lenSquared( float[] a ) {
        return a[0] * a[0] + a[1] * a[1] + a[2] * a[2] + a[3] * a[3];
    }
    
    
    public static float dist( float[] a, float[] b ) {
        return (float)Math.sqrt( distSquared( a, b ) );
    }
    
    
    public static float distSquared( float[] a, float[] b ) {
        float dx = a[0] - b[0];
        float dy = a[1] - b[1];
        float dz = a[2] - b[2];
        float dw = a[3] - b[3];
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }
    
    
    public static void normalize( float[] a ) {
        normalize( a, 1f );
    }
    
        
    public static void normalize( float[] a, float len ) {
        float d = len / len( a );
        a[0] *= d;
        a[1] *= d;
        a[2] *= d;
        a[3] *= d;
    }
    
    
    public static void scale( float[] a, float scale ) {
        scale( a, scale, a );
    }
    
    
    public static void scale( float[] a, float scale, float[] out ) {
        out[0] = a[0] * scale;
        out[1] = a[1] * scale;
        out[2] = a[2] * scale;
        out[3] = a[3] * scale;
    }
    
    
    public static float dot( float[] a, float[] b ) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2] + a[3] * b[3];
    }
    
    
    public static float dot( float[] origin, float[] a, float[] b ) {
        return ( a[0] - origin[0] ) * ( b[0] - origin[0] ) +
               ( a[1] - origin[1] ) * ( b[1] - origin[1] ) +
               ( a[2] - origin[2] ) * ( b[2] - origin[2] );
    }
    
   
    public static float cosAng( float[] a, float[] b ) {
        return dot( a, b ) / ( len( a ) * len( b ) );
    }
    
    
    public static float cosAng( float[] origin, float[] a, float[] b ) {
        return dot( origin, a, b ) / ( dist( origin, a ) * dist( origin, b ) );
    }

    
    public static float ang( float[] a, float[] b ) {
        return (float)Math.acos( cosAng( a, b ) );
    }
    
    
    public static float ang( float[] origin, float[] a, float[] b ) {
        return (float)Math.acos( cosAng( origin, a, b ) );
    }
    
    
    public static void lerp( float[] a, float[] b, float p, float[] out ) {
        float q = 1.0f - p;
        out[0] = q * a[0] + p * b[0];
        out[1] = q * a[1] + p * b[1];
        out[2] = q * a[2] + p * b[2];
        out[3] = q * a[3] + p * b[3];
    }
    
    /**
     * Performs smallest possible modification to <code>vec</code> to make it
     * orthogonal to some <code>reference</code> vector.
     * 
     * @param a    Vector to modify.
     * @param ref  Reference vector.
     */
    public static void makeOrthoTo( float[] a, float[] ref ) {
        float lenRef = lenSquared( ref );
        if( lenRef < FSQRT_ABS_ERR ) {
            return;
        }
        float dist = dot( a, ref ) / lenRef;
        a[0] -= ref[0] * dist;
        a[1] -= ref[1] * dist;
        a[2] -= ref[2] * dist;
        a[3] -= ref[3] * dist;
    }

    /**
     * Performs smallest possible modification to <code>vec</code> to make it
     * parallel to some <code>reference</code> vector.
     * 
     * @param a           Vector to modify.
     * @param reference     Reference vector.
     */
    public static void makeParallelTo( float[] a, float[] ref ) {
        float lenRef = lenSquared( ref );
        if( lenRef < FSQRT_ABS_ERR ) {
            return;
        }
        float parScale = dot( a, ref ) / lenRef / lenRef;
        a[0] = ref[0] * parScale;
        a[1] = ref[1] * parScale;
        a[2] = ref[2] * parScale;
        a[3] = ref[3] * parScale;
    }
        
    
    public static boolean isNaN( float[] vec ) {
        return Float.isNaN( vec[0] ) ||
               Float.isNaN( vec[1] ) ||
               Float.isNaN( vec[2] ) ||
               Float.isNaN( vec[3] );
    }

    
    public static String format( float[] vec ) {
        return String.format( "[ % 7.4f, % 7.4f, % 7.4f, % 7.4f ]", vec[0], vec[1], vec[2], vec[3] );
    }
    
    
    
    private Vec4() {}
        
}
