package cogmac.math3d.camera;

import cogmac.math3d.Matrices;

public class BasicViewportFunc implements ViewportFunc {
    @Override
    public void computeViewportMat( float[] viewport, float[] tileViewport, double[] outMat ) {
        float[] f = tileViewport == null ? viewport : tileViewport;
        Matrices.computeViewportMatrix( f[0], f[1], f[2] - f[0], f[3] - f[1], outMat );  
    }
}
