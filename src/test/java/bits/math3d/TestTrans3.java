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
public class TestTrans3 {

    @Test
    public void testInvert() {
        Random rand = new Random( 8 );
        Trans3 trans = new Trans3();
        Mat.put( Tests.randRotation( rand ), trans.mRot );
        trans.mPos.x = rand.nextFloat();
        trans.mPos.y = rand.nextFloat();
        trans.mPos.z = rand.nextFloat();

        Trans3 inv = new Trans3();
        Trans.invert( trans, inv );

        System.out.println( trans );
        System.out.println( inv   );

        Trans.invert( inv, inv );

        Tests.assertNear( trans.mPos, inv.mPos );
        Tests.assertNear( trans.mRot, inv.mRot );
    }


}
