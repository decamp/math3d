package cogmac.math3d;


/**
 * Methods for futzing with quaternions.
 * 
 * @author decamp
 */
public final class Quats {
    
    
    public static void mult( double[] a, double[] b, double[] out ) {
        // These local copies had no effect in performance tests, but whatevs. 
        final double a0 = a[0];
        final double a1 = a[1];
        final double a2 = a[2];
        final double a3 = a[3];
        final double b0 = b[0];
        final double b1 = b[1];
        final double b2 = b[2];
        final double b3 = b[3];
        out[0] = a0 * b0 - a1 * b1 - a2 * b2 - a3 * b3;
        out[1] = a0 * b1 + a1 * b0 + a2 * b3 - a3 * b2;
        out[2] = a0 * b2 - a1 * b3 + a2 * b0 + a3 * b1;
        out[3] = a0 * b3 + a1 * b2 - a2 * b1 + a3 * b0;
    }
    
    
    public static void mult( double[] a, double[] b ) {
        final double a0 = a[0];                        
        final double a1 = a[1];                        
        final double a2 = a[2];                        
        final double a3 = a[3];                        
        final double b0 = b[0];                        
        final double b1 = b[1];                        
        final double b2 = b[2];                        
        final double b3 = b[3];                        
        a[0] = a0 * b0 - a1 * b1 - a2 * b2 - a3 * b3;
        a[1] = a0 * b1 + a1 * b0 + a2 * b3 - a3 * b2;
        a[2] = a0 * b2 - a1 * b3 + a2 * b0 + a3 * b1;
        a[3] = a0 * b3 + a1 * b2 - a2 * b1 + a3 * b0;
    }

    
    public static double length( double[] q ) {
        return Math.sqrt( q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3] );
    }
    
    
    public static void normalize( double[] q ) {
        double r = 1.0 / Math.sqrt( q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3] );
        q[0] *= r;
        q[1] *= r;
        q[2] *= r;
        q[3] *= r;
    }
    
    
    public static void rotation( double rads, double x, double y, double z, double[] out ) {
        double cos = Math.cos( rads * 0.5 );
        double len = Math.sqrt( x * x + y * y + z * z );
        double sin = Math.sin( rads * 0.5 ) / len;
        out[0] = cos;
        out[1] = sin * x;
        out[2] = sin * y;
        out[3] = sin * z;
    }
    
    
    public static void matToQuat( double[] mat, double[] out ) {
        final double r00 = mat[ 0];
        final double r11 = mat[ 5];
        final double r22 = mat[10];
        
        double q0 = (  r00 + r11 + r22 + 1.0 );// * 0.25;
        double q1 = (  r00 - r11 - r22 + 1.0 );// * 0.25;
        double q2 = ( -r00 + r11 - r22 + 1.0 );// * 0.25;
        double q3 = ( -r00 - r11 + r22 + 1.0 );// * 0.25;
        
        q0 = q0 < 0.0 ? 0.0 : Math.sqrt( q0 );
        q1 = q1 < 0.0 ? 0.0 : Math.sqrt( q1 );
        q2 = q2 < 0.0 ? 0.0 : Math.sqrt( q2 );
        q3 = q3 < 0.0 ? 0.0 : Math.sqrt( q3 );
        
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
        
        normalize( out );
    }
    
    
    public static void quatToMat( double[] quat, double[] out ) {
        final double q0 = quat[0];
        final double q1 = quat[1];
        final double q2 = quat[2];
        final double q3 = quat[3];
        
        out[ 0] = q0 * q0 + q1 * q1 - q2 * q2 - q3 * q3;
        out[ 1] = 2.0 * ( q1 * q2 + q0 * q3 );
        out[ 2] = 2.0 * ( q1 * q3 - q0 * q2 );
        out[ 3] = 0.0;
        
        out[ 4] = 2.0 * ( q1 * q2 - q0 * q3 );
        out[ 5] = q0 * q0 - q1 * q1 + q2 * q2 - q3 * q3;
        out[ 6] = 2.0 * ( q2 * q3 + q0 * q1 );
        out[ 7] = 0.0;
        
        out[ 8] = 2.0 * ( q1 * q3 + q0 * q2 );
        out[ 9] = 2.0 * ( q2 * q3 - q0 * q1 );
        out[10] = q0 * q0 - q1 * q1 - q2 * q2 + q3 * q3;
        out[11] = 0.0;
        
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }
    
    
    public static void multVec( double[] quat, double[] vec, double[] out ) {
        final double q0 = quat[0];
        final double q1 = quat[1];
        final double q2 = quat[2];
        final double q3 = quat[3];
        
        out[0] = ( q0 * q0 + q1 * q1 - q2 * q2 - q3 * q3 ) * vec[0] +
                 ( 2.0 * ( q1 * q2 - q0 * q3 ) )           * vec[1] +
                 ( 2.0 * ( q1 * q3 + q0 * q2 ) )           * vec[2];
        
        out[1] = ( 2.0 * ( q1 * q2 + q0 * q3 ) )           * vec[0] +
                 ( q0 * q0 - q1 * q1 + q2 * q2 - q3 * q3 ) * vec[1] +
                 ( 2.0 * ( q2 * q3 - q0 * q1 ) )           * vec[2];
        
        out[2] = ( 2.0 * ( q1 * q3 - q0 * q2 ) )           * vec[0] +
                 ( 2.0 * ( q2 * q3 + q0 * q1 ) )           * vec[1] +
                 ( q0 * q0 - q1 * q1 - q2 * q2 + q3 * q3 ) * vec[2];
    }
    
    /**
     * Computes spherical interpolation between two quaternions. 
     * @param qa
     * @param qb
     * @param t
     * @param out
     */
    public static void slerp( double[] qa, double[] qb, double t, double[] out ) {
        // Calculate angle between them.
        double cosHalfTheta = qa[0] * qb[0] + qa[1] * qb[1] + qa[2] * qb[2] + qa[3] * qb[3];
        
        // if qa=qb or qa=-qb then theta = 0 and we can return qa
        if( cosHalfTheta >= 1.0 || cosHalfTheta <= -1.0 ) {
            out[0] = qa[0];
            out[1] = qa[1];
            out[2] = qa[2];
            out[3] = qa[3];
            return;
        }
        // Calculate temporary values.
        double halfTheta = Math.acos( cosHalfTheta );
        double sinHalfTheta = Math.sqrt( 1.0 - cosHalfTheta*cosHalfTheta );

        // if theta = 180 degrees then result is not fully defined
        // we could rotate around any axis normal to qa or qb
        if( sinHalfTheta < 0.00001 && sinHalfTheta > -0.00001 ) {
            out[0] = ( qa[0] * 0.5 + qb[0] * 0.5 );
            out[1] = ( qa[1] * 0.5 + qb[1] * 0.5 );
            out[2] = ( qa[2] * 0.5 + qb[2] * 0.5 );
            out[3] = ( qa[3] * 0.5 + qb[3] * 0.5 );
            return;
        }
        
        double ratioA = Math.sin( ( 1 - t ) * halfTheta ) / sinHalfTheta;
        double ratioB = Math.sin( t * halfTheta ) / sinHalfTheta; 
        //calculate Quaternion.
        out[0] = ( qa[0] * ratioA + qb[0] * ratioB );
        out[1] = ( qa[1] * ratioA + qb[1] * ratioB );
        out[2] = ( qa[2] * ratioA + qb[2] * ratioB );
        out[3] = ( qa[3] * ratioA + qb[3] * ratioB );
    }

    
    public static void uniformNoiseToQuat( double rand0, double rand1, double rand2, double[] out ) {
        if( rand0 > rand1 ) {
            double swap = rand0;
            rand0 = rand1;
            rand1 = swap;
        }
        if( rand1 > rand2 ) {
            double swap = rand1;
            rand1 = rand2;
            rand2 = swap;
        }
        if( rand0 > rand1 ) {
            double swap = rand0;
            rand0 = rand1;
            rand1 = swap;
        }
        
        out[0] = rand0;
        out[1] = rand1 - rand0;
        out[2] = rand2 - rand1;
        out[3] = 1.0 - rand2;
        normalize( out );
    }
        
    
    public static String format( double[] quat ) {
        return String.format( "[ % 7.4f, % 7.4f, % 7.4f, % 7.4f ]", quat[0], quat[1], quat[2], quat[3] );
    }
    
    
    
    private Quats() {}

}
