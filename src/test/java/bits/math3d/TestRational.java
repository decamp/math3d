/*
 * Copyright (c) 2015. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.*;


/**
 * @author Philip DeCamp
 */
public class TestRational {

    @Test public void testIntMult() {
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

    @Test
    public void testMultRationals() {
        Rational c = new Rational();
        assertTrue( Rational.multRationals( 1, 4, 1, 4, c ) );
        assertEquals( new Rational( 1, 16 ), c );

        assertTrue( Rational.multRationals( 4, 1, 1, 4, c ) );
        assertEquals( new Rational( 1, 1 ), c );

        assertFalse( Rational.multRationals( Integer.MAX_VALUE, 1, Integer.MAX_VALUE, 1, c ) );
        assertEquals( new Rational( 0x7FFFFFFE, 1 ), c );

        assertFalse( Rational.multRationals( Integer.MIN_VALUE, 1, Integer.MAX_VALUE, 1, c ) );
        assertEquals( new Rational( 0x80000001, 1 ), c );

        assertTrue( Rational.multRationals( Integer.MIN_VALUE, 0, Integer.MAX_VALUE, 1, c ) );
        assertEquals( new Rational( 0, 0 ), c );
    }

}
