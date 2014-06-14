package bits.math3d;


import java.util.Random;


/**
 * Methods for quaternions.
 * 
 * @author decamp
 */
public final class Quat {

    /**
     * Multiply two quaternions.
     * @param out Length-4 array to hold output on return. May be the same array as one of the inputs.
     */
    public static void mult( float[] a, float[] b, float[] out ) {
        // These local copies had no effect in performance tests, but whatevs. 
        final float a0 = a[0];
        final float a1 = a[1];
        final float a2 = a[2];
        final float a3 = a[3];
        final float b0 = b[0];
        final float b1 = b[1];
        final float b2 = b[2];
        final float b3 = b[3];
        out[0] = a0 * b0 - a1 * b1 - a2 * b2 - a3 * b3;
        out[1] = a0 * b1 + a1 * b0 + a2 * b3 - a3 * b2;
        out[2] = a0 * b2 - a1 * b3 + a2 * b0 + a3 * b1;
        out[3] = a0 * b3 + a1 * b2 - a2 * b1 + a3 * b0;
    }


    public static void multVec3( float[] quat, float[] vec, float[] out ) {
        final float q0 = quat[0];
        final float q1 = quat[1];
        final float q2 = quat[2];
        final float q3 = quat[3];

        out[0] = ( q0 * q0 + q1 * q1 - q2 * q2 - q3 * q3 ) * vec[0] +
                 ( 2  * ( q1 * q2 - q0 * q3 ) )            * vec[1] +
                 ( 2  * ( q1 * q3 + q0 * q2 ) )            * vec[2];

        out[1] = ( 2 * ( q1 * q2 + q0 * q3 ) )             * vec[0] +
                 ( q0 * q0 - q1 * q1 + q2 * q2 - q3 * q3 ) * vec[1] +
                 ( 2 * ( q2 * q3 - q0 * q1 ) )             * vec[2];

        out[2] = ( 2 * ( q1 * q3 - q0 * q2 ) )             * vec[0] +
                 ( 2 * ( q2 * q3 + q0 * q1 ) )             * vec[1] +
                 ( q0 * q0 - q1 * q1 - q2 * q2 + q3 * q3 ) * vec[2];
    }

    /**
     * Normalize quaternion to a valid unit-length.
     */
    public static void normalize( float[] q ) {
        Vec4.normalize( q );
    }

    /**
     * Converts axis-rotation to quaternion representation.
     * @param rads Radians of rotation
     * @param x    X-coord of rotation axis
     * @param y    Y-coord of rotation axis
     * @param z    Z-coord of rotation axis
     * @param out  Length-4 array that holds rotation on return.
     */
    public static void rotation( float rads, float x, float y, float z, float[] out ) {
        float cos = (float)Math.cos( rads * 0.5 );
        float len = (float)Math.sqrt( x * x + y * y + z * z );
        float sin = (float)Math.sin( rads * 0.5 ) / len;
        out[0] = cos;
        out[1] = sin * x;
        out[2] = sin * y;
        out[3] = sin * z;
    }

    /**
     * Converts a rotation matrix to an equivalent quaternion.
     * Non-rotation matrices will produce undefined results.
     *
     * @param mat Length-16 array holding rotation matrix.
     * @param out Length-4 array that holds equivalent quaternion on return.
     */
    public static void mat4ToQuat( float[] mat, float[] out ) {
        final float r00 = mat[ 0];
        final float r11 = mat[ 5];
        final float r22 = mat[10];
        
        float q0 = (  r00 + r11 + r22 + 1 );
        float q1 = (  r00 - r11 - r22 + 1 );
        float q2 = ( -r00 + r11 - r22 + 1 );
        float q3 = ( -r00 - r11 + r22 + 1 );
        
        q0 = q0 < 0 ? 0 : (float)Math.sqrt( q0 );
        q1 = q1 < 0 ? 0 : (float)Math.sqrt( q1 );
        q2 = q2 < 0 ? 0 : (float)Math.sqrt( q2 );
        q3 = q3 < 0 ? 0 : (float)Math.sqrt( q3 );
        
        if( q0 >= q1 && q0 >= q2 && q0 >= q3 ) {
            //System.out.println( "#0" );
            if( mat[6] - mat[9] < 0 ) q1 = -q1;
            if( mat[8] - mat[2] < 0 ) q2 = -q2;
            if( mat[1] - mat[4] < 0 ) q3 = -q3;
        } else if( q1 >= q2 && q1 >= q3 ) {
            //System.out.println( "#1" );
            if( mat[6] - mat[9] < 0 ) q0 = -q0;
            if( mat[1] + mat[4] < 0 ) q2 = -q2;
            if( mat[8] + mat[2] < 0 ) q3 = -q3;
        } else if( q2 >= q3 ) {
            //System.out.println( "#2" );
            if( mat[8] - mat[2] < 0 ) q0 = -q0;
            if( mat[1] + mat[4] < 0 ) q1 = -q1;
            if( mat[6] + mat[9] < 0 ) q3 = -q3;
        } else {
            //System.out.println( "#3" );
            if( mat[1] - mat[4] < 0 ) q0 = -q0;
            if( mat[2] + mat[8] < 0 ) q1 = -q1;
            if( mat[6] + mat[9] < 0 ) q2 = -q2;
        }
        
        float r = 1 / (float)Math.sqrt( q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3 );
        out[0] = q0 * r;
        out[1] = q1 * r;
        out[2] = q2 * r;
        out[3] = q3 * r;
    }

    /**
     * Converts quaternion to dim4 rotation matrix.
    *
     * @param quat Length-4 array holding quaternion.
     * @param out  Length-16 array that holds equivalent matrix on return.
     */
    public static void quatToMat4( float[] quat, float[] out ) {
        final float q0 = quat[0];
        final float q1 = quat[1];
        final float q2 = quat[2];
        final float q3 = quat[3];
        
        out[ 0] = q0 * q0 + q1 * q1 - q2 * q2 - q3 * q3;
        out[ 1] = 2 * ( q1 * q2 + q0 * q3 );
        out[ 2] = 2 * ( q1 * q3 - q0 * q2 );
        out[ 3] = 0;
        
        out[ 4] = 2 * ( q1 * q2 - q0 * q3 );
        out[ 5] = q0 * q0 - q1 * q1 + q2 * q2 - q3 * q3;
        out[ 6] = 2 * ( q2 * q3 + q0 * q1 );
        out[ 7] = 0;
        
        out[ 8] = 2 * ( q1 * q3 + q0 * q2 );
        out[ 9] = 2 * ( q2 * q3 - q0 * q1 );
        out[10] = q0 * q0 - q1 * q1 - q2 * q2 + q3 * q3;
        out[11] = 0;
        
        out[12] = 0;
        out[13] = 0;
        out[14] = 0;
        out[15] = 1;
    }

    /**
     * Computes spherical interpolation between two quaternions. 
     * @param qa  Quaternior
     * @param qb  Quaternion
     * @param t   Blend factor
     * @param out Length-4 array that holds quaternion output on return.
     */
    public static void slerp( float[] qa, float[] qb, float t, float[] out ) {
        // Calculate angle between them.
        float cosHalfTheta = qa[0] * qb[0] + qa[1] * qb[1] + qa[2] * qb[2] + qa[3] * qb[3];
        
        // if qa=qb or qa=-qb then theta = 0 and we can return qa
        if( cosHalfTheta >= 1.0 || cosHalfTheta <= -1.0 ) {
            out[0] = qa[0];
            out[1] = qa[1];
            out[2] = qa[2];
            out[3] = qa[3];
            return;
        }
        // Calculate temporary values.
        float halfTheta = (float)Math.acos( cosHalfTheta );
        float sinHalfTheta = (float)Math.sqrt( 1.0 - cosHalfTheta*cosHalfTheta );

        // if theta = 180 degrees then result is not fully defined
        // we could rotate around any axis normal to qa or qb
        if( sinHalfTheta < 0.00001f && sinHalfTheta > -0.00001f ) {
            out[0] = ( qa[0] * 0.5f + qb[0] * 0.5f );
            out[1] = ( qa[1] * 0.5f + qb[1] * 0.5f );
            out[2] = ( qa[2] * 0.5f + qb[2] * 0.5f );
            out[3] = ( qa[3] * 0.5f + qb[3] * 0.5f );
            return;
        }
        
        float ratioA = (float)Math.sin( ( 1 - t ) * halfTheta ) / sinHalfTheta;
        float ratioB = (float)Math.sin( t * halfTheta ) / sinHalfTheta; 
        //calculate Quaternion.
        out[0] = ( qa[0] * ratioA + qb[0] * ratioB );
        out[1] = ( qa[1] * ratioA + qb[1] * ratioB );
        out[2] = ( qa[2] * ratioA + qb[2] * ratioB );
        out[3] = ( qa[3] * ratioA + qb[3] * ratioB );
    }

    /**
     * Converts three random numbers from [0,1] into a quaternion
     * in such a way that if the samples are independent and
     * uniformly distributed, then the resulting quaternions will
     * uniformly represent the domain of quaternion rotations.
     * <p>
     * One implication of this function is that it can be used
     * to uniformly sample a 2-sphere.
     *
     * @param out   Holds output quaternion on return.
     */
    public static void sampleUniform( Random rand, float[] out ) {
        uniformNoiseToQuat( rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), out );
    }

    /**
     * Converts three random numbers from [0,1] into a quaternion
     * in such a way that if the samples are independent and
     * uniformly distributed, then the resulting quaternions will
     * uniformly represent the domain of quaternion rotations.
     * <p>
     * One implication of this function is that it can be used
     * to uniformly sample a 2-sphere.
     *
     * @param rand0 Arbitrary value in [0,1]
     * @param rand1 Arbitrary value in [0,1]
     * @param rand2 Arbitrary value in [0,1]
     * @param out   Holds quaternion on return.
     */
    public static void uniformNoiseToQuat( float rand0, float rand1, float rand2, float[] out ) {
        float sign0 = 1f;
        float sign1 = 1f;
        float sign2 = 1f;

        // Sort three numbers.
        // Use the sort order to generate 3 random booleans for sign values.
        if( rand0 > rand1 ) {
            sign0 = -1;
            sign1 = -1;
            float swap = rand0;
            rand0 = rand1;
            rand1 = swap;
        }
        if( rand1 > rand2 ) {
            sign1 = -sign1;
            sign2 = -sign2;
            float swap = rand1;
            rand1 = rand2;
            rand2 = swap;

            if( rand0 > rand1 ) {
                sign0 = -sign0;
                sign1 = -sign1;
                swap  = rand0;
                rand0 = rand1;
                rand1 = swap;
            }
        }

        out[0] = sign0 * (rand0        );
        out[1] = sign1 * (rand1 - rand0);
        out[2] = sign2 * (rand2 - rand1);
        out[3] = 1.0f - rand2;
        normalize( out );
    }
        
    
    public static String format( float[] quat ) {
        return String.format( "[ % 7.4f, % 7.4f, % 7.4f, % 7.4f ]", quat[0], quat[1], quat[2], quat[3] );
    }
    
    
    
    private Quat() {}



    public static void main( String[] args ) throws Exception {



    }


    @Deprecated public static void matToQuat( float[] mat, float[] out ) {
        mat4ToQuat( mat, out );
    }


    @Deprecated public static void quatToMat( float[] quat, float[] out ) {
        quatToMat4( quat, out );
    }


    @Deprecated public static void multVec( float[] quat, float[] vec, float[] out ) {
        multVec3( quat, vec, out );
    }

}
