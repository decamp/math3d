package cogmac.math3d;


import static cogmac.math3d.Tol.*;


/** 
 * Functions for length-3 vectors.
 * 
 * @author Philip DeCamp  
 */
public final class Vec3 {
    
    public static void add( float[] a, float scaleA, float[] b, float scaleB, float[] out ) {
        out[0] = a[0] * scaleA + b[0] * scaleB;
        out[1] = a[1] * scaleA + b[1] * scaleB;
        out[2] = a[2] * scaleA + b[2] * scaleB;
    }
    
    
    public static void add( float[] a, float[] b, float[] out ) {
        out[0] = a[0] + b[0];
        out[1] = a[1] + b[1];
        out[2] = a[2] + b[2];
    }   
    
    
    public static void addInto( float[] vec, float vecScale, float[] out ) {
        out[0] += vec[0] * vecScale;
        out[1] += vec[1] * vecScale;
        out[2] += vec[2] * vecScale;
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
    
    
    public static float length( float dx, float dy, float dz ) {
        return (float)Math.sqrt( dx * dx + dy * dy + dz * dz );
    }
    
    
    public static float length( float[] vec ) {
        return (float)Math.sqrt( vec[0] * vec[0] + vec[1] * vec[1] + vec[2] * vec[2] );
    }
    
    
    public static float length( float[] vec, int off ) {
        return (float)Math.sqrt( vec[  off] * vec[  off] + 
                                 vec[1+off] * vec[1+off] + 
                                 vec[2+off] * vec[2+off] );
    }
    
    
    public static float dist( float[] point0, float[] point1 ) {
        float dx = point0[0] - point1[0];
        float dy = point0[1] - point1[1];
        float dz = point0[2] - point1[2];
        return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    
    public static void normalize( float[] vec, float normalLength ) {
        float d = normalLength / length(vec);        
        vec[0] *= d;
        vec[1] *= d;
        vec[2] *= d;
    }

    
    public static void normalize( float[] vec, float normalLength, float[] out ) {
        float d = normalLength / length(vec);
        out[0] = vec[0] * d;
        out[1] = vec[1] * d;
        out[2] = vec[2] * d;
    }

    
    public static void normalize( float[] arr, int off, float normalLength ) {
        float d = normalLength / length( arr, off );
        arr[  off] *= d;
        arr[1+off] *= d;
        arr[2+off] *= d;
    }
    
    
    public static void scale( float[] vec, float scale ) {
        vec[0] *= scale;
        vec[1] *= scale;
        vec[2] *= scale;
    }
    
    
    public static void scale( float[] vec, float scale, float[] out ) {
        out[0] = vec[0] * scale;
        out[1] = vec[1] * scale;
        out[2] = vec[2] * scale;
    }
    

    public static float distSquared( float[] point0, float[] point1 ) {
        float dx = point0[0] - point1[0];
        float dy = point0[1] - point1[1];
        float dz = point0[2] - point1[2];
        return dx * dx + dy * dy + dz * dz;
    }
    
    
    public static float dot( float[] vec0, float[] vec1 ) {
        return vec0[0] * vec1[0] + vec0[1] * vec1[1] + vec0[2] * vec1[2];
    }
    
    
    public static float dot( float[] origin, float[] a, float[] b ) {
        return (a[0] - origin[0]) * (b[0] - origin[0]) +
               (a[1] - origin[1]) * (b[1] - origin[1]) +
               (a[2] - origin[2]) * (b[2] - origin[2]);
    }
    
   
    public static float cosAng( float[] vec0, float[] vec1 ) {
        return dot( vec0, vec1 ) / ( length( vec0 ) * length( vec1 ) );
    }
    
    
    public static float cosAng( float[] origin, float[] vec0, float[] vec1 ) {
        return dot(origin, vec0, vec1) / (dist(origin, vec0) * dist(origin, vec1));
    }

    
    public static float ang( float[] vec0, float[] vec1 ) {
        return (float)Math.acos( cosAng( vec0, vec1 ) );
    }
    
    
    public static float ang( float[] origin, float[] vec0, float[] vec1 ) {
        return (float)Math.acos( cosAng( origin, vec0, vec1 ) );
    }
    
    
    public static void lerp( float[] a, float[] b, float p, float[] out ) {
        float q = 1.0f - p;
        out[0] = q * a[0] + p * b[0];
        out[1] = q * a[1] + p * b[1];
        out[2] = q * a[2] + p * b[2];
    }
    
    /**
     * Performs smallest possible modification to <code>vec</code> to make it
     * orthogonal to some <code>reference</code> vector.
     * 
     * @param vec           Vector to modify.
     * @param reference     Reference vector.
     */
    public static void makeOrthoTo( float[] vec, float[] reference ) {
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
     * Performs smallest possible modification to <code>vec</code> to make it
     * parallel to some <code>reference</code> vector.
     * 
     * @param vec           Vector to modify.
     * @param reference     Reference vector.
     */
    public static void makeParallelTo( float[] vec, float[] reference ) {
        float lenRef = reference[0] * reference[0] + reference[1] * reference[1] + reference[2] * reference[2];
        if( lenRef < FSQRT_ABS_ERR ) {
            return;
        }
        float parScale = dot(vec, reference) / lenRef / lenRef;
        vec[0] = reference[0] * parScale;
        vec[1] = reference[1] * parScale;
        vec[2] = reference[2] * parScale;
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
     * @param x
     * @param y
     * @param z
     * @param out
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
     * @param x
     * @param y
     * @param z
     * @param zeroDim
     * @param out
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
        
        normalize( out, 1f );
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
        return length( cx, cy, cz ) / dist( n1, n2 );
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
    public static int intersectLineWithPlane( float[] line0, 
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
        
        if( Math.abs( den ) < FSQRT_ABS_ERR  ) {
            return Math.abs(num) < FSQRT_ABS_ERR ? 2 : 0;
        }
        
        if( optOut == null ) {
            return 1;
        }
        
        float d = num / den;
        optOut[0] = d * dx + line0[0];
        optOut[1] = d * dy + line0[1];
        optOut[2] = d * dz + line0[2];
        
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
    public static boolean lineLineIntersection( float[] a0, 
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
        
        if( Math.abs( den ) < FSQRT_ABS_ERR ) {
            return false;
        }
        float mua = num / den;
        if( optOutA != null ) {
            Vec3.add( a0, 1.0f - mua, a1, mua, optOutA );
        }
        if( optOutB != null ) {
            float mub = ( da0b0b1b0 + mua * db1b0a1a0 ) / db1b0b1b0;
            Vec3.add( b0, 1.0f - mub, b1, mub, optOutB );
        }
        
        return true;
    }
    
    
    
    public static boolean isValid( float[] vec ) {
        return !Float.isNaN( vec[0] ) &&
               !Float.isNaN( vec[1] ) &&
               !Float.isNaN( vec[2] );
    }

    
    public static String format( float[] vec ) {
        return String.format( "[ % 7.4f, % 7.4f, % 7.4f ]", vec[0], vec[1], vec[2] );
    }
    
    
    
    
    private Vec3() {}


    
    public static void main( String[] args ) {
        float[] f = { 1f, 1f, 1f };
        float[] out = new float[3];
        chooseOrtho( f[0], f[1], f[2], 0, out );
        System.out.println( format( out ) );
        System.out.println( dot( f, out ) );
        chooseOrtho( f[0], f[1], f[2], 2, out );
        System.out.println( format( out ) );
        System.out.println( dot( f, out ) );
    }
    
    
    @Deprecated
    public static void lerp( float[] a, int offA, float[] b, int offB, int len, float p, float[] out, int offOut ) {
        float q = 1.0f - p;
        
        for(int i = 0; i < len; i++) {
            out[i + offOut] = q * a[i + offA] + p * b[i + offB];
        }
    }

    
    /**
     * Performs smallest possible modification to <code>vec</code> to make it
     * orthogonal to some <code>reference</code> vector.
     * 
     * @param vec           Vector to modify.
     * @param reference     Reference vector.
     * @deprecated Use makeOrthoTo(), which has a shorter name.
     */
    public static void makePerpendicularTo( float[] vec, float[] reference ) {
        float lenRef = reference[0] * reference[0] + reference[1] * reference[1] + reference[2] * reference[2];
        if( lenRef < FSQRT_ABS_ERR ) {
            return;
        }
        float parScale = dot( vec, reference ) / lenRef;
        vec[0] -= reference[0] * parScale;
        vec[1] -= reference[1] * parScale;
        vec[2] -= reference[2] * parScale;
    }
    
}
