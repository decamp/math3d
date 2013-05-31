package cogmac.math3d;

import static cogmac.math3d.Tol.*;



/** 
 * These are all homographic coordinates, yo.  Matrices are 4x4.  Vectors are 3x1.
 * 
 * @author Philip DeCamp  
 */
public final class Mat4 {
    
        
    public static void mult( float[] a, float[] b, float[] out ) {
        out[ 0] = a[ 0]*b[ 0] + a[ 4]*b[ 1] + a[ 8]*b[ 2] + a[12]*b[ 3];
        out[ 1] = a[ 1]*b[ 0] + a[ 5]*b[ 1] + a[ 9]*b[ 2] + a[13]*b[ 3];
        out[ 2] = a[ 2]*b[ 0] + a[ 6]*b[ 1] + a[10]*b[ 2] + a[14]*b[ 3];
        out[ 3] = a[ 3]*b[ 0] + a[ 7]*b[ 1] + a[11]*b[ 2] + a[15]*b[ 3];
        
        out[ 4] = a[ 0]*b[ 4] + a[ 4]*b[ 5] + a[ 8]*b[ 6] + a[12]*b[ 7];
        out[ 5] = a[ 1]*b[ 4] + a[ 5]*b[ 5] + a[ 9]*b[ 6] + a[13]*b[ 7];
        out[ 6] = a[ 2]*b[ 4] + a[ 6]*b[ 5] + a[10]*b[ 6] + a[14]*b[ 7];
        out[ 7] = a[ 3]*b[ 4] + a[ 7]*b[ 5] + a[11]*b[ 6] + a[15]*b[ 7];
        
        out[ 8] = a[ 0]*b[ 8] + a[ 4]*b[ 9] + a[ 8]*b[10] + a[12]*b[11];
        out[ 9] = a[ 1]*b[ 8] + a[ 5]*b[ 9] + a[ 9]*b[10] + a[13]*b[11];
        out[10] = a[ 2]*b[ 8] + a[ 6]*b[ 9] + a[10]*b[10] + a[14]*b[11];
        out[11] = a[ 3]*b[ 8] + a[ 7]*b[ 9] + a[11]*b[10] + a[15]*b[11];
        
        out[12] = a[ 0]*b[12] + a[ 4]*b[13] + a[ 8]*b[14] + a[12]*b[15];
        out[13] = a[ 1]*b[12] + a[ 5]*b[13] + a[ 9]*b[14] + a[13]*b[15];
        out[14] = a[ 2]*b[12] + a[ 6]*b[13] + a[10]*b[14] + a[14]*b[15];
        out[15] = a[ 3]*b[12] + a[ 7]*b[13] + a[11]*b[14] + a[15]*b[15];
    }
    
    
    public static void multVec3( float[] a, float[] b, float[] out ) {
        out[0]  = a[ 0]*b[0] + a[ 4]*b[1] + a[ 8]*b[2] + a[12];
        out[1]  = a[ 1]*b[0] + a[ 5]*b[1] + a[ 9]*b[2] + a[13];
        out[2]  = a[ 2]*b[0] + a[ 6]*b[1] + a[10]*b[2] + a[14];
        float w = a[ 3]*b[0] + a[ 7]*b[1] + a[11]*b[2] + a[15];
        float err = w - 1.0f;
        if( err > FSQRT_ABS_ERR || -err > FSQRT_ABS_ERR ) {
            w = 1.0f / w;
            out[0] *= w;
            out[1] *= w;
            out[2] *= w;
        }
    }

    
    public static void multVec3( float[] a, int offA, float[] b, int offB, float[] out, int offOut ) {
        out[0+offOut] = a[ 0+offA]*b[0+offB] + a[ 4+offA]*b[1+offB] + a[ 8+offA]*b[2+offB] + a[12+offA];
        out[1+offOut] = a[ 1+offA]*b[0+offB] + a[ 5+offA]*b[1+offB] + a[ 9+offA]*b[2+offB] + a[13+offA];
        out[2+offOut] = a[ 2+offA]*b[0+offB] + a[ 6+offA]*b[1+offB] + a[10+offA]*b[2+offB] + a[14+offA];
        float w       = a[ 3+offA]*b[0+offB] + a[ 7+offA]*b[1+offB] + a[11+offA]*b[2+offB] + a[15+offA];
        float err = w - 1.0f;
        if( err > FSQRT_ABS_ERR || -err > FSQRT_ABS_ERR ) {
            w = 1.0f / w;
            out[0+offOut] *= w;
            out[1+offOut] *= w;
            out[2+offOut] *= w;
        }
    }
    
    
    public static void multVec4( float[] a, float[] b, float[] out ) {
        out[0] = a[ 0]*b[0] + a[ 4]*b[1] + a[ 8]*b[2] + a[12]*b[3];
        out[1] = a[ 1]*b[0] + a[ 5]*b[1] + a[ 9]*b[2] + a[13]*b[3];
        out[2] = a[ 2]*b[0] + a[ 6]*b[1] + a[10]*b[2] + a[14]*b[3];
        out[3] = a[ 3]*b[0] + a[ 7]*b[1] + a[11]*b[2] + a[15]*b[3];
    }
    
    
    
    
    /**
     * @param mat    Input matrix
     * @param out    Array to hold inverted matrix on return.
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

        // Should check for 0 determinant.
        float invdet = s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0;
        boolean ret   = invdet > SQRT_ABS_ERR || -invdet > SQRT_ABS_ERR;
        invdet = 1.0f / invdet;
        
        out[0+0*4] = ( mat[1+1*4] * c5 - mat[1+2*4] * c4 + mat[1+3*4] * c3) * invdet;
        out[0+1*4] = (-mat[0+1*4] * c5 + mat[0+2*4] * c4 - mat[0+3*4] * c3) * invdet;
        out[0+2*4] = ( mat[3+1*4] * s5 - mat[3+2*4] * s4 + mat[3+3*4] * s3) * invdet;
        out[0+3*4] = (-mat[2+1*4] * s5 + mat[2+2*4] * s4 - mat[2+3*4] * s3) * invdet;

        out[1+0*4] = (-mat[1+0*4] * c5 + mat[1+2*4] * c2 - mat[1+3*4] * c1) * invdet;
        out[1+1*4] = ( mat[0+0*4] * c5 - mat[0+2*4] * c2 + mat[0+3*4] * c1) * invdet;
        out[1+2*4] = (-mat[3+0*4] * s5 + mat[3+2*4] * s2 - mat[3+3*4] * s1) * invdet;
        out[1+3*4] = ( mat[2+0*4] * s5 - mat[2+2*4] * s2 + mat[2+3*4] * s1) * invdet;

        out[2+0*4] = ( mat[1+0*4] * c4 - mat[1+1*4] * c2 + mat[1+3*4] * c0) * invdet;
        out[2+1*4] = (-mat[0+0*4] * c4 + mat[0+1*4] * c2 - mat[0+3*4] * c0) * invdet;
        out[2+2*4] = ( mat[3+0*4] * s4 - mat[3+1*4] * s2 + mat[3+3*4] * s0) * invdet;
        out[2+3*4] = (-mat[2+0*4] * s4 + mat[2+1*4] * s2 - mat[2+3*4] * s0) * invdet;

        out[3+0*4] = (-mat[1+0*4] * c3 + mat[1+1*4] * c1 - mat[1+2*4] * c0) * invdet;
        out[3+1*4] = ( mat[0+0*4] * c3 - mat[0+1*4] * c1 + mat[0+2*4] * c0) * invdet;
        out[3+2*4] = (-mat[3+0*4] * s3 + mat[3+1*4] * s1 - mat[3+2*4] * s0) * invdet;
        out[3+3*4] = ( mat[2+0*4] * s3 - mat[2+1*4] * s1 + mat[2+2*4] * s0) * invdet;
            
        return ret;
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
    
    
    public static void scale( float sx, float sy, float sz, float[] out ) {
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
    
    
    public static void rotation( float radians, float x, float y, float z, float[] out ) {
        float c = (float)Math.cos( radians );
        float s = (float)Math.sin( radians );
        
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
        float err;
        
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
        Vec3.normalize( out, basis2*4, 1.0f );
        Vec3.cross( out, basis2*4, out, basis*4, out, basis1*4 );
        
        out[ 3] = 0.0f;
        out[ 7] = 0.0f;
        out[11] = 0.0f;
        out[12] = 0.0f;
        out[13] = 0.0f;
        out[14] = 0.0f;
        out[15] = 1.0f;
    }

    
    
//    public static void slerpRotations( float[] a,
//                                       float[] b,
//                                       float t,
//                                       float[] workQuatA,
//                                       float[] workQuatB,
//                                       float[] workQuatC,
//                                       float[] out )
//    {
//        Quats.matToQuat( a, workQuatA );
//        Quats.matToQuat( b, workQuatB );
//        Quats.slerp( workQuatA, workQuatB, t, workQuatC );
//        Quats.quatToMat( workQuatC, out );
//    }
    
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


    public static boolean isValid( float[] mat ) {
        for( int i = 0; i < 16; i++ ) {
            if( Double.isNaN( mat[i] ) ) {
                return false;
            }
        }
        return true;
    }
    
    
    
    private Mat4() {}

}
