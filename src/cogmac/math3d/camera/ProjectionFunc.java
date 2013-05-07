package cogmac.math3d.camera;

public interface ProjectionFunc {
    public float nearPlane();
    public ProjectionFunc nearPlane( float nearPlane );
    public float farPlane();
    public ProjectionFunc farPlane( float farPlane );
    
    public void computeProjectionMat( float[] viewport, float[] tileViewport, double[] outMat );
}
