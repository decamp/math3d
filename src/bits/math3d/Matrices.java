package bits.math3d;

import static bits.math3d.Tol.*;



/** 
 * These are all homographic coordinates, yo.  Matrices are 4x4.  Vectors are 3x1.
 * 
 * @author Philip DeCamp  
 */
public final class Matrices {
    
        
    public static void multMatMat( double[] a, double[] b, double[] out ) {
        double a00 = a[ 0];
        double a01 = a[ 1];
        double a02 = a[ 2];
        double a03 = a[ 3];
        double a04 = a[ 4];
        double a05 = a[ 5];
        double a06 = a[ 6];
        double a07 = a[ 7];
        double a08 = a[ 8];
        double a09 = a[ 9];
        double a10 = a[10];
        double a11 = a[11];
        double a12 = a[12];
        double a13 = a[13];
        double a14 = a[14];
        double a15 = a[15];
        double b0 = b[0];
        double b1 = b[1];
        double b2 = b[2];
        double b3 = b[3];
        out[ 0] = a00*b0 + a04*b1 + a08*b2 + a12*b3;
        out[ 1] = a01*b0 + a05*b1 + a09*b2 + a13*b3;
        out[ 2] = a02*b0 + a06*b1 + a10*b2 + a14*b3;
        out[ 3] = a03*b0 + a07*b1 + a11*b2 + a15*b3;
        b0 = b[4];
        b1 = b[5];
        b2 = b[6];
        b3 = b[7];
        out[ 4] = a00*b0 + a04*b1 + a08*b2 + a12*b3;
        out[ 5] = a01*b0 + a05*b1 + a09*b2 + a13*b3;
        out[ 6] = a02*b0 + a06*b1 + a10*b2 + a14*b3;
        out[ 7] = a03*b0 + a07*b1 + a11*b2 + a15*b3;
        b0 = b[8];
        b1 = b[9];
        b2 = b[10];
        b3 = b[11];
        out[ 8] = a00*b0 + a04*b1 + a08*b2 + a12*b3;
        out[ 9] = a01*b0 + a05*b1 + a09*b2 + a13*b3;
        out[10] = a02*b0 + a06*b1 + a10*b2 + a14*b3;
        out[11] = a03*b0 + a07*b1 + a11*b2 + a15*b3;
        b0 = b[12];
        b1 = b[13];
        b2 = b[14];
        b3 = b[15];
        out[12] = a00*b0 + a04*b1 + a08*b2 + a12*b3;
        out[13] = a01*b0 + a05*b1 + a09*b2 + a13*b3;
        out[14] = a02*b0 + a06*b1 + a10*b2 + a14*b3;
        out[15] = a03*b0 + a07*b1 + a11*b2 + a15*b3;
    }
    
    
    public static void multMatVec( double[] a, double[] b, double[] out ) {
        double b0 = b[0];
        double b1 = b[1];
        double b2 = b[2];
        double x = a[ 0]*b0 + a[ 4]*b1 + a[ 8]*b2 + a[12];
        double y = a[ 1]*b0 + a[ 5]*b1 + a[ 9]*b2 + a[13];
        double z = a[ 2]*b0 + a[ 6]*b1 + a[10]*b2 + a[14];
        double w = a[ 3]*b0 + a[ 7]*b1 + a[11]*b2 + a[15];
        w = 1.0 / w;
        out[0] = x * w;
        out[1] = y * w;
        out[2] = z * w;
    }
    
    
    public static void multMatVec( double[] a, int offA, double[] b, int offB, double[] out, int offOut ) {
        double x = a[ 0+offA]*b[0+offB] + a[ 4+offA]*b[1+offB] + a[ 8+offA]*b[2+offB] + a[12+offA];
        double y = a[ 1+offA]*b[0+offB] + a[ 5+offA]*b[1+offB] + a[ 9+offA]*b[2+offB] + a[13+offA];
        double z = a[ 2+offA]*b[0+offB] + a[ 6+offA]*b[1+offB] + a[10+offA]*b[2+offB] + a[14+offA];
        double w = a[ 3+offA]*b[0+offB] + a[ 7+offA]*b[1+offB] + a[11+offA]*b[2+offB] + a[15+offA];
        w = 1.0 / w;
        out[0+offOut] = x * w;
        out[1+offOut] = y * w;
        out[2+offOut] = z * w;
    }
    
    
    public static void multVec4( double[] a, double[] b, double[] out ) {
        double t0 = a[ 0]*b[0] + a[ 4]*b[1] + a[ 8]*b[2] + a[12]*b[3];
        double t1 = a[ 1]*b[0] + a[ 5]*b[1] + a[ 9]*b[2] + a[13]*b[3];
        double t2 = a[ 2]*b[0] + a[ 6]*b[1] + a[10]*b[2] + a[14]*b[3];
        double t3 = a[ 3]*b[0] + a[ 7]*b[1] + a[11]*b[2] + a[15]*b[3];
        out[0] = t0;
        out[1] = t1;
        out[2] = t2;
        out[3] = t3;
    }

    /**
     * @param mat    Input matrix
     * @param out    Array to hold inverted matrix on return.
     * @return true if matrix determinant is not near zero and accurate inverse was found.
     */    
    public static boolean invert( double[] mat, double[] out ) {
        double s0 = mat[0+0*4] * mat[1+1*4] - mat[1+0*4] * mat[0+1*4];
        double s1 = mat[0+0*4] * mat[1+2*4] - mat[1+0*4] * mat[0+2*4];
        double s2 = mat[0+0*4] * mat[1+3*4] - mat[1+0*4] * mat[0+3*4];
        double s3 = mat[0+1*4] * mat[1+2*4] - mat[1+1*4] * mat[0+2*4];
        double s4 = mat[0+1*4] * mat[1+3*4] - mat[1+1*4] * mat[0+3*4];
        double s5 = mat[0+2*4] * mat[1+3*4] - mat[1+2*4] * mat[0+3*4];

        double c5 = mat[2+2*4] * mat[3+3*4] - mat[3+2*4] * mat[2+3*4];
        double c4 = mat[2+1*4] * mat[3+3*4] - mat[3+1*4] * mat[2+3*4];
        double c3 = mat[2+1*4] * mat[3+2*4] - mat[3+1*4] * mat[2+2*4];
        double c2 = mat[2+0*4] * mat[3+3*4] - mat[3+0*4] * mat[2+3*4];
        double c1 = mat[2+0*4] * mat[3+2*4] - mat[3+0*4] * mat[2+2*4];
        double c0 = mat[2+0*4] * mat[3+1*4] - mat[3+0*4] * mat[2+1*4];

        // Compute determinant
        double invdet = s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0;
        // Check if invertible.
        boolean ret  = invdet > FSQRT_ABS_ERR || -invdet > FSQRT_ABS_ERR;
        // Invert determinant
        invdet = 1.0f / invdet;

        double t00 = ( mat[1+1*4] * c5 - mat[1+2*4] * c4 + mat[1+3*4] * c3) * invdet;
        double t01 = (-mat[1+0*4] * c5 + mat[1+2*4] * c2 - mat[1+3*4] * c1) * invdet;
        double t02 = ( mat[1+0*4] * c4 - mat[1+1*4] * c2 + mat[1+3*4] * c0) * invdet;
        double t03 = (-mat[1+0*4] * c3 + mat[1+1*4] * c1 - mat[1+2*4] * c0) * invdet;
        double t04 = (-mat[0+1*4] * c5 + mat[0+2*4] * c4 - mat[0+3*4] * c3) * invdet;
        double t05 = ( mat[0+0*4] * c5 - mat[0+2*4] * c2 + mat[0+3*4] * c1) * invdet;
        double t06 = (-mat[0+0*4] * c4 + mat[0+1*4] * c2 - mat[0+3*4] * c0) * invdet;
        double t07 = ( mat[0+0*4] * c3 - mat[0+1*4] * c1 + mat[0+2*4] * c0) * invdet;
        double t08 = ( mat[3+1*4] * s5 - mat[3+2*4] * s4 + mat[3+3*4] * s3) * invdet;
        double t09 = (-mat[3+0*4] * s5 + mat[3+2*4] * s2 - mat[3+3*4] * s1) * invdet;
        double t10 = ( mat[3+0*4] * s4 - mat[3+1*4] * s2 + mat[3+3*4] * s0) * invdet;
        double t11 = (-mat[3+0*4] * s3 + mat[3+1*4] * s1 - mat[3+2*4] * s0) * invdet;
        double t12 = (-mat[2+1*4] * s5 + mat[2+2*4] * s4 - mat[2+3*4] * s3) * invdet;
        double t13 = ( mat[2+0*4] * s5 - mat[2+2*4] * s2 + mat[2+3*4] * s1) * invdet;
        double t14 = (-mat[2+0*4] * s4 + mat[2+1*4] * s2 - mat[2+3*4] * s0) * invdet;
        double t15 = ( mat[2+0*4] * s3 - mat[2+1*4] * s1 + mat[2+2*4] * s0) * invdet;

        out[ 0] = t00;
        out[ 1] = t01;
        out[ 2] = t02;
        out[ 3] = t03;
        out[ 4] = t04;
        out[ 5] = t05;
        out[ 6] = t06;
        out[ 7] = t07;
        out[ 8] = t08;
        out[ 9] = t09;
        out[10] = t10;
        out[11] = t11;
        out[12] = t12;
        out[13] = t13;
        out[14] = t14;
        out[15] = t15;

        return ret;

    }

    
    public static void setToIdentity(double[] out) {
        out[ 0] = 1.0;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        out[ 4] = 0.0;
        out[ 5] = 1.0;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = 1.0;
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }
    
    
    public static void computeTranslationMatrix(double tx, double ty, double tz, double[] out) {
        out[ 0] = 1.0;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        out[ 4] = 0.0;
        out[ 5] = 1.0;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = 1.0;
        out[11] = 0.0;
        out[12] = tx;
        out[13] = ty;
        out[14] = tz;
        out[15] = 1.0;
    }
    
    
    public static void computeScaleMatrix(double sx, double sy, double sz, double[] out) {
        out[ 0] = sx;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        out[ 4] = 0.0;
        out[ 5] = sy;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = sz;
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }
    
    
    public static void computeRotationMatrix(double radians, double x, double y, double z, double[] out) {
        double c = Math.cos(radians);
        double s = Math.sin(radians);
        
        double sum = Math.sqrt(x*x+y*y+z*z);
        x /= sum;
        y /= sum;
        z /= sum;
        
        out[0 ] = x*x*(1-c)+c;
        out[1 ] = x*y*(1-c)+z*s;
        out[2 ] = x*z*(1-c)-y*s;
        out[3 ] = 0.0;
        
        out[4 ] = x*y*(1-c)-z*s;
        out[5 ] = y*y*(1-c)+c;
        out[6 ] = y*z*(1-c)+x*s;
        out[7 ] = 0.0;
        
        out[8 ] = x*z*(1-c)+y*s;
        out[9 ] = y*z*(1-c)-x*s;
        out[10] = z*z*(1-c)+c;
        out[11] = 0.0;
        
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }    

    
    public static void computeFrustumMatrix(double left, double right, double bottom, double top, double near, double far, double[] out) {
        out[ 0] = 2.0 * near / (right - left);
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        
        out[ 4] = 0.0;
        out[ 5] = 2 * near / (top - bottom);
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        
        out[ 8] = (right + left) / (right - left);
        out[ 9] = (top + bottom) / (top - bottom);
        out[10] = -(far + near) / (far - near);
        out[11] = -1.0;
        
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = -(2 * far * near) / (far - near);
        out[15] = 0.0;
    }
    
    
    public static void computeOrthoMatrix(double left, double right, double bottom, double top, double near, double far, double[] out) {
        out[ 0] = 2.0 / (right - left);
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        
        out[ 4] = 0.0;
        out[ 5] = 2.0 / (top - bottom);
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = -2.0 / (far - near);
        out[11] = 0.0;
        
        out[12] = -(right + left) / (right - left);
        out[13] = -(top + bottom) / (top - bottom);
        out[14] = -(far + near) / (far - near);
        out[15] = 1.0;
    }
    
    
    public static void computeLookAtMatrix(double[] eyeVec, double[] centerVec, double[] upVec, double[] outMat) {
        double fx  = centerVec[0] - eyeVec[0];
        double fy  = centerVec[1] - eyeVec[1];
        double fz  = centerVec[2] - eyeVec[2];
        double len = Math.sqrt(fx * fx + fy * fy + fz * fz);
        fx /= len;
        fy /= len;
        fz /= len;
        
        double ux = upVec[0];
        double uy = upVec[1];
        double uz = upVec[2];
        len = Math.sqrt(ux * ux + uy * uy + uz * uz);
        ux /= len;
        uy /= len;
        uz /= len;
        
        double sx = fy * uz - fz * uy;
        double sy = fz * ux - fx * uz;
        double sz = fx * uy - fy * ux;
        
        ux = sy * fz - sz * fy;
        uy = sz * fx - sx * fz;
        uz = sx * fy - sy * fx;
        
        outMat[ 0] = sx;
        outMat[ 1] = ux;
        outMat[ 2] = -fx;
        outMat[ 3] = 0;
        outMat[ 4] = sy;
        outMat[ 5] = uy;
        outMat[ 6] = -fy;
        outMat[ 7] = 0;
        outMat[ 8] = sz;
        outMat[ 9] = uz;
        outMat[10] = -fz;
        outMat[11] = 0;
        outMat[12] = -(sx * eyeVec[0] + sy * eyeVec[1] + sz * eyeVec[2]);
        outMat[13] = -(ux * eyeVec[0] + uy * eyeVec[1] + uz * eyeVec[2]);
        outMat[14] = fx * eyeVec[0] + fy * eyeVec[1] + fz * eyeVec[2];
        outMat[15] = 1.0;
    }
    

    public static void computeViewportMatrix(double x, double y, double w, double h, double[] out) {
        out[ 0] = w * 0.5;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        
        out[ 4] = 0.0;
        out[ 5] = h * 0.5;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = 1.0;
        out[11] = 0.0;
        
        out[12] = w * 0.5 + x;
        out[13] = h * 0.5 + y;
        out[14] = 0.0;
        out[15] = 1.0;
    }
    
    
    public static void computeViewportDepthMatrix(double x, double y, double w, double h, double near, double far, double[] out) {
        out[ 0] = w * 0.5;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        
        out[ 4] = 0.0;
        out[ 5] = h * 0.5;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = ( far - near ) * 0.5;
        out[11] = 0.0;
        
        out[12] = w * 0.5 + x;
        out[13] = h * 0.5 + y;
        out[14] = ( far + near ) * 0.5;
        out[15] = 1.0;
    }
    
    
    //************************************
    // Rotation methods.
    //************************************
    
    /**
     * Removes any translation/scaling/skew or other non-rotation 
     * transformations from a matrix.  
     * 
     * @param mat 4x4 homography matrix to turn into strict rotation matrix.
     */
    public static void normalizeRotationMatrix( double[] mat ) {
        double d;
        
        //Kill translation, scalings.
        mat[ 3] = 0.0;
        mat[ 7] = 0.0;
        mat[11] = 0.0;
        mat[12] = 0.0;
        mat[13] = 0.0;
        mat[14] = 0.0;
        mat[15] = 1.0;
        
        //Normalize length of X-axis.
        d = Math.sqrt(mat[0] * mat[0] + mat[1] * mat[1] + mat[2] * mat[2]);
        if( d > ABS_ERR ) {
            mat[0] /= d;
            mat[1] /= d;
            mat[2] /= d;
        }else{
            mat[0] = 1.0;
            mat[1] = 0.0;
            mat[2] = 0.0;
        }
        
        //Orthogonalize Y-axis to X-axis
        d = mat[0] * mat[4] + mat[1] * mat[5] + mat[2] * mat[6];
        mat[4] -= d * mat[0];
        mat[5] -= d * mat[1];
        mat[6] -= d * mat[2];
        
        //Normalize Y-axis.
        d = Math.sqrt(mat[4] * mat[4] + mat[5] * mat[5] + mat[6] * mat[6]);
        if( d > ABS_ERR ) {
            mat[4] /= d;
            mat[5] /= d;
            mat[6] /= d;
        }else{
            double[] orth = new double[3];
            Vectors.chooseOrtho(mat[0], mat[1], mat[2], orth);
            mat[4] = orth[0];
            mat[5] = orth[1];
            mat[6] = orth[2];
        }
        
        //Compute Z-axis
        mat[ 8] = mat[1]*mat[6] - mat[2]*mat[5];
        mat[ 9] = mat[2]*mat[4] - mat[0]*mat[6];
        mat[10] = mat[0]*mat[5] - mat[1]*mat[4];
    }
    
    
    public static void axesToTransform( double[] x, double[] y, double[] out ) {
        double[] z = new double[3];
        Vectors.cross( x, y, z );
        axesToTransform( x, y, z, out );
    }
    
    
    public static void axesToTransform( double[] x, double[] y, double[] z, double[] out ) {
        out[ 0] = x[0];
        out[ 1] = x[1];
        out[ 2] = x[2];
        out[ 3] = 0.0;
        out[ 4] = y[0];
        out[ 5] = y[1];
        out[ 6] = y[2];
        out[ 7] = 0.0;
        out[ 8] = z[0];
        out[ 9] = z[1];
        out[10] = z[2];
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }

    /**
     * Creates a rotation matrix that uses two provided vectors as bases.
     * If the two input vectors are not orthogonal, the second vector
     * will be orthogonalized. If the two vectors are parallel, the
     * orthogonalization will be performed arbitrarily with valid but
     * undefined results. Input vectors need not be normalized.
     * 
     * @param v0     Primary vector to use as basis. 
     * @param axis0  Axis of primary vector ( 0 == x, 1 == y, 2 == z )
     * @param v1     Secondary vector to use as basis.
     * @param axis1  Axis of secondary vector ( 0 == x, 1 == y, 2 == z )
     *               Results are undefined if axis0 == axis1.
     * @param out    On return, holds computed rotation matrix.
     */
    public static void basisVecsToRotation( double[] v0, int axis0, double[] v1, int axis1, double[] out ) {
        axis0 %= 3;
        axis1 %= 3;
        int axis2;
        boolean forward;
        
        if( axis1 == ( axis0 + 1 ) % 3 ) {
            axis2 = ( axis0 + 2 ) % 3;
            forward = true;
        } else if( axis1 == ( axis0 + 2 ) % 3 ) {
            axis2 = ( axis0 + 1 ) % 3;
            forward = false;
        } else {
            axis1 = ( axis0 + 1 ) % 3;
            axis2 = ( axis0 + 2 ) % 3;
            forward = true;
        }
        
        // Compute axis2.
        if( forward ) {
            Vectors.cross( v0, 0, v1, 0, out, axis2*4 );
        } else {
            Vectors.cross( v1, 0, v0, 0, out, axis2*4 );
        }
        
        // Normalize axis2.
        // If zero, select arbitrary axis ortohgonal to axis0.
        double d = Vectors.lenSquared( out, axis2*4 );
        if( d < Tol.SQRT_ABS_ERR ) {
            Vectors.chooseOrtho( out[axis2*4], out[axis2*4+1], out[axis2*4+2], out );
            System.arraycopy( out, 0, out, axis2*4,3 );
        } else {
            d = 1.0 / Math.sqrt( d );
            out[axis2*4  ] *= d;
            out[axis2*4+1] *= d;
            out[axis2*4+2] *= d;
        }
        
        // Copy and normalize axis0.
        System.arraycopy( v0, 0, out, axis0*4, 3 );
        Vectors.normalize( out, axis0*4, 1.0 );
        
        // Compute axis1.
        if( forward ) {
            Vectors.cross( out, axis2*4, out, axis0*4, out, axis1*4 );
        } else {
            Vectors.cross( out, axis0*4, out, axis2*4, out, axis1*4 );
        }
        
        // Initialize rest of matrix.
        out[ 3] = 0;
        out[ 7] = 0;
        out[11] = 0;
        out[12] = 0;
        out[13] = 0;
        out[14] = 0;
        out[15] = 1;
    }

    /**
     * This method will adjust a rotation matrix so that one of the basis vectors will
     * be parallel to an axis.
     * 
     * @param mat   Input rotation matrix.
     * @param basis Basis vector to align to axis. <code>0 == x-basis, 1 == y-basis, 2 == z-basis</code>.
     * @param out   Holds modified rotation matrix on output.
     */
    public static void alignBasisVectorToAxis( double[] mat, int basis, double[] out ) {
        out[12] = mat[basis*4  ];
        out[13] = mat[basis*4+1];
        out[14] = mat[basis*4+2];
        
        // Clamp specified vector to nearest axis.
        Vectors.nearestAxis( mat[basis*4  ], mat[basis*4+1], mat[basis*4+2], out );
        
        // Move into correct column.
        out[basis*4  ] = out[0];
        out[basis*4+1] = out[1];
        out[basis*4+2] = out[2];
        
        // Cross with next axis.
        final int basis1 = ( basis + 1 ) % 3;
        final int basis2 = ( basis + 2 ) % 3;
        
        Vectors.cross( out, basis*4, mat, basis1*4, out, basis2*4 );
        Vectors.normalize( out, basis2*4, 1.0 );
        Vectors.cross( out, basis2*4, out, basis*4, out, basis1*4 );
        
        out[ 3] = 0.0;
        out[ 7] = 0.0;
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }
    
    /**
     * Computes rotation matrix of smallest rotation angle needed to 
     * rotate vector <code>src</code> to vector <code>dst</code>.
     * 
     * @param src  Input vector
     * @param dst  Destination vector
     * @param outMat On output, holds rotation matrix that will transform src to dst.
     */
    public static void computeMinRotation( double[] src, double[] dst, double[] outMat ) {
        double cosAng = Vectors.cosAng( src, dst );
        double ang    = Math.acos( cosAng );
        if( Double.isNaN( ang ) ) {
            ang = cosAng > 0 ? 0 : Math.PI;
        }

        if( Tol.approxZero( ang, Math.PI ) ) {
            setToIdentity( outMat );
        } else if( ang < Math.PI * ( 1.0 - Tol.REL_ERR ) ) {
            Vectors.cross( src, dst, outMat );
            Matrices.computeRotationMatrix( ang, outMat[0], outMat[1], outMat[2], outMat ); 
        } else {
            Vectors.chooseOrtho( src[0], src[1], src[2], outMat );
            Matrices.computeRotationMatrix( Math.PI, outMat[0], outMat[1], outMat[2], outMat );
        }
    }
    
    /**
     * Performs spherical interpolation of rotation matrices.
     * 
     * @param a
     * @param b
     * @param t
     * @param workQuatA
     * @param workQuatB
     * @param workQuatC
     * @param out
     */
    public static void slerpRotations( double[] a,
                                       double[] b,
                                       double t,
                                       double[] workQuatA,
                                       double[] workQuatB,
                                       double[] workQuatC,
                                       double[] out )
    {
        Quats.matToQuat( a, workQuatA );
        Quats.matToQuat( b, workQuatB );
        Quats.slerp( workQuatA, workQuatB, t, workQuatC );
        Quats.quatToMat( workQuatC, out );
    }
    
    
    
    public static String format( double[] mat ) {
        StringBuilder sb = new StringBuilder();
        for( int r = 0; r < 4; r++ ) {
            if ( r == 0 ) {
                sb.append( "[[ " );
            } else {
                sb.append( " [ " );
            }
            
            sb.append( String.format( "% 7.4f  % 7.4f  % 7.4f  % 7.4f", mat[r   ], mat[r+4], mat[r+8], mat[r+12] ) );
            
            if( r == 3 ) {
                sb.append( " ]]" );
            } else {
                sb.append( " ]\n" );
            }
        }
            
        return sb.toString();
    }


    
    public static boolean isValid( double[] mat ) {
        for( int i = 0; i < 16; i++ ) {
            if( Double.isNaN( mat[i] ) ) {
                return false;
            }
        }
        return true;
    }
    
    
    
    
    /**
     * @param mat    Input matrix
     * @param work0  Working matrix.  Contents don't matter, but is overwritten.  
     * @param work1  Working matrix.  Contents don't matter, but is overwritten.
     * @param out    Array to hold inverted matrix on return.
     * @deprecated <code>invert( mat, out )</code> is much faster and requires no workspace matrices. 
     */
    @Deprecated
    public static void invertMat(double[] mat, double[] work0, double[] work1, double[] out) {
        invert( mat, out );
    }
    
    /**
     * @deprecated invert( mat, out ) is faster and easier to use. 
     */
    @Deprecated
    @SuppressWarnings( "unused" )
    public static void invertMat(double[] a, double[] w, double[] out) {
        invert( a, out );
    }
    
    /**
     * @deprecated Since no longer used for inversion, should be generalized or removed.
     */
    @Deprecated
    public static void decompPlu( double[] a, int[] p ) {
        final int m = 4;
        final int n = 4;
        final int d = Math.min( m, n );
        int pivSign = 1;

        for(int i = 0; i < m; i++)
            p[i] = i;

        //Outer loop
        for(int j = 0; j < n; j++) {

            //Apply previous transformations.
            for( int i = 0; i < m; i++ ) {
                int kmax = Math.min( i, j );
                double s = 0.0;
                
                for( int k = 0; k < kmax; k++ ){
                    s += a[ i + k * m ] * a[ k + j * m ];
                }
                
                a[ i + j * m ] -= s;
            }
            
            //Find pivot and exchange if necessary.
            int piv = j;
            for( int i = j + 1; i < m; i++ ) {
                if( Math.abs( a[ i + j * m ] ) > Math.abs( a[ piv + j * m ] ) ) {
                    piv = i;
                }
            }
            
            if( piv != j ) {
                for( int k = 0; k < n; k++ ) {
                    double t = a[ piv + k * m ];
                    a[ piv + k * m ] = a[ j + k * m ];
                    a[ j + k * m ] = t;
                }
                
                int k = p[piv];
                p[piv] = p[j];
                p[j] = k;
                pivSign = -pivSign;
            }

            //Compute multipliers.
            if(j < d && a[j+j*m] != 0f){
                for(int i = j+1; i < m; i++){
                    a[i+j*m] /= a[j+j*m];
                }
            }
        }
    }
    
    /**
     * @param lu
     * @param p
     * @param b
     * @param nx
     * @param out
     * @deprecated Since no longer used for inversion, sohuld be generalized or removed.
     */
    @Deprecated
    public static void solvePlu( double[] lu, int[] p, double[] b, int nx, double[] out ) {
        final int m = 4;
        final int n = 4;
        
        //Reorder rows.
        for ( int i = 0; i < m; i++ ) {
            final int k = p[i];
            
            for ( int j = 0; j < n; j++ ) {
                out[i+j*m] = b[k+j*m];
            }
        } 
                
        //Solve L*Y = B(piv,:)
        for ( int k = 0; k < n; k++ ) {
            for ( int i = k + 1; i < m; i++) {
                for ( int j = 0; j < nx; j++ ) {
                    out[i+j*m] -= out[k+j*m] * lu[i+k*m];
                }
            }
        }
        
        //Solve U*X = Y
        for ( int k = Math.min( m, n ) - 1; k >= 0; k-- ) {
            for( int j = 0; j < nx; j++ ) {
                out[k+j*m] /= lu[k+k*m];
            }
            
            for( int i = 0; i < k; i++ ) {
                for( int j = 0; j < nx; j++ ) {
                    out[i+j*m] -= out[k+j*m] * lu[i+k*m];
                }
            }
        }
    }


    /**
     * @deprecated Use format()
     */
    public static String matToString( double[] mat ) {
        return format( mat );
    }
    
    
    /**
     * @deprecated Until I figure out if this is correct or not. 
     */
    public static void multMatMatHomog(double[] a, double[] b, double[] out) {
        multMatMat(a, b, out);
        
        // Don't think this is right.
        //double scale = 1.0 / (out[3] + out[7]+ out[11] + out[15]);
        //for(int i = 0; i < 16; i++) {
        //    out[i] *= scale;
        //}
                
        double scale = 1.0 / out[15];
        out[15] = 1.0;
        
        for(int i = 0; i < 15; i++) {
            out[i] *= scale;
        }
    }

    
    
    private Matrices() {}

}
