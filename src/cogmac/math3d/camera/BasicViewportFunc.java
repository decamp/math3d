package cogmac.math3d.camera;

import cogmac.math3d.Matrices;

public class BasicViewportFunc implements ViewportFunc {
    @Override
    public void computeViewportMat( int[] viewport, int[] tileViewport, double[] outMat ) {
        int[] f = tileViewport == null ? viewport : tileViewport;
        Matrices.computeViewportDepthMatrix( f[0], f[1], f[2] - f[0], f[3] - f[1], 0.0, 1.0, outMat );  
    }
}
