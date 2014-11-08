/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d;


import java.util.NoSuchElementException;


/**
 * @author Philip DeCamp
 */
public interface VecView<T> {

    public int dim();
    public double get( T item, int n );
    public void set( T item, int n, double v );
    public double x( T item );
    public void x( T item, double x );
    public double y( T item );
    public void y( T item, double y );
    public double z( T item );
    public void z( T item, double z );
    public double w( T item );
    public void w( T item, double w );


    public static class FloatArrayView implements VecView<float[]> {

        private final int mDim;


        public FloatArrayView( int dim ) {
            mDim = dim;
        }

        @Override
        public int dim() {
            return mDim;
        }

        @Override
        public double get( float[] item, int n ) {
            return item[n];
        }

        @Override
        public void set( float[] item, int n, double v ) {
            item[n] = (float)v;
        }

        @Override
        public double x( float[] item ) {
            return item[0];
        }

        @Override
        public void x( float[] item, double x ) {
            item[0] = (float)x;
        }

        @Override
        public double y( float[] item ) {
            return item[1];
        }

        @Override
        public void y( float[] item, double y ) {
            item[1] = (float)y;
        }

        @Override
        public double z( float[] item ) {
            return item[2];
        }

        @Override
        public void z( float[] item, double z ) {
            item[2] = (float)z;
        }

        @Override
        public double w( float[] item ) {
            return item[3];
        }

        @Override
        public void w( float[] item, double w ) {
            item[3] = (float)w;
        }

    }


    public static class DoubleArrayView implements VecView<double[]> {

        private final int mDim;


        public DoubleArrayView( int dim ) {
            mDim = dim;
        }

        @Override
        public int dim() {
            return mDim;
        }

        @Override
        public double get( double[] item, int n ) {
            return item[n];
        }

        @Override
        public void set( double[] item, int n, double v ) {
            item[n] = v;
        }

        @Override
        public double x( double[] item ) {
            return item[0];
        }

        @Override
        public void x( double[] item, double x ) {
            item[0] = x;
        }

        @Override
        public double y( double[] item ) {
            return item[1];
        }

        @Override
        public void y( double[] item, double y ) {
            item[1] = y;
        }

        @Override
        public double z( double[] item ) {
            return item[2];
        }

        @Override
        public void z( double[] item, double z ) {
            item[2] = z;
        }

        @Override
        public double w( double[] item ) {
            return item[3];
        }

        @Override
        public void w( double[] item, double w ) {
            item[3] = w;
        }

    }


    public static VecView<Vec3> VEC3 = new VecView<Vec3>() {
        public int dim() { return 3; }
        public double x( Vec3 item )           { return item.x; }
        public void   x( Vec3 item, double x ) { item.x = (float)x; }
        public double y( Vec3 item )           { return item.y; }
        public void   y( Vec3 item, double y ) { item.y = (float)y; }
        public double z( Vec3 item )           { return item.z; }
        public void   z( Vec3 item, double z ) { item.z = (float)z; }
        public double w( Vec3 item )           { throw new NoSuchElementException(); }
        public void   w( Vec3 item, double w ) { throw new NoSuchElementException(); }

        public double get( Vec3 item, int n ) {
            switch( n ) {
            case 0: return item.x;
            case 1: return item.y;
            case 2: return item.z;
            default:
                throw new NoSuchElementException();
            }
        }

        public void set( Vec3 item, int n, double v ) {
            switch( n ) {
            case 0: item.x = (float)v; return;
            case 1: item.y = (float)v; return;
            case 2: item.z = (float)v; return;
            default:
                throw new NoSuchElementException();
            }
        }

    };

}
