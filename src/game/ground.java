package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ground {
    BufferedImage image;
    int x,y;  //位置
    int width,height;  //宽和高
    public ground() throws IOException {
        image= ImageIO.read(getClass().getResource("/resources/ground.png"));
        width=image.getWidth();
        height=image.getHeight();
        x=0;
        y=500;
    }
    /*左移*/
    public void step(){
        x--;
        if (x==-109){
            x=0;
        }
    }
}
