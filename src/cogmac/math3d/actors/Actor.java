package cogmac.math3d.actors;

import cogmac.math3d.*;


/**
 * For an actor: <br/>
 * Positive x-axis is considered forward, <br/>
 * Positive y-axis is left <br/>
 * Positive z-axis is up. <br/> 
 * 
 * @author decamp
 */
public class Actor extends SpatialObject {
    
    /**
     * Velocity at which the object is moving within
     * its own coordinate system.
     */
    public double[] mMoveVel = {0,0,0};
    
    private MoveMode mMode       = MoveMode.FLY;
    private boolean mRollLock    = false;
    
    private final double[][] mWorkVecs = new double[3][3];
    
    /**
     * @deprecated
     */
    @Override
    public void updateTime( double time ) {
        double prevTime = mTime;
        if( prevTime == time )
            return;
        
        double delta = time - prevTime;
        super.updateTime( time );
        
        double mx = mMoveVel[0] * delta;
        double my = mMoveVel[1] * delta;
        double mz = mMoveVel[2] * delta;
        
        move( mx, my, mz );
    }
    
    
    
    /**
     * Moves the actor.  Unlike translate(), which moves the
     * actor in global coordinates, this method is
     * defined relative to the avatar's perspective, 
     * where the x-axis comes out the right of the actor,
     * the y-axis comes out the top, and the z-axis comes out 
     * the back.
     * 
     * @param mx
     * @param my
     * @param mz
     */
    public void move( double mx, double my, double mz ) {
        if( mx == 0.0 && my == 0.0 && mz == 0.0 )
            return;
        
        if( mMode == MoveMode.FLY ) {
            moveFlying( mx, my, mz );
        }else{
            moveWalking( mx, my, mz );
        }
    }
    
    public MoveMode moveMode() {
        return mMode;
    }

    public void moveMode( MoveMode mode ) {
        mMode = mode;
    }

    public boolean rollLock() {
        return mRollLock;
    }

    public void rollLock( boolean rollLock ) {
        if( rollLock == mRollLock )
            return;
        
        mRollLock = rollLock;
    }
    
    
    public void removeRoll() {
        double[] x   = mWorkVecs[0];
        double[] y   = mWorkVecs[1];
        double[] z   = mWorkVecs[2];
        
        // Compute x-axis
        Matrices.multMatVec( mRot, FORWARD, x );
        Vectors.normalize( x, 1.0 );
        
        // Compute y-axis.
        Vectors.cross( UP, x, y );
        double len = Vectors.hypot( y );
        
        // Check if looking nearly straight up or down.
        if( len < 0.01 ) {
            return;
        }
        
        Vectors.scale( y, 1.0 / len );
        
        // Compute z-axis
        Vectors.cross( x, y, z );
        Matrices.axesToTransform( x, y, z, mRot );
    }
    
    /**
     * For flying mode, motion of the actor not restricted.
     */
    private void moveFlying( double dx, double dy, double dz ) {
        double[] w0 = mWorkVecs[0];
        double[] w1 = mWorkVecs[1];
        w0[0] = dx;
        w0[1] = dy;
        w0[2] = dz;
        
        Matrices.multMatVec( mRot, w0, w1 );
        translate( w1[0], w1[1], w1[2] );
    }
    
    /**
     * For walking mode, the motion along the actor's z-axis is
     * mapped directly to the global z-axis, while motion
     * along the actor's x-axis and y-axis is not counted
     * towards translation in the global z-axis.
     */
    private void moveWalking( double dx, double dy, double dz ) {
        double[] w0 = mWorkVecs[0];
        double[] w1 = mWorkVecs[1];
        w0[0] = dx;
        w0[1] = dy;
        w0[2] = 0.0;
        
        Matrices.multMatVec( mRot, w0, w1 );
        translate( w1[0], w1[1], dz );
    }
    

    private static final double[] FORWARD = ActorCoords.newForwardAxis();
    private static final double[] UP      = ActorCoords.newUpAxis();

}
