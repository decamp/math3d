/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

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
        Mat4 a = rand();
        Mat4 b = rand();
        Mat4 c = new Mat4();
        Mat4 d = new Mat4();

        final int iters = 1;

        double dur0 = 0;
        double dur1 = 0;

        for( int i = 0; i < 2000; i++ ) {
            long start;

            start = System.nanoTime();
            for( int j = 0; j < iters; j++ ) {
                Mat.rotation( 20, 1, 2, 3, a );
                Mat.mult( b, a, c );
            }
            dur0 += ( System.nanoTime() - start ) / 1000000000.0;

            start = System.nanoTime();
            for( int j = 0; j < iters; j++ ) {
                Mat.rotate( b, 20, 1, 2, 3, b );
            }
            dur1 += ( System.nanoTime() - start ) / 1000000000.0;

            if( i == 0 ) {
                System.out.println( Mat.format( c ) );
                System.out.println( Mat.format( b ) );
                assertTrue( c.equals( b ) );
            }

        }

        System.out.println( "MULT: " + dur0 );
        System.out.println( "MULT2: " + dur1 );
    }


    Mat4 rand() {
        Mat4 ret = new Mat4();
        ret.m00 = rand.nextFloat();
        ret.m01 = rand.nextFloat();
        ret.m02 = rand.nextFloat();
        ret.m03 = rand.nextFloat();
        ret.m10 = rand.nextFloat();
        ret.m11 = rand.nextFloat();
        ret.m12 = rand.nextFloat();
        ret.m13 = rand.nextFloat();
        ret.m20 = rand.nextFloat();
        ret.m21 = rand.nextFloat();
        ret.m22 = rand.nextFloat();
        ret.m23 = rand.nextFloat();
        ret.m30 = rand.nextFloat();
        ret.m31 = rand.nextFloat();
        ret.m32 = rand.nextFloat();
        ret.m33 = rand.nextFloat();
        return ret;
    }


    void rand( float[] arr ) {
        for( int i = 0; i < arr.length; i++ ) {
            arr[i] = rand.nextFloat();
        }
    }

}
