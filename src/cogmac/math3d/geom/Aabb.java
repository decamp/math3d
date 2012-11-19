package cogmac.math3d.geom;

import java.io.Serializable;

import cogmac.math3d.Matrices;

/** 
 * Axis-Aligned Bounding Box.
 * Aabb objects are immutable.
 * 
 * @author Philip DeCamp  
 */
public final class Aabb implements Cloneable, Serializable, Volume {

    private static final long serialVersionUID = 7863350136442702743L;


    /**
     * Create a new Aabb by specifying left and top faces and width and height dimensions.
     * 
     * @param x0 - The left face of the Aaabb.
     * @param y0 - The top face of the Aaabb.
     * @param z0 - The front face of the Aaabb.
     * @param spanX - The width of the Aaabb.
     * @param spanY - The height of the Aaabb.
     * @param spanZ - The depth of the Aaabb.
     * @returns a new Aabb object with the specified faces and dimensions.
     */
    public static Aabb fromBounds(double x0, double y0, double z0, double spanX, double spanY, double spanZ) {
        return new Aabb(x0, y0, z0, x0 + spanX, y0 + spanY, z0 + spanZ);
    }
        
    /**
     * Create a new Aabb by specifying location of all four faces.
     * 
     * @param x0 - The left face of the Aabb.
     * @param top - The top face of the Aabb.
     * @param front - The front face of the Aabb.
     * @param right - The right face of the Aabb.
     * @param bottom - The bottom face of the Aabb.
     * @param back - The back face of the Aabb.
     * @returns a new Aabb object with the specified faces.
     */
    public static Aabb fromEdges(double x0, double y0, double z0, double x1, double y1, double z1) {
        return new Aabb(x0, y0, z0, x1, y1, z1);
    }
    
    /**
     * Creates a new Aabb by specifying center and size.
     * 
     * @param centerX
     * @param centerY
     * @param centerZ
     * @param spanX
     * @param spanY
     * @param spanZ
     * @returns a new Aabb
     */
    public static Aabb fromCenter(double centerX, double centerY, double centerZ, double spanX, double spanY, double spanZ) {
        centerX -= spanX * 0.5f;
        centerY -= spanY * 0.5f;
        centerZ -= spanZ * 0.5f;
        
        return new Aabb(centerX, centerY, centerZ, centerX + spanX, centerY + spanY, centerZ + spanZ);
    }
    
    
    private final double mX0, mY0, mZ0, mX1, mY1, mZ1;
    
    
    private Aabb(double x0, double y0, double z0, double x1, double y1, double z1) {
        if(x0 <= x1) {
            mX0 = x0;
            mX1 = x1;
        }else{
            mX0 = x1;
            mX1 = x0;
        }
        
        if(y0 <= y1) {
            mY0 = y0;
            mY1 = y1;
        }else{
            mY0 = y1;
            mY1 = y0;
        }
        
        if(z0 <= z1) {
            mZ0 = z0;
            mZ1 = z1;
        }else{
            mZ0 = z1;
            mZ1 = z0;
        }
    }
    

    
    /****** POSITION ******/    
    
    public double minX() {
        return Math.min(mX0, mX1);
    }

    public double minY() {
        return Math.min(mY0, mY1);
    }

    public double minZ() {
        return Math.min(mZ0, mZ1);
    }

    public double maxX() {
        return Math.max(mX0, mX1);
    }

    public double maxY() {
        return Math.max(mY0, mY1);
    }

    public double maxZ() {
        return Math.max(mZ0, mZ1);
    }

    public double centerX() {
        return (mX0 + mX1) * 0.5f;
    }

    public double centerY() {
        return (mY0 + mY1) * 0.5f;
    }

    public double centerZ() {
        return (mZ0 + mZ1) * 0.5f;
    }

    /****** SIZE ******/

    public double spanX() {
        return mX1 - mX0;
    }

    public double spanY() {
        return mY1 - mY0;
    }

    public double spanZ() {
        return mZ1 - mZ0;
    }

    /**
     * @returns the absolute area of this Aabb.
     */
    public double area() {
        double area = (mX1 - mX0) * (mY1 - mY0);
        area += (mX1 - mX0) * (mZ1 - mZ0);
        area += (mY1 - mY0) * (mZ1 - mZ0);

        return area * 2;
    }

    /**
     * @returns the volume of this Aabb.
     */
    public double volume() {
        return (mX1 - mX0) * (mY1 - mY0) * (mZ1 - mY0);
    }

    /**
     * @returns the largest dimension of the Aabb.
     */
    public double maxDim() {
        return Math.max(mX1 - mX0, Math.max(mY1 - mY0, mZ1 - mZ0));
    }

    
    /****** TRANSFORMATIONS ******/
    
    /**
     * Creates a new Aabb that has the size of <code>this</code> Aabb, but is 
     * moved completely inside the argument Aabb.  If the Aabb is too
     * large to fit inside, it is centered inside the argument Aabb, but its
     * size is not changed.
     * 
     * @param bounds - Aabb in which to fit <code>this</code> Aabb.
     * @returns a new Aabb.
     */
    public Aabb clamp(Aabb bounds) {
        double left, right, top, bottom, front, back;
        
        
        if(mX0 < bounds.mX0){
            left = bounds.mX0;
            right = left + mX1 - mX0;
            
            if(right > bounds.mX1){
                left = (bounds.mX0 + bounds.mX1 + mX0 - mX1) * 0.5f;
                right = left + mX1 - mX0;
            }
            
        }else if(mX1 > bounds.mX1){
            right = bounds.mX1;
            left = right + mX0 - mX1;
                
            if(left < bounds.mX0){
                left  = (bounds.mX0 + bounds.mX1 + mX0 - mX1) * 0.5f;
                right = left + mX1 - mX0;
            }
            
        }else{
            left = mX0;
            right = mX1;
        }
         
        if(mY0 < bounds.mY0){
            top = bounds.mY0;
            bottom = top + mY1 - mY0;
            
            if(bottom > bounds.mY1){
                top = (bounds.mY0 + bounds.mY1 + mY0 - mY1) * 0.5f;
                bottom = top + mY1 - mY0;
            }
            
        }else if(mY1 > bounds.mY1){
            bottom = bounds.mY1;
            top = bottom + mY0 - mY1;
            
            if(top < bounds.mY0){
                top = (bounds.mY0 + bounds.mY1 + mY0 - mY1) * 0.5f;
                bottom = top + mY1 - mY0;
            }
            
        }else{
            top = mY0;
            bottom = mY1;
        }
        
        
        if(mZ0 < bounds.mZ0){
            front = bounds.mZ0;
            back = front + mZ1 - mZ0;
            
            if(back > bounds.mZ1){
                front = (bounds.mZ0 + bounds.mZ1 + mZ0 - mZ1) * 0.5f;
                back = front + mZ1 - mZ0;
            }
            
        }else if(mZ1 > bounds.mZ1){
            back = bounds.mZ1;
            front = back + mZ0 - mZ1;
            
            if(front < bounds.mZ0){
                front = (bounds.mZ0 + bounds.mZ1 + mZ0 - mZ1) * 0.5f;
                back = front + mZ1 - mZ0;
            }
            
        }else{
            front = mZ0;
            back = mZ1;
        }
        
        
        return new Aabb(left, top, front, right, bottom, back);
    }
    
    /**
     * @returns the doubleersection between <code>this</code> Aabb and the Parameter Aabb.
     * If the two Aabbs do not overlap, a Aabb with 0 size is returned.
     */
    public Aabb clip(Aabb bounds){
        return new Aabb(Math.max(mX0, bounds.mX0),
                        Math.max(mY0, bounds.mY0),
                        Math.max(mZ0, bounds.mZ0),
                        Math.min(mX1, bounds.mX1),
                        Math.min(mY1, bounds.mY1),
                        Math.min(mZ1, bounds.mZ1));
    }
    
    /**
     * Scales the size of the Aabb without changing the center podouble.
     * 
     * @param scaleX - Amount to scale width.
     * @param scaleY - Amount to scale height.
     * @param scaleZ - Amount to scale depth.
     * @returns new Aabb with scaled width and height.
     */
    public Aabb inflate(double scaleX, double scaleY, double scaleZ) {
        return Aabb.fromCenter( centerX(), 
                                centerY(),
                                centerZ(),
                                (spanX() * scaleX + 0.5f), 
                                (spanY() * scaleY + 0.5f),
                                (spanZ() * scaleZ + 0.5f));
    }
    
    /**
     * @returns the union between <code>this</code> Aabb and the parameter Aabb.  The
     * union may contain area not covered by either input Aabb.
     */
    public Aabb union(Aabb r) {
        return new Aabb(Math.min(mX0, r.mX0),
                        Math.min(mY0, r.mY0),
                        Math.min(mZ0, r.mZ0),
                        Math.max(mX1, r.mX1),
                        Math.max(mY1, r.mY1),
                        Math.max(mZ1, r.mZ1));
    }

    /**
     * Multiplies location and size.
     * @param multX - Amount to multiply the width and left face.
     * @param multY - Amount to multiply the height and top face.
     * @param multZ = Amount to multiply the depth and front face.
     * @returns new Aabb object.
     */
    public Aabb scale(double multX, double multY, double multZ) {
        return new Aabb( mX0 * multX, mY0 * multY, mZ0 * multZ,
                         mX1 * multX, mY1 * multY, mZ1 * multZ);
    }
    
    /**
     * Moves the Aabb.
     * @param dx - Amount to move the Aabb horizantally.
     * @param dy - Amount to move the Aabb vertically.
     * @param dz - Amount to move the Aabb depthally.
     * @returns a new Aabb.
     */
    public Aabb translate(double dx, double dy, double dz) {
        return new Aabb(mX0 + dx, mY0 + dy, mZ0 + dz, mX1 + dx, mY1 + dy, mZ1 + dz);
    }
    
    /**
     * Centers the Aabb on the given podouble.
     * @returns new Aabb centered on the given podouble.
     */
    public Aabb setCenter(double x, double y, double z) {
        x -= (mX1 - mX0) * 0.5f;
        y -= (mY1 - mY0) * 0.5f;
        z -= (mZ1 - mZ0) * 0.5f;
        
        return new Aabb(x, y, z, x + mX1 - mX0, y + mY1 - mY0, z + mZ1 - mZ0);
    }
    
    /**
     * Computes a new Aabb after an arbitrary homographic transform.
     */
    public Aabb transform(double[] transformMat) {
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        double[] inVert  = new double[3];
        double[] outVert = new double[3];
        
        for(int i = 0; i < 8; i++) {
            inVert[0] = (i % 2) < 1 ? mX0 : mX1;
            inVert[1] = (i % 4) < 2 ? mY0 : mY1;
            inVert[2] = (i    ) < 4 ? mZ0 : mZ1;
            
            Matrices.multMatVec(transformMat, inVert, outVert);
            
            minX = Math.min(minX, outVert[0]);
            maxX = Math.max(maxX, outVert[0]);
            minY = Math.min(minY, outVert[1]);
            maxY = Math.max(maxY, outVert[1]);
            minZ = Math.min(minZ, outVert[2]);
            maxZ = Math.max(maxZ, outVert[2]);
        }
        
        return new Aabb(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    
    /****** TESTS ******/
    
    /**
     * Tests if Aabb double intersects with a given point.
     * 
     * @param x - The x coordinate of a point.
     * @param y - The y coordinate of a point.
     * @param z - The z coordinate of a point.
     * @returns true if the double lies within the Aabb, otherwise false.
     */
    public boolean contains(double x, double y, double z){
        return x >= mX0 && 
               x <  mX1 && 
               y >= mY0 &&
               y <  mY1 && 
               z >= mZ0 && 
               z <  mZ1;
    }
    
    /**
     * Tests if this Aabb has intersectio with other Aabb.  
     * 
     * @param aabb - An Aabb to check for overlap.
     * @returns true if Aabbs have any doubleersection.
     */
    public boolean intersects(Aabb aabb) {
        return !(mX0 <  aabb.mX0 && mX1 <  aabb.mX0 ||
                 mX0 >= aabb.mX1 && mX1 >= aabb.mX1 ||
                 mY0 <  aabb.mY0 && mY1 <  aabb.mY0 ||
                 mY0 >= aabb.mY1 && mY1 >= aabb.mY1 ||
                 mZ0 <  aabb.mZ0 && mZ1 <  aabb.mZ0 ||
                 mZ0 >= aabb.mZ1 && mZ1 >= aabb.mZ1);
    }
    

    @Override
    public int hashCode() {
        long bits = Double.doubleToRawLongBits(mX0 + mY0 + mZ0 + mX1 + mY1 + mZ1);
        return (int)bits;
    }

    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Aabb))
            return false;

        Aabb r = (Aabb)o;
        return  mX0 == r.mX0 && 
                mY0 == r.mY0 &&
                mX1 == r.mX1 &&
                mY1 == r.mY1 &&
                mZ0 == r.mZ0 &&
                mZ1 == r.mZ1;
    }
    
    
    
    public Aabb getBounds() {
        return this;
    }

    
    public String toString() {
        StringBuilder b = new StringBuilder("Aabb [");
        b.append(mX0);
        b.append(", ");
        b.append(mY0);
        b.append(", ");
        b.append(mZ0);
        b.append(", ");
        b.append((mX1 - mX0));
        b.append(", ");
        b.append((mY1 - mY0));
        b.append(", ");
        b.append((mZ1 - mZ0));
        b.append("]");
        return b.toString();
    }

}
