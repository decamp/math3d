/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;

import java.util.Random;

import bits.math3d.geom.Rect;
import org.junit.*;
import static org.junit.Assert.*;

public class TestBox2 {

    @Test
    public void testFit() {
        Random rand = new Random( 0 );
        double[][] a = new double[128][4];
        double[][] b = new double[128][4];
        Rect[] ra;
        Rect[] rb;
        double[] c = new double[4];
        
        fill( rand, a );
        fill( rand, b );
        ra = toRect( a );
        rb = toRect( b );
        
        for( int i = 0; i < a.length; i++ ) {
            Rect r = ra[i].fit( rb[i] );
            Box.fit2( a[i], b[i], c );
            assertTrue( equiv( r, c ) );
        }
    }

    @Test
    public void testClamp() {
        Random rand = new Random( 0 );
        double[][] a = new double[128][4];
        double[][] b = new double[128][4];
        Rect[] ra;
        Rect[] rb;
        double[] c = new double[4];
        
        fill( rand, a );
        fill( rand, b );
        ra = toRect( a );
        rb = toRect( b );
        
        for( int i = 0; i < a.length; i++ ) {
            Rect r = ra[i].clamp( rb[i] );
            Box.clamp2( a[i], b[i], c );
            assertTrue( equiv( r, c ) );
        }
    }

    @Test
    @Ignore
    public void testSpeed() {
        Random rand = new Random( 0 );
        double[][] a = new double[128][4];
        double[][] b = new double[128][4];
        Rect[] ra;
        Rect[] rb;
        double[] c = new double[4];

        fill( rand, a );
        fill( rand, b );
        ra = toRect( a );
        rb = toRect( b );
        
        long ta = 0;
        long tb = 0;
        
        for( int trial = 0; trial < 10000; trial++ ) {
            long t0 = System.nanoTime();
            for( int i = 0; i < 1024 * 4; i++ ) {
                ra[i % a.length].clamp( rb[i % b.length] );
            }
            
            ta += System.nanoTime() - t0;
            t0 = System.nanoTime();
            
            for( int i = 0; i < 1024 * 4; i++ ) {
                Box.clamp2( a[i % a.length], b[i % b.length], c );
            }
            
            tb += System.nanoTime() - t0;
        }
        
        System.out.println( "A : " + ( ta / 1000000000.0 ) );
        System.out.println( "B : " + ( tb / 1000000000.0 ) );
    }

    
    private static void fill( Random rand, double[][] out ) {
        for( double[] a: out ) {
            fill( rand, a );
        }
    }
    
    
    private static void fill( Random rand, double[] out ) {
        for( int i = 0; i < 4; i++ ) {
            out[i] = rand.nextDouble();
        }
        Box.fix2( out );
    }


    private static boolean equiv( Rect r, double[] b ) {
        return Tol.approxEqual( r.minX(), b[0] ) &&
               Tol.approxEqual( r.minY(), b[1] ) &&
               Tol.approxEqual( r.maxX(), b[2] ) &&
               Tol.approxEqual( r.maxY(), b[3] );
    }
    
    
    private static Rect[] toRect( double[][] a ) {
        Rect[] ret = new Rect[a.length];
        for( int i = 0; i < a.length; i++ ) {
            ret[i] = Rect.fromEdges( a[i][0], a[i][1], a[i][2], a[i][3] );
        }
        return ret;
    }
    
}
