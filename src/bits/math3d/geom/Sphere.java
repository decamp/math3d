package bits.math3d.geom;

/**
 * @author decamp
 */
public class Sphere implements Volume {

    private final double mX;
    private final double mY;
    private final double mZ;
    private final double mRad;

    public Sphere( double x, double y, double z, double rad ) {
        mX = x;
        mY = y;
        mZ = z;
        mRad = rad;
    }


    public double x() {
        return mX;
    }

    public double y() {
        return mY;
    }

    public double z() {
        return mZ;
    }

    public double rad() {
        return mRad;
    }


    public boolean contains( double x, double y, double z ) {
        double dx = x - mX;
        double dy = y - mY;
        double dz = z - mZ;
        return dx * dx + dy * dy + dz * dz <= mRad * mRad;
    }


    public Aabb getBounds() {
        return Aabb.fromCenter( mX, mY, mZ, mRad * 2.0, mRad * 2.0, mRad * 2.0 );
    }


    @Override
    public boolean equals( Object obj ) {
        if( !(obj instanceof Sphere) ) {
            return false;
        }

        if( obj == this ) {
            return true;
        }

        Sphere s = (Sphere)obj;
        return mX == s.mX && mY == s.mY && mZ == s.mZ && mRad == s.mRad;
    }

    @Override
    public int hashCode() {
        long n = Double.doubleToLongBits( mX + mY + mZ + mRad );
        return (int)((n >> 32) ^ n);
    }

    @Override
    public String toString() {
        return String.format( "Sphere [%f, %f, %f,  rad: %f]", mX, mY, mZ, mRad );
    }
}
