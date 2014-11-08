/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;

import org.junit.Test;

import java.util.Random;


/**
 * Non-comprehensive microtest comparing {@code v != v} to {@code Float.isNan( v )}.
 *
 * <p>Result was that they were about the same.
 *
 * Looks like v != v is faster.
 *
 * @author Philip DeCamp
 */
public class TestNanSpeed {

    @Test
    public void testNanSpeed() {
        long t0 = 0;
        long t1 = 0;
        Random rand = new Random();

        boolean accum = false;

        for( int i = 0; i < 50000000; i++ ) {
            Mat3 mat = new Mat3();
            mat.m00 = rand.nextFloat();
            mat.m10 = rand.nextFloat();
            mat.m20 = rand.nextFloat();
            mat.m01 = rand.nextFloat();
            mat.m11 = rand.nextFloat();
            mat.m21 = Float.NaN; //rand.nextFloat();
            mat.m02 = rand.nextFloat();
            mat.m12 = rand.nextFloat();
            mat.m22 = rand.nextFloat();

            long start;
            final int check = 2;

            if( ( i & 1 ) == 0 ) {
                start = System.nanoTime();
                for( int j = 0; j < check; j++ ) {
                    accum ^= isNaN( mat );
                }
                t0 += System.nanoTime() - start;

                start = System.nanoTime();
                for( int j = 0; j < check; j++ ) {
                    accum ^= isNaN( mat );
                }
                t1 += System.nanoTime() - start;
            } else {
                start = System.nanoTime();
                for( int j = 0; j < check; j++ ) {
                    accum ^= isNaN( mat );
                }
                t1 += System.nanoTime() - start;

                start = System.nanoTime();
                for( int j = 0; j < check; j++ ) {
                    accum ^= isNaN( mat );
                }
                t0 += System.nanoTime() - start;
            }
        }

        System.out.println( accum );
        System.out.println( "v != v     : " + ( t0 / 10000000000.0 ) );
        System.out.println( "isNan( v ) : " + ( t1 / 10000000000.0 ) );

    }


    public static boolean isNaN( Mat3 mat ) {
        return mat.m00 != mat.m00 ||
               mat.m10 != mat.m10 ||
               mat.m20 != mat.m20 ||
               mat.m01 != mat.m01 ||
               mat.m11 != mat.m11 ||
               mat.m21 != mat.m21 ||
               mat.m02 != mat.m02 ||
               mat.m12 != mat.m12 ||
               mat.m22 != mat.m22;
    }


    public static boolean isNaNMeth( Mat3 mat ) {
        return Float.isNaN( mat.m00 ) ||
               Float.isNaN( mat.m10 ) ||
               Float.isNaN( mat.m20 ) ||
               Float.isNaN( mat.m01 ) ||
               Float.isNaN( mat.m11 ) ||
               Float.isNaN( mat.m21 ) ||
               Float.isNaN( mat.m02 ) ||
               Float.isNaN( mat.m12 ) ||
               Float.isNaN( mat.m22 );
    }

}
