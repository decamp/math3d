package cogmac.math3d;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class TestMatrices {
    
    
    @Test
    public void testInverse() {
        double[] a = new double[16];
        double[] b = new double[16];
        double[] c = new double[16];
        double[] eye = new double[16];
        Matrices.setToIdentity( eye );
        Random rand = new Random( 100 );
        
        for( int i = 0; i < 100; i++ ) {
            for( int j = 0; j < 16; j++ ) {
                a[j] = rand.nextDouble() * 10.0 - 5.0;
            }
            
            if( !Matrices.invert( a, b ) ) {
                continue;
            }
            
            Matrices.multMatMat( a, b, c );
            
            for( int j = 0; j < 16; j++ ) {
                boolean eq = Tolerance.approxEqual( c[j], 
                                                    eye[j],
                                                    0.000001,
                                                    0.000001 );
                assertTrue( eq ); 
            }
        }
        
        Arrays.fill( a, 1.0 );
        assertFalse( Matrices.invert( a, b ) );
    }

    
    @Test
    @SuppressWarnings( "deprecation" )
    public void testInverseSpeed() {
        
        double[] a = new double[16];
        double[] c = new double[16];
        double[] w0 = new double[16];
        double[] w1 = new double[16];
        Random rand = new Random( 100 );
        
        long t0 = 0;
        long t1 = 0;
        
        for( int i = 0; i < 100000; i++ ) {
            for( int j = 0; j < 16; j++ ) {
                a[j] = rand.nextDouble() * 1.0 - 5.0;
            }
            
            long start = System.nanoTime();
            
            for( int j = 0; j < 10; j++ ) {
                Matrices.invert( a, c );
            }
            
            t0 += System.nanoTime() - start;
            start = System.nanoTime();
            
            for( int j = 0; j < 10; j++) {
                Matrices.invertMat( a, w0, w1, c );
            }
            
            t1 += System.nanoTime() - start;
        }
        
        
        System.out.println( "Time0: " + ( t0 / 1000000000.0 ) );
        System.out.println( "Time1: " + ( t1 / 1000000000.0 ) );
    }
    
}
