/*
 * Copyright (c) 2015. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d.geom;

import bits.math3d.*;


/**
 * @author Philip DeCamp
 */
public class Polygons {

    public static float area( Vec2[] p ) {
        return area( p, 0, p.length );
    }


    public static float area( Vec2[] p, int off, int len ) {
        Vec2 p0 = p[len-1+off];
        float sum  = 0;
        for( int i = 0; i < len; i++ ) {
            Vec2 p1 = p[i+off];
            sum += ( p1.x - p0.x ) * ( p1.y + p0.y );
            p0 = p1;
        }
        return ( sum < 0 ? -sum : sum ) * 0.5f;
    }


    public static float triArea( Vec2 a, Vec2 b, Vec2 c ) {
        return Math.abs( 0.5f * ( (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y) ) );
    }


    public static float triArea( Vec3 a, Vec3 b, Vec3 c ) {
        float x = (b.y - a.y) * (c.z - a.z) - (c.y - a.y) * (b.z - a.z);
        float y = (b.z - a.z) * (c.x - a.x) - (c.z - a.z) * (b.x - a.x);
        float z = (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
        return (float)( 0.5 * Math.sqrt( x * y + y * y + z * z ) );
    }



    public static double area2( double[][] p ) {
        return area2( p, 0, p.length );
    }


    public static double area2( double[][] p, int off, int len ) {
        double[] p0 = p[len-1+off];
        double sum  = 0.0;

        for( int i = 0; i < len; i++ ) {
            double[] p1 = p[i+off];
            sum += ( p1[0] - p0[0] ) * ( p1[1] + p0[1] );
            p0 = p1;
        }

        return ( sum < 0.0 ? -sum : sum ) * 0.5;
    }


    public static double triArea2( double[] a, double[] b, double[] c ) {
        double sum = ( b[0] - a[0] ) * ( b[1] + a[1] );
        sum += ( c[0] - b[0] ) * ( c[1] + b[1] );
        sum += ( a[0] - c[0] ) * ( a[1] + c[1] );
        return ( sum < 0.0 ? -sum : sum ) * 0.5;
    }


    public static double triArea3( double[] a, double[] b, double[] c ) {
        double x = (b[1] - a[1]) * (c[2] - a[2]) - (c[1] - a[1]) * (b[2] - a[2]);
        double y = (b[2] - a[2]) * (c[0] - a[0]) - (c[2] - a[2]) * (b[0] - a[0]);
        double z = (b[0] - a[0]) * (c[1] - a[1]) - (c[0] - a[0]) * (b[1] - a[1]);
        return 0.5 * Math.sqrt( x * x + y * y + z * z );
    }

}
