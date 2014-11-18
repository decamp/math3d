/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d.func;

import bits.math3d.SimplexNoise;
import bits.math3d.Tol;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;


/**
 * @author Philip DeCamp
 */
public class TestSimplexNoise {

    @Test
    public void testSameness2() {
        Random rand = new Random( 8 );
        for( int i = 0; i < 10000; i++ ) {
            double sx = rand.nextDouble() * 20.0 - 10.0;
            double sy = rand.nextDouble() * 20.0 - 10.0;

            assertTrue( Tol.approxEqual( SimplexNoiseReference.noise( sx, sy ),
                                         SimplexNoise.noise( sx, sy ) ) );
        }
    }

    @Test
    public void testSameness3() {
        Random rand = new Random();
        for( int i = 0; i < 10000; i++ ) {
            double sx = rand.nextDouble() * 20.0 - 10.0;
            double sy = rand.nextDouble() * 20.0 - 10.0;
            double sz = rand.nextDouble() * 20.0 - 10.0;
            assertTrue( Tol.approxEqual( SimplexNoiseReference.noise( sx, sy, sz ),
                                         SimplexNoise.noise( sx, sy, sz ) ) );
        }
    }

    @Test
    public void testSameness4() {
        Random rand = new Random();
        for( int i = 0; i < 10000; i++ ) {
            double sx = rand.nextDouble() * 20.0 - 10.0;
            double sy = rand.nextDouble() * 20.0 - 10.0;
            double sz = rand.nextDouble() * 20.0 - 10.0;
            double sw = rand.nextDouble() * 20.0 - 10.0;

            assertTrue( Tol.approxEqual( SimplexNoiseReference.noise( sx, sy, sz, sw ),
                                         SimplexNoise.noise( sx, sy, sz, sw ) ) );
        }
    }

    @Test
    public void testSpeed2() {
        long t0 = 0;
        long t1 = 0;
        Random rand = new Random();
        double accum = 0.0;
        double sum   = 0.0;
        long start;

        for( int i = 0; i < 30000; i++ ) {
            double sx = rand.nextDouble() * 20.0 - 10.0;
            double sy = rand.nextDouble() * 20.0 - 10.0;

            if( i % 2 == 0 ) {
                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoise.noise( sx, sy );
                }
                t0 += System.nanoTime() - start;
                accum += sum;

                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoiseReference.noise( sx, sy );
                }
                t1 += System.nanoTime() - start;
                accum += sum;
            } else {
                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoiseReference.noise( sx, sy );
                }
                t1 += System.nanoTime() - start;
                accum += sum;

                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoise.noise( sx, sy );
                }
                t0 += System.nanoTime() - start;
                accum += sum;
            }
        }


        System.out.println( "t0: " + ( t0 / 1000000000.0 ) );
        System.out.println( "t1: " + ( t1 / 1000000000.0 ) );
    }

    @Test
    public void testSpeed3() {
        long t0 = 0;
        long t1 = 0;
        Random rand = new Random();
        double accum = 0.0;
        double sum   = 0.0;
        long start;

        for( int i = 0; i < 30000; i++ ) {
            double sx = rand.nextDouble() * 20.0 - 10.0;
            double sy = rand.nextDouble() * 20.0 - 10.0;
            double sz = rand.nextDouble() * 20.0 - 10.0;

            if( i % 2 == 0 ) {
                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoiseReference.noise( sx, sy, sz );
                }
                t0 += System.nanoTime() - start;
                accum += sum;

                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoise.noise( sx, sy, sz );
                }
                t1 += System.nanoTime() - start;
                accum += sum;
            } else {
                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoise.noise( sx, sy, sz );
                }
                t1 += System.nanoTime() - start;
                accum += sum;

                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoiseReference.noise( sx, sy, sz );
                }
                t0 += System.nanoTime() - start;
                accum += sum;
            }
        }


        System.out.println( "t0: " + ( t0 / 1000000000.0 ) );
        System.out.println( "t1: " + ( t1 / 1000000000.0 ) );
    }

    @Test
    public void testSpeed4() {
        long t0 = 0;
        long t1 = 0;
        Random rand = new Random();
        double accum = 0.0;
        double sum   = 0.0;
        long start;

        for( int i = 0; i < 30000; i++ ) {
            double sx = rand.nextDouble() * 20.0 - 10.0;
            double sy = rand.nextDouble() * 20.0 - 10.0;
            double sz = rand.nextDouble() * 20.0 - 10.0;
            double sw = rand.nextDouble() * 20.0 - 10.0;

            if( i % 2 == 0 ) {
                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoiseReference.noise( sx, sy, sz, sw );
                }
                t0 += System.nanoTime() - start;
                accum += sum;

                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoise.noise( sx, sy, sz, sw );
                }
                t1 += System.nanoTime() - start;
                accum += sum;
            } else {
                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoise.noise( sx, sy, sz, sw );
                }
                t1 += System.nanoTime() - start;
                accum += sum;

                sum = 0.0;
                start = System.nanoTime();
                for( int j = 0; j < 100; j++ ) {
                    sum += SimplexNoiseReference.noise( sx, sy, sz, sw );
                }
                t0 += System.nanoTime() - start;
                accum += sum;
            }
        }


        System.out.println( "t0: " + ( t0 / 1000000000.0 ) );
        System.out.println( "t1: " + ( t1 / 1000000000.0 ) );
    }

}
