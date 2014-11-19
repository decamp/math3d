/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;

import org.junit.Test;

import java.util.Random;


/**
 * @author Philip DeCamp
 */
public class TestMat3 {

    @Test
    public void testNormalizeRotation() {
        Random rand = new Random( 6 );

        for( int i = 0; i < 10; i++ ) {
            Mat3 mat = Tests.randMat3( rand, 3f );
            Mat.normalizeRotationMatrix( mat );
            Mat3 inv = new Mat3();
            Mat.transpose( mat, inv );
            Mat3 eye = new Mat3();
            Mat.mult( mat, inv, eye );
            Mat3 comp = new Mat3();
            Mat.identity( comp );

            Tests.assertNear( eye, comp );
        }
    }
}
