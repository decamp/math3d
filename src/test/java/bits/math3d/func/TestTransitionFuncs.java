/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d.func;

import org.junit.Test;

public class TestTransitionFuncs {
    
    @Test
    public void testTransSpeed() {
        long total0 = 0;
        long total1 = 0;
        
        Function11 cos = EaseFuncs.COS;
        Function11 ss  = EaseFuncs.SMOOTH;
        
        for( int i = 0; i < 1000; i++ ) {
            long t0 = System.nanoTime();
            for( int j = 0; j < 10000; j++) {
                cos.apply( j / 9999.0 );
            }
            long t1 = System.nanoTime();
            total0 += t1 - t0;
            
            t0 = System.nanoTime();
            for( int j = 0; j < 10000; j++) {
                ss.apply( j / 9999.0 );
            }
            t1 = System.nanoTime();
            total1 += t1 - t0;
        }
        
        System.out.println( "Cosine: " + ( total0 / 1000000000.0 ) );
        System.out.println( "SmootherStep: " + ( total1 / 1000000000.0 ) );
    }

}
