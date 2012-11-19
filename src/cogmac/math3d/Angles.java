package cogmac.math3d;

/** 
 * @author Philip DeCamp  
 */
public class Angles {

    /**
     * @param angle - Angle in radians.
     * @return equivalent angle between -PI inclusive to PI exclusive.
     */
    public static double normalize( double angle ) {
        angle %= Math.PI * 2.0;
        
        if( angle <= -Math.PI )
            return angle + Math.PI * 2.0;
        
        if( angle > Math.PI )
            return angle - Math.PI * 2.0;
        
        return angle;
    }

    
    public static void matrixToZyxAngles( double[] mat4x4, double[] out3x1 ) {
        double[] mat = mat4x4.clone();
        Matrices.normalizeRotationMatrix( mat );
        rotationMatrixToZyxAngles( mat, out3x1 );
    }
    
    
    public static void rotationMatrixToZyxAngles( double[] mat4x4, double[] out3x1 ) {
        final double[] mat = mat4x4;
        double a0, a1, a2;
        double v = Math.sqrt(1.0 - mat[2] * mat[2]);
        
        if( v > Tolerance.ABS_ERR ) {
            a0 = asincos(mat[1] / v, mat[0] / v);
            a1 = Math.asin(-mat[2]);
            a2 = asincos(mat[6] / v, mat[10] / v);
        
        }else if( mat[2] < 0.0 ) {
            a0 = asincos( -mat[4], mat[5] );
            a1 = Math.PI * 0.5;
            a2 = 0.0;
            
        }else{
            a0 = asincos( -mat[4], mat[5] );
            a1 = -Math.PI * 0.5;
            a2 = 0.0;
        }
        
        out3x1[0] = a0;
        out3x1[1] = a1;
        out3x1[2] = a2;
    }
    
    
    public static void zyxAnglesToRotationMatrix( double[] angs3x1, double[] out4x4 ) {
        final double s0 = Math.sin( angs3x1[0] );
        final double c0 = Math.cos( angs3x1[0] );
        final double s1 = Math.sin( angs3x1[1] );
        final double c1 = Math.cos( angs3x1[1] );
        final double s2 = Math.sin( angs3x1[2] );
        final double c2 = Math.cos( angs3x1[2] );
        
        out4x4[ 0] = c0 * c1;
        out4x4[ 1] = s0 * c1;
        out4x4[ 2] = -s1;
        out4x4[ 3] = 0;
        
        out4x4[ 4] = -s0 * c2 + c0 * s1 * s2;
        out4x4[ 5] =  c0 * c2 + s0 * s1 * s2;
        out4x4[ 6] =  c1 * s2;
        out4x4[ 7] = 0;
        
        out4x4[ 8] =  s0 * s2 + c0 * s1 * c2;
        out4x4[ 9] = -c0 * s2 + s0 * s1 * c2;
        out4x4[10] =  c1 * c2;
        out4x4[11] = 0;
        
        out4x4[12] = 0;
        out4x4[13] = 0;
        out4x4[14] = 0;
        out4x4[15] = 1;
    }
        
    
    private static double asincos( double sinAng, double cosAng ) {
        if( cosAng >= 1.0 ) {
            return cosAng < 1.0 + Tolerance.REL_ERR ? 0.0 : Double.NaN;
        }else if(cosAng <= -1.0) {
            return cosAng > -1.0 - Tolerance.REL_ERR ? Math.PI : Double.NaN; 
        }else{
            return ( sinAng >= 0.0 ? Math.acos( cosAng ) : -Math.acos( cosAng ) );
        }
    }

}
