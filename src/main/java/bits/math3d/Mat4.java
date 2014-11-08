/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;


/**
 * These are all homographic coordinates.
 *
 * @author Philip DeCamp
 */
public class Mat4 {

    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    public Mat4() {}


    public Mat4( Mat4 copy ) {
        Mat.put( copy, this );
    }


    @Override
    public boolean equals( Object obj ) {
        if( !( obj instanceof Mat4 ) ) {
            return false;
        }

        Mat4 v = (Mat4)obj;
        // v == this is necessary to catch NaNs.
        return v == this ||
               m00 == v.m00 &&
               m01 == v.m01 &&
               m02 == v.m02 &&
               m03 == v.m03 &&
               m10 == v.m10 &&
               m11 == v.m11 &&
               m12 == v.m12 &&
               m13 == v.m13 &&
               m20 == v.m20 &&
               m21 == v.m21 &&
               m22 == v.m22 &&
               m23 == v.m23 &&
               m30 == v.m30 &&
               m31 == v.m31 &&
               m32 == v.m32 &&
               m33 == v.m33;
    }

    @Override
    public int hashCode() {
        int hash = Float.floatToIntBits( m00 + m11 + m22 + m33 );
        hash ^= Float.floatToIntBits( m01 + m12 + m23 + m30 );
        hash ^= Float.floatToIntBits( m02 + m13 + m20 + m31 );
        hash ^= Float.floatToIntBits( m03 + m10 + m21 + m32 );
        return hash;
    }

    @Override
    public String toString() {
        return Mat.format( this );
    }

}
