package cogmac.math3d.geom;

/**
 * Uses some code adapted from java.awt.Polygon.
 *  
 * @author Philip DeCamp  
 */
public class ExtrudedLineLoop implements Volume {

    private final int mPointCount;
    private final double[] mX;
    private final double[] mY;
    
    private final double mMinX;
    private final double mMaxX;
    private final double mMinY;
    private final double mMaxY;
    private final double mMinZ;
    private final double mMaxZ;
    
    private Integer mHash = null;
    
    
    public ExtrudedLineLoop(int pointCount, double[] xRef, double[] yRef, double minZ, double maxZ) {
        mPointCount = pointCount;
        mX = xRef;
        mY = yRef;
        mMinZ = minZ;
        mMaxZ = maxZ;
        
        if(pointCount <= 0) {
            mMinX = 0.0;
            mMaxX = 0.0;
            mMinY = 0.0;
            mMaxY = 0.0;
        }else{
            double minX = xRef[0];
            double maxX = xRef[0];
            double minY = yRef[0];
            double maxY = yRef[0];
         
            for(int i = 1; i < pointCount; i++) {
                if(xRef[i] < minX) {
                    minX = xRef[i];
                }else if(xRef[i] > maxX) {
                    maxX = xRef[i];
                }
                
                if(yRef[i] < minY) {
                    minY = yRef[i];
                }else if(yRef[i] > maxY) {
                    maxY = yRef[i];
                }
            }
            
            mMinX = minX;
            mMaxX = maxX;
            mMinY = minY;
            mMaxY = maxY;
        }
    }
    
    
    
    public double minZ() {
        return mMinZ;
    }
    
    public double maxZ() {
        return mMaxZ;
    }

    public int pointCount() {
        return mPointCount;
    }
    
    public double[] xRef() {
        return mX;
    }
    
    public double[] yRef() {
        return mY;
    }
        

    public boolean contains(double x, double y, double z) {
        if(mPointCount < 3 || !(x >= mMinX && x <= mMaxX && y >= mMinY && y <= mMaxY && z >= mMinZ && z <= mMaxZ))
            return false;
        
        int hits = 0;

        double lastX = mX[mPointCount - 1];
        double lastY = mY[mPointCount - 1];
        double curX, curY;

        //Mostly from the source code for java.awt.Polygon.java
        for(int i = 0; i < mPointCount; lastX = curX, lastY = curY, i++) {
            curX = mX[i];
            curY = mY[i];

            if(curY == lastY) {
                continue;
            }

            double leftx;
            
            if(curX < lastX) {
                if (x >= lastX) {
                    continue;
                }
                leftx = curX;
            }else{
                if(x >= curX) {
                    continue;
                }
                leftx = lastX;
            }

            double test1, test2;
            if(curY < lastY) {
                if(y < curY || y >= lastY) {
                    continue;
                }
                
                if(x < leftx) {
                    hits++;
                    continue;
                }
                
                test1 = x - curX;
                test2 = y - curY;
                
            }else{
                if(y < lastY || y >= curY) {
                    continue;
                }
                
                if(x < leftx) {
                    hits++;
                    continue;
                }
                
                test1 = x - lastX;
                test2 = y - lastY;
            }

            if(test1 < (test2 / (lastY - curY) * (lastX - curX))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
    }
    
    public boolean isClockwise() {
        double a = 0.0;
        for(int i = 0; i < mPointCount - 1; i++) {
            a += mX[i] * mY[i+1] - mX[i+1] * mY[i];
        }
        
        return a < 0.0;
    }
    
    public Aabb getBounds() {
        return Aabb.fromEdges(mMinX, mMinY, mMinZ, mMaxX, mMaxY, mMaxZ); 
    }
    
    
    public int hashCode() {
        if(mHash != null)
            return mHash;
        
        double v = mPointCount;
        
        for(int i = 0; i < mPointCount; i++) {
            v += mX[i] + mY[i];
        }
        
        long vv = Double.doubleToRawLongBits(v);
        int h = (int)((vv >>> 32) ^ (vv & 0xFFFFFFFF));
        mHash = h;
        
        return h;
    }
    
    public boolean equals(Object obj) {
        if(!(obj instanceof ExtrudedLineLoop))
            return false;
        
        if(obj == this)
            return true;
        
        ExtrudedLineLoop e = (ExtrudedLineLoop)obj;
        
        if(mPointCount != e.mPointCount)
            return false;
        
        for(int i = 0; i < mPointCount; i++) {
            if(mX[i] != e.mX[i] || mY[i] != e.mY[i]) {
                return false;
            }
        }
        
        return true;
    }
    
}
