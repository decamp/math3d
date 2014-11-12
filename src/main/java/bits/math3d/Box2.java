/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;

/**
 * 2D Axis-Aligned Bounding-Box
 *
 * @author decamp
 */
public final class Box2 {

    /** Minimum X coord**/
    public float x0;
    /** Minimum Y coord**/
    public float y0;
    /** Maximum X coord**/
    public float x1;
    /** Maximum Y coord**/
    public float y1;

    public Box2() {}


    public Box2( Box2 copy ) {
        x0 = copy.x0;
        y0 = copy.y0;
        x1 = copy.x1;
        y1 = copy.y1;
    }


    public Box2( float x0, float y0, float z0, float x1, float y1, float z1 ) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }



    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof Box3 ) ) {
            return false;
        }

        Box3 b = (Box3)obj;
        return x0 == b.x0 &&
               y0 == b.y0 &&
               x1 == b.x1 &&
               y1 == b.y1;
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits( x0 + y0 ) ^
               Float.floatToIntBits( x1 + y1 );
    }

    @Override
    public String toString() {
        return Box.format( this );
    }

}
