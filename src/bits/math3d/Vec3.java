package bits.math3d;


import static bits.math3d.Tol.*;


/**
 * Functions for length-3 vectors.
 *
 * @author Philip DeCamp
 */
public final class Vec3 {


    public static void add( float[] a, float[] b, float[] out ) {
        out[0] = a[0] + b[0];
        out[1] = a[1] + b[1];
        out[2] = a[2] + b[2];
    }


    public static void addTo( float[] a, float[] out ) {
        out[0] += a[0];
        out[1] += a[1];
        out[2] += a[2];
    }


    public static void subtract( float[] a, float[] b, float[] out ) {
        out[0] = a[0] - b[0];
        out[1] = a[1] - b[1];
        out[2] = a[2] - b[2];
    }


    public static void subtractFrom( float[] a, float[] out ) {
        out[0] -= a[0];
        out[1] -= a[1];
        out[2] -= a[2];
    }


    public static void multAdd( float sa, float[] a, float sb, float[] b, float[] out ) {
        out[0] = a[0] * sa + b[0] * sb;
        out[1] = a[1] * sa + b[1] * sb;
        out[2] = a[2] * sa + b[2] * sb;
    }


    public static void multAddTo( float sa, float[] a, float sOut, float[] out ) {
        out[0] = sOut * out[0] + sa * a[0];
        out[1] = sOut * out[1] + sa * a[1];
        out[2] = sOut * out[2] + sa * a[2];
    }


    public static void cross( float[] a, int offA, float[] b, int offB, float[] out, int offOut ) {
        out[0+offOut] = a[1+offA] * b[2+offB] - b[1+offB] * a[2+offA];
        out[1+offOut] = a[2+offA] * b[0+offB] - b[2+offB] * a[0+offA];
        out[2+offOut] = a[0+offA] * b[1+offB] - b[0+offB] * a[1+offA];
    }


    public static void cross( float[] a, float[] b, float[] out ) {
        out[0] = a[1] * b[2] - b[1] * a[2];
        out[1] = a[2] * b[0] - b[2] * a[0];
        out[2] = a[0] * b[1] - b[0] * a[1];
    }


    public static void cross( float[] origin, float[] a, float[] b, float[] out ) {
        out[0] = (a[1] - origin[1]) * (b[2] - origin[2]) - (b[1] - origin[1]) * (a[2] - origin[2]);
        out[1] = (a[2] - origin[2]) * (b[0] - origin[0]) - (b[2] - origin[2]) * (a[0] - origin[0]);
        out[2] = (a[0] - origin[0]) * (b[1] - origin[1]) - (b[0] - origin[0]) * (a[1] - origin[1]);
    }


    public static float len( float[] a ) {
        return (float)Math.sqrt( lenSquared( a ) );
    }


    public static float lenSquared( float[] a ) {
        return a[0] * a[0] + a[1] * a[1] + a[2] * a[2];
    }


    public static float dist( float[] a, float[] b ) {
        return (float)Math.sqrt( distSquared( a, b ) );
    }


    public static float distSquared( float[] a, float[] b ) {
        float dx = a[0] - b[0];
        float dy = a[1] - b[1];
        float dz = a[2] - b[2];
        return dx * dx + dy * dy + dz * dz;
    }


    public static void normalize( float[] a ) {
        float s = 1f / len( a );
        a[0] *= s;
        a[1] *= s;
        a[2] *= s;
    }


    public static void normalize( float[] a, float normLength, float[] out ) {
        float d = normLength / len(a);
        out[0] = a[0] * d;
        out[1] = a[1] * d;
        out[2] = a[2] * d;
    }


    public static void mult( float sa, float[] a ) {
        a[0] *= sa;
        a[1] *= sa;
        a[2] *= sa;
    }


    public static void mult( float sa, float[] a, float[] out ) {
        out[0] = sa * a[0];
        out[1] = sa * a[1];
        out[2] = sa * a[2];
    }


    public static float dot( float[] a, float[] b ) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }


    public static float dot( float[] origin, float[] a, float[] b ) {
        return (a[0] - origin[0]) * (b[0] - origin[0]) +
               (a[1] - origin[1]) * (b[1] - origin[1]) +
               (a[2] - origin[2]) * (b[2] - origin[2]);
    }


    public static float cosAng( float[] a, float[] b ) {
        return dot( a, b ) / (float)Math.sqrt( lenSquared( a ) * lenSquared( b ) );
    }


    public static float cosAng( float[] origin, float[] a, float[] b ) {
        float ax = a[0] - origin[0];
        float ay = a[1] - origin[1];
        float az = a[2] - origin[2];
        float bx = b[0] - origin[0];
        float by = b[1] - origin[1];
        float bz = b[2] - origin[2];

        float dd = ( ax*ax + ay*ay + az*az ) * ( bx*bx + by*by + bz*bz );
        return ( ax*bx + ay*by + az*bz ) / (float)Math.sqrt( dd );
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
    }

    /**
     * Performs smallest possible modification to vector <code>a</code> to make it
     * orthogonal to vector <code>ref</code>.
     *
     * @param a    Vector to modify.
     * @param ref  Reference vector.
     */
    public static void makeOrthoTo( float[] a, float[] ref ) {
        float lenRef = ref[0] * ref[0] + ref[1] * ref[1] + ref[2] * ref[2];
        if( lenRef < FSQRT_ABS_ERR ) {
            return;
        }
        float parScale = dot( a, ref ) / lenRef;
        a[0] -= ref[0] * parScale;
        a[1] -= ref[1] * parScale;
        a[2] -= ref[2] * parScale;
    }

    /**
     * Performs smallest possible modification to vector <code>a</code> to make it
     * parallel to vector <code>ref</code>.
     *
     * @param a    Vector to modify.
     * @param ref  Reference vector.
     */
    public static void makeParallelTo( float[] a, float[] ref ) {
        float lenRef = ref[0] * ref[0] + ref[1] * ref[1] + ref[2] * ref[2];
        if( lenRef < FSQRT_ABS_ERR ) {
            return;
        }
        float parScale = dot( a, ref ) / ( lenRef * lenRef );
        a[0] = ref[0] * parScale;
        a[1] = ref[1] * parScale;
        a[2] = ref[2] * parScale;
    }

    /**
     * Makes the smallest angular adjustment possible to a vector such that
     * the resulting vector will lie inside the closed surface of a cone.
     * In other words, after calling <code>clampToCone</code>,
     * <code>ang(vec, coneAxis) <= coneRads</code>.
     *
     * @param vec       Vector to clamp
     * @param coneAxis  Axis of cone
     * @param coneRads  Angle between axis and surface of cone.
     * @param outVec    Vector with smallest possible angular distance from <code>vec</code> that lies inside cone.  May be <code>null</code>.
     * @param outMat    Rotation matrix used to rotate vector onto cone.  May be <code>null</code>
     * @return true if any adjustment made, false if output is identical to input
     */
    public static boolean clampToCone( float[] vec, float[] coneAxis, float coneRads, float[] outVec, float[] outMat ) {
        float cosAng = cosAng( vec, coneAxis );
        float ang    = (float)Math.acos( cosAng );

        if( Double.isNaN( ang ) ) {
            ang = cosAng > 0.0f ? 0.0f : (float)Math.PI;
        }
        if( ang <= coneRads ) {
            if( outVec != null ) {
                outVec[0] = vec[0];
                outVec[1] = vec[1];
                outVec[2] = vec[2];
            }
            if( outMat != null ) {
                Mat4.identity( outMat );
            }
            return false;
        }

        float[] rotAxis = new float[3];
        float[] rot     = outMat == null ? new float[16] : outMat;

        if( ang < Math.PI * (1.0 + FREL_ERR )) {
            Vec3.cross( vec, coneAxis, rotAxis );
        }else{
            Vec3.chooseOrtho( coneAxis[0], coneAxis[1], coneAxis[2], rotAxis );
        }
        Mat4.rotation( ang - coneRads * (1.0f - FREL_ERR), rotAxis[0], rotAxis[1], rotAxis[2], rot );
        if( outVec != null ) {
            Mat4.multVec3( rot, vec, outVec );
        }

        return true;
    }

    /**
     * Finds signed axis-aligned unit-vector nearest to input vector.
     * For example, the nearest axis to [ 0.8, -1.3, 0.1 ] is [ 0.0, -1.0, 0.0 ].
     *
     * @param x X-coord of axis.
     * @param y Y-coord of axis.
     * @param z Z-coord of axis.
     * @param out Length-3 array to hold axis on return.
     */
    public static void nearestAxis( float x, float y, float z, float[] out ) {
        float ax = x >= 0 ? x : -x;
        float ay = y >= 0 ? y : -y;
        float az = z >= 0 ? z : -z;

        if( ax > ay && ax > az ) {
            out[0] = x >= 0 ? 1 : -1;
            out[1] = 0;
            out[2] = 0;
        } else if( ay > az ) {
            out[0] = 0;
            out[1] = y >= 0 ? 1 : -1;
            out[2] = 0;
        } else {
            out[0] = 0;
            out[1] = 0;
            out[2] = z >= 0 ? 1 : -1;
        }
    }

    /**
     * Picks a unit-length vector that is orthogonal to the input vector.
     */
    public static void chooseOrtho( float x, float y, float z, float[] out3x1 ) {
        chooseOrtho( x, y, z, 2, out3x1 );
    }

    /**
     * Picks a unitl-length vector that is orthogonal to the input vector.
     * <p>
     * This method allows the user to define a "zero-dimension", where the
     * vector that is returned by this method is guaranteed to have a zero coordinate
     * for that dimension. Additionally, the coordinate after the zero-dimension will
     * hold a non-negative value.
     * <p>
     * For example, <br/>
     * <code>chooseOrtho( 1.0f, 1.0f, 1.0f, 0, out )</code><br/>
     * will set <br/>
     * <code>out = [  0.0000,  0.7071, -0.7071 ] </code><br/>
     * where <code>out[0]</code> is zero and <code>out[1]</code> is non-negative. <br>
     * <code>chooseOrtho( 1.0f, 1.0f, 1.0f, 2, out )</code><br/>
     * will set <br/>
     * <code>out = [  0.7071, -0.7071,  0.0000 ] </code><br/>
     *
     * @param x       X-coord of vector.
     * @param y       Y-coord of vector.
     * @param z       Z-coord of vector.
     * @param zeroDim Number {0,1,2} indicating if the output x, y, or z axis should be zero.
     * @param out     Length-3 array to hold output axis.
     */
    public static void chooseOrtho( float x, float y, float z, int zeroDim, float[] out ) {
        switch( zeroDim ) {
        case 2:
            if( y > FSQRT_ABS_ERR || -y > FSQRT_ABS_ERR ) {
                out[0] = 1;
                out[1] = -x/y;
                out[2] = 0;
            } else if ( x > FSQRT_ABS_ERR || -x > FSQRT_ABS_ERR ) {
                out[0] = -y/x;
                out[1] = 1;
                out[2] = 0;
            } else {
                out[0] = 1;
                out[1] = 0;
                out[2] = 0;
                // No need to normalize.
                return;
            }
            break;

        case 1:
            if( x > FSQRT_ABS_ERR || -x > FSQRT_ABS_ERR ) {
                out[0] = -z / x;
                out[1] = 0;
                out[2] = 1;
            } else if( z > FSQRT_ABS_ERR || -z > FSQRT_ABS_ERR ) {
                out[0] = 1;
                out[1] = 0;
                out[2] = -x / z;
            } else {
                out[0] = 0;
                out[1] = 0;
                out[2] = 1;
                // No need to normalize.
                return;
            }
            break;

        default:
            if( z > FSQRT_ABS_ERR || -z > FSQRT_ABS_ERR ) {
                out[0] = 0;
                out[1] = 1;
                out[2] = -y / z;
            } else if( y > FSQRT_ABS_ERR || -y > FSQRT_ABS_ERR ) {
                out[0] = 0;
                out[1] = -z / y;
                out[2] = 1;
            } else {
                out[0] = 0;
                out[1] = 1;
                out[2] = 0;
                // No need to normalize.
                return;
            }
            break;
        }

        normalize( out );
    }

    /**
     * Returns shortest distance between a line defined and a point.
     * @param n1 A point on the line
     * @param n2 A second point on the line.
     * @param p  Some point
     * @return Distance between <tt>p</tt> and line that passes through <tt>n1</tt> and <tt>n2</tt>.
     */
    public static float lineToPointDistance( float[] n1, float[] n2, float[] p ) {
        float cx = (p[1] - n1[1]) * (p[2] - n2[2]) - (p[1] - n2[1]) * (p[2] - n1[2]);
        float cy = (p[2] - n1[2]) * (p[0] - n2[0]) - (p[2] - n2[2]) * (p[0] - n1[0]);
        float cz = (p[0] - n1[0]) * (p[1] - n2[1]) - (p[0] - n2[0]) * (p[1] - n1[1]);
        return (float)Math.sqrt( cx * cx + cy * cy + cz * cz ) / dist( n1, n2 );
    }

    /**
     * Returns shortest distance between a line segment and a point.
     *
     * @param n1 Start of line segment
     * @param n2 End of line segment
     * @param p Some point
     * @return smallest distance from point to any point on line segment.
     */
    public static float lineSegmentToPointDistance( float[] n1, float[] n2, float[] p ) {
        float u0 = p[0] - n1[0];
        float u1 = p[1] - n1[1];
        float u2 = p[2] - n1[2];

        float v0 = n2[0] - n1[0];
        float v1 = n2[1] - n1[1];
        float v2 = n2[2] - n1[2];

        float num = u0 * v0 + u1 * v1 + u2 * v2;
        float den = v0 * v0 + v1 * v1 + v2 * v2;
        float t   = num / den;

        if( den < FSQRT_ABS_ERR || t <= 0.0 ) {
            //Return distance from n1 to p (which is already stored in u0,u1,u2).
        } else if( t >= 1.0 ) {
            //Return distance from n2 to p.
            u0 = p[0] - n2[0];
            u1 = p[1] - n2[1];
            u2 = p[2] - n2[2];
        } else {
            //Make u perpendicular to line.
            u0 -= v0 * t;
            u1 -= v1 * t;
            u2 -= v2 * t;
        }

        return (float)Math.sqrt( u0 * u0 + u1 * u1 + u2 * u2 );
    }

    /**
     * Finds intersection of line and plane. The intersection point is
     * only defined if this method returns 1, indicating that the line and
     * plane intersect at one point.
     *
     * @param line0      First point on line.
     * @param line1      Second point on line.
     * @param planePoint A Point on the plane.
     * @param planeNorm  Normal vector of plane.
     * @param optOut     length-3 array to hold point of intersection. May be <code>null</code>
     * @return 0 if no intersection,
     *         1 if point intersection ( line crosses plane )
     *         2 if line intersection ( line lies on plane )
     */
    public static int intersectLinePlane( float[] line0,
                                          float[] line1,
                                          float[] planePoint,
                                          float[] planeNorm,
                                          float[] optOut )
    {
        float dx = line1[0] - line0[0];
        float dy = line1[1] - line0[1];
        float dz = line1[2] - line0[2];

        float num = (planePoint[0] - line0[0]) * planeNorm[0] +
                    (planePoint[1] - line0[1]) * planeNorm[1] +
                    (planePoint[2] - line0[2]) * planeNorm[2];

        float den = dx * planeNorm[0] + dy * planeNorm[1] + dz * planeNorm[2];

        if( den < FSQRT_ABS_ERR && -den < FSQRT_ABS_ERR ) {
            return num < FSQRT_ABS_ERR && -num < FSQRT_ABS_ERR ? 2 : 0;
        }

        if( optOut != null ) {
            float d = num / den;
            optOut[0] = d * dx + line0[0];
            optOut[1] = d * dy + line0[1];
            optOut[2] = d * dz + line0[2];
        }

        return 1;
    }

    /**
     * Finds "intersection", or smallest connecting line, between two 3d lines.
     * (Lines, not line segments).
     *
     * @param a0       First point on line a
     * @param a1       Second point on line a
     * @param b0       First point on line b
     * @param b1       Second point on line b
     * @param optOutA  On return, holds point on line a nearest to line b (optional)
     * @param optOutB  On return, holds point on line b nearest to line a (optional)
     * @return true if lines are not parallel and intersection was found.  False if lines are parallel.
     */
    public static boolean intersectLineLine( float[] a0,
                                             float[] a1,
                                             float[] b0,
                                             float[] b1,
                                             float[] optOutA,
                                             float[] optOutB )
    {
        float da0b0b1b0 = (a0[0] - b0[0]) * (b1[0] - b0[0]) + (a0[1] - b0[1]) * (b1[1] - b0[1]) + (a0[2] - b0[2]) * (b1[2] - b0[2]);
        float db1b0a1a0 = (b1[0] - b0[0]) * (a1[0] - a0[0]) + (b1[1] - b0[1]) * (a1[1] - a0[1]) + (b1[2] - b0[2]) * (a1[2] - a0[2]);
        float da0b0a1a0 = (a0[0] - b0[0]) * (a1[0] - a0[0]) + (a0[1] - b0[1]) * (a1[1] - a0[1]) + (a0[2] - b0[2]) * (a1[2] - a0[2]);
        float db1b0b1b0 = (b1[0] - b0[0]) * (b1[0] - b0[0]) + (b1[1] - b0[1]) * (b1[1] - b0[1]) + (b1[2] - b0[2]) * (b1[2] - b0[2]);
        float da1a0a1a0 = (a1[0] - a0[0]) * (a1[0] - a0[0]) + (a1[1] - a0[1]) * (a1[1] - a0[1]) + (a1[2] - a0[2]) * (a1[2] - a0[2]);

        float num = da0b0b1b0 * db1b0a1a0 - da0b0a1a0 * db1b0b1b0;
        float den = da1a0a1a0 * db1b0b1b0 - db1b0a1a0 * db1b0a1a0;

        if( den < FSQRT_ABS_ERR && -den < FSQRT_ABS_ERR ) {
            return false;
        }

        float mua = num / den;
        if( optOutA != null ) {
            Vec3.multAdd( 1.0f - mua, a0, mua, a1, optOutA );
        }
        if( optOutB != null ) {
            float mub = ( da0b0b1b0 + mua * db1b0a1a0 ) / db1b0b1b0;
            Vec3.multAdd( 1.0f - mub, b0, mub, b1, optOutB );
        }

        return true;
    }


    public static boolean isNaN( float[] vec ) {
        return Float.isNaN( vec[0] ) ||
               Float.isNaN( vec[1] ) ||
               Float.isNaN( vec[2] );

    }


    public static String format( float[] vec ) {
        return String.format( "[ % 7.4f, % 7.4f, % 7.4f ]", vec[0], vec[1], vec[2] );
    }



    public static void add( double[] a, double[] b, double[] out ) {
        out[0] = a[0] + b[0];
        out[1] = a[1] + b[1];
        out[2] = a[2] + b[2];
    }


    public static void addTo( double[] a, double[] out ) {
        out[0] += a[0];
        out[1] += a[1];
        out[2] += a[2];
    }


    public static void subtract( double[] a, double[] b, double[] out ) {
        out[0] = a[0] - b[0];
        out[1] = a[1] - b[1];
        out[2] = a[2] - b[2];
    }


    public static void subtractFrom( double[] a, double[] out ) {
        out[0] -= a[0];
        out[1] -= a[1];
        out[2] -= a[2];
    }


    public static void multAdd( double sa, double[] a, double sb, double[] b, double[] out ) {
        out[0] = a[0] * sa + b[0] * sb;
        out[1] = a[1] * sa + b[1] * sb;
        out[2] = a[2] * sa + b[2] * sb;
    }


    public static void multAddTo( double sa, double[] a, double sOut, double[] out ) {
        out[0] = sOut * out[0] + sa * a[0];
        out[1] = sOut * out[1] + sa * a[1];
        out[2] = sOut * out[2] + sa * a[2];
    }


    public static void cross( double[] a, int offA, double[] b, int offB, double[] out, int offOut ) {
        out[0+offOut] = a[1+offA] * b[2+offB] - b[1+offB] * a[2+offA];
        out[1+offOut] = a[2+offA] * b[0+offB] - b[2+offB] * a[0+offA];
        out[2+offOut] = a[0+offA] * b[1+offB] - b[0+offB] * a[1+offA];
    }


    public static void cross( double[] a, double[] b, double[] out ) {
        out[0] = a[1] * b[2] - b[1] * a[2];
        out[1] = a[2] * b[0] - b[2] * a[0];
        out[2] = a[0] * b[1] - b[0] * a[1];
    }


    public static void cross( double[] origin, double[] a, double[] b, double[] out ) {
        out[0] = (a[1] - origin[1]) * (b[2] - origin[2]) - (b[1] - origin[1]) * (a[2] - origin[2]);
        out[1] = (a[2] - origin[2]) * (b[0] - origin[0]) - (b[2] - origin[2]) * (a[0] - origin[0]);
        out[2] = (a[0] - origin[0]) * (b[1] - origin[1]) - (b[0] - origin[0]) * (a[1] - origin[1]);
    }


    public static double len( double[] a ) {
        return Math.sqrt( lenSquared( a ) );
    }


    public static double lenSquared( double[] a ) {
        return a[0] * a[0] + a[1] * a[1] + a[2] * a[2];
    }


    public static double dist( double[] a, double[] b ) {
        return Math.sqrt( distSquared( a, b ) );
    }


    public static double distSquared( double[] a, double[] b ) {
        double dx = a[0] - b[0];
        double dy = a[1] - b[1];
        double dz = a[2] - b[2];
        return dx * dx + dy * dy + dz * dz;
    }


    public static void normalize( double[] a ) {
        double s = 1 / len( a );
        a[0] *= s;
        a[1] *= s;
        a[2] *= s;
    }


    public static void normalize( double[] a, double normLength, double[] out ) {
        double d = normLength / len(a);
        out[0] = a[0] * d;
        out[1] = a[1] * d;
        out[2] = a[2] * d;
    }


    public static void mult( double sa, double[] a ) {
        a[0] *= sa;
        a[1] *= sa;
        a[2] *= sa;
    }


    public static void mult( double sa, double[] a, double[] out ) {
        out[0] = sa * a[0];
        out[1] = sa * a[1];
        out[2] = sa * a[2];
    }


    public static double dot( double[] a, double[] b ) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }


    public static double dot( double[] origin, double[] a, double[] b ) {
        return (a[0] - origin[0]) * (b[0] - origin[0]) +
               (a[1] - origin[1]) * (b[1] - origin[1]) +
               (a[2] - origin[2]) * (b[2] - origin[2]);
    }


    public static double cosAng( double[] a, double[] b ) {
        return dot( a, b ) / Math.sqrt( lenSquared( a ) * lenSquared( b ) );
    }


    public static double cosAng( double[] origin, double[] a, double[] b ) {
        double ax = a[0] - origin[0];
        double ay = a[1] - origin[1];
        double az = a[2] - origin[2];
        double bx = b[0] - origin[0];
        double by = b[1] - origin[1];
        double bz = b[2] - origin[2];

        double dd = ( ax*ax + ay*ay + az*az ) * ( bx*bx + by*by + bz*bz );
        return ( ax*bx + ay*by + az*bz ) / Math.sqrt( dd );
    }


    public static double ang( double[] a, double[] b ) {
        return Math.acos( cosAng( a, b ) );
    }


    public static double ang( double[] origin, double[] a, double[] b ) {
        return Math.acos( cosAng( origin, a, b ) );
    }


    public static void lerp( double[] a, double[] b, double p, double[] out ) {
        double q = 1.0 - p;
        out[0] = q * a[0] + p * b[0];
        out[1] = q * a[1] + p * b[1];
        out[2] = q * a[2] + p * b[2];
    }

    /**
     * Performs smallest possible modification to vector <code>a</code> to make it
     * orthogonal to vector <code>ref</code>.
     *
     * @param a    Vector to modify.
     * @param ref  Reference vector.
     */
    public static void makeOrthoTo( double[] a, double[] ref ) {
        double lenRef = ref[0] * ref[0] + ref[1] * ref[1] + ref[2] * ref[2];
        if( lenRef < SQRT_ABS_ERR ) {
            return;
        }
        double parScale = dot( a, ref ) / lenRef;
        a[0] -= ref[0] * parScale;
        a[1] -= ref[1] * parScale;
        a[2] -= ref[2] * parScale;
    }

    /**
     * Performs smallest possible modification to vector <code>a</code> to make it
     * parallel to vector <code>ref</code>.
     *
     * @param a    Vector to modify.
     * @param ref  Reference vector.
     */
    public static void makeParallelTo( double[] a, double[] ref ) {
        double lenRef = ref[0] * ref[0] + ref[1] * ref[1] + ref[2] * ref[2];
        if( lenRef < SQRT_ABS_ERR ) {
            return;
        }
        double parScale = dot( a, ref ) / ( lenRef * lenRef );
        a[0] = ref[0] * parScale;
        a[1] = ref[1] * parScale;
        a[2] = ref[2] * parScale;
    }

    /**
     * Makes the smallest angular adjustment possible to a vector such that
     * the resulting vector will lie inside the closed surface of a cone.
     * In other words, after calling <code>clampToCone</code>,
     * <code>ang(vec, coneAxis) <= coneRads</code>.
     *
     * @param vec       Vector to clamp
     * @param coneAxis  Axis of cone
     * @param coneRads  Angle between axis and surface of cone.
     * @param outVec    Vector with smallest possible angular distance from <code>vec</code> that lies inside cone.  May be <code>null</code>.
     * @param outMat    Rotation matrix used to rotate vector onto cone.  May be <code>null</code>
     * @return true if any adjustment made, false if output is identical to input
     */
    public static boolean clampToCone( double[] vec, double[] coneAxis, double coneRads, double[] outVec, double[] outMat ) {
        double cosAng = cosAng( vec, coneAxis );
        double ang    = Math.acos( cosAng );

        if( Double.isNaN( ang ) ) {
            ang = cosAng > 0.0 ? 0.0 : Math.PI;
        }
        if( ang <= coneRads ) {
            if( outVec != null ) {
                outVec[0] = vec[0];
                outVec[1] = vec[1];
                outVec[2] = vec[2];
            }
            if( outMat != null ) {
                Mat4.identity( outMat );
            }
            return false;
        }

        double[] rotAxis = new double[3];
        double[] rot     = outMat == null ? new double[16] : outMat;

        if( ang < Math.PI * (1.0 + REL_ERR )) {
            cross( vec, coneAxis, rotAxis );
        }else{
            chooseOrtho( coneAxis[0], coneAxis[1], coneAxis[2], rotAxis );
        }

        Mat4.rotation( ang - coneRads * (1.0 - REL_ERR), rotAxis[0], rotAxis[1], rotAxis[2], rot );
        if( outVec != null ) {
            Mat4.multVec3( rot, vec, outVec );
        }

        return true;
    }

    /**
     * Finds signed axis-aligned unit-vector nearest to input vector.
     * For example, the nearest axis to [ 0.8, -1.3, 0.1 ] is [ 0.0, -1.0, 0.0 ].
     */
    public static void nearestAxis( double x, double y, double z, double[] out ) {
        double ax = x >= 0 ? x : -x;
        double ay = y >= 0 ? y : -y;
        double az = z >= 0 ? z : -z;

        if( ax > ay && ax > az ) {
            out[0] = x >= 0 ? 1 : -1;
            out[1] = 0;
            out[2] = 0;
        } else if( ay > az ) {
            out[0] = 0;
            out[1] = y >= 0 ? 1 : -1;
            out[2] = 0;
        } else {
            out[0] = 0;
            out[1] = 0;
            out[2] = z >= 0 ? 1 : -1;
        }
    }

    /**
     * Picks a unit-length vector that is orthogonal to the input vector.
     */
    public static void chooseOrtho( double x, double y, double z, double[] out3x1 ) {
        chooseOrtho( x, y, z, 2, out3x1 );
    }

    /**
     * Picks a unitl-length vector that is orthogonal to the input vector.
     * <p>
     * This method allows the user to define a "zero-dimension", where the
     * vector that is returned by this method is guaranteed to have a zero coordinate
     * for that dimension. Additionally, the coordinate after the zero-dimension will
     * hold a non-negative value.
     * <p>
     * For example, <br/>
     * <code>chooseOrtho( 1.0, 1.0, 1.0, 0, out )</code><br/>
     * will set <br/>
     * <code>out = [  0.0000,  0.7071, -0.7071 ] </code><br/>
     * where <code>out[0]</code> is zero and <code>out[1]</code> is non-negative. <br>
     * <code>chooseOrtho( 1.0, 1.0, 1.0, 2, out )</code><br/>
     * will set <br/>
     * <code>out = [  0.7071, -0.7071,  0.0000 ] </code><br/>
     *
     * @param x       X-coord of vector.
     * @param y       Y-coord of vector.
     * @param z       Z-coord of vector.
     * @param zeroDim Number {0,1,2} indicating if the output x, y, or z axis should be zero.
     * @param out     Length-3 array to hold output axis.
     */
    public static void chooseOrtho( double x, double y, double z, int zeroDim, double[] out ) {
        switch( zeroDim ) {
        case 2:
            if( y > SQRT_ABS_ERR || -y > SQRT_ABS_ERR ) {
                out[0] = 1;
                out[1] = -x/y;
                out[2] = 0;
            } else if ( x > SQRT_ABS_ERR || -x > SQRT_ABS_ERR ) {
                out[0] = -y/x;
                out[1] = 1;
                out[2] = 0;
            } else {
                out[0] = 1;
                out[1] = 0;
                out[2] = 0;
                // No need to normalize.
                return;
            }
            break;

        case 1:
            if( x > SQRT_ABS_ERR || -x > SQRT_ABS_ERR ) {
                out[0] = -z / x;
                out[1] = 0;
                out[2] = 1;
            } else if( z > SQRT_ABS_ERR || -z > SQRT_ABS_ERR ) {
                out[0] = 1;
                out[1] = 0;
                out[2] = -x / z;
            } else {
                out[0] = 0;
                out[1] = 0;
                out[2] = 1;
                // No need to normalize.
                return;
            }
            break;

        default:
            if( z > SQRT_ABS_ERR || -z > SQRT_ABS_ERR ) {
                out[0] = 0;
                out[1] = 1;
                out[2] = -y / z;
            } else if( y > SQRT_ABS_ERR || -y > SQRT_ABS_ERR ) {
                out[0] = 0;
                out[1] = -z / y;
                out[2] = 1;
            } else {
                out[0] = 0;
                out[1] = 1;
                out[2] = 0;
                // No need to normalize.
                return;
            }
            break;
        }

        normalize( out );
    }

    /**
     * Returns shortest distance between a line defined and a point.
     * @param n1 A point on the line
     * @param n2 A second point on the line.
     * @param p  Some point
     * @return Distance between <tt>p</tt> and line that passes through <tt>n1</tt> and <tt>n2</tt>.
     */
    public static double lineToPointDistance( double[] n1, double[] n2, double[] p ) {
        double cx = (p[1] - n1[1]) * (p[2] - n2[2]) - (p[1] - n2[1]) * (p[2] - n1[2]);
        double cy = (p[2] - n1[2]) * (p[0] - n2[0]) - (p[2] - n2[2]) * (p[0] - n1[0]);
        double cz = (p[0] - n1[0]) * (p[1] - n2[1]) - (p[0] - n2[0]) * (p[1] - n1[1]);
        return Math.sqrt( cx * cx + cy * cy + cz * cz ) / dist( n1, n2 );
    }

    /**
     * Returns shortest distance between a line segment and a point.
     *
     * @param n1 Start of line segment
     * @param n2 End of line segment
     * @param p Some point
     * @return smallest distance from point to any point on line segment.
     */
    public static double lineSegmentToPointDistance( double[] n1, double[] n2, double[] p ) {
        double u0 = p[0] - n1[0];
        double u1 = p[1] - n1[1];
        double u2 = p[2] - n1[2];

        double v0 = n2[0] - n1[0];
        double v1 = n2[1] - n1[1];
        double v2 = n2[2] - n1[2];

        double num = u0 * v0 + u1 * v1 + u2 * v2;
        double den = v0 * v0 + v1 * v1 + v2 * v2;
        double t   = num / den;

        if( den < SQRT_ABS_ERR || t <= 0.0 ) {
            //Return distance from n1 to p (which is already stored in u0,u1,u2).
        } else if( t >= 1.0 ) {
            //Return distance from n2 to p.
            u0 = p[0] - n2[0];
            u1 = p[1] - n2[1];
            u2 = p[2] - n2[2];
        } else {
            //Make u perpendicular to line.
            u0 -= v0 * t;
            u1 -= v1 * t;
            u2 -= v2 * t;
        }

        return Math.sqrt( u0 * u0 + u1 * u1 + u2 * u2 );
    }

    /**
     * Finds intersection of line and plane. The intersection point is
     * only defined if this method returns 1, indicating that the line and
     * plane intersect at one point.
     *
     * @param line0      First point on line.
     * @param line1      Second point on line.
     * @param planePoint A Point on the plane.
     * @param planeNorm  Normal vector of plane.
     * @param optOut     length-3 array to hold point of intersection. May be <code>null</code>
     * @return 0 if no intersection,
     *         1 if point intersection ( line crosses plane )
     *         2 if line intersection ( line lies on plane )
     */
    public static int intersectLinePlane( double[] line0,
                                          double[] line1,
                                          double[] planePoint,
                                          double[] planeNorm,
                                          double[] optOut )
    {
        double dx = line1[0] - line0[0];
        double dy = line1[1] - line0[1];
        double dz = line1[2] - line0[2];

        double num = (planePoint[0] - line0[0]) * planeNorm[0] +
                     (planePoint[1] - line0[1]) * planeNorm[1] +
                     (planePoint[2] - line0[2]) * planeNorm[2];

        double den = dx * planeNorm[0] + dy * planeNorm[1] + dz * planeNorm[2];

        if( den < SQRT_ABS_ERR && -den < SQRT_ABS_ERR ) {
            return num < SQRT_ABS_ERR && -num < SQRT_ABS_ERR ? 2 : 0;
        }

        if( optOut != null ) {
            double d = num / den;
            optOut[0] = d * dx + line0[0];
            optOut[1] = d * dy + line0[1];
            optOut[2] = d * dz + line0[2];
        }

        return 1;
    }

    /**
     * Finds "intersection", or smallest connecting line, between two 3d lines.
     * (Lines, not line segments).
     *
     * @param a0       First point on line a
     * @param a1       Second point on line a
     * @param b0       First point on line b
     * @param b1       Second point on line b
     * @param optOutA  On return, holds point on line a nearest to line b (optional)
     * @param optOutB  On return, holds point on line b nearest to line a (optional)
     * @return true if lines are not parallel and intersection was found.  False if lines are parallel.
     */
    public static boolean intersectLineLine( double[] a0,
                                             double[] a1,
                                             double[] b0,
                                             double[] b1,
                                             double[] optOutA,
                                             double[] optOutB )
    {
        double da0b0b1b0 = (a0[0] - b0[0]) * (b1[0] - b0[0]) + (a0[1] - b0[1]) * (b1[1] - b0[1]) + (a0[2] - b0[2]) * (b1[2] - b0[2]);
        double db1b0a1a0 = (b1[0] - b0[0]) * (a1[0] - a0[0]) + (b1[1] - b0[1]) * (a1[1] - a0[1]) + (b1[2] - b0[2]) * (a1[2] - a0[2]);
        double da0b0a1a0 = (a0[0] - b0[0]) * (a1[0] - a0[0]) + (a0[1] - b0[1]) * (a1[1] - a0[1]) + (a0[2] - b0[2]) * (a1[2] - a0[2]);
        double db1b0b1b0 = (b1[0] - b0[0]) * (b1[0] - b0[0]) + (b1[1] - b0[1]) * (b1[1] - b0[1]) + (b1[2] - b0[2]) * (b1[2] - b0[2]);
        double da1a0a1a0 = (a1[0] - a0[0]) * (a1[0] - a0[0]) + (a1[1] - a0[1]) * (a1[1] - a0[1]) + (a1[2] - a0[2]) * (a1[2] - a0[2]);

        double num = da0b0b1b0 * db1b0a1a0 - da0b0a1a0 * db1b0b1b0;
        double den = da1a0a1a0 * db1b0b1b0 - db1b0a1a0 * db1b0a1a0;

        if( den < SQRT_ABS_ERR && -den < SQRT_ABS_ERR ) {
            return false;
        }

        double mua = num / den;
        if( optOutA != null ) {
            multAdd( 1.0 - mua, a0, mua, a1, optOutA );
        }
        if( optOutB != null ) {
            double mub = ( da0b0b1b0 + mua * db1b0a1a0 ) / db1b0b1b0;
            multAdd( 1.0 - mub, b0, mub, b1, optOutB );
        }

        return true;
    }


    public static boolean isNaN( double[] vec ) {
        return Double.isNaN( vec[0] ) ||
               Double.isNaN( vec[1] ) ||
               Double.isNaN( vec[2] );

    }


    public static String format( double[] vec ) {
        return String.format( "[ % 7.4f, % 7.4f, % 7.4f ]", vec[0], vec[1], vec[2] );
    }





    private Vec3() {}



    /**
     * @deprecated Use Arr.lerp( float[], int, float[], int, int, float, float[], int
     */
    @Deprecated public static void lerp( float[] a, int offA, float[] b, int offB, int len, float p, float[] out, int offOut ) {
        Arr.lerp( a, offA, b, offB, len, p, out, offOut );
    }

    /**
     * @deprecated Use makeOrthoTo( float[], flaot[] )
     */
    @Deprecated public static void makePerpendicularTo( float[] vec, float[] reference ) {
        float lenRef = reference[0] * reference[0] + reference[1] * reference[1] + reference[2] * reference[2];
        if( lenRef < FSQRT_ABS_ERR ) {
            return;
        }
        float parScale = dot( vec, reference ) / lenRef;
        vec[0] -= reference[0] * parScale;
        vec[1] -= reference[1] * parScale;
        vec[2] -= reference[2] * parScale;
    }

    /**
     * @deprecated Use multAdd( float, float[], float, float[], float[] )
     */
    @Deprecated public static void add( float[] a, float scaleA, float[] b, float scaleB, float[] out ) {
        out[0] = a[0] * scaleA + b[0] * scaleB;
        out[1] = a[1] * scaleA + b[1] * scaleB;
        out[2] = a[2] * scaleA + b[2] * scaleB;
    }

    /**
     * @deprecated Use addTo( float[], float[] )
     */
    @Deprecated public static void addInto( float[] vec, float[] out ) {
        out[0] += vec[0];
        out[1] += vec[1];
        out[2] += vec[2];
    }

    /**
     * @deprecated Use intersectLinePlane
     */
    @Deprecated public static int intersectLineWithPlane( float[] line0,
                                              float[] line1,
                                              float[] planePoint,
                                              float[] planeNorm,
                                              float[] optOut )
    {
        return intersectLinePlane( line0, line1, planePoint, planeNorm, optOut );
    }

    /**
     * @deprecated Use intersectLineLine()
     */
    @Deprecated public static boolean lineLineIntersection( float[] a0,
                                                float[] a1,
                                                float[] b0,
                                                float[] b1,
                                                float[] optOutA,
                                                float[] optOutB )
    {
        return intersectLineLine( a0, a1, b0, b1, optOutA, optOutB );
    }

    /**
     * @depracated
     */
    @Deprecated public static float length( float dx, float dy, float dz ) {
        return (float)Math.sqrt( dx * dx + dy * dy + dz * dz );
    }

    /**
     * @depracated Use len( float[] )
     */
    @Deprecated public static float length( float[] vec ) {
        return (float)Math.sqrt( vec[0] * vec[0] + vec[1] * vec[1] + vec[2] * vec[2] );
    }

    /**
     * @depracated Use arr.len( float[], int, int )
     */
    @Deprecated public static float length( float[] vec, int off ) {
        return (float)Math.sqrt( vec[  off] * vec[  off] +
                                 vec[1+off] * vec[1+off] +
                                 vec[2+off] * vec[2+off] );
    }

    /**
     * @deprecated Use mult( float, float[] )
     */
    @Deprecated public static void scale( float[] a, float scale ) {
        a[0] *= scale;
        a[1] *= scale;
        a[2] *= scale;
    }

    /**
     * @deprecated Use mult( float, float[], float[] )
     */
    @Deprecated public static void scale( float[] vec, float scale, float[] out ) {
        out[0] = vec[0] * scale;
        out[1] = vec[1] * scale;
        out[2] = vec[2] * scale;
    }

    /**
     * @deprecated Use Arr.normalize
     */
    @Deprecated public static void normalize( float[] arr, int off, float normalLength ) {
        float d = normalLength / Arr.len( arr, off, 3 );
        arr[  off] *= d;
        arr[1+off] *= d;
        arr[2+off] *= d;
    }

    /**
     * @deprecated Use normalize( float[], float, float[] )
     */
    @Deprecated public static void normalize( float[] vec, float normLength ) {
        float d = normLength / len(vec);
        vec[0] *= d;
        vec[1] *= d;
        vec[2] *= d;
    }


    /**
     * @deprecated Use isNaN( float[] )
     */
    @Deprecated public static boolean isValid( float[] vec ) {
        return !Float.isNaN( vec[0] ) &&
               !Float.isNaN( vec[1] ) &&
               !Float.isNaN( vec[2] );
    }

}
