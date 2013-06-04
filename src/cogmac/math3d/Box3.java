package cogmac.math3d;

/**
 * Methods for manipulating 3-dimensional Axis-Aligned-Bounding-Boxes.
 * All boxes are stored in a length-6 array as [ x0, y0, z0, x1, y1, z1 ].
 * 
 * @author decamp
 */
public final class Box3 {

    /**
     * Scales size of box without changing center point.
     * 
     * @param box  Box to inflate.
     * @param sx   Scale factor for x dimension.
     * @param sy   Scale factor for y dimension.
     * @param out  Holds output. May be same object as <code>a</code>.
     */
    public static void inflate( float[] a, float sx, float sy, float sz, float[] out ) {
        float c, d;
        c = ( a[0] + a[3] ) * 0.5f;
        d = ( a[3] - a[0] ) * 0.5f * sx;
        out[0] = c - d;
        out[3] = c + d;
        c = ( a[1] + a[4] ) * 0.5f;
        d = ( a[4] - a[1] ) * 0.5f * sy;
        out[1] = c - d;
        out[4] = c + d;
        c = ( a[2] + a[5] ) * 0.5f;
        d = ( a[5] - a[2] ) * 0.5f * sy;
        out[2] = c - d;
        out[5] = c + d;
    }
    
    /**
     * Computes intersection between <code>a</code> and <code>b</code>.
     * If boxes do not overlap on a given dimension, then the output will
     * be located entirely within <code>a</code> on the side nearest to 
     * <code>b</code> and will have zero size. For example, the calling
     * <code>clip( { 0, 0, 1, 1 }, { 2, 0, 3, 0.5f }, out )</code>
     * will result in
     * <code>out = { 1, 0, 1, 0.5f }</code>.
     * 
     * @param a   First box
     * @param b   Second box
     * @param out Output box. May be same object as a or b. 
     * @return true iff boxes contain non-zero overlap.
     */
    public static boolean clip( float[] a, float[] b, float[] out ) {
        boolean nonzero = true;
        
        for( int i = 0; i < 3; i++ ) {
            final int j = i + 3;
            out[i] = a[i] >= b[i] ? a[i] :
                     a[j] >= b[i] ? a[j] : b[i];
            out[j] = a[j] <= b[j] ? a[j] : b[j];
            
            if( out[j] <= out[i] ) {
                out[j] = out[i];
                nonzero = false;
            }
        }
        
        return nonzero;
    }
    
    /**
     * Creates box that has same size as <code>box</code>, but positioned to
     * be inside <code>ref</code>. If <code>box</code> is larger than 
     * <code>ref</code> for a given dimension, it will be centered inside 
     * <code>ref</code> for that dimension. If <code>box</code> is already 
     * completely inside of <code>ref</code>, it will not be changed.
     *
     * @param box  Box to reposition.
     * @param ref  Box defining target space for <code>in</code>
     * @param out  Holds output. May be same object as in or ref.
     */
    public static void clamp( float[] box, float[] ref, float[] out ) {
        for( int i = 0; i < 3; i++ ) {
            final int j = i + 3;
            final float d0 = box[j] - box[i];
            final float d1 = ref[j] - ref[i];
            
            if( d0 >= d1 ) {
                float x = ( ref[i] + ref[j] ) * 0.5f;
                out[i] = x - d0 * 0.5f;
                out[j] = x + d0 * 0.5f;
            } else if( box[i] <= ref[i] ) {
                out[i] = ref[i];
                out[j] = ref[i] + d0;
            } else if( box[j] >= ref[j] ) {
                out[i] = ref[j] - d0;
                out[j] = ref[j];
            } else {
                out[i] = box[i];
                out[j] = box[j];
            }
        }
    }
    
    /**
     * Tests if a point is inside given box. 
     * Each dimension of box is treated as half-open interval.
     * 
     * @param box
     * @param x
     * @param y
     * @return true iff x and y are contained by box.
     */
    public static boolean contains( float[] box, float x, float y, float z ) {
        return x >= box[0] && y >= box[1] && z >= box[2] &&
               x <  box[3] && y <  box[4] && z <  box[5];
    }
    
    /**
     * Maps a point in model coordinates to box coordinates, 
     * where ( 0, 0 ) is the lower-left corner and ( 1, 1 ) is upper-right corner.
     * 
     * @param x
     * @param y
     * @param box
     * @param outXYZ
     */
    public static void modelToBox( float x, float y, float z, float[] box, float[] outXYZ, int outOff ) {
        outXYZ[0+outOff] = ( x - box[0] ) / ( box[3] - box[0] );
        outXYZ[1+outOff] = ( y - box[1] ) / ( box[4] - box[1] );
        outXYZ[2+outOff] = ( z - box[2] ) / ( box[5] - box[2] );
    }
    
    /**
     * Maps a point in box coordinates into model coordinates.
     */
    public static void boxToModel( float x, float y, float z, float[] box, float[] outXYZ, int outOff ) {
        outXYZ[0+outOff] = x * ( box[3] - box[0] ) + box[0];
        outXYZ[1+outOff] = y * ( box[4] - box[1] ) + box[1];
        outXYZ[2+outOff] = z * ( box[5] - box[2] ) + box[2];
    }
    
    /**
     * Performs linear mapping of some coordinate in a space defined by 
     * <code>srcDomain</code> into the coordinate space defined by <code>dstDomain</code>.
     * 
     * @param x
     * @param y
     * @param srcDomain
     * @param dstDomain
     * @param outXYZ
     */
    public static void mapPoint( float x, float y, float z, float[] srcDomain, float[] dstDomain, float[] outXYZ, int outOff ) {
        outXYZ[0+outOff] = ( x - srcDomain[0] ) / ( srcDomain[3] - srcDomain[0] ) * ( dstDomain[3] - dstDomain[0] ) + dstDomain[0];
        outXYZ[1+outOff] = ( y - srcDomain[1] ) / ( srcDomain[4] - srcDomain[1] ) * ( dstDomain[4] - dstDomain[1] ) + dstDomain[1];
        outXYZ[2+outOff] = ( z - srcDomain[2] ) / ( srcDomain[5] - srcDomain[2] ) * ( dstDomain[5] - dstDomain[2] ) + dstDomain[2];
    }

    /**
     * Performs linear mapping of a Box3 in a space defined by 
     * <code>srcDomain</code> into the coordinate space defined by <code>dstDomain</code>.
     * 
     * @param in        Input box
     * @param srcDomain Box defining domain of input coordinate. 
     * @param dstDomain Box defining destination domain (codomain). 
     * @param out       On return, holds mapped box. May be same is in.
     */
    public static void mapBox( float[] in, float[] srcDomain, float[] dstDomain, float[] out ) {
        float sx = ( dstDomain[3] - dstDomain[0] ) / ( srcDomain[3] - srcDomain[0] );
        float sy = ( dstDomain[4] - dstDomain[1] ) / ( srcDomain[4] - srcDomain[1] );
        float sz = ( dstDomain[5] - dstDomain[2] ) / ( srcDomain[5] - srcDomain[2] );
        float tx = dstDomain[0] - srcDomain[0] * sx;
        float ty = dstDomain[1] - srcDomain[1] * sy;
        float tz = dstDomain[2] - srcDomain[2] * sz;
        out[0] = sx * in[0] + tx;
        out[1] = sy * in[1] + ty;
        out[2] = sz * in[2] + tz;
        out[3] = sx * in[3] + tx;
        out[4] = sy * in[4] + ty;
        out[5] = tz * in[5] + tz;
    }
    
    /**
     * Ensures box has defines non-negative space.
     * @param box
     */
    public static void fix( float[] box ) {
        for( int i = 0; i < 3; i++ ) {
            final int j = i + 3;
            if( box[i] > box[j] ) {
                float f = box[i];
                box[i] = box[j];
                box[j] = f;
            }
        }
    } 
    
    
    public static String format( float[] v ) {
        return String.format( "box3[% 7.4f, % 7.4f, % 7.4f][% 7.4f,% 7.4f, % 7.4f]", 
                v[0], v[1], v[2], v[3], v[4], v[5] );
    }
    
    
    
    private Box3() {}
    
}
