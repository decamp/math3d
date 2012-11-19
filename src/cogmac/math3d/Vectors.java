package cogmac.math3d;


import static cogmac.math3d.Tolerance.*;


/** 
 * Functions for length-3 vectors.
 * 
 * @author Philip DeCamp  
 */
public final class Vectors {
    
    public static void add( double[] a, double scaleA, double[] b, double scaleB, double[] out ) {
        out[0] = a[0] * scaleA + b[0] * scaleB;
        out[1] = a[1] * scaleA + b[1] * scaleB;
        out[2] = a[2] * scaleA + b[2] * scaleB;
    }
    
    
    public static void addInto( double[] vec, double vecScale, double[] out ) {
        out[0] += vec[0] * vecScale;
        out[1] += vec[1] * vecScale;
        out[2] += vec[2] * vecScale;
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
    
    
    public static double hypot( double dx, double dy, double dz ) {
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    
    public static double hypot( double[] vec ) {
        return Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1] + vec[2] * vec[2]);
    }
    
    
    public static double hypot( double[] vec, int off ) {
        return Math.sqrt( vec[  off] * vec[  off] + 
                          vec[1+off] * vec[1+off] + 
                          vec[2+off] * vec[2+off] );
    }
    
    
    public static double dist( double[] point0, double[] point1 ) {
        double dx = point0[0] - point1[0];
        double dy = point0[1] - point1[1];
        double dz = point0[2] - point1[2];
        
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    
    public static void normalize( double[] vec, double normalLength ) {
        double d = normalLength / hypot(vec);        
        vec[0] *= d;
        vec[1] *= d;
        vec[2] *= d;
    }

    
    public static void normalize( double[] vec, double normalLength, double[] out ) {
        double d = normalLength / hypot(vec);
        out[0] = vec[0] * d;
        out[1] = vec[1] * d;
        out[2] = vec[2] * d;
    }

    
    public static void normalize( double[] arr, int off, double normalLength ) {
        double d = normalLength / hypot( arr, off );
        arr[  off] *= d;
        arr[1+off] *= d;
        arr[2+off] *= d;
    }
    
    
    public static void scale( double[] vec, double scale ) {
        vec[0] *= scale;
        vec[1] *= scale;
        vec[2] *= scale;
    }
    
    
    public static void scale( double[] vec, double scale, double[] out ) {
        out[0] = vec[0] * scale;
        out[1] = vec[1] * scale;
        out[2] = vec[2] * scale;
    }
    

    public static double distSquared( double[] point0, double[] point1 ) {
        double dx = point0[0] - point1[0];
        double dy = point0[1] - point1[1];
        double dz = point0[2] - point1[2];
        return dx * dx + dy * dy + dz * dz;
    }
    
    
    public static double dot( double[] vec0, double[] vec1 ) {
        return vec0[0] * vec1[0] + vec0[1] * vec1[1] + vec0[2] * vec1[2];
    }
    
    
    public static double dot( double[] origin, double[] a, double[] b ) {
        return (a[0] - origin[0]) * (b[0] - origin[0]) +
               (a[1] - origin[1]) * (b[1] - origin[1]) +
               (a[2] - origin[2]) * (b[2] - origin[2]);
    }
    
   
    public static double cosAng( double[] vec0, double[] vec1 ) {
        return dot(vec0, vec1) / (hypot(vec0) * hypot(vec1));
    }
    
    
    public static double cosAng( double[] origin, double[] vec0, double[] vec1 ) {
        return dot(origin, vec0, vec1) / (dist(origin, vec0) * dist(origin, vec1));
    }

    
    public static double ang( double[] vec0, double[] vec1 ) {
        return Math.acos(cosAng(vec0, vec1));
    }
    
    
    public static double ang( double[] origin, double[] vec0, double[] vec1 ) {
        return Math.acos(cosAng(origin, vec0, vec1));
    }
    
    
    public static void lerp( double[] a, double[] b, double p, double[] out ) {
        double q = 1.0 - p;
        out[0] = q * a[0] + p * b[0];
        out[1] = q * a[1] + p * b[1];
        out[2] = q * a[2] + p * b[2];
    }
    
    
    public static void lerp( double[] a, int offA, double[] b, int offB, int len, double p, double[] out, int offOut ) {
        double q = 1.0 - p;
        
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
     */
    public static void makePerpendicularTo( double[] vec, double[] reference ) {
        double lenRef = hypot(reference);
        if( lenRef < ABS_ERR )
            return;
        
        double parScale = dot(vec, reference) / lenRef / lenRef;
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
    public static void makeParallelTo( double[] vec, double[] reference ) {
        double lenRef = hypot(reference);
        if(lenRef < ABS_ERR)
            return;
        
        double parScale = dot(vec, reference) / lenRef / lenRef;
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
    public static boolean clampToCone( double[] vec, double[] coneAxis, double coneRads, double[] outVec, double[] outMat ) {
        
        double d1     = Vectors.hypot(vec);
        double d2     = Vectors.hypot(coneAxis);
        double cosAng = cosAng(vec, coneAxis);
        double ang    = Math.acos(cosAng);
        
        if(Double.isNaN(ang)) {
            ang = cosAng > 0.0 ? 0 : Math.PI;
        }
        
        if(ang <= coneRads) {
            if(outVec != null) {
                outVec[0] = vec[0];
                outVec[1] = vec[1];
                outVec[2] = vec[2];
            }
            
            if(outMat != null) {
                Matrices.setToIdentity(outMat);
            }
            
            return false;
        }
        
        double[] rotAxis = new double[3];
        double[] rot     = outMat == null ? new double[16] : outMat;
        
        if( ang < Math.PI * (1.0 + REL_ERR )) {
            Vectors.cross(vec, coneAxis, rotAxis);
        }else{
            Vectors.chooseOrtho(coneAxis[0], coneAxis[1], coneAxis[2], rotAxis);
        }

        Matrices.computeRotationMatrix(ang - coneRads * (1.0 - REL_ERR), rotAxis[0], rotAxis[1], rotAxis[2], rot);
        
        if(outVec != null) {
            Matrices.multMatVec(rot, vec, outVec);
        }
        
        return true;
    }
    
    /**
     * Picks a unit-length vector that is orthogonal to the input vector.
     */
    public static void chooseOrtho( double x, double y, double z, double[] out3x1 ) {
        double d = y * y + z * z;
        
        if( d > ABS_ERR ) {
            d = Math.sqrt( z * z / d );
            double sign = ( y * z >= 0.0 ? -1.0 : 1.0 );
            out3x1[0] = 0.0;
            out3x1[1] = d;
            out3x1[2] = Math.sqrt( 1 - d * d ) * sign;
        }else{
            out3x1[0] = 0.0;
            out3x1[1] = 1.0;
            out3x1[2] = 0.0;
        }
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
        return hypot(cx, cy, cz) / dist(n1, n2);
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
                
        if(den < ABS_ERR || t <= 0.0) {
            //Return distance from n1 to p (which is already stored in u0,u1,u2).
        }else if(t >= 1.0) {
            //Return distance from n2 to p.
            u0 = p[0] - n2[0];
            u1 = p[1] - n2[1];
            u2 = p[2] - n2[2];
        }else{
            //Make u perpendicular to line.
            u0 -= v0 * t;
            u1 -= v1 * t;
            u2 -= v2 * t;
        }

        return Math.sqrt(u0 * u0 + u1 * u1 + u2 * u2);
    }
    
    /**
     * Finds intersection of line and plane.  The output is only defined if this method returns 1, 
     * indicating that the line and plane intersect at one point. 
     * 
     * @param line0      First point on line.
     * @param line1      Second point on line.
     * @param planePoint A Point on the plane.
     * @param planeNorm  Normal vector of plane.
     * @param out        3x1 array to hold output point.  May be <code>null</code>
     * @return 0 if no intersection, 1 if point intersection, 2 if complete intersection.
     */
    public static int intersectLineWithPlane( double[] line0, 
                                              double[] line1, 
                                              double[] planePoint, 
                                              double[] planeNorm, 
                                              double[] out ) 
    {
        double dx = line1[0] - line0[0];
        double dy = line1[1] - line0[1];
        double dz = line1[2] - line0[2];
        
        double num = (planePoint[0] - line0[0]) * planeNorm[0] + 
                     (planePoint[1] - line0[1]) * planeNorm[1] +
                     (planePoint[2] - line0[2]) * planeNorm[2];
        
        double den = dx * planeNorm[0] + dy * planeNorm[1] + dz * planeNorm[2];
        
        if( Math.abs(den) < ABS_ERR )
            return Math.abs(num) < ABS_ERR ? 2 : 0;
        
        if(out == null)
            return 1;
        
        double d = num / den;
        out[0] = d * dx + line0[0];
        out[1] = d * dy + line0[1];
        out[2] = d * dz + line0[2];
        
        return 1;
    }
    
    /**
     * Finds "intersection", or smallest connecting line, between two 3d lines.
     * (Lines, not line segments).
     * 
     * @param a0   First point on line a
     * @param a1   Second point on line a 
     * @param b0   First point on line b
     * @param b1   Second point on line b
     * @param outA Point on line a nearest to line b (optional)
     * @param outB Point on line b nearest to line a (optional)
     * @return true if lines are not parallel and intersection was found.  False if lines are parallel.
     */
    public static boolean lineLineIntersection( double[] a0, 
                                                double[] a1, 
                                                double[] b0, 
                                                double[] b1, 
                                                double[] outA, 
                                                double[] outB )
    {
        double da0b0b1b0 = (a0[0] - b0[0]) * (b1[0] - b0[0]) + (a0[1] - b0[1]) * (b1[1] - b0[1]) + (a0[2] - b0[2]) * (b1[2] - b0[2]); 
        double db1b0a1a0 = (b1[0] - b0[0]) * (a1[0] - a0[0]) + (b1[1] - b0[1]) * (a1[1] - a0[1]) + (b1[2] - b0[2]) * (a1[2] - a0[2]); 
        double da0b0a1a0 = (a0[0] - b0[0]) * (a1[0] - a0[0]) + (a0[1] - b0[1]) * (a1[1] - a0[1]) + (a0[2] - b0[2]) * (a1[2] - a0[2]); 
        double db1b0b1b0 = (b1[0] - b0[0]) * (b1[0] - b0[0]) + (b1[1] - b0[1]) * (b1[1] - b0[1]) + (b1[2] - b0[2]) * (b1[2] - b0[2]);   
        double da1a0a1a0 = (a1[0] - a0[0]) * (a1[0] - a0[0]) + (a1[1] - a0[1]) * (a1[1] - a0[1]) + (a1[2] - a0[2]) * (a1[2] - a0[2]);  
        
        double num = da0b0b1b0 * db1b0a1a0 - da0b0a1a0 * db1b0b1b0;
        double den = da1a0a1a0 * db1b0b1b0 - db1b0a1a0 * db1b0a1a0;
        
        if( Math.abs(den) < ABS_ERR )
            return false;
        
        double mua = num / den;
        
        if(outA != null) {
            Vectors.add(a0, 1.0 - mua, a1, mua, outA);
        }
        
        if(outB != null) {
            double mub = (da0b0b1b0 + mua * db1b0a1a0) / db1b0b1b0;
            Vectors.add(b0, 1.0 - mub, b1, mub, outB);
        }
        
        return true;
    }
    
    
    
    private Vectors() {}

    
    public static void main(String[] args) {
        double[] a0 = {0,0,0};
        double[] a1 = {10,0,0};
        double[] b0 = {60, 10, 10};
        double[] b1 = {50,-10, 10};
        
        double[] oa = new double[3];
        double[] ob = new double[3];
        
        System.out.println(lineLineIntersection(a0, a1, b0, b1, oa, ob));
        
        System.out.println(oa[0] + "  " + oa[1] + "  " + oa[2]);
        System.out.println(ob[0] + "  " + ob[1] + "  " + ob[2]);
        
    }
    
}
