/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;

/**
 * @author Philip DeCamp
 */
public class Box3 {

    public float x0;
    public float y0;
    public float z0;
    public float x1;
    public float y1;
    public float z1;

    public Box3() {}


    public Box3( Box3 copy ) {
        x0 = copy.x0;
        y0 = copy.y0;
        z0 = copy.z0;
        x1 = copy.x1;
        y1 = copy.y1;
        z1 = copy.z1;
    }


    public Box3( float x0, float y0, float z0, float x1, float y1, float z1 ) {
        this.x0 = x0;
        this.y0 = y0;
        this.z0 = z0;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
    }



    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof Box3 ) ) {
            return false;
        }

        Box3 b = (Box3)obj;
        return x0 == b.x0 &&
               y0 == b.y0 &&
               z0 == b.z0 &&
               x1 == b.x1 &&
               y1 == b.y1 &&
               z1 == b.z1;
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits( x0 + y0 + z0 ) ^
               Float.floatToIntBits( x1 + y1 + z1 );
    }

    @Override
    public String toString() {
        return Box.format( this );
    }

}

