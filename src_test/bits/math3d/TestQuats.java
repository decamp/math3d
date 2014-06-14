package bits.math3d;

import java.util.*;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertTrue;


public class TestQuats {

    @Test public void testRandMatrixConversions() {
        Random rand = new Random( 6 );
        
        double[] rotIn  = new double[16];
        double[] q      = new double[4];
        double[] rotOut = new double[16];
        
        for( int i = 0; i < 20000; i++ ) {
            rotXyz( rand.nextDouble() * Math.PI,
                    rand.nextDouble() * Math.PI,
                    rand.nextDouble() * Math.PI,
                    rotIn );
            
            Quats.matToQuat( rotIn, q );
            Quats.quatToMat( q, rotOut );
            
            if( !matEquals( rotIn, rotOut ) ) {
                System.out.println( Matrices.format( rotIn ) );
                System.out.println( Quats.format( q ) );
                System.out.println( Matrices.format( rotOut ) );
                assertTrue( "Conversion failed.", false );
            }
        }
    }
    
    
    @Test public void testOrthoMatrixConversions() {
        double[] rotIn  = new double[16];
        double[] q      = new double[4];
        double[] rotOut = new double[16];
        
        for( int i = 0; i < 4*4*4; i++ ) {
            double rx = ( i      % 4 ) * Math.PI * 0.5;
            double ry = ( i /  4 % 4 ) * Math.PI * 0.5;
            double rz = ( i / 16 % 4 ) * Math.PI * 0.5;
                        
            rotXyz( rx, ry, rz, rotIn );
            Quats.matToQuat( rotIn, q );
            Quats.quatToMat( q, rotOut );

            //System.out.println( Quats.format( q ) );
            
            if( !matEquals( rotIn, rotOut ) ) {
                System.out.println( Matrices.format( rotIn ) );
                System.out.println( Quats.format( q ) );
                System.out.println( Matrices.format( rotOut ) );
                assertTrue( "Conversion failed.", false );
            }
        }
    }
    

    @Test public void testMultSpeed() {
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

    /**
     * Make sure quaternion sampling is actually uniform.
     */
    @Test public void testSamplingUniformity() {
        float[] startVec = { 1, 0, 0 };
        float[] outVec   = new float[3];
        float[] quat     = new float[4];
        float[][] testVecs = { {  1,  0,  0 },
                               { -1,  0,  0 },
                               {  0,  1,  0 },
                               {  0, -1,  0 },
                               {  0,  0,  1 },
                               {  0,  0, -1 } };
        float[] testAngs = new float[testVecs.length];

        int trials = 100000;
        Random rand = new Random( 20 );

        for( int i = 0; i < trials; i++ ) {
            Quat.sampleUniform( rand, quat );
            Quat.multVec3( quat, startVec, outVec );

            for( int j = 0; j < testVecs.length; j++ ) {
                testAngs[j] += Vec3.ang( outVec, testVecs[j] );
            }
        }

        for( int j = 0; j < testVecs.length; j++ ) {
            double err = Math.abs( testAngs[j] / trials - (float)( 0.5 * Math.PI ) );
            assertTrue( "Quaternion sampling not uniform.",  err < 0.01 * Math.PI );

            System.out.println( Vec3.format( testVecs[j] ) + " : " + err );
        }
    }

    /**
     * I understand you can uniformly sample a sphere with gaussians samples!
     * Yes. It is true.
     */
    @Ignore @Test public void testSphericalSamplingWithGaussians() {
        float[] outVec   = new float[3];
        float[][] testVecs = { {  1,  0,  0 },
                               { -1,  0,  0 },
                               {  0,  1,  0 },
                               {  0, -1,  0 },
                               {  0,  0,  1 },
                               {  0,  0, -1 } };
        float[] testAngs = new float[testVecs.length];

        int trials = 100000;
        Random rand = new Random( 20 );

        for( int i = 0; i < trials; i++ ) {
            double r;
            r = rand.nextDouble();
            double x = Phi.ncdfInv( r );
            r = rand.nextDouble();
            double y = Phi.ncdfInv( r );
            r = rand.nextDouble();
            double z = Phi.ncdfInv( r );
            r = 1.0 / Math.sqrt( x * x + y * y + z * z );
            outVec[0] = (float)( r * x );
            outVec[1] = (float)( r * y );
            outVec[2] = (float)( r * z );

            for( int j = 0; j < testVecs.length; j++ ) {
                testAngs[j] += Vec3.ang( outVec, testVecs[j] );
            }
        }

        for( int j = 0; j < testVecs.length; j++ ) {
            double err = Math.abs( testAngs[j] / trials ) - 0.5 * Math.PI;
            assertTrue( "Gaussian sampling not uniform.",  err < 0.01 * Math.PI );
            //System.out.println( Vec3.format( testVecs[j] ) + " : " + err );
        }


    }



    private static void uniformRandQuat( Random rand, double[] out ) {
        // Draw three uniform samples.
        double u0 = rand.nextDouble();
        double u1 = rand.nextDouble();
        double u2 = rand.nextDouble();
        Quats.uniformNoiseToQuat( u0, u1, u2, out );
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
            if( !Tol.approxEqual( a[i], b[i], 1E-8, 1E-8 ) ) {
                return false;
            }
        }
        
        return true;
    }

}
