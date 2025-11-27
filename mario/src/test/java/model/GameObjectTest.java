package model;


import java.awt.*;
import java.awt.image.BufferedImage;

import view.IImageLoader;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameObjectTest {
    
    @Test
    void boundsComputationsTest(){
        // Sprite dimensions
        double x = 10;
        double y = 10;
        int width = 48;     // from Mario.java line 22
        int height = 48;

        // Create BufferedImage 
        BufferedImage style = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // BufferedImage fireball = imageLoader.loadImage("/sprite.png");
        // fireballStyle = imageLoader.getSubImage(fireball, 3, 4, 24, 24);// MarioForm 

        // Create Mock GameObject and set dimensions in it
        GameObject obj = new GameObject(x, y, style){};

        // =========== Act and Assert =====

        // getBounds() ---- {return new Rectangle((int)getX(), (int)getY(), dimension.width, dimension.height);}
        Rectangle bounds = obj.getBounds();
        Rectangle check = new Rectangle((int)x, (int)y, width, height);
        // assrt 
        assertThat(bounds).isEqualTo(check);


        // getBottomBounds(){return new Rectangle((int)getX()+dimension.width/6, (int)getY() + dimension.height/2, 2*dimension.width/3, dimension.height/2);}
        // Rectangle bottom = obj.getBottomBounds();
        // Rectangle checkBottom = new Rectangle()
    }

}




// getBounds() — expects the rectangle that covers the full object: origin (int)x, (int)y and size (width, height).
// getTopBounds() — expects rectangle computed as (x + width/6, y, 2*width/3, height/2) (exact integers).
// getBottomBounds() — expects (x + width/6, y + height/2, 2*width/3, height/2).
// getLeftBounds() — expects (x, y + height/4, width/4, height/2).
// getRightBounds() — expects (x + 3*width/4, y + height/4, width/4, height/2).
