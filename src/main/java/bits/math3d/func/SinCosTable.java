/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d.func;

import bits.math3d.Ang;


public class SinCosTable {

    private static final int      BINS  = 256;
    private static final double[] TABLE = new double[2 * BINS];
    private static final double   SCALE = TABLE.length / (Ang.TWO_PI);

    static {
        for( int i = 0; i < BINS; i++ ) {
            double ang = Ang.TWO_PI * i / BINS;
            TABLE[ i*2   ] = Math.sin( ang );
            TABLE[ i*2+1 ] = Math.cos( ang );
        }
    }


    public static void sinCos( double rads, double[] out2x1 ) {
        if( rads < 0.0 || rads >= Ang.TWO_PI ) {
            rads = Ang.normalize( rads, Ang.PI );
        }

        final int i0 = (int)rads;
        final int i1 = ( i0 + 1 ) % BINS;
        final double q = SCALE * rads - i0;
        final double p = 1.0 - q;

        out2x1[0] = p * TABLE[ 2*i0   ] + q * TABLE[ 2*i1   ];
        out2x1[1] = p * TABLE[ 2*i0+1 ] + q * TABLE[ 2*i1+1 ];
    }


    public static double sin( double rads ) {
        if( rads < 0.0 || rads >= Ang.TWO_PI ) {
            rads = Ang.normalize( rads, Ang.PI );
        }

        final int i0 = (int)rads;
        final int i1 = ( i0 + 1 ) % BINS;
        final double q = SCALE * rads - i0;
        final double p = 1.0 - q;

        return p * TABLE[ 2*i0   ] + q * TABLE[ 2*i1   ];
    }


    public static double cos( double rads ) {
        if( rads < 0.0 || rads >= Ang.TWO_PI ) {
            rads = Ang.normalize( rads, Ang.PI );
        }

        final int i0 = (int)rads;
        final int i1 = ( i0 + 1 ) % BINS;
        final double q = SCALE * rads - i0;
        final double p = 1.0 - q;

        return p * TABLE[ 2*i0+1 ] + q * TABLE[ 2*i1+1 ];
    }

}
