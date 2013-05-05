package cogmac.math3d;

import java.util.Random;

import org.junit.*;
import static org.junit.Assert.*;

public class TestBox2 {
    
    
    @Test
    public void testFit() {
        Random rand = new Random( 0 );
        float[][] a = new float[128][4];
        float[][] b = new float[128][4];
        Rect[] ra;
        Rect[] rb;
        float[] c = new float[4];
        
        fill( rand, a );
        fill( rand, b );
        ra = toRect( a );
        rb = toRect( b );
        
        for( int i = 0; i < a.length; i++ ) {
            Rect r = ra[i].fit( rb[i] );
            Box2.fit( a[i], b[i], c );
            assertTrue( equiv( r, c ) );
        }
    }
    

    @Test
    public void testClamp() {
        Random rand = new Random( 0 );
        float[][] a = new float[128][4];
        float[][] b = new float[128][4];
        Rect[] ra;
        Rect[] rb;
        float[] c = new float[4];
        
        fill( rand, a );
        fill( rand, b );
        ra = toRect( a );
        rb = toRect( b );
        
        for( int i = 0; i < a.length; i++ ) {
            Rect r = ra[i].clamp( rb[i] );
            Box2.clamp( a[i], b[i], c );
            assertTrue( equiv( r, c ) );
        }
    }
    
    
    @Test
    @Ignore
    public void testSpeed() {
        Random rand = new Random( 0 );
        float[][] a = new float[128][4];
        float[][] b = new float[128][4];
        Rect[] ra;
        Rect[] rb;
        
        float[] c   = new float[4];
        
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
                Box2.clamp( a[i % a.length], b[i % b.length], c );
            }
            
            tb += System.nanoTime() - t0;
        }
        
        System.out.println( "A : " + ( ta / 1000000000.0 ) );
        System.out.println( "B : " + ( tb / 1000000000.0 ) );
        
    }

    
    private static void fill( Random rand, float[][] out ) {
        for( float[] a: out ) {
            fill( rand, a );
        }
    }
    
    
    private static void fill( Random rand, float[] out ) {
        for( int i = 0; i < 4; i++ ) {
            out[i] = rand.nextFloat();
        }
        Box2.fix( out );
    }

    
    private static boolean equiv( Rect r, float[] b ) {
        return Tol.approxEqual( (float)r.minX(), b[0] ) &&
               Tol.approxEqual( (float)r.minY(), b[1] ) &&
               Tol.approxEqual( (float)r.maxX(), b[2] ) &&
               Tol.approxEqual( (float)r.maxY(), b[3] );
    }
    
    
    private static Rect[] toRect( float[][] a ) { 
        Rect[] ret = new Rect[a.length];
        for( int i = 0; i < a.length; i++ ) {
            ret[i] = Rect.fromEdges( a[i][0], a[i][1], a[i][2], a[i][3] );
        }
        return ret;
    }
    
}
