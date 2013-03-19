package cogmac.math3d;

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestQuaternions {
    
    
    @Test
    public void testRandMatrixConversions() {
        Random rand = new Random( 5 );
        
        double[] rotIn  = new double[16];
        double[] q      = new double[4];
        double[] rotOut = new double[16];
        
        for( int i = 0; i < 20000; i++ ) {
            rotXyz( rand.nextDouble() * Math.PI,
                    rand.nextDouble() * Math.PI,
                    rand.nextDouble() * Math.PI,
                    rotIn );
            
            Quats.rotationMatToQuat( rotIn, q );
            Quats.quatToRotationMat( q, rotOut );
            
            if( !matEquals( rotIn, rotOut ) ) {
                System.out.println( Matrices.format( rotIn ) );
                System.out.println( Quats.format( q ) );
                System.out.println( Matrices.format( rotOut ) );
                assertTrue( "Conversion failed.", false );
            }
        }
    }
    
    
    @Test
    public void testOrthoMatrixConversions() {
        double[] rotIn  = new double[16];
        double[] q      = new double[4];
        double[] rotOut = new double[16];
        
        for( int i = 0; i < 4*4*4; i++ ) {
            double rx = ( i      % 4 ) * Math.PI * 0.5;
            double ry = ( i /  4 % 4 ) * Math.PI * 0.5;
            double rz = ( i / 16 % 4 ) * Math.PI * 0.5;
                        
            rotXyz( rx, ry, rz, rotIn );
            Quats.rotationMatToQuat( rotIn, q );
            Quats.quatToRotationMat( q, rotOut );

            //System.out.println( Quats.format( q ) );
            
            if( !matEquals( rotIn, rotOut ) ) {
                System.out.println( Matrices.format( rotIn ) );
                System.out.println( Quats.format( q ) );
                System.out.println( Matrices.format( rotOut ) );
                assertTrue( "Conversion failed.", false );
            }
        }
    }
    
    
    
    static void rotXyz( double rx, double ry, double rz, double[] out ) {
        double[] a = new double[16];
        double[] b = new double[16];
        double[] c = new double[16];
        
        Matrices.computeRotationMatrix( rx, 1, 0, 0, a );
        Matrices.computeRotationMatrix( ry, 0, 1, 0, out );
        Matrices.multMatMat( out, a, b );
        Matrices.computeRotationMatrix( rz, 0, 0, 1, a );
        Matrices.multMatMat( a, b, out );
    }
    
    
    private static boolean matEquals( double[] a, double[] b ) {
        for( int i = 0; i < 16; i++ ) {
            if( !Tolerance.approxEqual( a[i], b[i], 1E-5, 1E-5 ) ) {
                return false;
            }
        }
        
        return true;
    }

}
