package bits.math3d;

import static bits.math3d.Tol.*;



/** 
 * These are all homographic coordinates, yo.  Matrices are 4x4.  Vectors are 3x1.
 * 
 * @author Philip DeCamp  
 */
public final class Mat4 {

    /**
     * Multiplies two matrices.
     *
     * @param a   Length-16 array. Holds matrix in column-major ordering.
     * @param b   Length-16 array. Holds matrix in column-major ordering.
     * @param out Length-16 array. On return, holds <tt>a * b</tt>. May be same array as <tt>a</tt> or <tt>b</tt>.
     */
    public static void mult( float[] a, float[] b, float[] out ) {
        // I tested many configurations for multiplication.
        // This one was the fastest as well as avoids array aliasing without branching.
        float a00 = a[ 0];
        float a01 = a[ 1];
        float a02 = a[ 2];
        float a03 = a[ 3];
        float a04 = a[ 4];
        float a05 = a[ 5];
        float a06 = a[ 6];
        float a07 = a[ 7];
        float a08 = a[ 8];
        float a09 = a[ 9];
        float a10 = a[10];
        float a11 = a[11];
        float a12 = a[12];
        float a13 = a[13];
        float a14 = a[14];
        float a15 = a[15];
        float b0 = b[0];
        float b1 = b[1];
        float b2 = b[2];
        float b3 = b[3];
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


    public static void multVec3( float[] a, float[] b, float[] out ) {
        float b0 = b[0];
        float b1 = b[1];
        float b2 = b[2];
        float x = a[ 0]*b0 + a[ 4]*b1 + a[ 8]*b2 + a[12];
        float y = a[ 1]*b0 + a[ 5]*b1 + a[ 9]*b2 + a[13];
        float z = a[ 2]*b0 + a[ 6]*b1 + a[10]*b2 + a[14];
        float w = a[ 3]*b0 + a[ 7]*b1 + a[11]*b2 + a[15];
        w = 1.0f / w;
        out[0] = x * w;
        out[1] = y * w;
        out[2] = z * w;
    }

    
    public static void multVec3( float[] a, int offA, float[] b, int offB, float[] out, int offOut ) {
        float x = a[ 0+offA]*b[0+offB] + a[ 4+offA]*b[1+offB] + a[ 8+offA]*b[2+offB] + a[12+offA];
        float y = a[ 1+offA]*b[0+offB] + a[ 5+offA]*b[1+offB] + a[ 9+offA]*b[2+offB] + a[13+offA];
        float z = a[ 2+offA]*b[0+offB] + a[ 6+offA]*b[1+offB] + a[10+offA]*b[2+offB] + a[14+offA];
        float w = a[ 3+offA]*b[0+offB] + a[ 7+offA]*b[1+offB] + a[11+offA]*b[2+offB] + a[15+offA];
        w = 1.0f / w;
        out[0+offOut] = x * w;
        out[1+offOut] = y * w;
        out[2+offOut] = z * w;
    }
    
    
    public static void multVec4( float[] a, float[] b, float[] out ) {
        float t0 = a[ 0]*b[0] + a[ 4]*b[1] + a[ 8]*b[2] + a[12]*b[3];
        float t1 = a[ 1]*b[0] + a[ 5]*b[1] + a[ 9]*b[2] + a[13]*b[3];
        float t2 = a[ 2]*b[0] + a[ 6]*b[1] + a[10]*b[2] + a[14]*b[3];
        float t3 = a[ 3]*b[0] + a[ 7]*b[1] + a[11]*b[2] + a[15]*b[3];
        out[0] = t0;
        out[1] = t1;
        out[2] = t2;
        out[3] = t3;
    }

    /**
     * @param mat Input matrix
     * @param out Array to hold inverted matrix on return.
     * @return true if matrix determinant is not near zero and accurate inverse was found.
     */    
    public static boolean invert( float[] mat, float[] out ) {
        float s0 = mat[0+0*4] * mat[1+1*4] - mat[1+0*4] * mat[0+1*4];
        float s1 = mat[0+0*4] * mat[1+2*4] - mat[1+0*4] * mat[0+2*4];
        float s2 = mat[0+0*4] * mat[1+3*4] - mat[1+0*4] * mat[0+3*4];
        float s3 = mat[0+1*4] * mat[1+2*4] - mat[1+1*4] * mat[0+2*4];
        float s4 = mat[0+1*4] * mat[1+3*4] - mat[1+1*4] * mat[0+3*4];
        float s5 = mat[0+2*4] * mat[1+3*4] - mat[1+2*4] * mat[0+3*4];

        float c5 = mat[2+2*4] * mat[3+3*4] - mat[3+2*4] * mat[2+3*4];
        float c4 = mat[2+1*4] * mat[3+3*4] - mat[3+1*4] * mat[2+3*4];
        float c3 = mat[2+1*4] * mat[3+2*4] - mat[3+1*4] * mat[2+2*4];
        float c2 = mat[2+0*4] * mat[3+3*4] - mat[3+0*4] * mat[2+3*4];
        float c1 = mat[2+0*4] * mat[3+2*4] - mat[3+0*4] * mat[2+2*4];
        float c0 = mat[2+0*4] * mat[3+1*4] - mat[3+0*4] * mat[2+1*4];

        // Compute determinant
        float invdet = s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0;
        // Check if invertible.
        boolean ret  = invdet > FSQRT_ABS_ERR || -invdet > FSQRT_ABS_ERR;
        // Invert determinant
        invdet = 1.0f / invdet;

        float t00 = ( mat[1+1*4] * c5 - mat[1+2*4] * c4 + mat[1+3*4] * c3) * invdet;
        float t01 = (-mat[1+0*4] * c5 + mat[1+2*4] * c2 - mat[1+3*4] * c1) * invdet;
        float t02 = ( mat[1+0*4] * c4 - mat[1+1*4] * c2 + mat[1+3*4] * c0) * invdet;
        float t03 = (-mat[1+0*4] * c3 + mat[1+1*4] * c1 - mat[1+2*4] * c0) * invdet;
        float t04 = (-mat[0+1*4] * c5 + mat[0+2*4] * c4 - mat[0+3*4] * c3) * invdet;
        float t05 = ( mat[0+0*4] * c5 - mat[0+2*4] * c2 + mat[0+3*4] * c1) * invdet;
        float t06 = (-mat[0+0*4] * c4 + mat[0+1*4] * c2 - mat[0+3*4] * c0) * invdet;
        float t07 = ( mat[0+0*4] * c3 - mat[0+1*4] * c1 + mat[0+2*4] * c0) * invdet;
        float t08 = ( mat[3+1*4] * s5 - mat[3+2*4] * s4 + mat[3+3*4] * s3) * invdet;
        float t09 = (-mat[3+0*4] * s5 + mat[3+2*4] * s2 - mat[3+3*4] * s1) * invdet;
        float t10 = ( mat[3+0*4] * s4 - mat[3+1*4] * s2 + mat[3+3*4] * s0) * invdet;
        float t11 = (-mat[3+0*4] * s3 + mat[3+1*4] * s1 - mat[3+2*4] * s0) * invdet;
        float t12 = (-mat[2+1*4] * s5 + mat[2+2*4] * s4 - mat[2+3*4] * s3) * invdet;
        float t13 = ( mat[2+0*4] * s5 - mat[2+2*4] * s2 + mat[2+3*4] * s1) * invdet;
        float t14 = (-mat[2+0*4] * s4 + mat[2+1*4] * s2 - mat[2+3*4] * s0) * invdet;
        float t15 = ( mat[2+0*4] * s3 - mat[2+1*4] * s1 + mat[2+2*4] * s0) * invdet;

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


    public static void transpose( float[] mat, float[] out ) {
        // About 15% faster without local copies.
        float a00 = mat[ 0];
        float a01 = mat[ 1];
        float a02 = mat[ 2];
        float a03 = mat[ 3];
        float a04 = mat[ 4];
        float a05 = mat[ 5];
        float a06 = mat[ 6];
        float a07 = mat[ 7];
        float a08 = mat[ 8];
        float a09 = mat[ 9];
        float a10 = mat[10];
        float a11 = mat[11];
        float a12 = mat[12];
        float a13 = mat[13];
        float a14 = mat[14];
        float a15 = mat[15];

        out[ 0] = a00;
        out[ 1] = a04;
        out[ 2] = a08;
        out[ 3] = a12;

        out[ 4] = a01;
        out[ 5] = a05;
        out[ 6] = a09;
        out[ 7] = a13;

        out[ 8] = a02;
        out[ 9] = a06;
        out[10] = a10;
        out[11] = a14;

        out[12] = a03;
        out[13] = a07;
        out[14] = a11;
        out[15] = a15;
    }


    public static void identity( float[] out ) {
        out[ 0] = 1.0f;
        out[ 1] = 0.0f;
        out[ 2] = 0.0f;
        out[ 3] = 0.0f;
        out[ 4] = 0.0f;
        out[ 5] = 1.0f;
        out[ 6] = 0.0f;
        out[ 7] = 0.0f;
        out[ 8] = 0.0f;
        out[ 9] = 0.0f;
        out[10] = 1.0f;
        out[11] = 0.0f;
        out[12] = 0.0f;
        out[13] = 0.0f;
        out[14] = 0.0f;
        out[15] = 1.0f;
    }

    /**
     * Computes a rotation matrix.
     *
     * @param radians Degree of rotation.
     * @param x       X-Coord of rotation axis.
     * @param y       Y-Coord of rotation axis.
     * @param z       Z-Coord of rotation axis.
     * @param out     Length-16 array to hold output on return.
     */
    public static void rotation( float radians, float x, float y, float z, float[] out ) {
        double cc = Math.cos( radians );
        float c = (float)cc;
        float s = (float)Math.sqrt( 1.0 - cc * cc );
        float sum = 1f / (float)Math.sqrt( x*x + y*y + z*z );
        x *= sum;
        y *= sum;
        z *= sum;

        out[0 ] = x*x*(1-c)+c;
        out[1 ] = x*y*(1-c)+z*s;
        out[2 ] = x*z*(1-c)-y*s;
        out[3 ] = 0;

        out[4 ] = x*y*(1-c)-z*s;
        out[5 ] = y*y*(1-c)+c;
        out[6 ] = y*z*(1-c)+x*s;
        out[7 ] = 0;

        out[8 ] = x*z*(1-c)+y*s;
        out[9 ] = y*z*(1-c)-x*s;
        out[10] = z*z*(1-c)+c;
        out[11] = 0.0f;

        out[12] = 0;
        out[13] = 0;
        out[14] = 0;
        out[15] = 1;
    }

    /**
     * Multiplies an arbitrary matrix with a rotation matrix.
     *
     * @param mat     Input matrix.
     * @param radians Degree of rotation.
     * @param x       X-Coord of rotation axis.
     * @param y       Y-Coord of rotation axis.
     * @param z       Z-Coord of rotation axis.
     * @param out     Length-16 array to hold output on return.
     */
    public static void rotate( float[] mat, float radians, float x, float y, float z, float[] out ) {
        double cc = Math.cos( radians );
        float c = (float)cc;
        float s = (float)Math.sqrt( 1.0 - cc * cc );
        float invSum = 1f / (float)Math.sqrt( x*x + y*y + z*z );
        x *= invSum;
        y *= invSum;
        z *= invSum;

        float a00 = x*x*(1-c)+c;
        float a01 = x*y*(1-c)+z*s;
        float a02 = x*z*(1-c)-y*s;
        float a04 = x*y*(1-c)-z*s;
        float a05 = y*y*(1-c)+c;
        float a06 = y*z*(1-c)+x*s;
        float a08 = x*z*(1-c)+y*s;
        float a09 = y*z*(1-c)-x*s;
        float a10 = z*z*(1-c)+c;

        float b0 = mat[ 0];
        float b1 = mat[ 4];
        float b2 = mat[ 8];
        float b3 = mat[12];
        out[ 0] = a00*b0 + a01*b1 + a02*b2;
        out[ 4] = a04*b0 + a05*b1 + a06*b2;
        out[ 8] = a08*b0 + a09*b1 + a10*b2;
        out[12] = b3;
        b0 = mat[ 1];
        b1 = mat[ 5];
        b2 = mat[ 9];
        b3 = mat[13];
        out[ 1] = a00*b0 + a01*b1 + a02*b2;
        out[ 5] = a04*b0 + a05*b1 + a06*b2;
        out[ 9] = a08*b0 + a09*b1 + a10*b2;
        out[13] = b3;
        b0 = mat[ 2];
        b1 = mat[ 6];
        b2 = mat[10];
        b3 = mat[14];
        out[ 2] = a00*b0 + a01*b1 + a02*b2;
        out[ 6] = a04*b0 + a05*b1 + a06*b2;
        out[10] = a08*b0 + a09*b1 + a10*b2;
        out[14] = b3;
        b0 = mat[ 3];
        b1 = mat[ 7];
        b2 = mat[11];
        b3 = mat[15];
        out[ 3] = a00*b0 + a01*b1 + a02*b2;
        out[ 7] = a04*b0 + a05*b1 + a06*b2;
        out[11] = a08*b0 + a09*b1 + a10*b2;
        out[15] = b3;
    }

    /**
     * Multiplies a rotation matrix with an arbitrary matrix.
     *
     * @param radians Degree of rotation.
     * @param x       X-Coord of rotation axis.
     * @param y       Y-Coord of rotation axis.
     * @param z       Z-Coord of rotation axis.
     * @param mat     Input matrix.
     * @param out     Length-16 array to hold output on return.
     */
    public static void preRotate( float radians, float x, float y, float z, float[] mat, float[] out ) {
        double cc = Math.cos( radians );
        float c = (float)cc;
        float s = (float)Math.sqrt( 1.0 - cc * cc );
        float sum = 1f / (float)Math.sqrt( x*x + y*y + z*z );
        x *= sum;
        y *= sum;
        z *= sum;

        float a00 = x*x*(1-c)+c;
        float a01 = x*y*(1-c)+z*s;
        float a02 = x*z*(1-c)-y*s;
        float a04 = x*y*(1-c)-z*s;
        float a05 = y*y*(1-c)+c;
        float a06 = y*z*(1-c)+x*s;
        float a08 = x*z*(1-c)+y*s;
        float a09 = y*z*(1-c)-x*s;
        float a10 = z*z*(1-c)+c;
        float b0 = mat[0];
        float b1 = mat[1];
        float b2 = mat[2];
        float b3 = mat[3];
        out[ 0] = a00*b0 + a04*b1 + a08*b2;
        out[ 1] = a01*b0 + a05*b1 + a09*b2;
        out[ 2] = a02*b0 + a06*b1 + a10*b2;
        out[ 3] = b3;
        b0 = mat[4];
        b1 = mat[5];
        b2 = mat[6];
        b3 = mat[7];
        out[ 4] = a00*b0 + a04*b1 + a08*b2;
        out[ 5] = a01*b0 + a05*b1 + a09*b2;
        out[ 6] = a02*b0 + a06*b1 + a10*b2;
        out[ 7] = b3;
        b0 = mat[8];
        b1 = mat[9];
        b2 = mat[10];
        b3 = mat[11];
        out[ 8] = a00*b0 + a04*b1 + a08*b2;
        out[ 9] = a01*b0 + a05*b1 + a09*b2;
        out[10] = a02*b0 + a06*b1 + a10*b2;
        out[11] = b3;
        b0 = mat[12];
        b1 = mat[13];
        b2 = mat[14];
        b3 = mat[15];
        out[12] = a00*b0 + a04*b1 + a08*b2;
        out[13] = a01*b0 + a05*b1 + a09*b2;
        out[14] = a02*b0 + a06*b1 + a10*b2;
        out[15] = b3;
    }


    public static void scaling( float sx, float sy, float sz, float[] out ) {
        out[ 0] = sx;
        out[ 1] = 0.0f;
        out[ 2] = 0.0f;
        out[ 3] = 0.0f;
        out[ 4] = 0.0f;
        out[ 5] = sy;
        out[ 6] = 0.0f;
        out[ 7] = 0.0f;
        out[ 8] = 0.0f;
        out[ 9] = 0.0f;
        out[10] = sz;
        out[11] = 0.0f;
        out[12] = 0.0f;
        out[13] = 0.0f;
        out[14] = 0.0f;
        out[15] = 1.0f;
    }


    public static void scale( float[] mat, float sx, float sy, float sz, float[] out ) {
        out[ 0] = sx * mat[ 0];
        out[ 1] = sx * mat[ 1];
        out[ 2] = sx * mat[ 2];
        out[ 3] = sx * mat[ 3];
        out[ 4] = sy * mat[ 4];
        out[ 5] = sy * mat[ 5];
        out[ 6] = sy * mat[ 6];
        out[ 7] = sy * mat[ 7];
        out[ 8] = sz * mat[ 8];
        out[ 9] = sz * mat[ 9];
        out[10] = sz * mat[10];
        out[11] = sz * mat[11];
        out[12] =      mat[12];
        out[13] =      mat[13];
        out[14] =      mat[14];
        out[15] =      mat[15];
    }


    public static void preScale( float sx, float sy, float sz, float[] mat, float[] out ) {
        out[ 0] = sx * mat[ 0];
        out[ 1] = sy * mat[ 1];
        out[ 2] = sz * mat[ 2];
        out[ 3] =      mat[ 3];
        out[ 4] = sx * mat[ 4];
        out[ 5] = sy * mat[ 5];
        out[ 6] = sz * mat[ 6];
        out[ 7] =      mat[ 7];
        out[ 8] = sx * mat[ 8];
        out[ 9] = sy * mat[ 9];
        out[10] = sz * mat[10];
        out[11] =      mat[11];
        out[12] = sx * mat[12];
        out[13] = sy * mat[13];
        out[14] = sz * mat[14];
        out[15] =      mat[15];
    }


    public static void translation( float tx, float ty, float tz, float[] out ) {
        out[ 0] = 1.0f;
        out[ 1] = 0.0f;
        out[ 2] = 0.0f;
        out[ 3] = 0.0f;
        out[ 4] = 0.0f;
        out[ 5] = 1.0f;
        out[ 6] = 0.0f;
        out[ 7] = 0.0f;
        out[ 8] = 0.0f;
        out[ 9] = 0.0f;
        out[10] = 1.0f;
        out[11] = 0.0f;
        out[12] = tx;
        out[13] = ty;
        out[14] = tz;
        out[15] = 1.0f;
    }


    public static void translate( float[] mat, float tx, float ty, float tz, float[] out ) {
        float b0 = mat[ 0];
        float b1 = mat[ 4];
        float b2 = mat[ 8];
        float b3 = mat[12];
        out[ 0] = b0;
        out[ 4] = b1;
        out[ 8] = b2;
        out[12] = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat[ 1];
        b1 = mat[ 5];
        b2 = mat[ 9];
        b3 = mat[13];
        out[ 1] = b0;
        out[ 5] = b1;
        out[ 9] = b2;
        out[13] = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat[ 2];
        b1 = mat[ 6];
        b2 = mat[10];
        b3 = mat[14];
        out[ 2] = b0;
        out[ 6] = b1;
        out[10] = b2;
        out[14] = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat[ 3];
        b1 = mat[ 7];
        b2 = mat[11];
        b3 = mat[15];
        out[ 3] = b0;
        out[ 7] = b1;
        out[11] = b2;
        out[15] = tx * b0 + ty * b1 + tz * b2 + b3;
    }


    public static void preTranslate( float tx, float ty, float tz, float[] mat, float[] out ) {
        float b0 = mat[0];
        float b1 = mat[1];
        float b2 = mat[2];
        float b3 = mat[3];
        out[0] = b0 + tx * b3;
        out[1] = b1 + ty * b3;
        out[2] = b2 + tz * b3;
        out[3] = b3;
        b0 = mat[4];
        b1 = mat[5];
        b2 = mat[6];
        b3 = mat[7];
        out[ 4] = b0 + tx * b3;
        out[ 5] = b1 + ty * b3;
        out[ 6] = b2 + tz * b3;
        out[ 7] = b3;
        b0 = mat[8];
        b1 = mat[9];
        b2 = mat[10];
        b3 = mat[11];
        out[ 8] = b0 + tx * b3;
        out[ 9] = b1 + ty * b3;
        out[10] = b2 + tz * b3;
        out[11] = b3;
        b0 = mat[12];
        b1 = mat[13];
        b2 = mat[14];
        b3 = mat[15];
        out[12] = b0 + tx * b3;
        out[13] = b1 + ty * b3;
        out[14] = b2 + tz * b3;
        out[15] = b3;
    }


    public static void frustum( float left, float right, float bottom, float top, float near, float far, float[] out ) {
        out[ 0] = 2.0f * near / (right - left);
        out[ 1] = 0;
        out[ 2] = 0;
        out[ 3] = 0;

        out[ 4] = 0;
        out[ 5] = 2 * near / (top - bottom);
        out[ 6] = 0;
        out[ 7] = 0;

        out[ 8] = (right + left) / (right - left);
        out[ 9] = (top + bottom) / (top - bottom);
        out[10] = -(far + near) / (far - near);
        out[11] = -1;

        out[12] = 0;
        out[13] = 0;
        out[14] = -(2 * far * near) / (far - near);
        out[15] = 0;
    }
    
    
    public static void ortho( float left, float right, float bottom, float top, float near, float far, float[] out ) {
        out[ 0] = 2.0f / (right - left);
        out[ 1] = 0;
        out[ 2] = 0;
        out[ 3] = 0;
        
        out[ 4] = 0;
        out[ 5] = 2.0f / (top - bottom);
        out[ 6] = 0;
        out[ 7] = 0;
        
        out[ 8] = 0;
        out[ 9] = 0;
        out[10] = -2.0f / (far - near);
        out[11] = 0;
        
        out[12] = -(right + left) / (right - left);
        out[13] = -(top + bottom) / (top - bottom);
        out[14] = -(far + near) / (far - near);
        out[15] = 1;
    }
    
    
    public static void lookAt( float[] eyeVec, float[] centerVec, float[] upVec, float[] outMat ) {
        float fx  = centerVec[0] - eyeVec[0];
        float fy  = centerVec[1] - eyeVec[1];
        float fz  = centerVec[2] - eyeVec[2];
        float len = (float)( 1.0 / Math.sqrt( fx * fx + fy * fy + fz * fz ) );
        fx *= len;
        fy *= len;
        fz *= len;
        
        float ux = upVec[0];
        float uy = upVec[1];
        float uz = upVec[2];
        len = (float)( 1.0 / Math.sqrt( ux * ux + uy * uy + uz * uz ) );
        ux /= len;
        uy /= len;
        uz /= len;
        
        float sx = fy * uz - fz * uy;
        float sy = fz * ux - fx * uz;
        float sz = fx * uy - fy * ux;
        
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
        outMat[15] = 1;
    }
    

    public static void viewport( float x, float y, float w, float h, float[] out ) {
        out[ 0] = w * 0.5f;
        out[ 1] = 0;
        out[ 2] = 0;
        out[ 3] = 0;
        
        out[ 4] = 0;
        out[ 5] = h * 0.5f;
        out[ 6] = 0;
        out[ 7] = 0;
        
        out[ 8] = 0;
        out[ 9] = 0;
        out[10] = 1;
        out[11] = 0;
        
        out[12] = w * 0.5f + x;
        out[13] = h * 0.5f + y;
        out[14] = 0;
        out[15] = 1;
    }

    /**
     * Removes any translation/scaling/skew or other non-rotation 
     * transformations from a matrix.  
     * 
     * @param mat 4x4 homography matrix to turn into strict rotation matrix.
     */
    public static void normalizeRotationMatrix( float[] mat ) {
        float d;
        
        //Kill translation, scalings.
        mat[ 3] = 0;
        mat[ 7] = 0;
        mat[11] = 0;
        mat[12] = 0;
        mat[13] = 0;
        mat[14] = 0;
        mat[15] = 1;
        
        //Normalize length of X-axis.
        d = (float)Math.sqrt( mat[0] * mat[0] + mat[1] * mat[1] + mat[2] * mat[2] );
        if( d > FSQRT_ABS_ERR || -d > FSQRT_ABS_ERR ) {
            d = 1f / d;
            mat[0] *= d;
            mat[1] *= d;
            mat[2] *= d;
        }else{
            mat[0] = 1;
            mat[1] = 0;
            mat[2] = 0;
        }
        
        //Orthogonalize Y-axis to X-axis
        d = mat[0] * mat[4] + mat[1] * mat[5] + mat[2] * mat[6];
        mat[4] -= d * mat[0];
        mat[5] -= d * mat[1];
        mat[6] -= d * mat[2];
        
        //Normalize Y-axis.
        d = (float)Math.sqrt( mat[4] * mat[4] + mat[5] * mat[5] + mat[6] * mat[6] );
        if( d > FSQRT_ABS_ERR || -d > FSQRT_ABS_ERR ) {
            d = 1.0f / d;
            mat[4] *= d;
            mat[5] *= d;
            mat[6] *= d;
        }else{
            float m0 = mat[0];
            float m1 = mat[1];
            float m2 = mat[2];
            Vec3.chooseOrtho( m0, m1, m2, mat );
            mat[4] = mat[0];
            mat[5] = mat[1];
            mat[6] = mat[2];
            mat[0] = m0;
            mat[1] = m1;
            mat[2] = m2;
        }
        
        //Compute Z-axis
        mat[ 8] = mat[1]*mat[6] - mat[2]*mat[5];
        mat[ 9] = mat[2]*mat[4] - mat[0]*mat[6];
        mat[10] = mat[0]*mat[5] - mat[1]*mat[4];
    }
    
    
    public static void axesToTransform( float[] x, float[] y, float[] out ) {
        float[] z = new float[3];
        Vec3.cross( x, y, z );
        axesToTransform( x, y, z, out );
    }
    
    
    public static void axesToTransform( float[] x, float[] y, float[] z, float[] out ) {
        out[ 0] = x[0];
        out[ 1] = x[1];
        out[ 2] = x[2];
        out[ 3] = 0;
        out[ 4] = y[0];
        out[ 5] = y[1];
        out[ 6] = y[2];
        out[ 7] = 0;
        out[ 8] = z[0];
        out[ 9] = z[1];
        out[10] = z[2];
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
    public static void alignBasisVectorToAxis( float[] mat, int basis, float[] out ) {
        out[12] = mat[basis*4  ];
        out[13] = mat[basis*4+1];
        out[14] = mat[basis*4+2];
        
        // Clamp specified vector to nearest axis.
        Vec3.nearestAxis( mat[basis*4  ], mat[basis*4+1], mat[basis*4+2], out );
        
        // Move into correct column.
        out[basis*4  ] = out[0];
        out[basis*4+1] = out[1];
        out[basis*4+2] = out[2];
        
        // Cross with next axis.
        final int basis1 = ( basis + 1 ) % 3;
        final int basis2 = ( basis + 2 ) % 3;
        
        Vec3.cross( out, basis*4, mat, basis1*4, out, basis2*4 );
        Arr.normalize( out, basis2*4, 3, 1.0f );
        Vec3.cross( out, basis2*4, out, basis*4, out, basis1*4 );
        
        out[ 3] = 0.0f;
        out[ 7] = 0.0f;
        out[11] = 0.0f;
        out[12] = 0.0f;
        out[13] = 0.0f;
        out[14] = 0.0f;
        out[15] = 1.0f;
    }

    
    public static String format( float[] mat ) {
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


    public static boolean isNaN( float[] mat ) {
        for( int i = 0; i < 16; i++ ) {
            if( Float.isNaN( mat[i] ) ) {
                return false;
            }
        }
        return true;
    }



    /**
     * Multiplies two matrices.
     *
     * @param a   Length-16 array. Holds matrix in column-major ordering.
     * @param b   Length-16 array. Holds matrix in column-major ordering.
     * @param out Length-16 array. On return, holds <tt>a * b</tt>. May be same array as <tt>a</tt> or <tt>b</tt>.
     */
    public static void mult( double[] a, double[] b, double[] out ) {
        // I tested many configurations for multiplication.
        // This one was the fastest as well as avoids array aliasing without branching.
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


    public static void multVec3( double[] a, double[] b, double[] out ) {
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


    public static void multVec3( double[] a, int offA, double[] b, int offB, double[] out, int offOut ) {
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
     * @param mat Input matrix
     * @param out Array to hold inverted matrix on return.
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
        boolean ret  = invdet > SQRT_ABS_ERR || -invdet > SQRT_ABS_ERR;
        // Invert determinant
        invdet = 1.0 / invdet;

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


    public static void transpose( double[] mat, double[] out ) {
        // About 15% faster without local copies.
        double a00 = mat[ 0];
        double a01 = mat[ 1];
        double a02 = mat[ 2];
        double a03 = mat[ 3];
        double a04 = mat[ 4];
        double a05 = mat[ 5];
        double a06 = mat[ 6];
        double a07 = mat[ 7];
        double a08 = mat[ 8];
        double a09 = mat[ 9];
        double a10 = mat[10];
        double a11 = mat[11];
        double a12 = mat[12];
        double a13 = mat[13];
        double a14 = mat[14];
        double a15 = mat[15];

        out[ 0] = a00;
        out[ 1] = a04;
        out[ 2] = a08;
        out[ 3] = a12;

        out[ 4] = a01;
        out[ 5] = a05;
        out[ 6] = a09;
        out[ 7] = a13;

        out[ 8] = a02;
        out[ 9] = a06;
        out[10] = a10;
        out[11] = a14;

        out[12] = a03;
        out[13] = a07;
        out[14] = a11;
        out[15] = a15;
    }


    public static void identity( double[] out ) {
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

    /**
     * Computes a rotation matrix.
     *
     * @param radians Degree of rotation.
     * @param x       X-Coord of rotation axis.
     * @param y       Y-Coord of rotation axis.
     * @param z       Z-Coord of rotation axis.
     * @param out     Length-16 array to hold output on return.
     */
    public static void rotation( double radians, double x, double y, double z, double[] out ) {
        double cc = Math.cos( radians );
        double c = cc;
        double s = Math.sqrt( 1.0 - cc * cc );
        double sum = 1 / Math.sqrt( x*x + y*y + z*z );
        x *= sum;
        y *= sum;
        z *= sum;

        out[0 ] = x*x*(1-c)+c;
        out[1 ] = x*y*(1-c)+z*s;
        out[2 ] = x*z*(1-c)-y*s;
        out[3 ] = 0;

        out[4 ] = x*y*(1-c)-z*s;
        out[5 ] = y*y*(1-c)+c;
        out[6 ] = y*z*(1-c)+x*s;
        out[7 ] = 0;

        out[8 ] = x*z*(1-c)+y*s;
        out[9 ] = y*z*(1-c)-x*s;
        out[10] = z*z*(1-c)+c;
        out[11] = 0.0;

        out[12] = 0;
        out[13] = 0;
        out[14] = 0;
        out[15] = 1;
    }

    /**
     * Multiplies an arbitrary matrix with a rotation matrix.
     *
     * @param mat     Input matrix.
     * @param radians Degree of rotation.
     * @param x       X-Coord of rotation axis.
     * @param y       Y-Coord of rotation axis.
     * @param z       Z-Coord of rotation axis.
     * @param out     Length-16 array to hold output on return.
     */
    public static void rotate( double[] mat, double radians, double x, double y, double z, double[] out ) {
        double cc = Math.cos( radians );
        double c = cc;
        double s = Math.sqrt( 1.0 - cc * cc );
        double invSum = 1 / Math.sqrt( x*x + y*y + z*z );
        x *= invSum;
        y *= invSum;
        z *= invSum;

        double a00 = x*x*(1-c)+c;
        double a01 = x*y*(1-c)+z*s;
        double a02 = x*z*(1-c)-y*s;
        double a04 = x*y*(1-c)-z*s;
        double a05 = y*y*(1-c)+c;
        double a06 = y*z*(1-c)+x*s;
        double a08 = x*z*(1-c)+y*s;
        double a09 = y*z*(1-c)-x*s;
        double a10 = z*z*(1-c)+c;

        double b0 = mat[ 0];
        double b1 = mat[ 4];
        double b2 = mat[ 8];
        double b3 = mat[12];
        out[ 0] = a00*b0 + a01*b1 + a02*b2;
        out[ 4] = a04*b0 + a05*b1 + a06*b2;
        out[ 8] = a08*b0 + a09*b1 + a10*b2;
        out[12] = b3;
        b0 = mat[ 1];
        b1 = mat[ 5];
        b2 = mat[ 9];
        b3 = mat[13];
        out[ 1] = a00*b0 + a01*b1 + a02*b2;
        out[ 5] = a04*b0 + a05*b1 + a06*b2;
        out[ 9] = a08*b0 + a09*b1 + a10*b2;
        out[13] = b3;
        b0 = mat[ 2];
        b1 = mat[ 6];
        b2 = mat[10];
        b3 = mat[14];
        out[ 2] = a00*b0 + a01*b1 + a02*b2;
        out[ 6] = a04*b0 + a05*b1 + a06*b2;
        out[10] = a08*b0 + a09*b1 + a10*b2;
        out[14] = b3;
        b0 = mat[ 3];
        b1 = mat[ 7];
        b2 = mat[11];
        b3 = mat[15];
        out[ 3] = a00*b0 + a01*b1 + a02*b2;
        out[ 7] = a04*b0 + a05*b1 + a06*b2;
        out[11] = a08*b0 + a09*b1 + a10*b2;
        out[15] = b3;
    }

    /**
     * Multiplies a rotation matrix with an arbitrary matrix.
     *
     * @param radians Degree of rotation.
     * @param x       X-Coord of rotation axis.
     * @param y       Y-Coord of rotation axis.
     * @param z       Z-Coord of rotation axis.
     * @param mat     Input matrix.
     * @param out     Length-16 array to hold output on return.
     */
    public static void preRotate( double radians, double x, double y, double z, double[] mat, double[] out ) {
        double cc = Math.cos( radians );
        double c = cc;
        double s = Math.sqrt( 1.0 - cc * cc );
        double sum = 1 / Math.sqrt( x*x + y*y + z*z );
        x *= sum;
        y *= sum;
        z *= sum;

        double a00 = x*x*(1-c)+c;
        double a01 = x*y*(1-c)+z*s;
        double a02 = x*z*(1-c)-y*s;
        double a04 = x*y*(1-c)-z*s;
        double a05 = y*y*(1-c)+c;
        double a06 = y*z*(1-c)+x*s;
        double a08 = x*z*(1-c)+y*s;
        double a09 = y*z*(1-c)-x*s;
        double a10 = z*z*(1-c)+c;
        double b0 = mat[0];
        double b1 = mat[1];
        double b2 = mat[2];
        double b3 = mat[3];
        out[ 0] = a00*b0 + a04*b1 + a08*b2;
        out[ 1] = a01*b0 + a05*b1 + a09*b2;
        out[ 2] = a02*b0 + a06*b1 + a10*b2;
        out[ 3] = b3;
        b0 = mat[4];
        b1 = mat[5];
        b2 = mat[6];
        b3 = mat[7];
        out[ 4] = a00*b0 + a04*b1 + a08*b2;
        out[ 5] = a01*b0 + a05*b1 + a09*b2;
        out[ 6] = a02*b0 + a06*b1 + a10*b2;
        out[ 7] = b3;
        b0 = mat[8];
        b1 = mat[9];
        b2 = mat[10];
        b3 = mat[11];
        out[ 8] = a00*b0 + a04*b1 + a08*b2;
        out[ 9] = a01*b0 + a05*b1 + a09*b2;
        out[10] = a02*b0 + a06*b1 + a10*b2;
        out[11] = b3;
        b0 = mat[12];
        b1 = mat[13];
        b2 = mat[14];
        b3 = mat[15];
        out[12] = a00*b0 + a04*b1 + a08*b2;
        out[13] = a01*b0 + a05*b1 + a09*b2;
        out[14] = a02*b0 + a06*b1 + a10*b2;
        out[15] = b3;
    }


    public static void scaling( double sx, double sy, double sz, double[] out ) {
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


    public static void scale( double[] mat, double sx, double sy, double sz, double[] out ) {
        out[ 0] = sx * mat[ 0];
        out[ 1] = sx * mat[ 1];
        out[ 2] = sx * mat[ 2];
        out[ 3] = sx * mat[ 3];
        out[ 4] = sy * mat[ 4];
        out[ 5] = sy * mat[ 5];
        out[ 6] = sy * mat[ 6];
        out[ 7] = sy * mat[ 7];
        out[ 8] = sz * mat[ 8];
        out[ 9] = sz * mat[ 9];
        out[10] = sz * mat[10];
        out[11] = sz * mat[11];
        out[12] =      mat[12];
        out[13] =      mat[13];
        out[14] =      mat[14];
        out[15] =      mat[15];
    }


    public static void preScale( double sx, double sy, double sz, double[] mat, double[] out ) {
        out[ 0] = sx * mat[ 0];
        out[ 1] = sy * mat[ 1];
        out[ 2] = sz * mat[ 2];
        out[ 3] =      mat[ 3];
        out[ 4] = sx * mat[ 4];
        out[ 5] = sy * mat[ 5];
        out[ 6] = sz * mat[ 6];
        out[ 7] =      mat[ 7];
        out[ 8] = sx * mat[ 8];
        out[ 9] = sy * mat[ 9];
        out[10] = sz * mat[10];
        out[11] =      mat[11];
        out[12] = sx * mat[12];
        out[13] = sy * mat[13];
        out[14] = sz * mat[14];
        out[15] =      mat[15];
    }


    public static void translation( double tx, double ty, double tz, double[] out ) {
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


    public static void translate( double[] mat, double tx, double ty, double tz, double[] out ) {
        double b0 = mat[ 0];
        double b1 = mat[ 4];
        double b2 = mat[ 8];
        double b3 = mat[12];
        out[ 0] = b0;
        out[ 4] = b1;
        out[ 8] = b2;
        out[12] = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat[ 1];
        b1 = mat[ 5];
        b2 = mat[ 9];
        b3 = mat[13];
        out[ 1] = b0;
        out[ 5] = b1;
        out[ 9] = b2;
        out[13] = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat[ 2];
        b1 = mat[ 6];
        b2 = mat[10];
        b3 = mat[14];
        out[ 2] = b0;
        out[ 6] = b1;
        out[10] = b2;
        out[14] = tx * b0 + ty * b1 + tz * b2 + b3;
        b0 = mat[ 3];
        b1 = mat[ 7];
        b2 = mat[11];
        b3 = mat[15];
        out[ 3] = b0;
        out[ 7] = b1;
        out[11] = b2;
        out[15] = tx * b0 + ty * b1 + tz * b2 + b3;
    }


    public static void preTranslate( double tx, double ty, double tz, double[] mat, double[] out ) {
        double b0 = mat[0];
        double b1 = mat[1];
        double b2 = mat[2];
        double b3 = mat[3];
        out[0] = b0 + tx * b3;
        out[1] = b1 + ty * b3;
        out[2] = b2 + tz * b3;
        out[3] = b3;
        b0 = mat[4];
        b1 = mat[5];
        b2 = mat[6];
        b3 = mat[7];
        out[ 4] = b0 + tx * b3;
        out[ 5] = b1 + ty * b3;
        out[ 6] = b2 + tz * b3;
        out[ 7] = b3;
        b0 = mat[8];
        b1 = mat[9];
        b2 = mat[10];
        b3 = mat[11];
        out[ 8] = b0 + tx * b3;
        out[ 9] = b1 + ty * b3;
        out[10] = b2 + tz * b3;
        out[11] = b3;
        b0 = mat[12];
        b1 = mat[13];
        b2 = mat[14];
        b3 = mat[15];
        out[12] = b0 + tx * b3;
        out[13] = b1 + ty * b3;
        out[14] = b2 + tz * b3;
        out[15] = b3;
    }


    public static void frustum( double left, double right, double bottom, double top, double near, double far, double[] out ) {
        out[ 0] = 2.0 * near / (right - left);
        out[ 1] = 0;
        out[ 2] = 0;
        out[ 3] = 0;

        out[ 4] = 0;
        out[ 5] = 2 * near / (top - bottom);
        out[ 6] = 0;
        out[ 7] = 0;

        out[ 8] = (right + left) / (right - left);
        out[ 9] = (top + bottom) / (top - bottom);
        out[10] = -(far + near) / (far - near);
        out[11] = -1;

        out[12] = 0;
        out[13] = 0;
        out[14] = -(2 * far * near) / (far - near);
        out[15] = 0;
    }


    public static void ortho( double left, double right, double bottom, double top, double near, double far, double[] out ) {
        out[ 0] = 2.0 / (right - left);
        out[ 1] = 0;
        out[ 2] = 0;
        out[ 3] = 0;

        out[ 4] = 0;
        out[ 5] = 2.0 / (top - bottom);
        out[ 6] = 0;
        out[ 7] = 0;

        out[ 8] = 0;
        out[ 9] = 0;
        out[10] = -2.0 / (far - near);
        out[11] = 0;

        out[12] = -(right + left) / (right - left);
        out[13] = -(top + bottom) / (top - bottom);
        out[14] = -(far + near) / (far - near);
        out[15] = 1;
    }


    public static void lookAt( double[] eyeVec, double[] centerVec, double[] upVec, double[] outMat ) {
        double fx  = centerVec[0] - eyeVec[0];
        double fy  = centerVec[1] - eyeVec[1];
        double fz  = centerVec[2] - eyeVec[2];
        double len = ( 1.0 / Math.sqrt( fx * fx + fy * fy + fz * fz ) );
        fx *= len;
        fy *= len;
        fz *= len;

        double ux = upVec[0];
        double uy = upVec[1];
        double uz = upVec[2];
        len = ( 1.0 / Math.sqrt( ux * ux + uy * uy + uz * uz ) );
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
        outMat[15] = 1;
    }


    public static void viewport( double x, double y, double w, double h, double[] out ) {
        out[ 0] = w * 0.5;
        out[ 1] = 0;
        out[ 2] = 0;
        out[ 3] = 0;

        out[ 4] = 0;
        out[ 5] = h * 0.5;
        out[ 6] = 0;
        out[ 7] = 0;

        out[ 8] = 0;
        out[ 9] = 0;
        out[10] = 1;
        out[11] = 0;

        out[12] = w * 0.5 + x;
        out[13] = h * 0.5 + y;
        out[14] = 0;
        out[15] = 1;
    }

    /**
     * Removes any translation/scaling/skew or other non-rotation
     * transformations from a matrix.
     *
     * @param mat 4x4 homography matrix to turn into strict rotation matrix.
     */
    public static void normalizeRotationMatrix( double[] mat ) {
        double d;

        //Kill translation, scalings.
        mat[ 3] = 0;
        mat[ 7] = 0;
        mat[11] = 0;
        mat[12] = 0;
        mat[13] = 0;
        mat[14] = 0;
        mat[15] = 1;

        //Normalize length of X-axis.
        d = Math.sqrt( mat[0] * mat[0] + mat[1] * mat[1] + mat[2] * mat[2] );
        if( d > SQRT_ABS_ERR || -d > SQRT_ABS_ERR ) {
            d = 1 / d;
            mat[0] *= d;
            mat[1] *= d;
            mat[2] *= d;
        }else{
            mat[0] = 1;
            mat[1] = 0;
            mat[2] = 0;
        }

        //Orthogonalize Y-axis to X-axis
        d = mat[0] * mat[4] + mat[1] * mat[5] + mat[2] * mat[6];
        mat[4] -= d * mat[0];
        mat[5] -= d * mat[1];
        mat[6] -= d * mat[2];

        //Normalize Y-axis.
        d = Math.sqrt( mat[4] * mat[4] + mat[5] * mat[5] + mat[6] * mat[6] );
        if( d > SQRT_ABS_ERR || -d > SQRT_ABS_ERR ) {
            d = 1.0 / d;
            mat[4] *= d;
            mat[5] *= d;
            mat[6] *= d;
        }else{
            double m0 = mat[0];
            double m1 = mat[1];
            double m2 = mat[2];
            Vec3.chooseOrtho( m0, m1, m2, mat );
            mat[4] = mat[0];
            mat[5] = mat[1];
            mat[6] = mat[2];
            mat[0] = m0;
            mat[1] = m1;
            mat[2] = m2;
        }

        //Compute Z-axis
        mat[ 8] = mat[1]*mat[6] - mat[2]*mat[5];
        mat[ 9] = mat[2]*mat[4] - mat[0]*mat[6];
        mat[10] = mat[0]*mat[5] - mat[1]*mat[4];
    }


    public static void axesToTransform( double[] x, double[] y, double[] out ) {
        double[] z = new double[3];
        Vec3.cross( x, y, z );
        axesToTransform( x, y, z, out );
    }


    public static void axesToTransform( double[] x, double[] y, double[] z, double[] out ) {
        out[ 0] = x[0];
        out[ 1] = x[1];
        out[ 2] = x[2];
        out[ 3] = 0;
        out[ 4] = y[0];
        out[ 5] = y[1];
        out[ 6] = y[2];
        out[ 7] = 0;
        out[ 8] = z[0];
        out[ 9] = z[1];
        out[10] = z[2];
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
        Vec3.nearestAxis( mat[basis*4  ], mat[basis*4+1], mat[basis*4+2], out );

        // Move into correct column.
        out[basis*4  ] = out[0];
        out[basis*4+1] = out[1];
        out[basis*4+2] = out[2];

        // Cross with next axis.
        final int basis1 = ( basis + 1 ) % 3;
        final int basis2 = ( basis + 2 ) % 3;

        Vec3.cross( out, basis*4, mat, basis1*4, out, basis2*4 );
        Arr.normalize( out, basis2*4, 3, 1.0 );
        Vec3.cross( out, basis2*4, out, basis*4, out, basis1*4 );

        out[ 3] = 0.0;
        out[ 7] = 0.0;
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
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


    public static boolean isNaN( double[] mat ) {
        for( int i = 0; i < 16; i++ ) {
            if( Double.isNaN( mat[i] ) ) {
                return false;
            }
        }
        return true;
    }




    private Mat4() {}


    @Deprecated public static void trans( float[] a, float[] out ) {
        transpose( a, out );
    }


    @Deprecated public static void scale( float sx, float sy, float sz, float[] out ) {
        scaling( sx, sy, sz, out );
    }


    @Deprecated public static boolean isValid( float[] mat ) {
        return isNaN( mat );
    }

}
