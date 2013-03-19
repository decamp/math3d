package cogmac.math3d;

import java.util.*;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestQuats {
    
    
    @Test
    public void testRandMatrixConversions() {
        Random rand = new Random( 6 );
        
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
    

    @Test
    public void testMultSpeed() {
        double[] a  = new double[4];
        double[] b  = new double[4];
        double[] c  = new double[4];
        Random rand = new Random( 100 );
        
        long t0 = 0;
        long t1 = 0;
        
        for( int i = 0; i < 100000; i++ ) {
            uniformRandQuat( rand, a );
            uniformRandQuat( rand, b );
            
            long start = System.nanoTime();
            
            for( int j = 0; j < 200; j++ ) {
                Quats.mult( a, b, c );
            }
            
            t0 += System.nanoTime() - start;
            start = System.nanoTime();
            
            for( int j = 0; j < 200; j++) {
                Quats.mult( a, b, c );
            }
            
            t1 += System.nanoTime() - start;
        }
        
        System.out.println( "Time0: " + ( t0 / 1000000000.0 ) );
        System.out.println( "Time1: " + ( t1 / 1000000000.0 ) );
    }
    

    
    private static void uniformRandQuat( Random rand, double[] out ) {
        // Draw three uniform samples.
        double u0 = rand.nextDouble();
        double u1 = rand.nextDouble();
        double u2 = rand.nextDouble();
        // Sort smallest to largest.
        if( u0 > u1 ) {
            double t = u0;
            u0 = u1;
            u1 = t;
        }
        if( u1 > u2 ) {
            double t = u1;
            u1 = u2;
            u2 = t;
        }
        if( u0 > u1 ) {
            double t = u0;
            u0 = u1;
            u1 = t;
        }
        
        out[0] = u0;
        out[1] = u1 - u0;
        out[2] = u2 - u1;
        out[3] = 1.0 - u2;
    }
    
    
    private static void rotXyz( double rx, double ry, double rz, double[] out ) {
        double[] a = new double[16];
        double[] b = new double[16];
        
        Matrices.computeRotationMatrix( rx, 1, 0, 0, a );
        Matrices.computeRotationMatrix( ry, 0, 1, 0, out );
        Matrices.multMatMat( out, a, b );
        Matrices.computeRotationMatrix( rz, 0, 0, 1, a );
        Matrices.multMatMat( a, b, out );
    }
    
    
    private static boolean matEquals( double[] a, double[] b ) {
        for( int i = 0; i < 16; i++ ) {
            if( !Tolerance.approxEqual( a[i], b[i], 1E-8, 1E-8 ) ) {
                return false;
            }
        }
        
        return true;
    }

}
