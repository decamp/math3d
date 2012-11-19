package cogmac.math3d.camera;

import cogmac.math3d.LongRect;
import cogmac.math3d.Matrices;
import cogmac.math3d.actors.SpatialObject;

/**
 * Convenience method that implements several CameraTransform methods.
 * 
 * @author decamp
 */
public abstract class AbstractCameraTransform implements CameraTransform {
    
    private final SpatialObject mCamera;

    private final double[] mWork1    = new double[16];
    private final double[] mModelMat = new double[16];
    private final double[] mProjMat  = new double[16];
    private final double[] mViewMat  = new double[16];

    
    protected AbstractCameraTransform( SpatialObject camera ) {
        mCamera = camera;
    }
    
    
    
    public SpatialObject getCameraObject() {
        return mCamera;
    }
    
    
    public abstract void computeModelToCameraMatrix( LongRect viewport, LongRect subViewport, double[] out );

    
    public abstract void computeCameraToNormDeviceMatrix( LongRect viewport, LongRect subViewport, double[] out );
    
    
    public synchronized void computeNormDeviceToAwtMatrix( LongRect viewport, LongRect subViewport, double[] out ) {
        if( subViewport == null )
            subViewport = viewport;
        
        Matrices.computeViewportMatrix( subViewport.minX(), subViewport.maxY(), subViewport.spanX(), -subViewport.spanY(), out );
    }
    
    
    public synchronized void computeModelToNormDeviceMatrix( LongRect viewport, LongRect subViewport, double[] out ) {
        final double[] mod   = mModelMat;
        final double[] proj  = mProjMat;
                
        computeModelToCameraMatrix( viewport, subViewport, mod );
        computeCameraToNormDeviceMatrix( viewport, subViewport, proj );
        Matrices.multMatMat( proj, mod, out );
    }
    
    
    public synchronized void computeModelToAwtMatrix( LongRect viewport, LongRect subViewport, double[] out ) {
        final double[] mod   = mModelMat;
        final double[] proj  = mProjMat;
        final double[] view  = mViewMat;
        final double[] work1 = mWork1;
        
        computeModelToCameraMatrix( viewport, subViewport, mod );
        computeCameraToNormDeviceMatrix( viewport, subViewport, proj );
        computeNormDeviceToAwtMatrix( viewport, subViewport, view );
        
        Matrices.multMatMat( proj, mod, work1 );
        Matrices.multMatMat( view, work1, out );
    }
    
}
