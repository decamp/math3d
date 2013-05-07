package cogmac.math3d.camera;

public interface ViewportFunc {
    public void computeViewportMat( float[] viewport, float[] tileViewport, double[] outMat );
}
