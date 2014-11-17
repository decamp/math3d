/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d.func;

import bits.math3d.Vec4;


/**
 * @author Philip DeCamp
 */
public interface Func1v4 {
    public void apply( float t, Vec4 out );
}
