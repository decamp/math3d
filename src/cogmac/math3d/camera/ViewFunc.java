package cogmac.math3d.camera;

import cogmac.math3d.actors.SpatialObject;

public interface ViewFunc {
    public void computeViewMat( SpatialObject camera, double[] out );
}
