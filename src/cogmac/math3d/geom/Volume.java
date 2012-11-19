package cogmac.math3d.geom;


/** 
 * @author Philip DeCamp  
 */
public interface Volume {
    public boolean contains(double x, double y, double z);
    public Aabb getBounds();
}
