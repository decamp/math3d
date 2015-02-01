/*
 * Copyright (c) 2015. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d.geom;

import bits.math3d.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * @author Philip DeCamp
 */
public class PolyTest {

    @Test
    public void testArea() {
        Vec2[] t = { new Vec2( 0, 0 ), new Vec2( 1, 0 ), new Vec2( 0, 1 ) };
        Vec3[] u = { new Vec3( 0, 0, 0 ), new Vec3( 1, 0, 0 ), new Vec3( 0, 1, 0 ) };

        assertTrue( Tol.approxEqual( 0.5, Polygons.area( t ) ) );
        assertTrue( Tol.approxEqual( 0.5, Polygons.triArea( t[0], t[1], t[2] ) ) );
        assertTrue( Tol.approxEqual( 0.5, Polygons.triArea( u[0], u[1], u[2] ) ) );
    }

}
