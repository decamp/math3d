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
    public static void modelToBox( float x, float y, float z, float[] box, float[] outXYZ ) {
        outXYZ[0] = ( x - box[0] ) / ( box[3] - box[0] );
        outXYZ[1] = ( y - box[1] ) / ( box[4] - box[1] );
        outXYZ[2] = ( z - box[2] ) / ( box[5] - box[2] );
    }
    
    /**
     * Maps a point in box coordinates into model coordinates.
     */
    public static void boxToModel( float x, float y, float z, float[] box, float[] outXYZ ) {
        outXYZ[0] = x * ( box[3] - box[0] ) + box[0];
        outXYZ[1] = y * ( box[4] - box[1] ) + box[1];
        outXYZ[2] = z * ( box[5] - box[2] ) + box[2];
    }
    
    /**
     * Performs linear mapping of some coordinate in a space defined by 
     * <code>src</code> into the coordinate space defined by <code>dst</code>.
     * 
     * @param x
     * @param y
     * @param src
     * @param dst
     * @param outXYZ
     */
    public static void map( float x, float y, float z, float[] src, float[] dst, float[] outXYZ ) {
        outXYZ[0] = ( x - src[0] ) / ( src[3] - src[0] ) * ( dst[3] - dst[0] ) + dst[0];
        outXYZ[1] = ( y - src[1] ) / ( src[4] - src[1] ) * ( dst[4] - dst[1] ) + dst[1];
        outXYZ[2] = ( z - src[2] ) / ( src[5] - src[2] ) * ( dst[5] - dst[2] ) + dst[2];
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
