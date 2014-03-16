package bits.math3d.func;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class TestEase {
    
    public static void main( String[] args ) {
        //double decay = 5.0;
        //double cycle = 2.0;
        
        Function11[] funcs = {
                Ease.LINEAR,
                Ease.SMOOTH,
                Ease.SMOOTHER,
                Ease.COS,
                Ease.POW2,
                Ease.POW3,
                Ease.CIRC
                
                //Ease.newSpring( cycle, decay ),
                //Ease.newSpringIn( cycle, decay ),
                //Ease.newSpringOut( cycle, decay )
        };
        
        BufferedImage im = plot( 1024, 1024, funcs );
        ImagePanel.showImage( im ); 
    }
    
    
    public static BufferedImage plot( int w, int h, Function11... funcs ) {
        BufferedImage ret = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g = (Graphics2D)ret.getGraphics();
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setBackground( Color.GRAY );
        g.clearRect( 0, 0, w, h );
        
        AffineTransform trans = AffineTransform.getTranslateInstance( w * 0.25, h * 0.75 );
        double sx = 0.5 * w;
        double sy = 0.5 * h;
        double thick = 2.0 / Math.min( sx, sy );
        trans.scale( sx, -sy );
        g.setTransform( trans );
        
        g.setColor( Color.BLACK );
        g.setStroke( new BasicStroke( (float)( thick * 0.5 ) ) );
        g.drawRect( 0, 0, 1, 1 );
        g.setStroke( new BasicStroke( (float)( thick ) ) );
        
        int samps = w / 2;
        
        for( int i = 0; i < funcs.length; i++ ) {
            float hue = funcs.length < 2 ? 0f : (float)i / funcs.length;
            Color color = Color.getHSBColor( hue, 1f, 1f );
            g.setColor( color );
            plot( g, funcs[i], samps );
        }
        
        return ret;
    }
    
    
    private static void plot( Graphics2D g, Function11 func, int samps ) {
        Path2D path = new Path2D.Double();
        
        for( int i = 0; i <= samps; i++ ) {
            double x = i / (double)samps;
            double y = func.apply( x );
            
            if( i == 0 ) {
                path.moveTo( x, y );
            } else {
                path.lineTo( x, y );
            }
        }
        
        g.draw( path );
    }
    
}
