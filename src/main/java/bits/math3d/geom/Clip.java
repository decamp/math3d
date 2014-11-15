/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package bits.math3d.geom;

import bits.math3d.*;


/**
 * @author Philip DeCamp
 */
public class Clip {

    /**
     * Provided with an array of COPLANAR vertices and an AABB, this method
     * will provide the geometric intersection.
     *
     * @param vertLoop  Array of length-3 vertices.
     * @param vertCount Number of vertices
     * @param clip      Clipping box
     * @param out       pre-allocated loop object to hold clipped results.
     * @return true iff intersection is found with non zero surface area.
     * (This isn't precisely true because I haven't formalized all the boundary conditions)
     */
    public static boolean clipPlanar( Vec3[] vertLoop, int vertOff, int vertCount, Box3 clip, PolyLine out ) {
        final int cap = vertCount + 6;
        out.ensureCapacity( cap );
        Vec3[] verts = out.mVerts;

        // Copy data into output.
        for( int i = 0; i < vertCount; i++ ) {
            Vec.put( vertLoop[i + vertOff], verts[i] );
        }

        // Iterate through clip planes.
        // Iterate through clip planes.
        for( int axis = 0; axis < 3; axis++ ) {
            // Iterate through vertices.
            float min = Box.min( clip, axis );
            float max = Box.max( clip, axis );
            float v0  = verts[0].el( axis );

            int m0 = -1;
            int m1 = -1;
            int n0 = -1;
            int n1 = -1;

            // Find plane crossings.
            for( int i = 0, j = vertCount - 1; i < vertCount; j = i++ ) {
                float a = verts[i].el( axis );
                float b = verts[j].el( axis );

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
                if( v0 < min ) {
                    // No intersection with volume.
                    out.mSize = 0;
                    return false;
                }
            } else {
                // Split loop at min plane.
                if( v0 < min ) {
                    vertCount = retainLoopSection( verts, vertCount, m0, m1, axis, min );
                } else {
                    vertCount = removeLoopSection( verts, vertCount, m0, m1, axis, min );
                }

                // Recompute max crossings, if necessary.
                if( n1 != -1 ) {
                    n0 = -1;
                    n1 = -1;
                    for( int i = 0, j = vertCount - 1; i < vertCount; j = i++ ) {
                        float a = verts[i].el( axis );
                        float b = verts[j].el( axis );
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
                if( v0 > max ) {
                    // No intersection with volume.
                    out.mSize = 0;
                    return false;
                }
            } else {
                // Split loop at max plane.
                if( v0 > max ) {
                    vertCount = retainLoopSection( verts, vertCount, n0, n1, axis, max );
                } else {
                    vertCount = removeLoopSection( verts, vertCount, n0, n1, axis, max );
                }
            }
        }

        out.mSize = vertCount;
        return true;
    }


    private static int removeLoopSection( Vec3[] v, int count, int start, int stop, int axis, float pos ) {
        // Make sure there is enough room between start and stop vertices if they're adjacent.
        if( start + 1 == stop ) {
            Vec3 temp = v[v.length-1];
            System.arraycopy( v, stop - 1, v, stop, v.length - stop );
            v[stop++] = temp;
            Vec.put( v[stop], temp );
            count++;
        }

        // Cache start values.
        Vec3 va = v[start];
        Vec3 vb = v[start + 1];
        float a = va.el( axis );
        float b = vb.el( axis );

        // Check if va is precisely on edge.
        if( a != pos ) {
            float r = (pos - a) / (b - a);
            if( va.x != vb.x ) {
                vb.x = r * vb.x + (1f - r) * va.x;
            }
            if( va.y != vb.y ) {
                vb.y = r * vb.y + (1f - r) * va.y;
            }
            if( va.z != vb.z ) {
                vb.z = r * vb.z + (1f - r) * va.z;
            }
            vb.el( axis, pos ); // Avoid rounding errors.
            start++;
        }

        // Cache stop values.
        va = v[stop];
        vb = v[(stop + 1) % count];
        a = va.el( axis );
        b = vb.el( axis );

        // Check if vb is precisely on edge.
        if( b != pos ) {
            float r = (pos - a) / (b - a);
            if( va.x != vb.x ) {
                va.x = r * vb.x + (1.0f - r) * va.x;
            }
            if( va.y != vb.y ) {
                va.y = r * vb.y + (1.0f - r) * va.y;
            }
            if( va.z != vb.z ) {
                va.z = r * vb.z + (1.0f - r) * va.z;
            }
            va.el( axis, pos ); // Avoid rounding errors.
        } else {
            stop++;
        }

        // Check if there's a gap to fill.
        int gap = stop - start - 1;
        if( gap == 0 ) {
            return count;
        }
        count -= gap;
        for( int i = start + 1; i < count; i++ ) {
            Vec3 temp = v[i];
            v[i] = v[i + gap];
            v[i + gap] = temp;
        }

        return count;
    }


    private static int retainLoopSection( Vec3[] v, int count, int start, int stop, int axis, float pos ) {
        // Make sure there's enough space if vertices are adjacent.
        if( stop + 1 == count ) {
            Vec3 temp = v[count++];
            Vec.put( v[0], temp );
        }

        // Cache start values.
        Vec3 va = v[start  ];
        Vec3 vb = v[start+1];
        float a = va.el( axis );
        float b = vb.el( axis );

        // Check if vb is precisely on edge.
        if( b != pos ) {
            float r = (pos - a) / (b - a);
            if( va.x != vb.x ) {
                va.x = r * vb.x + (1.0f - r) * va.x;
            }
            if( va.y != vb.y ) {
                va.y = r * vb.y + (1.0f - r) * va.y;
            }
            if( va.z != vb.z ) {
                va.z = r * vb.z + (1.0f - r) * va.z;
            }
            va.el( axis, pos ); // To avoid rounding errors.
        } else {
            start++;
        }

        // Cache stop values.
        va = v[stop];
        vb = v[stop + 1];
        a = va.el( axis );
        b = vb.el( axis );

        // Check if vb is precisely on edge.
        if( b != pos ) {
            float r = (pos - a) / (b - a);
            if( va.x != vb.x ) {
                vb.x = r * vb.x + (1 - r) * va.x;
            }
            if( va.y != vb.y ) {
                vb.y = r * vb.y + (1 - r) * va.y;
            }
            if( va.z != vb.z ) {
                vb.z = r * vb.z + (1 - r) * va.z;
            }
            vb.el( axis, pos );
            stop++;
        }

        // Shift elements back.
        count = stop - start + 1;
        if( start == 0 ) {
            return count;
        }
        for( int i = 0; i < count; i++ ) {
            Vec3 temp = v[i];
            v[i] = v[i + start];
            v[i + start] = temp;
        }

        return count;
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
    public static boolean clipPlanar3( double[][] vertLoop, int vertOff, int vertCount, double[] clipAabb, Loop out ) {
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
                    vertCount = retainLoopSection3( verts, vertCount, m0, m1, axis, min );
                } else {
                    vertCount = removeLoopSection3( verts, vertCount, m0, m1, axis, min );
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
                    vertCount = retainLoopSection3( verts, vertCount, n0, n1, axis, max );
                } else {
                    vertCount = removeLoopSection3( verts, vertCount, n0, n1, axis, max );
                }
            }
        }

        out.mLength = vertCount;
        return true;
    }


    private static int removeLoopSection3( double[][] v, int count, int start, int stop, int axis, double pos ) {
        // Make sure there's enough room between start and stop vertices if
        // they're adjacent.
        if( start + 1 == stop ) {
            double[] temp = v[v.length - 1];
            System.arraycopy( v, stop - 1, v, stop, v.length - stop );
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


    private static int retainLoopSection3( double[][] v, int count, int start, int stop, int axis, double pos ) {

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

}
