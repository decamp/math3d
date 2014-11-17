/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d.geom;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

import bits.math3d.*;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * @author decamp
 */
public class ClipTest {

    @Test public void testClipCenterOfTriangle() throws Exception {
        Box3 clip = new Box3( 0, 0, 0, 10, 10, 10 );
        Vec3 v0   = new Vec3( 10,  0,  5 );
        Vec3 v1   = new Vec3( 10,  5, 10 );
        Vec3 v2   = new Vec3(  5,  0, 10 );
        Vec3[] v  = { v0, v1, v2 };

        // Inflate triangle.
        {
            Vec3 c = new Vec3();
            for( int i = 0; i < 3; i++ ) {
                c.x += v[i].x / 3;
                c.y += v[i].y / 3;
                c.z += v[i].z / 3;
            }

            float inf = 1.2f;
            for( int i = 0; i < 3; i++ ) {
                float d;
                v[i].x = c.x + inf * ( v[i].x - c.x );
                v[i].y = c.y + inf * ( v[i].y - c.y );
                v[i].z = c.z + inf * ( v[i].z - c.z );
            }
        }

        PolyLine loop = new PolyLine();
        for( int m = 0; m < 8; m++ ) {
            Vec3[] vv = mirror( v, m, clip );
//            Vec3[] vv = v;

            for( int i = 0; i < 3; i++ ) {
                assertTrue( Clip.clipPlanar( vv, 0, 3, clip, loop ) );
                assertEquals( 3, loop.mSize );
                //graph(clip, loop);
                rotate( vv, 3 );
            }
        }

        // Thread.sleep(100000L);
    }


    @Test public void testClipTriangleOneVertexOut() {
        Box3 clip = new Box3( 0, 0, 0, 10, 10, 10 );
        Vec3 v0 = new Vec3( 5, 5, 5  );
        Vec3 v1 = new Vec3( 15, 5, 5 );
        Vec3 v2 = new Vec3( 6, 5, 7  );
        Vec3[] v = { v0, v1, v2 };

        PolyLine loop = new PolyLine();

        for( int m = 0; m < 8; m++ ) {
            //Vec3[] vv = mirror( v, m, clip );
            Vec3[] vv = v;

            for( int i = 0; i < 3; i++ ) {
                assertTrue( Clip.clipPlanar( vv, 0, 3, clip, loop ) );
                assertEquals( 4, loop.mSize );
                // graph(clip, loop);
                rotate( vv, 3 );
            }
        }
    }


    @Test public void testClipTriangleTwoVerticesOut() {
        Box3 clip = new Box3( 0, 0, 0, 10, 10, 10 );
        Vec3 v0 = new Vec3( 5, 5, 15 );
        Vec3 v1 = new Vec3( 15, 5, 5 );
        Vec3 v2 = new Vec3( 6, 5, 7  );
        Vec3[] v = { v0, v1, v2 };
        PolyLine loop = new PolyLine();

        for( int m = 0; m < 8; m++ ) {
            Vec3[] vv = mirror( v, m, clip );

            for( int i = 0; i < 3; i++ ) {
                assertTrue( Clip.clipPlanar( vv, 0, 3, clip, loop ) );
                assertEquals( 4, loop.mSize );
                // graph(clip, loop);
                rotate( vv, 3 );
            }
        }
    }


    @Test public void testClipTriangleAllIn() throws InterruptedException {
        Box3 clip = new Box3( 0, 0, 0, 10, 10, 10 );
        Vec3 v0 = new Vec3( 5, 5, 5 );
        Vec3 v1 = new Vec3( 7, 5, 5 );
        Vec3 v2 = new Vec3( 6, 5, 7 );
        Vec3[] v = { v0, v1, v2 };
        PolyLine loop = new PolyLine();

        for( int m = 0; m < 8; m++ ) {
            Vec3[] vv = mirror( v, m, clip );
            for( int i = 0; i < 3; i++ ) {
                assertTrue( Clip.clipPlanar( vv, 0, 3, clip, loop ) );
                assertEquals( 3, loop.mSize );
                // graph(clip, loop);
                rotate( vv, 3 );
            }
        }
    }
    

    @Test public void testAllOut() {
        Box3 clip = new Box3( 0, 0, 0, 10, 10, 10 );
        Vec3 v0 = new Vec3( 10, 0, 5 );
        Vec3 v1 = new Vec3( 10, 5, 10 );
        Vec3 v2 = new Vec3( 5, 0, 10 );
        Vec3[] v = { v0, v1, v2 };

        // Translate triangle.
        {
            Vec3 c = new Vec3();
            for( int i = 0; i < 3; i++ ) {
                c.x += v[i].x / 3;
                c.y += v[i].y / 3;
                c.z += v[i].z / 3;
            }

            float dist = Vec.len( c ) * 1.2f;
            c.x = (10 - c.x) * dist;
            c.y = ( 0 - c.y) * dist;
            c.z = (10 - c.z) * dist;
            for( int i = 0; i < 3; i++ ) {
                v[i].x += c.x;
                v[i].y += c.y;
                v[i].z += c.z;
            }
        }

        PolyLine loop = new PolyLine();
        for( int m = 0; m < 8; m++ ) {
            Vec3[] vv = mirror( v, m, clip );

            for( int i = 0; i < 3; i++ ) {
                assertFalse( Clip.clipPlanar( vv, 0, 3, clip, loop ) );
                assertEquals( 0, loop.mSize );
                rotate( vv, 3 );
            }
        }
    }


    private static void rotate( Vec3[] v, int len ) {
        if( len == 0 ) {
            return;
        }

        Vec3 temp = v[0];
        System.arraycopy( v, 1, v, 0, len - 1 );
        v[len - 1] = temp;
    }


    private static Vec3[] mirror( Vec3[] v, int axes, Box3 clip ) {
        Vec3[] ret = new Vec3[ v.length ];
        for( int i = 0; i < v.length; i++ ) {
            ret[i] = new Vec3( v[i] );
        }

        for( int axis = 0; axis < 3; axis++ ) {
            if( (axes & (1 << axis)) == 0 ) {
                continue;
            }

            float cent = clip.center( axis );
            for( int i = 0; i < v.length; i++ ) {
                ret[i].el( axis, 2f * cent - ret[i].el( axis ) );
            }
        }

        return ret;
    }

    @SuppressWarnings( "unused" )
    private static void graph( Box3 clip, PolyLine loop ) {
        final int w = 1024;
        final int h = 1024;

        BufferedImage im = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g = (Graphics2D)im.getGraphics();

        g.setColor( Color.WHITE );
        g.fillRect( 0, 0, 1024, 1024 );
        g.setColor( Color.BLACK );

        double min = clip.x0 - 10.0;
        double max = clip.x1 + 10.0;
        double span = max - min;

        AffineTransform aff = AffineTransform.getScaleInstance( w / ( clip.x1 - clip.x0 + 20.0 ),
                                                                h / ( clip.z1 - clip.z0 + 20.0 ) );
        aff.translate( -clip.x0 + 10.0, -clip.z0 + 10.0 );
        g.setTransform( aff );
        g.setStroke( new BasicStroke( (float)(1f * (clip.x1 - clip.x0) / w) ) );

        {
            g.draw( new Rectangle2D.Double( clip.x0, clip.z0, clip.x1 - clip.x0, clip.z1 - clip.z0 ) );
        }

        g.setColor( Color.RED );
        for( int i = 0; i < loop.mSize; i++ ) {
            Vec3 a = loop.mVerts[i];
            Vec3 b = loop.mVerts[(i + 1) % loop.mSize ];
            g.draw( new Line2D.Double( a.x, a.z, b.x, b.z ) );
        }

        ImagePanel.showImage( im );
    }



    @Test public void testClipCenterOfTriangle3() throws Exception {
        double[] clip = { 0.0, 0.0, 0.0, 10.0, 10.0, 10.0 };
        double[] v0 = { 10.0, 0.0, 5.0 };
        double[] v1 = { 10.0, 5.0, 10.0 };
        double[] v2 = { 5.0, 0.0, 10.0 };
        double[][] v = { v0, v1, v2 };

        // Inflate triangle.
        {

            double[] c = { 0, 0, 0 };
            for( int i = 0; i < 3; i++ ) {
                for( int j = 0; j < 3; j++ ) {
                    c[j] += v[i][j] / 3.0;
                }
            }

            double inf = 1.2;
            for( int i = 0; i < 3; i++ ) {
                for( int j = 0; j < 3; j++ ) {
                    double d = v[i][j] - c[j];
                    v[i][j] = c[j] + d * inf;
                }
            }
        }


        Loop loop = new Loop();
        for( int m = 0; m < 8; m++ ) {
            double[][] vv = mirror( v, m, clip );

            for( int i = 0; i < 3; i++ ) {
                assertTrue( Clip.clipPlanar3( vv, 0, 3, clip, loop ) );
                assertEquals( 3, loop.mLength );
                //graph(clip, loop);
                rotate( vv, 3 );
            }
        }

        // Thread.sleep(100000L);
    }


    @Test public void testClipTriangleOneVertexOut3() {
        double[] clip = { 0.0, 0.0, 0.0, 10.0, 10.0, 10.0 };
        double[] v0 = { 5, 5, 5 };
        double[] v1 = { 15, 5, 5 };
        double[] v2 = { 6, 5, 7 };
        double[][] v = { v0, v1, v2 };

        Loop loop = new Loop();

        for( int m = 0; m < 8; m++ ) {
            double[][] vv = mirror( v, m, clip );

            for( int i = 0; i < 3; i++ ) {
                assertTrue( Clip.clipPlanar3( vv, 0, 3, clip, loop ) );
                assertEquals( 4, loop.mLength );
                // graph(clip, loop);
                rotate( vv, 3 );
            }
        }
    }


    @Test public void testClipTriangleTwoVerticesOut3() {
        double[] clip = { 0.0, 0.0, 0.0, 10.0, 10.0, 10.0 };
        double[] v0 = { 5, 5, 15 };
        double[] v1 = { 15, 5, 5 };
        double[] v2 = { 6, 5, 7 };
        double[][] v = { v0, v1, v2 };

        Loop loop = new Loop();

        for( int m = 0; m < 8; m++ ) {
            double[][] vv = mirror( v, m, clip );

            for( int i = 0; i < 3; i++ ) {
                assertTrue( Clip.clipPlanar3( vv, 0, 3, clip, loop ) );
                assertEquals( 4, loop.mLength );
                // graph(clip, loop);
                rotate( vv, 3 );
            }
        }
    }


    @Test public void testClipTriangleAllIn3() throws InterruptedException {
        double[] clip = { 0.0, 0.0, 0.0, 10.0, 10.0, 10.0 };
        double[] v0 = { 5, 5, 5 };
        double[] v1 = { 7, 5, 5 };
        double[] v2 = { 6, 5, 7 };
        double[][] v = { v0, v1, v2 };

        Loop loop = new Loop();

        for( int m = 0; m < 8; m++ ) {
            double[][] vv = mirror( v, m, clip );

            for( int i = 0; i < 3; i++ ) {
                assertTrue( Clip.clipPlanar3( vv, 0, 3, clip, loop ) );
                assertEquals( 3, loop.mLength );
                // graph(clip, loop);
                rotate( vv, 3 );
            }
        }
    }


    @Test public void testAllOut3() {
        double[] clip = { 0.0, 0.0, 0.0, 10.0, 10.0, 10.0 };
        double[] v0 = { 10.0, 0.0, 5.0 };
        double[] v1 = { 10.0, 5.0, 10.0 };
        double[] v2 = { 5.0, 0.0, 10.0 };
        double[][] v = { v0, v1, v2 };

        // Translate triangle.
        {
            double[] c = { 0, 0, 0 };

            for( int i = 0; i < 3; i++ ) {
                for( int j = 0; j < 3; j++ ) {
                    c[j] += v[i][j] / 3.0;
                }
            }

            double dist = Vec.len3( c ) * 1.2;
            c[0] = (10.0 - c[0]) * dist;
            c[1] = (0.0 - c[1]) * dist;
            c[2] = (10.0 - c[2]) * dist;

            for( int i = 0; i < 3; i++ ) {
                for( int j = 0; j < 3; j++ ) {
                    v[i][j] += c[j];
                }
            }
        }

        Loop loop = new Loop();

        for( int m = 0; m < 8; m++ ) {
            double[][] vv = mirror( v, m, clip );

            for( int i = 0; i < 3; i++ ) {
                assertFalse( Clip.clipPlanar3( vv, 0, 3, clip, loop ) );
                assertEquals( 0, loop.mLength );
                rotate( vv, 3 );
            }
        }
    }


    private static void rotate( double[][] v, int len ) {
        if( len == 0 ) {
            return;
        }

        double[] temp = v[0];

        for( int i = 1; i < len; i++ ) {
            v[i - 1] = v[i];
        }

        v[len - 1] = temp;
    }


    private static double[][] mirror( double[][] v, int axes, double[] clip ) {
        double[][] ret = new double[v.length][];

        for( int i = 0; i < v.length; i++ ) {
            ret[i] = v[i].clone();
        }

        for( int axis = 0; axis < 3; axis++ ) {
            if( (axes & (1 << axis)) == 0 ) {
                continue;
            }

            double cent = (clip[axis] + clip[axis + 3]) * 0.5;

            for( int i = 0; i < v.length; i++ ) {
                ret[i][axis] = 2.0 * cent - ret[i][axis];
            }
        }

        return ret;
    }

    @SuppressWarnings( "unused" )
    private static void graph( double[] clip, Loop loop ) {
        final int w = 1024;
        final int h = 1024;

        BufferedImage im = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g = (Graphics2D)im.getGraphics();

        g.setColor( Color.WHITE );
        g.fillRect( 0, 0, 1024, 1024 );
        g.setColor( Color.BLACK );

        double min = clip[0] - 10.0;
        double max = clip[3] + 10.0;
        double span = max - min;

        // AffineTransform aff = AffineTransform.getTranslateInstance(-clip[0],
        // -clip[3]);
        // aff.scale(w / (clip[3] - clip[0] + 20.0), h / (clip[5] - clip[2] +
        // 20.0));

        AffineTransform aff = AffineTransform.getScaleInstance( w / (clip[3] - clip[0] + 20.0),
                                                                h / (clip[5] - clip[2] + 20.0) );
        aff.translate( -clip[0] + 10.0, -clip[3] + 20.0 );
        g.setTransform( aff );
        g.setStroke( new BasicStroke( (float)(1f * (clip[3] - clip[0]) / w) ) );

        {
            g.draw( new Rectangle2D.Double( clip[0], clip[2], clip[3] - clip[0], clip[5] - clip[2] ) );
        }

        g.setColor( Color.RED );
        for( int i = 0; i < loop.mLength; i++ ) {
            double[] a = loop.mVerts[i];
            double[] b = loop.mVerts[(i + 1) % loop.mLength];

            g.draw( new Line2D.Double( a[0], a[2], b[0], b[2] ) );
        }

        ImagePanel.showImage( im );
    }

}
