/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;

/**
 * Functions for length-2 vectors.
 *
 * @author Philip DeCamp  
 */
public class Vec2 {

    public float x;
    public float y;


    public Vec2() {}


    public Vec2( float x, float y ) {
        this.x = x;
        this.y = y;
    }


    public Vec2( Vec2 copy ) {
        x = copy.x;
        y = copy.y;
    }



    @Override
    public boolean equals( Object obj ) {
        if( !( obj instanceof Vec2 ) ) {
            return false;
        }
        Vec2 v = (Vec2)obj;
        // "v == this" is needed to protect against NaNs.
        return v == this || x == v.x && y == v.y;
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits( x + y );
    }

    @Override
    public String toString() {
        return Vec.format( this );
    }

}
