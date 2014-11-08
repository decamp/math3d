/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;


/**
 * These are all homographic coordinates, yo.  Matrices are 4x4.  Vectors are 3x1.
 *
 * @author Philip DeCamp
 */
public class Mat3 {

    public float m00, m01, m02;
    public float m10, m11, m12;
    public float m20, m21, m22;


    public Mat3() {}


    public Mat3( Mat3 copy ) {
        Mat.put( copy, this );
    }


    public Mat3( float v00, float v10, float v20,
                 float v01, float v11, float v21,
                 float v02, float v12, float v22 )
    {
        this.m00 = v00;
        this.m10 = v01;
        this.m20 = v01;
        this.m01 = v01;
        this.m11 = v01;
        this.m21 = v01;
        this.m02 = v01;
        this.m12 = v01;
        this.m22 = v01;
    }



    @Override
    public boolean equals( Object obj ) {
        if( !( obj instanceof Mat3 ) ) {
            return false;
        }

        Mat3 v = (Mat3)obj;
        // v == this is necessary to catch NaNs.
        return v == this ||
               m00 == v.m00 &&
               m01 == v.m01 &&
               m02 == v.m02 &&
               m10 == v.m10 &&
               m11 == v.m11 &&
               m12 == v.m12 &&
               m20 == v.m20 &&
               m21 == v.m21 &&
               m22 == v.m22;
    }

    @Override
    public int hashCode() {
        int hash = Float.floatToIntBits( m00 + m11 + m22 );
        hash ^= Float.floatToIntBits(    m01 + m12 + m20 );
        hash ^= Float.floatToIntBits(    m02 + m10 + m21 );
        return hash;
    }

    @Override
    public String toString() {
        return Mat.format( this );
    }

}
