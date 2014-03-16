package bits.math3d;

import java.util.Arrays;

import bits.math3d.geom.Loop;

/**
 * Methods for manipulating 3-dimensional Axis-Aligned-Bounding-Boxes.
 * All boxes are stored in a length-6 array as [ x0, y0, z0, x1, y1, z1 ].
 * 
 * @author decamp
 */
public final class Box3 {

    /**
     * Scales size of box without changing center point.
     * 
     * @param box  Box to inflate.
     * @param sx   Scale factor for x dimension.
     * @param sy   Scale factor for y dimension.
     * @param out  Holds output. May be same object as <code>a</code>.
     */
    public static void inflate( float[] a, float sx, float sy, float sz, float[] out ) {
        float c, d;
        c = ( a[0] + a[3] ) * 0.5f;
        d = ( a[3] - a[0] ) * 0.5f * sx;
        out[0] = c - d;
        out[3] = c + d;
        c = ( a[1] + a[4] ) * 0.5f;
        d = ( a[4] - a[1] ) * 0.5f * sy;
        out[1] = c - d;
        out[4] = c + d;
        c = ( a[2] + a[5] ) * 0.5f;
        d = ( a[5] - a[2] ) * 0.5f * sz;
        out[2] = c - d;
        out[5] = c + d;
    }

    /**
     * Computes intersection between <code>a</code> and <code>b</code>.
     * If boxes do not overlap on a given dimension, then the output will
     * be located entirely within <code>a</code> on the side nearest to 
     * <code>b</code> and will have zero size. For example, the calling
     * <code>clip( { 0, 0, 1, 1 }, { 2, 0, 3, 0.5f }, out )</code>
     * will result in
     * <code>out = { 1, 0, 1, 0.5f }</code>.
     * 
     * @param a   First box
     * @param b   Second box
     * @param out Output box. May be same object as a or b. 
     * @return true iff boxes contain non-zero overlap.
     */
    public static boolean clip( float[] a, float[] b, float[] out ) {
        boolean nonzero = true;

        for( int i = 0; i < 3; i++ ) {
            final int j = i + 3;
            out[i] = a[i] >= b[i] ? a[i] :
                a[j] >= b[i] ? a[j] : b[i];
                out[j] = a[j] <= b[j] ? a[j] : b[j];

                if( out[j] <= out[i] ) {
                    out[j] = out[i];
                    nonzero = false;
                }
        }

        return nonzero;
    }

    /**
     * Creates box that has same size as <code>box</code>, but positioned to
     * be inside <code>ref</code>. If <code>box</code> is larger than 
     * <code>ref</code> for a given dimension, it will be centered inside 
     * <code>ref</code> for that dimension. If <code>box</code> is already 
     * completely inside of <code>ref</code>, it will not be changed.
     *
     * @param box  Box to reposition.
     * @param ref  Box defining target space for <code>in</code>
     * @param out  Holds output. May be same object as in or ref.
     */
    public static void clamp( float[] box, float[] ref, float[] out ) {
        for( int i = 0; i < 3; i++ ) {
            final int j = i + 3;
            final float d0 = box[j] - box[i];
            final float d1 = ref[j] - ref[i];

            if( d0 >= d1 ) {
                float x = ( ref[i] + ref[j] ) * 0.5f;
                out[i] = x - d0 * 0.5f;
                out[j] = x + d0 * 0.5f;
            } else if( box[i] <= ref[i] ) {
                out[i] = ref[i];
                out[j] = ref[i] + d0;
            } else if( box[j] >= ref[j] ) {
                out[i] = ref[j] - d0;
                out[j] = ref[j];
            } else {
                out[i] = box[i];
                out[j] = box[j];
            }
        }
    }

    /**
     * Tests if a point is inside given box. 
     * Each dimension of box is treated as half-open interval.
     * 
     * @param box
     * @param x
     * @param y
     * @return true iff x and y are contained by box.
     */
    public static boolean contains( float[] box, float x, float y, float z ) {
        return x >= box[0] && y >= box[1] && z >= box[2] &&
                x <  box[3] && y <  box[4] && z <  box[5];
    }

    /**
     * Maps a point in model coordinates to box coordinates, 
     * where ( 0, 0 ) is the lower-left corner and ( 1, 1 ) is upper-right corner.
     * 
     * @param x
     * @param y
     * @param box
     * @param outXYZ
     */
    public static void modelToBox( float x, float y, float z, float[] box, float[] outXYZ, int outOff ) {
        outXYZ[0+outOff] = ( x - box[0] ) / ( box[3] - box[0] );
        outXYZ[1+outOff] = ( y - box[1] ) / ( box[4] - box[1] );
        outXYZ[2+outOff] = ( z - box[2] ) / ( box[5] - box[2] );
    }

    /**
     * Maps a point in box coordinates into model coordinates.
     */
    public static void boxToModel( float x, float y, float z, float[] box, float[] outXYZ, int outOff ) {
        outXYZ[0+outOff] = x * ( box[3] - box[0] ) + box[0];
        outXYZ[1+outOff] = y * ( box[4] - box[1] ) + box[1];
        outXYZ[2+outOff] = z * ( box[5] - box[2] ) + box[2];
    }

    /**
     * Performs linear mapping of some coordinate in a space defined by 
     * <code>srcDomain</code> into the coordinate space defined by <code>dstDomain</code>.
     * 
     * @param x
     * @param y
     * @param srcDomain
     * @param dstDomain
     * @param outXYZ
     */
    public static void mapPoint( float x, float y, float z, float[] srcDomain, float[] dstDomain, float[] outXYZ, int outOff ) {
        outXYZ[0+outOff] = ( x - srcDomain[0] ) / ( srcDomain[3] - srcDomain[0] ) * ( dstDomain[3] - dstDomain[0] ) + dstDomain[0];
        outXYZ[1+outOff] = ( y - srcDomain[1] ) / ( srcDomain[4] - srcDomain[1] ) * ( dstDomain[4] - dstDomain[1] ) + dstDomain[1];
        outXYZ[2+outOff] = ( z - srcDomain[2] ) / ( srcDomain[5] - srcDomain[2] ) * ( dstDomain[5] - dstDomain[2] ) + dstDomain[2];
    }

    /**
     * Performs linear mapping of a Box3 in a space defined by 
     * <code>srcDomain</code> into the coordinate space defined by <code>dstDomain</code>.
     * 
     * @param in        Input box
     * @param srcDomain Box defining domain of input coordinate. 
     * @param dstDomain Box defining destination domain (codomain). 
     * @param out       On return, holds mapped box. May be same is in.
     */
    public static void mapBox( float[] in, float[] srcDomain, float[] dstDomain, float[] out ) {
        float sx = ( dstDomain[3] - dstDomain[0] ) / ( srcDomain[3] - srcDomain[0] );
        float sy = ( dstDomain[4] - dstDomain[1] ) / ( srcDomain[4] - srcDomain[1] );
        float sz = ( dstDomain[5] - dstDomain[2] ) / ( srcDomain[5] - srcDomain[2] );
        float tx = dstDomain[0] - srcDomain[0] * sx;
        float ty = dstDomain[1] - srcDomain[1] * sy;
        float tz = dstDomain[2] - srcDomain[2] * sz;
        out[0] = sx * in[0] + tx;
        out[1] = sy * in[1] + ty;
        out[2] = sz * in[2] + tz;
        out[3] = sx * in[3] + tx;
        out[4] = sy * in[4] + ty;
        out[5] = tz * in[5] + tz;
    }

    /**
     * Ensures box has defines non-negative space.
     * @param box
     */
    public static void fix( float[] box ) {
        for( int i = 0; i < 3; i++ ) {
            final int j = i + 3;
            if( box[i] > box[j] ) {
                float f = box[i];
                box[i] = box[j];
                box[j] = f;
            }
        }
    } 


    public static String format( float[] v ) {
        return String.format( "box3[% 7.4f, % 7.4f, % 7.4f][% 7.4f,% 7.4f, % 7.4f]", 
                              v[0], v[1], v[2], v[3], v[4], v[5] );
    }

    /**
     * @param points  array of length-3 vertices.
     * @param off     offset into points
     * @param len     number of points
     * @param out     length-6 array that to hold the union of the points on return.
     */
    public static void boundPoints( double[][] points, int off, int len, double[] out ) {
        if( len == 0 ) {
            Arrays.fill( out, 0, 6, 0.0 );
            return;
        }

        double[] v = points[off];
        double minX = v[0], maxX = v[0];
        double minY = v[1], maxY = v[1];
        double minZ = v[2], maxZ = v[2];

        for( int i = 1; i < len; i++ ) {
            v = points[off + i];

            if( v[0] < minX ) {
                minX = v[0];
            } else if( v[0] > maxX ) {
                maxX = v[0];
            }

            if( v[1] < minY ) {
                minY = v[1];
            } else if( v[1] > maxY) {
                maxY = v[1];
            }

            if( v[2] < minZ ) {
                minZ = v[2];
            } else if( v[2] > maxZ ) {
                maxZ = v[2];
            }
        }

        out[0] = minX;
        out[1] = minY;
        out[2] = minZ;
        out[3] = maxX;
        out[4] = maxY;
        out[5] = maxZ;
    }

    /**
     * Provided with an array of COPLANAR vertices and an AABB, this method
     * will provide the geometric intersection.
     * 
     * @param vertLoop  Array of length-3 vertices.
     * @param vertCount Number of vertices
     * @param clipAabb  Clipping box
     * @param out       pre-allocated loop object to hold clipped results.
     * @return true iff intersection is found with non zero surface area.
     * (This isn't precisely true because I haven't formalized all the boundary conditions)
     */
    public static boolean clipPlanar( double[][] vertLoop, int vertOff, int vertCount, double[] clipAabb, Loop out ) {
        double[][] verts = out.mVerts;

        if( verts == null || verts.length < vertCount + 6 ) {
            verts = new double[vertLoop.length + 6][3];
            out.mVerts = verts;
        }

        // Copy data into output.
        for( int i = 0; i < vertCount; i++ ) {
            System.arraycopy( vertLoop[i + vertOff], 0, verts[i], 0, 3 );
        }

        // Iterate through clip planes.
        // Iterate through clip planes.
        for( int axis = 0; axis < 3; axis++ ) {

            // Iterate through vertices.
            double min = clipAabb[axis];
            double max = clipAabb[axis + 3];
            int m0 = -1;
            int m1 = -1;
            int n0 = -1;
            int n1 = -1;

            // Find plane crossings.
            for( int i = 0; i < vertCount; i++ ) {
                double a = verts[i][axis];
                double b = verts[(i + 1) % vertCount][axis];

                if( (a < min) != (b < min) ) {
                    if( m0 == -1 ) {
                        m0 = i;
                    } else {
                        m1 = i;
                    }
                }

                if( (a > max) != (b > max) ) {
                    if( n0 == -1 ) {
                        n0 = i;
                    } else {
                        n1 = i;
                    }
                }
            }

            if( m1 == -1 ) {
                // No intersection with min plane.
                if( verts[0][axis] < min ) {
                    // No intersection with volume.
                    out.mLength = 0;
                    return false;
                }
            } else {
                // Split loop at min plane.
                if( verts[0][axis] < min ) {
                    vertCount = retainLoopSection( verts, vertCount, m0, m1, axis, min );
                } else {
                    vertCount = removeLoopSection( verts, vertCount, m0, m1, axis, min );
                }

                // Recompute max crossings, if necessary.
                if( n1 != -1 ) {
                    n0 = -1;
                    n1 = -1;

                    for( int i = 0; i < vertCount; i++ ) {
                        double a = verts[i][axis];
                        double b = verts[(i + 1) % vertCount][axis];

                        if( (a > max) != (b > max) ) {
                            if( n0 == -1 ) {
                                n0 = i;
                            } else {
                                n1 = i;
                            }
                        }
                    }
                }
            }

            if( n1 == -1 ) {
                // No intersection with max plane.
                if( verts[0][axis] > max ) {
                    // No intersection with volume.
                    out.mLength = 0;
                    return false;
                }
            } else {
                // Split loop at max plane.
                if( verts[0][axis] > max ) {
                    vertCount = retainLoopSection( verts, vertCount, n0, n1, axis, max );
                } else {
                    vertCount = removeLoopSection( verts, vertCount, n0, n1, axis, max );
                }
            }
        }

        out.mLength = vertCount;
        return true;
    }



    private static int removeLoopSection( double[][] v, int count, int start, int stop, int axis, double pos ) {
        // Make sure there's enough room between start and stop vertices if
        // they're adjacent.
        if( start + 1 == stop ) {
            double[] temp = v[v.length - 1];

            for( int i = v.length - 1; i >= stop; i-- ) {
                v[i] = v[i - 1];
            }

            v[stop++] = temp;
            temp[0] = v[stop][0];
            temp[1] = v[stop][1];
            temp[2] = v[stop][2];
            count++;
        }

        // Cache start values.
        double[] va = v[start];
        double[] vb = v[start + 1];

        double a = va[axis];
        double b = vb[axis];

        // Check if va is precisely on edge.
        if( a != pos ) {
            double r = (pos - a) / (b - a);
            if( va[0] != vb[0] )
                vb[0] = r * vb[0] + (1.0 - r) * va[0];
            if( va[1] != vb[1] )
                vb[1] = r * vb[1] + (1.0 - r) * va[1];
            if( va[2] != vb[2] )
                vb[2] = r * vb[2] + (1.0 - r) * va[2];
            vb[axis] = pos; // To avoid rounding errors.
            start++;
        }

        // Cache stop values.
        va = v[stop];
        vb = v[(stop + 1) % count];
        a = va[axis];
        b = vb[axis];

        // Check if vb is precisely on edge.
        if( b != pos ) {
            double r = (pos - a) / (b - a);
            if( va[0] != vb[0] )
                va[0] = r * vb[0] + (1.0 - r) * va[0];
            if( va[1] != vb[1] )
                va[1] = r * vb[1] + (1.0 - r) * va[1];
            if( va[2] != vb[2] )
                va[2] = r * vb[2] + (1.0 - r) * va[2];
            va[axis] = pos; // To avoid rounding errors.
        } else {
            stop++;
        }

        // Check if there's a gap to fill.
        int gap = stop - start - 1;

        if( gap == 0 )
            return count;

        count -= gap;

        for( int i = start + 1; i < count; i++ ) {
            double[] temp = v[i];
            v[i] = v[i + gap];
            v[i + gap] = temp;
        }

        return count;
    }


    private static int retainLoopSection( double[][] v, int count, int start, int stop, int axis, double pos ) {

        // Make sure there's enough space if vertices are adjacent.
        if( stop + 1 == count ) {
            double[] temp = v[count++];
            temp[0] = v[0][0];
            temp[1] = v[0][1];
            temp[2] = v[0][2];
        }

        // Cache start values.
        double[] va = v[start];
        double[] vb = v[start + 1];

        double a = va[axis];
        double b = vb[axis];

        // Check if vb is precisely on edge.
        if( b != pos ) {
            double r = (pos - a) / (b - a);
            if( va[0] != vb[0] )
                va[0] = r * vb[0] + (1.0 - r) * va[0];
            if( va[1] != vb[1] )
                va[1] = r * vb[1] + (1.0 - r) * va[1];
            if( va[2] != vb[2] )
                va[2] = r * vb[2] + (1.0 - r) * va[2];
            va[axis] = pos; // To avoid rounding errors.
        } else {
            start++;
        }

        // Cache stop values.
        va = v[stop];
        vb = v[stop + 1];
        a = va[axis];
        b = vb[axis];

        // Check if vb is precisely on edge.
        if( b != pos ) {
            double r = (pos - a) / (b - a);
            if( va[0] != vb[0] )
                vb[0] = r * vb[0] + (1.0 - r) * va[0];
            if( va[1] != vb[1] )
                vb[1] = r * vb[1] + (1.0 - r) * va[1];
            if( va[2] != vb[2] )
                vb[2] = r * vb[2] + (1.0 - r) * va[2];
            vb[axis] = pos;
            stop++;
        }

        // Shift elements back.
        count = stop - start + 1;

        if( start == 0 )
            return count;

        for( int i = 0; i < count; i++ ) {
            double[] temp = v[i];
            v[i] = v[i + start];
            v[i + start] = temp;
        }

        return count;
    }


    private Box3() {}

}
