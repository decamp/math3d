/*
  * This code was ported from FFMPEG, released under LGPL 2.1 or later.
 */
package bits.math3d;


public final class Rational implements Comparable<Rational> {

    public static final int ROUND_ZERO        = 0; ///< Round toward zero.
    public static final int ROUND_INF         = 1; ///< Round away from zero.
    public static final int ROUND_DOWN        = 2; ///< Round toward -infinity.
    public static final int ROUND_UP          = 3; ///< Round toward +infinity.
    public static final int ROUND_NEAR_INF    = 5; ///< Round to nearest and halfway cases away from zero.
    public static final int ROUND_PASS_MINMAX = 8192; ///< Flag to pass Long.MIN_VALUE / Long.MAX_VALEU through instead of rescaling.

    /**
     * Reduces the fraction {@code num / den } to a <i>canonical</i> reduced representation, {@code out}, where:
     * <ul>
     * <li> There will exist no integer that can evenly divide both {@code out.num()} and {@code out.den()}.
     * <li> {@code out.den() >= 0} </li>
     * <li> If {@code den == 0, then out.num == 0 && out.den == 0 }.
     * <li> If {@code den != 0 && num == 0, then out.num = 0 && out.den == 1}.
     * </ul>
     * <p>
     * As a consequnce, if Rationals {@code a} and {@code b} are both
     * reduced, then: <br>
     * {@code a.toDouble() == b.toDouble() => a.equals(b) || Double.isNaN( a.toDouble() ) && Double.isNaN( b.toDouble() )}
     * 
     * @param num Numerator
     * @param den Denominator
     * @param out Receives rational in canonical reduced format.
     */
    public static void reduce( int num, int den, Rational out ) {
        if( den == 0 ) {
            num = 0;
        } else if( num == 0 ) {
            den = 1;
        } else {
            int d = gcd( num, den );
            num /= d;
            den /= d;
            if( den < 0 ) {
                num = -num;
                den = -den;
            }
        }
        out.mNum = num;
        out.mDen = den;
    }

    /**
     * Computes greatest common divisor of two terms using Euclid's method.
     * 
     * @param a Arbitrary number
     * @param b Arbitrary number
     * @return Largest number that evenly divides both a and b.
     */
    public static int gcd( int a, int b ) {
        while( b != 0 ) {
            int c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    /**
     * Computes greatest common divisor of two terms using Euclid's method.
     * 
     * @param a Arbitrary number
     * @param b Arbitrary number
     * @return Largest number that evenly divides both a and b.
     */
    public static long gcd( long a, long b ) {
        while( b != 0 ) {
            long c = a % b;
            a = b;
            b = c;
        }
        return a;
    }
    
    /**
     * Compute {@code val * num / den} with protection against overflow.
     * 
     * @return value nearest to {@code val * num / den}
     */
    public static long mult( long val, long num, long den ) {
        return multRound( val, num, den, ROUND_NEAR_INF );
    }

    /**
     * Computes {@code val * num / den} with specified rounding
     * and protection against overflow.
     * 
     * @return value nearest to {@code val * num / den},
     *          or if ROUND_PASS_MINMAX is set and a is Long.MIN_VALUE or
     *          Long.MAX_VALUE, then {@code val} is returned unchanged.
     */
    public static long multRound( long val, long num, long den, int rnd ) {
        long r = 0;
        assert( den > 0 );
        assert( num >= 0 );
        assert( ( rnd & ~ROUND_PASS_MINMAX) <= 5 && ( rnd & ~ROUND_PASS_MINMAX) != 4 );
        
        if( ( rnd & ROUND_PASS_MINMAX) != 0) {
            if( val == Long.MIN_VALUE || val == Long.MAX_VALUE ) {
                return val;
            }
            rnd -= ROUND_PASS_MINMAX;
        }
        
        if( val < 0 && val != Long.MIN_VALUE ) {
            return -multRound( -val, num, den, rnd ^ ((rnd >> 1) & 1) );
        }
        if( rnd == ROUND_NEAR_INF ) {
            r = den / 2;
        } else if( ( rnd & 1 ) != 0 ) {
            r = den -1;
        }
        
        if( num <= Integer.MAX_VALUE && den <= Integer.MAX_VALUE ) {
            if( val <= Integer.MAX_VALUE ) {
                return ( val * num + r ) / den;
            } else {
                return val / den * num + ( val % den * num + r ) / den;
            }
        } else {
            long a0 = val & 0xFFFFFFFFL;
            long a1 = val >> 32;
            long b0 = num & 0xFFFFFFFFL;
            long b1 = num >> 32;
            long t1 = a0 * b1 + a1 * b0;
            long t1a = t1 << 32;
            
            a0 = a0 * b0 + t1a;
            a1 = a1 * b1 + ( t1 >> 32 ) + ( a0 < t1a ? 1 : 0 );
            a0 += r;
            a1 += ( a0 < r ? 1 : 0 );
            
            for( int i = 63; i >= 0; i-- ) {
                a1 += a1 + (( a0 >> i ) & 1 );
                t1 += t1;
                if( den <= a1 ) {
                    a1 -= den;
                    t1++;
                }
            }

            return t1;
        }
    }

    /**
     * Computes {@code val * (multNum / multNum) / (divNum / divDen)} with protection against overflow.
     *
     * @return value nearest to {@code val * (multNum / multNum) / (divNum / divDen)}
     */
    public static long rescale( long val, int multNum, int multDen, int divNum, int divDen ) {
        return rescaleRound( val, multNum, multDen, divNum, divDen, ROUND_NEAR_INF );
    }

    /**
     * Computes {@code val * (multNum / multNum) / (divNum / divDen)} with protection against overflow.
     *
     * @return value nearest to {@code val * (multNum / multNum) / (divNum / divDen)},
     *          or if ROUND_PASS_MINMAX is set and a is Long.MIN_VALUE or Long.MAX_VALUE,
     *          then val is returned unchanged.
     */
    public static long rescaleRound( long val, int multNum, int multDen, int divNum, int divDen, int rnd ) {
        long b = multNum * (long)divDen;
        long c = divNum  * (long)multDen;
        return multRound( val, b, c, rnd );
    }
    
    /**
     * Compare 2 values each with own scaling.
     * The result of the function is undefined if one of the scalings is
     * outside the Long range when represented in the others timebase.
     *
     * @return -1 if valA is before valB, 1 if valA is after valB or 0 if they represent the same position
     */
    public static int compare( long valA, int aNum, int aDen, long valB, int bNum, int bDen ) {
        long a = aNum * (long)bDen;
        long b = bNum * (long)aDen;
        if( ( Math.abs( valA ) | a | Math.abs( valB ) | b ) <= Integer.MAX_VALUE ) {
            return ( valA * a > valB * b ? 1 : 0 ) - ( valA * a < valB * b ? 1 : 0 );
        }
        if( multRound( valA, a, b, ROUND_DOWN ) < valB ) {
            return -1;
        }
        if( multRound( valB, b, a, ROUND_DOWN ) < valA ) {
            return 1;
        }
        
        return 0;
    }



    public int mNum;
    public int mDen;


    public Rational() {}


    public Rational( int num, int den ) {
        mNum = num;
        mDen = den;
    }


    public Rational( Rational copy ) {
        mNum = copy.mNum;
        mDen = copy.mDen;
    }



    public double toDouble() {
        return (double)mNum / mDen;
    }


    public void reduce() {
        reduce( mNum, mDen, this );
    }


    @Override
    public String toString() {
        return String.format( "%d/%d", mNum, mDen );
    }

    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof Rational) ) {
            return false;
        }
        Rational r = (Rational)obj;
        return mNum == r.mNum && mDen == r.mDen;
    }

    @Override
    public int hashCode() {
        return mNum ^ mDen;
    }

    @Override
    public int compareTo( Rational r ) {
        return compare( 1, mNum, mDen, 1, r.mNum, r.mDen );
    }

}
