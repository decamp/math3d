package bits.math3d;

import static bits.math3d.Tol.*;

import java.util.Random;



/** 
 * These are all homographic coordinates, yo.  Matrices are 4x4.  Vectors are 3x1.
 * 
 * @author Philip DeCamp  
 */
public final class Mat3 {
    
        
    public static void mult( float[] a, float[] b, float[] out ) {
        out[0] = a[0]*b[0] + a[3]*b[1] + a[6]*b[2];
        out[1] = a[1]*b[0] + a[4]*b[1] + a[7]*b[2];
        out[2] = a[2]*b[0] + a[5]*b[1] + a[8]*b[2];
        
        out[3] = a[0]*b[3] + a[3]*b[4] + a[6]*b[5];
        out[4] = a[1]*b[3] + a[4]*b[4] + a[7]*b[5];
        out[5] = a[2]*b[3] + a[5]*b[4] + a[8]*b[5];
        
        out[6] = a[0]*b[6] + a[3]*b[7] + a[6]*b[8];
        out[7] = a[1]*b[6] + a[4]*b[7] + a[7]*b[8];
        out[8] = a[2]*b[6] + a[5]*b[7] + a[8]*b[8];
    }
    
    
    public static void multVec3( float[] a, float[] b, float[] out ) {
        out[0] = a[0]*b[0] + a[3]*b[1] + a[6]*b[2];
        out[1] = a[1]*b[0] + a[4]*b[1] + a[7]*b[2];
        out[2] = a[2]*b[0] + a[5]*b[1] + a[8]*b[2];
    }

    
    public static void multVec3( float[] a, int offA, float[] b, int offB, float[] out, int offOut ) {
        out[0+offOut] = a[0+offA]*b[0+offB] + a[3+offA]*b[1+offB] + a[6+offA]*b[2+offB];
        out[1+offOut] = a[1+offA]*b[0+offB] + a[4+offA]*b[1+offB] + a[7+offA]*b[2+offB];
        out[2+offOut] = a[2+offA]*b[0+offB] + a[5+offA]*b[1+offB] + a[8+offA]*b[2+offB];
    }

    
    public static void multVec4( float[] a, float[] b, float[] out ) {
        multVec3( a, b, out );
        out[3] = b[3];
    }
    
    
    public static void multVec4( float[] a, int offA, float[] b, int offB, float[] out, int offOut ) {
        multVec3( a, offA, b, offB, out, offOut );
        out[offOut+3] = b[offB+3];
    }
    
    
    /**
     * @param mat    Input matrix
     * @param out    Array to hold inverted matrix on return.
     * @return true if matrix determinant is not near zero and accurate inverse was found.
     */    
    public static boolean invert( float[] mat, float[] out ) {
        float c00 = mat[1+1*3] * mat[2+2*3] - mat[2+1*3] * mat[1+2*3];
        float c01 = mat[2+0*3] * mat[1+2*3] - mat[1+0*3] * mat[2+2*3];
        float c02 = mat[1+0*3] * mat[2+1*3] - mat[2+0*3] * mat[1+1*3];
        float c10 = mat[2+1*3] * mat[0+2*3] - mat[0+1*3] * mat[2+2*3];
        float c11 = mat[0+0*3] * mat[2+2*3] - mat[2+0*3] * mat[0+2*3];
        float c12 = mat[2+0*3] * mat[0+1*3] - mat[0+0*3] * mat[2+1*3];
        float c20 = mat[0+1*3] * mat[1+2*3] - mat[1+1*3] * mat[0+2*3];
        float c21 = mat[1+0*3] * mat[0+2*3] - mat[0+0*3] * mat[1+2*3];
        float c22 = mat[0+0*3] * mat[1+1*3] - mat[1+0*3] * mat[0+1*3];
        
        // Compute determinant
        float invDet  = mat[0+0*3] * c00 + mat[0+1*3] * c01 + mat[0+2*3] * c02;
        // Check if invertible
        boolean valid = invDet > SQRT_ABS_ERR || -invDet > SQRT_ABS_ERR;
        // Invert determinant
        invDet = 1f / invDet;
        
        out[0] = c00 * invDet;
        out[1] = c01 * invDet;
        out[2] = c02 * invDet;
        out[3] = c10 * invDet;
        out[4] = c11 * invDet;
        out[5] = c12 * invDet;
        out[6] = c20 * invDet;
        out[7] = c21 * invDet;
        out[8] = c22 * invDet;
        
        return valid;
    }
    
    
    public static void trans( float[] a, float[] out ) {
        out[0] = a[0];
        out[1] = a[3];
        out[2] = a[6];
        out[3] = a[1];
        out[4] = a[4];
        out[5] = a[7];
        out[6] = a[2];
        out[7] = a[5];
        out[8] = a[8];
    }
    
    
    public static float det( float[] mat ) {
        return mat[0+0*3] * ( mat[1+1*3] * mat[2+2*3] - mat[2+1*3] * mat[1+2*3] ) +
               mat[0+1*3] * ( mat[2+0*3] * mat[1+2*3] - mat[1+0*3] * mat[2+2*3] ) +
               mat[0+2*3] * ( mat[1+0*3] * mat[2+1*3] - mat[2+0*3] * mat[1+1*3] );
    }
    
    
    public static void identity( float[] out ) {
        out[0] = 1f;
        out[1] = 0f;
        out[2] = 0f;
        out[3] = 0f;
        out[4] = 1f;
        out[5] = 0f;
        out[6] = 0f;
        out[7] = 0f;
        out[8] = 1f;
    }
    
    
    public static void scale( float sx, float sy, float sz, float[] out ) {
        out[0] = sx;
        out[1] = 0f;
        out[2] = 0f;
        out[3] = 0f;
        out[4] = sy;
        out[5] = 0f;
        out[6] = 0f;
        out[7] = 0f;
        out[8] = sz;
    }
    
    
    public static void rotation( float radians, float x, float y, float z, float[] out ) {
        float c = (float)Math.cos( radians );
        float s = (float)Math.sin( radians );
        
        float sum = 1f / (float)Math.sqrt( x*x + y*y + z*z );
        x *= sum;
        y *= sum;
        z *= sum;
        
        out[0] = x*x*(1-c)+c;
        out[1] = x*y*(1-c)+z*s;
        out[2] = x*z*(1-c)-y*s;
        
        out[3] = x*y*(1-c)-z*s;
        out[4] = y*y*(1-c)+c;
        out[5] = y*z*(1-c)+x*s;
        
        out[6] = x*z*(1-c)+y*s;
        out[7] = y*z*(1-c)-x*s;
        out[8] = z*z*(1-c)+c;
    }    

    /**
     * Removes any translation/scaling/skew or other non-rotation 
     * transformations from a matrix.  
     * 
     * @param mat 3x3 homography matrix to turn into strict rotation matrix.
     */
    public static void normalizeRotationMatrix( float[] mat ) {
        float d;
        
        //Normalize length of X-axis.
        d = (float)Math.sqrt( mat[0] * mat[0] + mat[1] * mat[1] + mat[2] * mat[2] );
        if( d > FSQRT_ABS_ERR || -d > FSQRT_ABS_ERR ) {
            d = 1f / d;
            mat[0] *= d;
            mat[1] *= d;
            mat[2] *= d;
        } else {
            mat[0] = 1;
            mat[1] = 0;
            mat[2] = 0;
        }
        
        //Orthogonalize Y-axis to X-axis
        d = mat[0] * mat[3] + mat[1] * mat[4] + mat[2] * mat[5];
        mat[3] -= d * mat[0];
        mat[4] -= d * mat[1];
        mat[5] -= d * mat[2];
        
        //Normalize Y-axis.
        d = (float)Math.sqrt( mat[3] * mat[3] + mat[4] * mat[4] + mat[5] * mat[5] );
        if( d > FSQRT_ABS_ERR || -d > FSQRT_ABS_ERR ) {
            d = 1.0f / d;
            mat[4] *= d;
            mat[5] *= d;
            mat[6] *= d;
        } else {
            float m0 = mat[0];
            float m1 = mat[1];
            float m2 = mat[2];
            Vec3.chooseOrtho( m0, m1, m2, mat );
            mat[3] = mat[0];
            mat[4] = mat[1];
            mat[5] = mat[2];
            mat[0] = m0;
            mat[1] = m1;
            mat[2] = m2;
        }
        
        //Compute Z-axis
        mat[6] = mat[1]*mat[5] - mat[2]*mat[4];
        mat[7] = mat[2]*mat[3] - mat[0]*mat[5];
        mat[8] = mat[0]*mat[4] - mat[1]*mat[3];
    }
    
    
    public static void axesToTransform( float[] x, float[] y, float[] out ) {
        float[] z = new float[3];
        Vec3.cross( x, y, z );
        axesToTransform( x, y, z, out );
    }
    

    public static void axesToTransform( float[] x, float[] y, float[] z, float[] out ) {
        out[0] = x[0];
        out[1] = x[1];
        out[2] = x[2];
        out[3] = y[0];
        out[4] = y[1];
        out[5] = y[2];
        out[6] = z[0];
        out[7] = z[1];
        out[8] = z[2];
    }

    
    public static String format( float[] mat ) {
        StringBuilder sb = new StringBuilder();
        for( int r = 0; r < 3; r++ ) {
            if ( r == 0 ) {
                sb.append( "[[ " );
            } else {
                sb.append( " [ " );
            }
            
            sb.append( String.format( "% 7.4f  % 7.4f  % 7.4f", mat[r   ], mat[r+3], mat[r+6] ) );
            
            if( r == 2 ) {
                sb.append( " ]]" );
            } else {
                sb.append( " ]\n" );
            }
        }
            
        return sb.toString();
    }

    
    public static boolean isValid( float[] mat ) {
        for( int i = 0; i < 16; i++ ) {
            if( Float.isNaN( mat[i] ) ) {
                return false;
            }
        }
        return true;
    }
    
    
    
    
    private Mat3() {}

    
    
    public static void main( String[] args ) {
        float[] a = new float[9];
        float[] b = new float[9];
        float[] c = new float[9];
        
        Random rand = new Random();
        for( int i = 0; i < 9; i++ ) {
            a[i] = rand.nextFloat();
        }
        
        a = new float[]{ 1, -1, 1, 2, 1, 2, 0, 1, 3 };
        
        invert( a, b );
        //scale( 1f, 2f, 3f, b );
        mult( b, a, c );
        
        System.out.println( format( a ) );
        System.out.println( format( b ) );
        System.out.println( format( c ) );
        
        
    }
    
   
    
}
