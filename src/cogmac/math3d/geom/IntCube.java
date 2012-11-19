//package cogmac.math3d.geom;
//
//import java.io.Serializable;
//
///** 
// * IntCube is a lie.  This is actually a class for an axis-aligned cuboid  bounding-box.
// * <p>
// * IntCubes are immutable.
// * 
// * @author Philip DeCamp  
// */
//public class IntCube implements Cloneable, Serializable {
//
//    private static final long serialVersionUID = 8113931339833451506L;
//
//
//    /**
//     * Create a new IntCube by specifying left and top faces and width and height dimensions.
//     * 
//     * @param minX - The left face of the cube.
//     * @param minY - The top face of the cube.
//     * @param minZ - The front face of the cube.
//     * @param spanX - The width of the cube.
//     * @param spanY - The height of the cube.
//     * @param spanZ - The depth of the cube.
//     * @returns a new Bounds3 object with the specified faces and dimensions.
//     */
//    public static IntCube fromBounds(int minX, int minY, int minZ, int spanX, int spanY, int spanZ) {
//        return new IntCube(minX, minY, minZ, minX + spanX, minY + spanY, minZ + spanZ);
//    }
//    
//    
//    /**
//     * Create a new IntCube by specifying location of all four faces.
//     * 
//     * @param minX - The left face of the cube.
//     * @param minY - The top face of the cube.
//     * @param minZ - The front face of the cube.
//     * @param maxX - The right face of the cube.
//     * @param maxY - The bottom face of the cube.
//     * @param maxZ - The back face of the cube.
//     * @returns a new Bounds3 object with the specified faces.
//     */
//    public static IntCube fromEdges(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
//        return new IntCube(minX, minY, minZ, maxX, maxY, maxZ);
//    }
//    
//    
//    /**
//     * Creates a new Bounds3 by specifying center and size.
//     * 
//     * @param centerX
//     * @param centerY
//     * @param centerZ
//     * @param spanX
//     * @param spanY
//     * @param spanZ
//     * @returns a new Bounds3
//     */
//    public static IntCube fromCenter(int centerX, int centerY, int centerZ, int spanX, int spanY, int spanZ) {
//        centerX -= spanX / 2;
//        centerY -= spanY / 2;
//        centerZ -= spanZ / 2;
//        
//        return new IntCube(centerX, centerY, centerZ, centerX + spanX, centerY + spanY, centerZ + spanZ);
//    }
//    
//
//    
//    private final int mX0, mY0, mZ0, mX1, mY1, mZ1;
//    
//    
//    private IntCube(int x0, int y0, int z0, int x1, int y1, int z1) {
//        if(x0 <= x1) {
//            mX0 = x0;
//            mX1 = x1;
//        }else{
//            mX0 = x1;
//            mX1 = x0;
//        }
//        
//        if(y0 <= y1) {
//            mY0 = y0;
//            mY1 = y1;
//        }else{
//            mY0 = y1;
//            mY1 = y0;
//        }
//        
//        if(z0 <= z1) {
//            mZ0 = z0;
//            mZ1 = z1;
//        }else{
//            mZ0 = z1;
//            mZ1 = z0;
//        }
//    }
//    
//
//    
//    /****** POSITION ******/    
//    
//    public int minX() {
//        return mX0;
//    }
//
//    public int minY() {
//        return mY0;
//    }
//
//    public int minZ() {
//        return mZ0;
//    }
//
//    public int maxX() {
//        return mX1;
//    }
//
//    public int maxY() {
//        return mY1;
//    }
//
//    public int maxZ() {
//        return mZ1;
//    }
//   
//    
//    /**
//     * @returns the center point between the left and right faces.
//     */
//    public int centerX() {
//        return (mX0 + mX1) / 2;
//    }
//        
//    /**
//     * @returns the center point between the bottom and top faces.
//     */
//    public int centerY() {
//        return (mY0 + mY1) / 2;
//    }
//    
//    /**
//     * @returns the center point between the bottom and top faces.
//     */
//    public int centerZ() {
//        return (mZ0 + mZ1) / 2;
//    }
//    
//
//    
//    /****** SIZE ******/
//    
//    /**
//     * @returns the width of this cube.  May be negative.
//     */
//    public int spanX() {
//        return mX1 - mX0;
//    }
//    
//    /**
//     * @returns the height of this cube.  May be negative.
//     */
//    public int spanY() {
//        return mY1 - mY0;
//    }
//   
//    /**
//    * @returns the depth of this cube.  May be negative.
//    */
//   public int spanZ() {
//       return mZ1 - mZ0;
//   }
//    
//    /**
//     * @returns the absolute area of this cube.
//     */
//    public int area() {
//        int area = Math.abs((mX1 - mX0) * (mY1 - mY0));
//        area += Math.abs((mX1 - mX0) * (mZ1 - mZ0));
//        area += Math.abs((mY1 - mY0) * (mZ1 - mZ0));
//        
//        return area * 2;
//    }
//    
//    /**
//     * @returns the volume of this cube.
//     */
//    public int volume() {
//        return Math.abs((mX1 - mX0) * (mY1 - mY0) * (mZ1 - mY0));
//    }
//
//    
//    
//    /****** TRANSFORMATIONS ******/
//    
//    /**
//     * Creates a new Bounds3 that has the size of <code>this</code> Bounds3, but is 
//     * moved completely inside the argument Bounds3.  If the cube is too
//     * large to fit inside, it is centered inside the argument Bounds3, but its
//     * size is not changed.
//     * 
//     * @param bounds - Bounds3 in which to fit <code>this</code> cube.
//     * @returns a new Bounds3.
//     */
//    public IntCube clamp(IntCube bounds) {
//        int left, right, top, bottom, front, back;
//        
//        
//        if(mX0 < bounds.mX0){
//            left = bounds.mX0;
//            right = left + mX1 - mX0;
//            
//            if(right > bounds.mX1){
//                left = (bounds.mX0 + bounds.mX1 + mX0 - mX1) / 2;
//                right = left + mX1 - mX0;
//            }
//            
//        }else if(mX1 > bounds.mX1){
//            right = bounds.mX1;
//            left = right + mX0 - mX1;
//                
//            if(left < bounds.mX0){
//                left  = (bounds.mX0 + bounds.mX1 + mX0 - mX1) / 2;
//                right = left + mX1 - mX0;
//            }
//            
//        }else{
//            left = mX0;
//            right = mX1;
//        }
//         
//        if(mY0 < bounds.mY0){
//            top = bounds.mY0;
//            bottom = top + mY1 - mY0;
//            
//            if(bottom > bounds.mY1){
//                top = (bounds.mY0 + bounds.mY1 + mY0 - mY1) / 2;
//                bottom = top + mY1 - mY0;
//            }
//            
//        }else if(mY1 > bounds.mY1){
//            bottom = bounds.mY1;
//            top = bottom + mY0 - mY1;
//            
//            if(top < bounds.mY0){
//                top = (bounds.mY0 + bounds.mY1 + mY0 - mY1) / 2;
//                bottom = top + mY1 - mY0;
//            }
//            
//        }else{
//            top = mY0;
//            bottom = mY1;
//        }
//        
//        
//        if(mZ0 < bounds.mZ0){
//            front = bounds.mZ0;
//            back = front + mZ1 - mZ0;
//            
//            if(back > bounds.mZ1){
//                front = (bounds.mZ0 + bounds.mZ1 + mZ0 - mZ1) / 2;
//                back = front + mZ1 - mZ0;
//            }
//            
//        }else if(mZ1 > bounds.mZ1){
//            back = bounds.mZ1;
//            front = back + mZ0 - mZ1;
//            
//            if(front < bounds.mZ0){
//                front = (bounds.mZ0 + bounds.mZ1 + mZ0 - mZ1) / 2;
//                back = front + mZ1 - mZ0;
//            }
//            
//        }else{
//            front = mZ0;
//            back = mZ1;
//        }
//        
//        
//        return new IntCube(left, top, front, right, bottom, back);
//    }
//    
//    /**
//     * @returns the intersection between <code>this</code> Bounds3 and the Parameter Bounds3.
//     * If the two Cubes do not overlap, a Bounds3 with 0 size is returned.
//     */
//    public IntCube clip(IntCube bounds) {
//        return new IntCube(Math.max(mX0, bounds.mX0),
//                        Math.max(mY0, bounds.mY0),
//                        Math.max(mZ0, bounds.mZ0),
//                        Math.min(mX1, bounds.mX1),
//                        Math.min(mY1, bounds.mY1),
//                        Math.min(mZ1, bounds.mZ1));
//    }
//    
//    /**
//     * Scales the size of the Bounds3 without changing the center point.
//     * 
//     * @param scaleX - Amount to scale width.
//     * @param scaleY - Amount to scale height.
//     * @param scaleZ - Amount to scale depth.
//     * @returns new Bounds3 with scaled width and height.
//     */
//    public IntCube inflate(float scaleX, float scaleY, float scaleZ) {
//        return IntCube.fromCenter( centerX(), 
//                                centerY(),
//                                centerZ(),
//                                (int)(spanX() * scaleX + 0.5f), 
//                                (int)(spanY() * scaleY + 0.5f),
//                                (int)(spanZ() * scaleZ + 0.5f));
//    }
//    
//    /**
//     * @returns the union between <code>this</code> Bounds3 and the parameter Bounds3.  The
//     * union may contain area not covered by either input Bounds3.
//     */
//    public IntCube union(IntCube r) {
//        return new IntCube(Math.min(mX0, r.mX0),
//                        Math.min(mY0, r.mY0),
//                        Math.min(mZ0, r.mZ0),
//                        Math.max(mX1, r.mX1),
//                        Math.max(mY1, r.mY1),
//                        Math.max(mZ1, r.mZ1));
//    }
//        
//    /**
//     * Multiplies location and size.
//     * @param multX - Amount to multiply the width and left face.
//     * @param multY - Amount to multiply the height and top face.
//     * @param multZ = Amount to multiply the depth and front face.
//     * @returns new Bounds3 object.
//     */
//    public IntCube scale(int multX, int multY, int multZ){
//        return new IntCube( mX0 * multX, mY0 * multY, mZ0 * multZ,
//                         mX1 * multX, mY1 * multY, mZ1 * multZ);
//    }
//    
//    /**
//     * Moves the Cube.
//     * @param dx - Amount to move the cube horizantally.
//     * @param dy - Amount to move the cube vertically.
//     * @param dz - Amount to move the cube depthally.
//     * @returns a new cube.
//     */
//    public IntCube translate(int dx, int dy, int dz) {
//        return new IntCube(mX0 + dx, mY0 + dy, mZ0 + dz, mX1 + dx, mY1 + dy, mZ1 + dz);
//    }
//    
//    /**
//     * Centers the cube on the given point.
//     * @returns new Bounds3 centered on the given point.
//     */
//    public IntCube setCenter(int x, int y, int z) {
//        x -= (mX1 - mX0) / 2;
//        y -= (mY1 - mY0) / 2;
//        z -= (mZ1 - mZ0) / 2;
//        
//        return new IntCube(x, y, z, x + mX1 - mX0, y + mY1 - mY0, z + mZ1 - mZ0);
//    }
//    
//    
//    /****** TESTS ******/
//
//    /**
//     * Tests if cube intersects with a given point.
//     * 
//     * @param x - The x coordinate of a point.
//     * @param y - The y coordinate of a point.
//     * @returns true if the point lies within the cube, otherwise false.
//     */
//    public boolean intersects(int x, int y, int z){
//        return x >= mX0 && x < mX1 && y >= mY0 && y < mY1 && z >= mZ0 && z < mZ1;
//    }
//    
//    /**
//     * Tests if this cube has any intersection with other cube.  
//     * 
//     * @param rect - A rect to check for overlap.
//     * @returns true if cubes have any intersection.
//     */
//    public boolean intersects(IntCube rect) {
//        return !(mX0  <  rect.mX0   && mX1  <  rect.mX0   ||
//                 mX0  >= rect.mX1  && mX1  >= rect.mX1  ||
//                 mY0   <  rect.mY0    && mY1 <  rect.mY0    ||
//                 mY0   >= rect.mY1 && mY1 >= rect.mY1 ||
//                 mZ0 <  rect.mZ0  && mZ1   <  rect.mZ0  ||
//                 mZ0 >= rect.mZ1   && mZ1   >= rect.mZ1    );
//
//    }
//
//    
//    @Override
//    public int hashCode() {
//        return mX0 ^ mY0 ^ mZ0 ^ mX1 ^ mY1 ^ mZ1;
//    }
//    
//    
//    @Override
//    public boolean equals(Object o) {
//        if(o == null || !(o instanceof IntCube))
//            return false;
//        
//        IntCube r = (IntCube)o;
//        return  mX0 == r.mX0 && 
//                mY0 == r.mY0 &&
//                mX1 == r.mX1 &&
//                mY1 == r.mY1 &&
//                mZ0 == r.mZ0 &&
//                mZ1 == r.mZ1;
//    }
//    
//    
//    /****** CONVERSIONS ******/
//    
//    public String toString() {
//        StringBuilder b = new StringBuilder("IntCube [");
//        b.append(mX0);
//        b.append(", ");
//        b.append(mY0);
//        b.append(", ");
//        b.append(mZ0);
//        b.append(", ");
//        b.append((mX1 - mX0));
//        b.append(", ");
//        b.append((mY1 - mY0));
//        b.append(", ");
//        b.append((mZ1 - mZ0));
//        b.append("]");
//        return b.toString();
//    }
//
//}
