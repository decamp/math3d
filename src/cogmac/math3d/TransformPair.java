package cogmac.math3d;


/**
 * Manages a pair of matrices: a transform and the inverse of the transform.
 * TransformPair is not thread-safe.
 * 
 * @author decamp
 */
public class TransformPair {

    private final double[] mWork1 = new double[16];
    private final double[] mWork2 = new double[16];
    
    private double[] mTransform = new double[16];
    private double[] mInverse = new double[16];
    
    private boolean mTransformValid = false;
    private boolean mInverseValid   = false;
    
    
    public TransformPair() {}
    
    
    public TransformPair(double[] transform) {
        System.arraycopy(transform, 0, mTransform, 0, 16);
        mTransformValid = true;
    }

    
    
    public void getTransform(double[] out, int offset) {
        checkTransform();
        System.arraycopy(mTransform, 0, out, offset, 16);
    }
    
    
    public double[] getTransformRef()  {
        checkTransform();
        return mTransform;
    }
    
    
    public void setTransform(double[] mat, int offset) {
        System.arraycopy(mat, offset, mTransform, 0, 16);
        mTransformValid = true;
        mInverseValid = false;
    }
    
    
    public void setTransformRef(double[] mat) {
        mTransform = mat;
        mTransformValid = true;
        mInverseValid = false;
    }
    

    public void getInverse(double[] out, int offset) {
        checkInverse();
        System.arraycopy(mInverse, 0, out, offset, 16);
    }
    
    
    public double[] getInverseRef() {
        checkInverse();
        return mInverse;
    }
    
    
    public void setInverse(double[] mat, int offset) {
        System.arraycopy(mat, offset, mInverse, 0, 16);
        mTransformValid = false;
        mInverseValid = true;
    }
    
    
    public void setInverseRef(double[] mat) {
        mInverse = mat;
        mTransformValid = false;
        mInverseValid = true;
    }
    
    
    
    private void checkTransform() {
        if(mTransformValid || !mInverseValid)
            return;
        
        System.arraycopy(mInverse, 0, mWork1, 0, 16);
        Matrices.invertMat(mWork1, mWork2, mTransform);
        mTransformValid = true;
    }
    
    
    private void checkInverse() {
        if(mInverseValid || !mTransformValid)
            return;
        
        System.arraycopy(mTransform, 0, mWork1, 0, 16);
        Matrices.invertMat(mWork1, mWork2, mInverse);
        mInverseValid = true;
    }
            
}
