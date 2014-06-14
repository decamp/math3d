package bits.math3d;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;


/**
 * @author Philip DeCamp
 */
public class TestMat2 {

    Random rand = new Random( 8 );

    @Test
    public void testSpeed() {
        float[] a = rand( 16 );
        float[] b = rand( 16 );
        float[] c = new float[16];
        float[] d = new float[16];

        final int iters = 1;

        double dur0 = 0;
        double dur1 = 0;

        for( int i = 0; i < 2000; i++ ) {
            long start;

            start = System.nanoTime();
            for( int j = 0; j < iters; j++ ) {
                Mat4.rotation( 20, 1, 2, 3, a );
                Mat4.mult( b, a, c );
            }
            dur0 += ( System.nanoTime() - start ) / 1000000000.0;

            start = System.nanoTime();
            for( int j = 0; j < iters; j++ ) {
                Mat4.rotate( b, 20, 1, 2, 3, b );
            }
            dur1 += ( System.nanoTime() - start ) / 1000000000.0;

            if( i == 0 ) {
                System.out.println( Mat4.format( c ) );
                System.out.println( Mat4.format( b ) );
                assertSame( c, b );
            }

        }

        System.out.println( "MULT: " + dur0 );
        System.out.println( "MULT2: " + dur1 );
    }


    float[] rand( int len ) {
        float[] ret = new float[len];
        for( int i = 0; i < len; i++ ) {
            ret[i] = rand.nextFloat();
        }
        return ret;
    }


    void rand( float[] arr ) {
        for( int i = 0; i < arr.length; i++ ) {
            arr[i] = rand.nextFloat();
        }
    }


    static void assertSame( float[] a, float[] b ) {
        for( int i = 0; i < a.length; i++ ) {
            assertTrue( Tol.approxEqual( a[i], b[i] ) );
        }
    }

}
