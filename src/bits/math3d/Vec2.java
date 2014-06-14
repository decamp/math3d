package bits.math3d;

import static bits.math3d.Tol.*;


/** 
 * Functions for length-2 vectors.
 * 
 * @author Philip DeCamp  
 */
public final class Vec2 {

    public static void add( float[] a, float[] b, float[] out ) {
        out[0] = a[0] + b[0];
        out[1] = a[1] + b[1];
    }
    
    
    public static void addTo( float[] a, float[] out ) {
        out[0] += a[0];
        out[1] += a[1];
    }


    public static void subtract( float[] a, float[] b, float[] out ) {
        out[0] = a[0] - b[0];
        out[1] = a[1] - b[1];
    }
    
    
    public static void subtractFrom( float[] a, float[] out ) {
        out[0] -= a[0];
        out[1] -= a[1];
    }

    
    public static void mult( float sa, float[] a ) {
        a[0] *= sa;
        a[1] *= sa;
    }
    
    
    public static void mult( float sa, float[] a, float[] out ) {
        out[0] = sa * a[0];
        out[1] = sa * a[1];
    }

    
    public static void multAdd( float sa, float[] a, float sb, float[] b, float[] out ) {
        out[0] = a[0] * sa + b[0] * sb;
        out[1] = a[1] * sa + b[1] * sb;
    }
    
    
    public static void multAddTo( float sa, float[] a, float sOut, float[] out ) {
        out[0] = sOut * out[0] + sa * a[0];
        out[1] = sOut * out[1] + sa * a[1];
    }

        
    public static float len( float[] a ) {
        return (float)Math.sqrt( lenSquared( a ) );
    }
    
    
    public static float lenSquared( float[] a ) {
        return a[0] * a[0] + a[1] * a[1];
    }
    
    
    public static float dist( float[] a, float[] b ) {
        return (float)Math.sqrt( distSquared( a, b ) );
    }
    
    
    public static float distSquared( float[] a, float[] b ) {
        float dx = a[0] - b[0];
        float dy = a[1] - b[1];
        return dx * dx + dy * dy;
    }
    
    
    public static void normalize( float[] a ) {
        float s = 1f / len( a );
        a[0] *= s;
        a[1] *= s;
    }
    
    
    public static void normalize( float[] a, float normLength, float[] out ) {
        float d = normLength / len(a);
        out[0] = a[0] * d;
        out[1] = a[1] * d;
    }
    
    
    public static float dot( float[] a, float[] b ) {
        return a[0] * b[0] + a[1] * b[1];
    }
    
    
    public static float dot( float[] origin, float[] a, float[] b ) {
        return (a[0] - origin[0]) * (b[0] - origin[0]) +
               (a[1] - origin[1]) * (b[1] - origin[1]);
    }
    
   
    public static float cosAng( float[] a, float[] b ) {
        return dot( a, b ) / (float)Math.sqrt( lenSquared( a ) * lenSquared( b ) );
    }
    
    
    public static float cosAng( float[] origin, float[] a, float[] b ) {
        float ax = a[0] - origin[0];
        float ay = a[1] - origin[1];
        float bx = b[0] - origin[0];
        float by = b[1] - origin[1];
        
        float dd = ( ax*ax + ay*ay ) * ( bx*bx + by*by );
        return ( ax*bx + ay*by ) / (float)Math.sqrt( dd );
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
    }
    
    /**
     * Performs smallest possible modification to vector <code>a</code> to make it
     * orthogonal to vector <code>ref</code>.
     * 
     * @param a    Vector to modify.
     * @param ref  Reference vector.
     */
    public static void makeOrthoTo( float[] a, float[] ref ) {
        float lenRef = ref[0] * ref[0] + ref[1] * ref[1];
        if( lenRef < FSQRT_ABS_ERR ) {
            return;
        }
        float parScale = dot( a, ref ) / lenRef;
        a[0] -= ref[0] * parScale;
        a[1] -= ref[1] * parScale;
    }

    /**
     * Performs smallest possible modification to vector <code>a</code> to make it
     * parallel to vector <code>ref</code>.
     * 
     * @param a    Vector to modify.
     * @param ref  Reference vector.
     */
    public static void makeParallelTo( float[] a, float[] ref ) {
        float lenRef = ref[0] * ref[0] + ref[1] * ref[1];
        if( lenRef < FSQRT_ABS_ERR ) {
            return;
        }
        float parScale = dot( a, ref ) / ( lenRef * lenRef );
        a[0] = ref[0] * parScale;
        a[1] = ref[1] * parScale;
    }
    
    /**
     * Returns shortest distance between a line defined and a point.
     * @param n1 A point on the line
     * @param n2 A second point on the line.
     * @param p  Some point
     * @return Distance between <tt>p</tt> and line that passes through <tt>n1</tt> and <tt>n2</tt>.
     */
    public static float linePointDistance( float[] n1, float[] n2, float[] p ) {
        float c = (p[0] - n1[0]) * (p[1] - n2[1]) - (p[0] - n2[0]) * (p[1] - n1[1]);
        return c / dist( n1, n2 );
    }
    
    /**
     * Returns shortest distance between a line segment and a point.
     * 
     * @param s1 Start of line segment
     * @param s2 End of line segment
     * @param p Some point
     * @return smallest distance from point to any point on line segment.
     */
    public static float segmentPointDistance( float[] s1, float[] s2, float[] p ) {
        float u0 =  p[0] - s1[0];
        float u1 =  p[1] - s1[1];
        float v0 = s2[0] - s1[0];
        float v1 = s2[1] - s1[1];
        
        float num = u0 * v0 + u1 * v1;
        float den = v0 * v0 + v1 * v1;
        float t   = num / den;
                
        if( den < FSQRT_ABS_ERR || t <= 0.0 ) {
            //Return distance from n1 to p (which is already stored in <u0,u1>).
        } else if( t >= 1.0 ) {
            //Return distance from n2 to p.
            u0 = p[0] - s2[0];
            u1 = p[1] - s2[1];
        } else {
            //Make u perpendicular to line.
            u0 -= v0 * t;
            u1 -= v1 * t;
        }

        return (float)Math.sqrt( u0 * u0 + u1 * u1 );
    }
    
    /**
     * Finds intersection between two lines.
     * 
     * @param a0  First point on line a
     * @param a1  Second point on line a 
     * @param b0  First point on line b
     * @param b1  Second point on line b
     * @param out On return, holds point of intersection if lines are not parallel.
     * @return true if lines are not parallel and intersection was found.  False if lines are parallel.
     */
    public static boolean lineIntersection( float[] a0, float[] a1, float[] b0, float[] b1, float[] out ) {
        float bx = a1[0] - a0[0];
        float by = a1[1] - a0[1];
        float dx = b1[0] - b0[0];
        float dy = b1[1] - b0[1];
        float dotPerp = bx * dy - by * dx;
        if( dotPerp < FSQRT_ABS_ERR && -dotPerp < FSQRT_ABS_ERR ) {
            return false;
        }
        float cx = b0[0] - a0[0];
        float cy = b0[1] - a0[1];
        float t = (cx * dy - cy * dx) / dotPerp;

        out[0] = a0[0] + t * bx;
        out[1] = a0[1] + t * by;
        return true;
    }
    
    /**
     * Finds intersection between two line segments.
     * 
     * @param a0  First point on segment a
     * @param a1  Second point on segment a 
     * @param b0  First point on segment b
     * @param b1  Second point on segment b
     * @param out On return, holds point of intersection if exists.
     * @return true if lines intersect at single place.
     */
    public static boolean segmentIntersection( float[] a0, float[] a1, float[] b0, float[] b1, float[] out ) {
        float bx = a1[0] - a0[0];
        float by = a1[1] - a0[1];
        float dx = b1[0] - b0[0];
        float dy = b1[1] - b0[1];
        float dotPerp = bx * dy - by * dx;
        if( dotPerp < FSQRT_ABS_ERR && -dotPerp < FSQRT_ABS_ERR ) {
            return false;
        }

        float cx = b0[0] - a0[0];
        float cy = b0[1] - a0[1];
        float t = (cx * dy - cy * dx) / dotPerp;
        if( t < 0 || t > 1 ) {
            return false;
        }

        float u = (cx * by - cy * bx) / dotPerp;
        if( u < 0 || u > 1 ) {
            return false;
        }

        out[0] = a0[0] + t * bx;
        out[1] = a0[1] + t * by;
        return true;
    }

    
    public static boolean isNaN( float[] vec ) {
        return Float.isNaN( vec[0] ) ||
               Float.isNaN( vec[1] );
        
    }
    
    
    public static String format( float[] vec ) {
        return String.format( "[ % 7.4f, % 7.4f ]", vec[0], vec[1] );
    }
    
    
    private Vec2() {}


}
