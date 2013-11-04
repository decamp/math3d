package bits.math3d;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;


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
                boolean eq = Tol.approxEqual( c[j], 
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
    public void testInverseSpeed() {
        double[] a = new double[16];
        double[] c = new double[16];
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
                //Matrices.invert2( a, c );
            }
            
            t1 += System.nanoTime() - start;
        }
        
        
        System.out.println( "Time0: " + ( t0 / 1000000000.0 ) );
        System.out.println( "Time1: " + ( t1 / 1000000000.0 ) );
    }
    

    @Test
    @Ignore
    public void testSlerp() {
        double[] a = new double[16];
        double[] b = new double[16];
        double[] c = new double[16];
        
        rotXyz( Math.PI * 0.5, 0.0, 0.0, a );
        rotXyz( 0, 0, 0, b );
        
        Matrices.slerpRotations( a, b, 0.5, new double[4], new double[4], new double[4], c );
        
        //System.out.println( Matrices.format( a ) );
        //System.out.println( Matrices.format( c ) );
    }
    
    
    static void rotXyz( double rx, double ry, double rz, double[] out ) {
        double[] a = new double[16];
        double[] b = new double[16];
        
        Matrices.computeRotationMatrix( rx, 1, 0, 0, a );
        Matrices.computeRotationMatrix( ry, 0, 1, 0, out );
        Matrices.multMatMat( out, a, b );
        Matrices.computeRotationMatrix( rz, 0, 0, 1, a );
        Matrices.multMatMat( a, b, out );
    }

}
