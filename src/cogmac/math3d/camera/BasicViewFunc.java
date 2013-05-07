package cogmac.math3d.camera;

import cogmac.math3d.Matrices;
import cogmac.math3d.actors.SpatialObject;

public class BasicViewFunc implements ViewFunc {
    
    private final double[][] mWork = new double[2][16];
    
    public BasicViewFunc() {}
    
    
    @Override
    public void computeViewMat( SpatialObject camera, double[] out ) {
        Matrices.invert( camera.mRot, mWork[0] );
        double[] pos = camera.mPos;
        Matrices.computeTranslationMatrix( -pos[0], -pos[1], -pos[2], mWork[1] );
        Matrices.multMatMat( mWork[0], mWork[1], out );
    }

}
