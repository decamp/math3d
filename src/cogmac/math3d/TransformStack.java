package cogmac.math3d;

import java.util.*;

public class TransformStack {

    private double[][] mStack = new double[8][16];
    private int mPos = 0;
    
    private double[] mHead = new double[16];
    private double[] mWork = new double[16];
    private double[] mSwap = new double[16];
    
    
    public TransformStack() {
        identity();
    }

    
    
    public double[] get() {
        return mHead;
    }

    
    public void get( double[] out ) {
        System.arraycopy( mHead, 0, out, 0, 16 );
    }
    
    
    public void set( double[] m ) {
        System.arraycopy( m, 0, mHead, 0, 16 );
    }
    
    
    public void push() {
        ensureCapacity( mPos );
        System.arraycopy( mHead, 0, mStack[ mPos++ ], 0, 16 );
    }
    
    
    public double[] pop() {
        if( --mPos < 0 ) {
            mPos = 0;
            throw new EmptyStackException();
        }
        
        double[] m   = mStack[mPos];
        mStack[mPos] = mHead;
        mHead        = m;
        
        return mHead;
    }
    
    
    public void identity() {
        Matrices.setToIdentity( mHead );
    }
    

    public void mult( double[] m ) {
        Matrices.multMatMat( mHead, m, mSwap );
        m      = mSwap;
        mSwap  = mHead;
        mHead  = m;
    }
    
    
    public void premult( double[] m ) {
        Matrices.multMatMat( m, mHead, mSwap );
        m     = mSwap;
        mSwap = mHead;
        mHead = m;
    }
    
    
    public void translate( double dx, double dy, double dz ) {
        Matrices.computeTranslationMatrix( dx, dy, dz, mWork );
        mult( mWork );
    }
    
    
    public void rotate( double radians, double x, double y, double z ) {
        Matrices.computeRotationMatrix( radians, x, y, z, mWork );
        mult( mWork );
    }
    
    
    public void scale( double sx, double sy, double sz ) {
        Matrices.computeScaleMatrix( sx, sy, sz, mWork );
        mult( mWork );
    }
    
    
    public void invert() {
        Matrices.invert( mHead, mSwap );
        double[] m = mHead;
        mHead      = mSwap;
        mSwap      = m;
    }
    
    
    public int size() {
        return mPos;
    }

    
    public void clean() {
        mPos = 0;
        identity();
    }
    
    
    public void ensureCapacity( int minCapacity ) {
        int oldCapacity = mStack.length;
        
        if( minCapacity > oldCapacity ) {
            int newCapacity = ( oldCapacity * 3 ) / 2 + 1;
            if( newCapacity < minCapacity ) {
                newCapacity = minCapacity;
            }
            
            mStack = Arrays.copyOf( mStack, newCapacity );
            for( int i = oldCapacity; i < newCapacity; i++ ) {
                mStack[i] = new double[16];
            }
        }
    }
    
}
