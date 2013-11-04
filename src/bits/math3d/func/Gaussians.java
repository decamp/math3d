package bits.math3d.func;

/**
 * @author decamp
 */
public final class Gaussians {

    
    /**
     * Computes the sigma value of a gaussian whose output value
     * at specified distance divided its output value at zero is 
     * <code>falloffAtDist</code>.
     * <p>
     * For example, if <br/>
     * <code>N(sigma,0)  == 0.5</code><br/>
     * and<br/>
     * <code>N(sigma,2.0) == 0.05</code><br/>
     * then 
     * <code>sigma = falloffToSigma( 2.0, 0.1 )</code>
     * 
     * @param sigma
     * @param falloffAtDist
     * @return
     */
    public static double falloffToSigma( double dist, double falloffAtDist ) {
        return Math.sqrt( -dist * dist * 0.5 / Math.log( falloffAtDist ) );
    }

    /**
     * Computes distance from the zero at which a gaussian function with specified sigma value
     * will reach a particular attenuation point. 
     * <p>
     * For example, if <br/>
     * <code>N(sigma,0) == 0.5</code><br/>
     * then<br/>
     * <code>x = falloffToDist( sigma, 0.1 )</code><br/>
     * returns  an <code>x</code> such that:<br/>
     * <code>N(sigma,x) == 0.05 == (0.5 * 0.1)</code>
     * 
     * @param sigma
     * @param falloffAtDist
     * @return
     */
    public static double falloffToDist( double sigma, double falloffAtDist ) {
        return Math.sqrt( -2.0 * sigma * sigma * Math.log( falloffAtDist ) );
    }
    
    /**
     * By specifying the sigma of a Gaussian and the desired ratio between the edge of 
     * a Gaussian array and the center, this method will compute an appropriate kernel
     * length.  
     * 
     * @param sigma      The sigma value for the desired Gaussian array.
     * @param falloff The desired ratio between the edge of the calculated array and the center.
     * 
     * @returns The minimum length of the kernel needed to meet or exceed the provided ratio.
     */
    public static int computeKernalSize( double sigma, double falloffAtEdge ) {
        double halfSize = falloffToDist( sigma, falloffAtEdge );
        return (int)Math.ceil( halfSize ) * 2 + 1;
    }
    
    
    private Gaussians() {}
    
    
    
    /**
     * By specifying the ratio between the center and edge (not corner) of a Gaussian
     * and the size of the Gaussian array, this method will compute an appropriate
     * sigma.  For example, assume you want a 9x9 holding Gaussian distribution and
     * that you want the value at the edge of the array to be 5% of the highest value
     * in the Gaussian.  To find the appropriate sigma, you would call:
     * 
     *  convertRatioToSigma(9, 0.05)
     *  
     *  @param kernelLength  The desired length of one edge of a Gaussian array.
     *  @param ratioEdge  The desired ratio between the edge of the calculated array and the center.
     *  
     *  @returns A sigma that may be used for that Gaussian.
     *  
     *  @deprecated 
     */
    public static double convertRatioToSigma(int kernelLength, double ratioAtEdge) {
        double halfSize = (kernelLength - 1) * 0.5;
        return Math.sqrt(-halfSize * halfSize * 0.5 / Math.log(ratioAtEdge));
    }


}
