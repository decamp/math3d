/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d.func;

import bits.math3d.Vec2;

/**
 * @author Philip DeCamp
 */
public interface Func1v2 {
    public void apply( float t, Vec2 out );
}
