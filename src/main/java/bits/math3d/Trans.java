/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;


public final class Trans {

    public static void put( Trans3 a, Trans3 out ) {
        Vec.put( a.mPos, out.mPos );
        Mat.put( a.mRot, out.mRot );
    }


    public static void identity( Trans3 a ) {
        Vec.put( 0, 0, 0, a.mPos );
        Mat.identity( a.mRot );
    }


    public static void mult( Trans3 a, Vec3 vec, Vec3 out ) {
        Vec3 p = a.mPos;
        float x = p.x;
        float y = p.y;
        float z = p.z;
        Mat.mult( a.mRot, vec, out );
        out.x += x;
        out.y += y;
        out.z += z;
    }


    public static void mult( Trans3 a, Trans3 b, Trans3 out ) {
        mult( a, b.mPos, out.mPos );
        Mat.mult( a.mRot, b.mRot, out.mRot );
    }


    public static void invert( Trans3 tr, Trans3 out ) {
        Mat.transpose( tr.mRot, out.mRot );
        Vec.mult( -1f, out.mPos );
        Mat.mult( out.mRot, out.mPos, out.mPos );
    }


    public static void matToTrans( Mat4 a, Trans3 out ) {
        Mat.put( a, out.mRot );
        Vec.put( a.m30, a.m31, a.m32, out.mPos );
    }


    public static void transToMat( Trans3 tr, Mat4 out ) {
        final Mat3 rot = tr.mRot;
        out.m00 = rot.m00;
        out.m01 = rot.m01;
        out.m02 = rot.m02;
        out.m03 = 0;
        out.m10 = rot.m10;
        out.m11 = rot.m11;
        out.m12 = rot.m12;
        out.m23 = 0;
        out.m20 = rot.m20;
        out.m21 = rot.m21;
        out.m22 = rot.m22;
        out.m23 = 0;
        out.m30 = tr.mPos.x;
        out.m31 = tr.mPos.y;
        out.m32 = tr.mPos.z;
        out.m33 = 1;
    }


    public static String format( Trans3 tr ) {
        return Trans3.class.getSimpleName() + Vec.format( tr.mPos ) + "\n" + Mat.format( tr.mRot );
    }


}
