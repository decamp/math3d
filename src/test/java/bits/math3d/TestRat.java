/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;

import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Random;


/**
 * @author Philip DeCamp
 */
public class TestRat {

    @Test
    public void testIntMult() {
        Random rand = new Random( 100 );

        for( int i = 0; i < 100; i++ ) {
            int val  = rand.nextInt();
            int mult = rand.nextInt() & 0x7FFFFFFF;
            int div  = rand.nextInt() & 0x7FFFFFFF;
            int round = rand.nextInt( 5 );
            if( round == 4 ) {
                round = 5;
            }

            long ans0 = Rat.mult( val, mult, div, round );
            long ans1 = Rat.mult( val, mult, div, Rat.ROUND_DOWN );
            long ans2 = Rat.mult( val, mult, div, Rat.ROUND_UP );
            BigInteger bigint = BigInteger.valueOf( val ).multiply( BigInteger.valueOf( mult ) ).divide( BigInteger.valueOf( div ) );

            System.out.format( "%d * %d / %d ==    %d  /  %d  /   %d   /  %d\n", val, mult, div, ans0, ans1, ans2, bigint.longValue() );
//            if( ans1 < Integer.MAX_VALUE && ans1 > Integer.MIN_VALUE ) {
//                assertEquals( ans1, ans0 );
//            }
        }

    }

}
