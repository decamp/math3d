package cogmac.math3d;

/**
 * Methods for manipulating 2-dimensional axis-aligned-bounding-boxes.
 * All boxes are stored in a length-4 array as [ x0, y0, x1, y1 ].
 * 
 * @author decamp
 */
public final class Box2 {

    /**
     * Sets center of box.
     */
    public static void center( float[] box, float cx, float cy, float[] out ) {
        float dx = cx - ( box[0] + box[2] ) * 0.5f;
        float dy = cy - ( box[1] + box[3] ) * 0.5f;
        out[0] = box[0] + dx;
        out[1] = box[1] + dy;
        out[2] = box[2] + dx;
        out[3] = box[3] + dy;
    }
    
    /**
     * Scales size of box.
     */
    public static void scale( float[] box, float sx, float sy, float[] out ) {
        out[0] = box[0] * sx;
        out[1] = box[1] * sy;
        out[2] = box[2] * sx;
        out[3] = box[3] * sy;
    }
    
    /**
     * Scales size of box without changing center point.
     * 
     * @param box  Box to inflate.
     * @param sx   Scale factor for x dimension.
     * @param sy   Scale factor for y dimension.
     * @param out  Holds output. May be same object as <code>a</code>.
     */
    public static void inflate( float[] a, float sx, float sy, float[] out ) {
        float c, d;
        c = ( a[0] + a[2] ) * 0.5f;
        d = ( a[2] - a[0] ) * 0.5f * sx;
        out[0] = c - d;
        out[2] = c + d;
        c = ( a[1] + a[3] ) * 0.5f;
        d = ( a[3] - a[1] ) * 0.5f * sy;
        out[1] = c - d;
        out[3] = c + d;
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
        
        for( int i = 0; i < 2; i++ ) {
            final int j = i + 2;
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
     * Centers box <code>in</code> inside box <code>ref</code> and scales
     * uniformly (without affecting aspect) until the boxes share at least 
     * two borders.
     * 
     * @param in   Box to fit
     * @param ref  Reference box.
     * @param out  Holds output. May be same object as <code>in</code> or <code>ref</code>.
     */
    public static void fit( float[] in, float[] ref, float[] out ) {
        float w0 = ( in[2]  - in[0]  );
        float w1 = ( ref[2] - ref[0] );
        float h0 = ( in[3]  - in[1]  );
        float h1 = ( ref[3] - ref[1] );
        
        if( w0 * h1 > w1 * h0 ) {
            float h = w1 * h0 / w0;
            float m = (h1 - h) * 0.5f;
            out[3] = ref[1] + m + h;
            out[2] = ref[2];
            out[1] = ref[1] + m;
            out[0] = ref[0];
        } else {
            float w = h1 * w0 / h0;
            float m = (w1 - w) * 0.5f;
            out[3] = ref[3];
            out[2] = ref[0] + m + w;
            out[1] = ref[1];
            out[0] = ref[0] + m;
        }
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
        for( int i = 0; i < 2; i++ ) {
            final int j = i + 2;
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
    public static boolean contains( float[] box, float x, float y ) {
        return x >= box[0] && y >= box[1] && x < box[2] && y < box[3];
    }
    
    /**
     * Maps a point in model coordinates to box coordinates, 
     * where ( 0, 0 ) is the lower-left corner and ( 1, 1 ) is upper-right corner.
     * 
     * @param x
     * @param y
     * @param box
     * @param outXY
     * @param outOff
     */
    public static void modelToBox( float x, float y, float[] box, float[] outXY, int outOff ) {
        outXY[outOff  ] = ( x - box[0] ) / ( box[2] - box[0] );
        outXY[outOff+1] = ( y - box[1] ) / ( box[3] - box[1] );
    }
    
    /**
     * Maps a point in box coordinates into model coordinates.
     */
    public static void boxToModel( float x, float y, float[] box, float[] outXY, int outOff ) {
        outXY[outOff  ] = x * ( box[2] - box[0] ) + box[0];
        outXY[outOff+1] = y * ( box[3] - box[1] ) + box[1];
    }
    
    /**
     * Performs linear mapping of some coordinate in a space defined by 
     * <code>src</code> into the coordinate space defined by <code>dst</code>.
     * 
     * @param x         Input x-coordinate
     * @param y         Input y-coordinate
     * @param srcDomain Box defining domain of input coordinate. 
     * @param dstDomain Box defining destination domain (codomain). 
     * @param outXY     Array to hold resulting xy coordinates.
     * @param outOff    Offset into output array.
     */
    public static void map( float x, 
                            float y, 
                            float[] srcDomain, 
                            float[] dstDomain, 
                            float[] outXY, 
                            int outOff ) 
    {
        outXY[outOff  ] = ( x - srcDomain[0] ) / ( srcDomain[2] - srcDomain[0] ) * ( dstDomain[2] - dstDomain[0] ) + dstDomain[0];
        outXY[outOff+1] = ( y - srcDomain[1] ) / ( srcDomain[3] - srcDomain[1] ) * ( dstDomain[3] - dstDomain[1] ) + dstDomain[1];
    }
    
    /**
     * Ensures box has defines non-negative space.
     * @param box
     */
    public static void fix( float[] box ) {
        for( int i = 0; i < 2; i++ ) {
            final int j = i + 2;
            if( box[i] > box[j] ) {
                float f = box[i];
                box[i] = box[j];
                box[j] = f;
            }
        }
    } 
    
    
    public static String format( float[] v ) {
        return String.format( "box2[% 7.4f, % 7.4f][% 7.4f,% 7.4f]", v[0], v[1], v[2], v[3] );
    }
    
    
    
    private Box2() {}
    
}
