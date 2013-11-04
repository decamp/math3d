package bits.math3d.actors;

import bits.math3d.*;


/**
 * @author decamp
 * @deprecated
 */
public class Povs {
    
    /**
     * @deprecated Use Vectors.isValid()
     */
    public static void assertValidPos( double[] pos ) {
        for( int i = 0; i < 3; i++ ) {
            if( Double.isNaN( pos[i] ) ) {
                System.err.println( "NaN Position" );
                throw new RuntimeException( "NaN Position" );
            }
        }
    }


    /**
     * @deprecated Use Matrices.isValid()
     */
    public static void assertValidRot( double[] rot ) {
        // TODO: Complete this method.
        for(int i = 0; i < 16; i++) {
            if(Double.isNaN(rot[i])) {
                System.err.println("NaN Rotation");
                throw new RuntimeException("NaN Rotation");
            }
        }
    }
    
    
    /**
     * @deprecated Has bugs. Not smooth.
     */
    public static final void lerpRots( double[] rotA, 
                                       double[] rotB, 
                                       double p, 
                                       double[] workVec0, 
                                       double[] workVec1, 
                                       double[] workVec2,
                                       double[] out ) 
    {
        
        // Compute gaze/x vector.
        workVec2[0] = 1.0;
        workVec2[1] = 0.0;
        workVec2[2] = 0.0;
        Matrices.multMatVec( rotA, workVec2, workVec0 );
        Matrices.multMatVec( rotB, workVec2, workVec1 );
        Vectors.lerp( workVec0, workVec1, p, workVec2 );
        
        double hypot = Vectors.hypot( workVec2 );
        if( hypot < Tolerance.ABS_ERR ) {
            Vectors.normalize( workVec0, 1.0 );
            out[ 0] = workVec0[0];
            out[ 1] = workVec0[1];
            out[ 2] = workVec0[2];
            out[ 3] = 0.0;
        } else {
            Vectors.scale( workVec2, 1.0 / hypot );
            out[ 0] = workVec2[0];
            out[ 1] = workVec2[1];
            out[ 2] = workVec2[2];
            out[ 3] = 0.0;
        }
        
        // Compute up/z vector.
        workVec2[0] = 0.0;
        workVec2[1] = 0.0;
        workVec2[2] = 0.1;
        Matrices.multMatVec( rotA, workVec2, workVec0 );
        Matrices.multMatVec( rotB, workVec2, workVec1 );
        Vectors.lerp( workVec0, workVec1, p, workVec2 );
        
        // Compute side/y vector.
        Vectors.cross( workVec2, out, workVec1 );
        Vectors.normalize( workVec1, 1.0 );
        
        // Orthogonalize up/z vector.
        Vectors.cross( out, workVec1, workVec2 );
        
        out[ 4] = workVec1[0];
        out[ 5] = workVec1[1];
        out[ 6] = workVec1[2];
        out[ 7] = 0.0;
        out[ 8] = workVec2[0];
        out[ 9] = workVec2[1];
        out[10] = workVec2[2];
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }
    
    /**
     * Removes roll and forces the gaze onto the specified plane.
     * 
     * @param rot       Rotation, where gaze is determined by the x-axis (see ActorCoords).
     * @param norm      Normal vector of plane.
     * @param out       Output matrix in which to hold new orientation. 
     *                  X-axis and Y-axis of new rotation will be parallel to <code>norm</code>.
     *                  
     * @deprecated Is anyone using this?                 
     */
    public static void clampGazeToPlane( double[] rot, 
                                         double[] norm,
                                         double[] outRot ) 
    {
        outRot[ 8] = norm[0];
        outRot[ 9] = norm[1];
        outRot[10] = norm[2];
        
        Vectors.normalize( outRot, 8, 1.0 );
        ActorCoords.forwardAxis( outRot, 4 );
        Matrices.multMatVec( rot, 0, outRot, 4, outRot, 0);
        
        Vectors.cross( outRot, 8, outRot, 0, outRot, 4 );
        Vectors.cross( outRot, 4, outRot, 8, outRot, 0 );
        Vectors.normalize( outRot, 0, 1.0 );
        Vectors.normalize( outRot, 4, 1.0 );
        
        outRot[ 4] = 0.0;
        outRot[ 7] = 0.0;
        outRot[11] = 0.0;
        outRot[12] = 0.0;
        outRot[13] = 0.0;
        outRot[14] = 0.0;
        outRot[15] = 1.0;
    }

}
