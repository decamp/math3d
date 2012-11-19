package cogmac.math3d;

import java.util.*;
import static cogmac.math3d.Tolerance.*;



/** 
 * These are all homographic coordinates, yo.  Matrices are 4x4.  Vectors are 3x1.
 * 
 * @author Philip DeCamp  
 */
public final class Matrices {
    
        
    public static void multMatMat(double[] a, double[] b, double[] out) {
        out[ 0] = a[ 0]*b[ 0] + a[ 4]*b[ 1] + a[ 8]*b[ 2] + a[12]*b[ 3];
        out[ 1] = a[ 1]*b[ 0] + a[ 5]*b[ 1] + a[ 9]*b[ 2] + a[13]*b[ 3];
        out[ 2] = a[ 2]*b[ 0] + a[ 6]*b[ 1] + a[10]*b[ 2] + a[14]*b[ 3];
        out[ 3] = a[ 3]*b[ 0] + a[ 7]*b[ 1] + a[11]*b[ 2] + a[15]*b[ 3];
        
        out[ 4] = a[ 0]*b[ 4] + a[ 4]*b[ 5] + a[ 8]*b[ 6] + a[12]*b[ 7];
        out[ 5] = a[ 1]*b[ 4] + a[ 5]*b[ 5] + a[ 9]*b[ 6] + a[13]*b[ 7];
        out[ 6] = a[ 2]*b[ 4] + a[ 6]*b[ 5] + a[10]*b[ 6] + a[14]*b[ 7];
        out[ 7] = a[ 3]*b[ 4] + a[ 7]*b[ 5] + a[11]*b[ 6] + a[15]*b[ 7];
        
        out[ 8] = a[ 0]*b[ 8] + a[ 4]*b[ 9] + a[ 8]*b[10] + a[12]*b[11];
        out[ 9] = a[ 1]*b[ 8] + a[ 5]*b[ 9] + a[ 9]*b[10] + a[13]*b[11];
        out[10] = a[ 2]*b[ 8] + a[ 6]*b[ 9] + a[10]*b[10] + a[14]*b[11];
        out[11] = a[ 3]*b[ 8] + a[ 7]*b[ 9] + a[11]*b[10] + a[15]*b[11];
        
        out[12] = a[ 0]*b[12] + a[ 4]*b[13] + a[ 8]*b[14] + a[12]*b[15];
        out[13] = a[ 1]*b[12] + a[ 5]*b[13] + a[ 9]*b[14] + a[13]*b[15];
        out[14] = a[ 2]*b[12] + a[ 6]*b[13] + a[10]*b[14] + a[14]*b[15];
        out[15] = a[ 3]*b[12] + a[ 7]*b[13] + a[11]*b[14] + a[15]*b[15];
    }
    
    
    public static void multMatMatHomog(double[] a, double[] b, double[] out) {
        multMatMat(a, b, out);
        double scale = 1.0 / (out[3] + out[7]+ out[11] + out[15]);
        
        for(int i = 0; i < 16; i++) {
            out[i] *= scale;
        }
    }
    
    
    public static void multMatVec(double[] a, double[] b, double[] out) {
        out[0]   = a[ 0]*b[0] + a[ 4]*b[1] + a[ 8]*b[2] + a[12];
        out[1]   = a[ 1]*b[0] + a[ 5]*b[1] + a[ 9]*b[2] + a[13];
        out[2]   = a[ 2]*b[0] + a[ 6]*b[1] + a[10]*b[2] + a[14];
        double w = a[ 3]*b[0] + a[ 7]*b[1] + a[11]*b[2] + a[15];
        
        if(w != 1.0) {
            out[0] /= w;
            out[1] /= w;
            out[2] /= w;
        }
    }

    
    public static void multMatVec(double[] a, int offA, double[] b, int offB, double[] out, int offOut ) {
        out[0+offOut]   = a[ 0+offA]*b[0+offB] + a[ 4+offA]*b[1+offB] + a[ 8+offA]*b[2+offB] + a[12+offA];
        out[1+offOut]   = a[ 1+offA]*b[0+offB] + a[ 5+offA]*b[1+offB] + a[ 9+offA]*b[2+offB] + a[13+offA];
        out[2+offOut]   = a[ 2+offA]*b[0+offB] + a[ 6+offA]*b[1+offB] + a[10+offA]*b[2+offB] + a[14+offA];
        double w        = a[ 3+offA]*b[0+offB] + a[ 7+offA]*b[1+offB] + a[11+offA]*b[2+offB] + a[15+offA];
        
        if(w != 1.0) {
            out[0+offOut] /= w;
            out[1+offOut] /= w;
            out[2+offOut] /= w;
        }
    }


    
    
    
    public static void setToIdentity(double[] out) {
        out[ 0] = 1.0;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        out[ 4] = 0.0;
        out[ 5] = 1.0;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = 1.0;
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }
    
    
    public static void computeTranslationMatrix(double tx, double ty, double tz, double[] out) {
        out[ 0] = 1.0;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        out[ 4] = 0.0;
        out[ 5] = 1.0;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = 1.0;
        out[11] = 0.0;
        out[12] = tx;
        out[13] = ty;
        out[14] = tz;
        out[15] = 1.0;
    }
    
    
    public static void computeScaleMatrix(double sx, double sy, double sz, double[] out) {
        out[ 0] = sx;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        out[ 4] = 0.0;
        out[ 5] = sy;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = sz;
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }
    
    
    public static void computeRotationMatrix(double radians, double x, double y, double z, double[] out) {
        double c = Math.cos(radians);
        double s = Math.sin(radians);
        
        double sum = Math.sqrt(x*x+y*y+z*z);
        x /= sum;
        y /= sum;
        z /= sum;
        
        out[0 ] = x*x*(1-c)+c;
        out[1 ] = x*y*(1-c)+z*s;
        out[2 ] = x*z*(1-c)-y*s;
        out[3 ] = 0.0;
        
        out[4 ] = x*y*(1-c)-z*s;
        out[5 ] = y*y*(1-c)+c;
        out[6 ] = y*z*(1-c)+x*s;
        out[7 ] = 0.0;
        
        out[8 ] = x*z*(1-c)+y*s;
        out[9 ] = y*z*(1-c)-x*s;
        out[10] = z*z*(1-c)+c;
        out[11] = 0.0;
        
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }    

    
    public static void computeFrustumMatrix(double left, double right, double bottom, double top, double near, double far, double[] out) {
        out[ 0] = 2.0 * near / (right - left);
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        
        out[ 4] = 0.0;
        out[ 5] = 2 * near / (top - bottom);
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        
        out[ 8] = (right + left) / (right - left);
        out[ 9] = (top + bottom) / (top - bottom);
        out[10] = -(far + near) / (far - near);
        out[11] = -1.0;
        
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = -(2 * far * near) / (far - near);
        out[15] = 0.0;
    }
    
    
    public static void computeOrthoMatrix(double left, double right, double bottom, double top, double near, double far, double[] out) {
        out[ 0] = 2.0 / (right - left);
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        
        out[ 4] = 0.0;
        out[ 5] = 2.0 / (top - bottom);
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = -2.0 / (far - near);
        out[11] = 0.0;
        
        out[12] = -(right + left) / (right - left);
        out[13] = -(top + bottom) / (top - bottom);
        out[14] = -(far + near) / (far - near);
        out[15] = 1.0;
    }
    
    
    public static void computeLookAtMatrix(double[] eyeVec, double[] centerVec, double[] upVec, double[] outMat) {
        double fx  = centerVec[0] - eyeVec[0];
        double fy  = centerVec[1] - eyeVec[1];
        double fz  = centerVec[2] - eyeVec[2];
        double len = Math.sqrt(fx * fx + fy * fy + fz * fz);
        fx /= len;
        fy /= len;
        fz /= len;
        
        double ux = upVec[0];
        double uy = upVec[1];
        double uz = upVec[2];
        len = Math.sqrt(ux * ux + uy * uy + uz * uz);
        ux /= len;
        uy /= len;
        uz /= len;
        
        double sx = fy * uz - fz * uy;
        double sy = fz * ux - fx * uz;
        double sz = fx * uy - fy * ux;
        
        ux = sy * fz - sz * fy;
        uy = sz * fx - sx * fz;
        uz = sx * fy - sy * fx;
        
        outMat[ 0] = sx;
        outMat[ 1] = ux;
        outMat[ 2] = -fx;
        outMat[ 3] = 0;
        outMat[ 4] = sy;
        outMat[ 5] = uy;
        outMat[ 6] = -fy;
        outMat[ 7] = 0;
        outMat[ 8] = sz;
        outMat[ 9] = uz;
        outMat[10] = -fz;
        outMat[11] = 0;
        outMat[12] = -(sx * eyeVec[0] + sy * eyeVec[1] + sz * eyeVec[2]);
        outMat[13] = -(ux * eyeVec[0] + uy * eyeVec[1] + uz * eyeVec[2]);
        outMat[14] = fx * eyeVec[0] + fy * eyeVec[1] + fz * eyeVec[2];
        outMat[15] = 1.0;
    }
    

    public static void computeViewportMatrix(double x, double y, double w, double h, double[] out) {
        out[ 0] = w * 0.5;
        out[ 1] = 0.0;
        out[ 2] = 0.0;
        out[ 3] = 0.0;
        
        out[ 4] = 0.0;
        out[ 5] = h * 0.5;
        out[ 6] = 0.0;
        out[ 7] = 0.0;
        
        out[ 8] = 0.0;
        out[ 9] = 0.0;
        out[10] = 1.0;
        out[11] = 0.0;
        
        out[12] = w * 0.5 + x;
        out[13] = h * 0.5 + y;
        out[14] = 0.0;
        out[15] = 1.0;
    }
    
    /**
     * Removes any translation/scaling/skew or other non-rotation 
     * transformations from a matrix.  
     * 
     * @param mat 4x4 homography matrix to turn into strict rotation matrix.
     */
    public static void normalizeRotationMatrix( double[] mat ) {
        double d;
        
        //Kill translation, scalings.
        mat[ 3] = 0.0;
        mat[ 7] = 0.0;
        mat[11] = 0.0;
        mat[12] = 0.0;
        mat[13] = 0.0;
        mat[14] = 0.0;
        mat[15] = 1.0;
        
        //Normalize length of X-axis.
        d = Math.sqrt(mat[0] * mat[0] + mat[1] * mat[1] + mat[2] * mat[2]);
        if( d > ABS_ERR ) {
            mat[0] /= d;
            mat[1] /= d;
            mat[2] /= d;
        }else{
            mat[0] = 1.0;
            mat[1] = 0.0;
            mat[2] = 0.0;
        }
        
        //Orthogonalize Y-axis to X-axis
        d = mat[0] * mat[4] + mat[1] * mat[5] + mat[2] * mat[6];
        mat[4] -= d * mat[0];
        mat[5] -= d * mat[1];
        mat[6] -= d * mat[2];
        
        //Normalize Y-axis.
        d = Math.sqrt(mat[4] * mat[4] + mat[5] * mat[5] + mat[6] * mat[6]);
        if( d > ABS_ERR ) {
            mat[4] /= d;
            mat[5] /= d;
            mat[6] /= d;
        }else{
            double[] orth = new double[3];
            Vectors.chooseOrtho(mat[0], mat[1], mat[2], orth);
            mat[4] = orth[0];
            mat[5] = orth[1];
            mat[6] = orth[2];
        }
        
        //Compute Z-axis
        mat[ 8] = mat[1]*mat[6] - mat[2]*mat[5];
        mat[ 9] = mat[2]*mat[4] - mat[0]*mat[6];
        mat[10] = mat[0]*mat[5] - mat[1]*mat[4];
    }
    
    
    public static void axesToTransform(double[] x, double[] y, double[] out) {
        double[] z = new double[3];
        Vectors.cross(x, y, z);
        axesToTransform(x, y, z, out);
    }
    
    
    public static void axesToTransform(double[] x, double[] y, double[] z, double[] out) {
        out[ 0] = x[0];
        out[ 1] = x[1];
        out[ 2] = x[2];
        out[ 3] = 0.0;
        out[ 4] = y[0];
        out[ 5] = y[1];
        out[ 6] = y[2];
        out[ 7] = 0.0;
        out[ 8] = z[0];
        out[ 9] = z[1];
        out[10] = z[2];
        out[11] = 0.0;
        out[12] = 0.0;
        out[13] = 0.0;
        out[14] = 0.0;
        out[15] = 1.0;
    }
    
       
    
    /**
     * @param mat    Input matrix
     * @param work0  Working matrix.  Contents don't matter, but is overwritten.  
     * @param work1  Working matrix.  Contents don't matter, but is overwritten.
     * @param out    Array to hold inverted matrix on return.
     */
    public static void invertMat(double[] mat, double[] work0, double[] work1, double[] out) {
        System.arraycopy(mat, 0, work0, 0, 16);
        invertMat(work0, work1, out);
    }
    
    /**
     * PLEASE READ because matrix <code>a</code> is scrambled when you call
     * this method. 
     * 
     * @param a Input matrix, which is also used as work space
     * @param w Working matrix.  Doesn't matter what it contains.
     * @param out Array to hold inverted matrix on return. 
     */
    public static void invertMat(double[] a, double[] w, double[] out) {
        int[] p = new int[4];      //Pivots
        
        if(w == null) {
            w = new double[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
        }else{
            Arrays.fill(w, 0);
            w[ 0] = 1.0;
            w[ 5] = 1.0;
            w[10] = 1.0;
            w[15] = 1.0;    
        }
        
        decompPlu(a, p);
        solvePlu(a, p, w, 4, out);
    }

    
    public static void decompPlu(double[] a, int[] p) {
        final int m = 4;
        final int n = 4;
        final int d = Math.min(m, n);
        int pivSign = 1;

        for(int i = 0; i < m; i++)
            p[i] = i;

        //Outer loop
        for(int j = 0; j < n; j++) {

            //Apply previous transformations.
            for(int i = 0; i < m; i++) {
                int kmax = Math.min(i,j);
                double s = 0.0;
                
                for(int k = 0; k < kmax; k++){
                    s += a[i+k*m]*a[k+j*m];
                }
                
                a[i+j*m] -= s;
            }
            
            //Find pivot and exchange if necessary.
            int piv = j;
            for(int i = j+1; i < m; i++) {
                if(Math.abs(a[i+j*m]) > Math.abs(a[piv+j*m]))
                    piv = i;
            }
            
            if(piv != j) {
                for(int k = 0; k < n; k++) {
                    double t = a[piv+k*m];
                    a[piv+k*m] = a[j+k*m];
                    a[j+k*m] = t;
                }
                
                int k = p[piv];
                p[piv] = p[j];
                p[j] = k;
                pivSign = -pivSign;
            }

            //Compute multipliers.
            if(j < d && a[j+j*m] != 0f){
                for(int i = j+1; i < m; i++){
                    a[i+j*m] /= a[j+j*m];
                }
            }
        }
    }

    
    public static void solvePlu(double[] lu, int[] p, double[] b, int nx, double[] out) {
        final int m = 4;
        final int n = 4;
        
        //Reorder rows.
        for(int i = 0; i < m; i++) {
            final int k = p[i];
            
            for(int j = 0; j < n; j++) {
                out[i+j*m] = b[k+j*m];
            }
        } 
                
        //Solve L*Y = B(piv,:)
        for(int k = 0; k < n; k++) {
            for(int i = k+1; i < m; i++) {
                for(int j = 0; j < nx; j++) {
                    out[i+j*m] -= out[k+j*m] * lu[i+k*m];
                }
            }
        }
        
        //Solve U*X = Y
        for(int k = Math.min(m,n)-1; k >= 0; k--) {
            for(int j = 0; j < nx; j++) {
                out[k+j*m] /= lu[k+k*m];
            }
            
            for(int i = 0; i < k; i++) {
                for(int j = 0; j < nx; j++) {
                    out[i+j*m] -= out[k+j*m] * lu[i+k*m];
                }
            }
        }
    }

    
    public static String matToString(double[] mat) {
        StringBuilder sb = new StringBuilder();
        
        for(int r = 0; r < 4; r++) {
            if(r == 0) {
                sb.append("[[ ");
            }else{
                sb.append(" [ ");
            }
            
            sb.append(String.format("% 7.4f  % 7.4f  % 7.4f  % 7.4f", mat[r   ], mat[r+4], mat[r+8], mat[r+12]));
            
            if(r == 3) {
                sb.append(" ]]");
            }else{
                sb.append(" ]\n");
            }
        }
            
        return sb.toString();
    }

    
    
    private Matrices() {}

}
