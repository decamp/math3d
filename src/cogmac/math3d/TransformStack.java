package cogmac.math3d;

import java.util.Deque;
import java.util.LinkedList;

public class TransformStack {
    
    private final Deque<double[]> mStack = new LinkedList<double[]>();
    private double[] mHead  = new double[16];
    private double[] mWork0 = new double[16];
    private double[] mWork1 = new double[16];
    
    
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
        mStack.push( mHead.clone() );
    }
    
    
    public double[] pop() {
        double[] p = mStack.pollFirst();
        if( p != null )
            mHead = p;
        
        return mHead;
    }
    
    
    public void identity() {
        mHead[ 0] = 1; mHead[ 4] = 0; mHead[ 8] = 0; mHead[12] = 0;
        mHead[ 1] = 0; mHead[ 5] = 1; mHead[ 9] = 0; mHead[13] = 0;
        mHead[ 2] = 0; mHead[ 6] = 0; mHead[10] = 1; mHead[14] = 0;
        mHead[ 3] = 0; mHead[ 7] = 0; mHead[11] = 0; mHead[15] = 1;
    }
    

    public void multiply( double[] m ) {
        Matrices.multMatMat( mHead, m, mWork1 );
        m      = mWork1;
        mWork1 = mHead;
        mHead  = m;
    }
    
    
    public void premult( double[] m ) {
        Matrices.multMatMat( m, mHead, mWork1 );
        m      = mWork1;
        mWork1 = mHead;
        mHead  = m;
    }
    
    
    public void translate( double dx, double dy, double dz ) {
        Matrices.computeTranslationMatrix( dx, dy, dz, mWork0 );
        multiply( mWork0 );
    }
    
    
    public void rotate( double radians, double x, double y, double z ) {
        Matrices.computeRotationMatrix( radians, x, y, z, mWork0 );
        multiply( mWork0 );
    }
    
    
    public void scale( double sx, double sy, double sz ) {
        Matrices.computeScaleMatrix( sx, sy, sz, mWork0 );
        multiply( mWork0 );
    }
    
    
    public void invert() {
        Matrices.invertMat( mHead, mWork0, mWork1 );
        double[] m = mHead;
        mHead      = mWork1;
        mWork1     = m;
    }
    
}
