/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;

/**
 * @author Philip DeCamp
 */
public class TestPots {

    public static void main( String[] args ) {
        for( int i = 0; i < 66; i++ ) {
            System.out.println( i + "\t" + Pots.ceilLog2( (long)i ) );
        }

    }

}
