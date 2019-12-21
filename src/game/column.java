package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class column {
    BufferedImage image;
    int x,y;  //位置
    int width,height;  //宽和高
    int gap;  //柱子之间的缝隙
    int distance;  //两个柱子之间的距离
    Random random=new Random();   //随机数，用来改变每根柱子的上下位置
    /*n为第几根柱子*/
    public column(int n) throws IOException {
        image= ImageIO.read(getClass().getResource("/resources/column.png"));
        width=image.getWidth();
        height=image.getHeight();
        gap=144;
        distance=245;
        x=550+(n-1)*distance;  //第一根柱子初始x坐标为550，以此类推后面的柱子的初始位置
        y=random.nextInt(218)+132;  //虽说每一根柱子的y轴位置是随机生成的，但还是要有一个范围使游戏更合理
    }
    /*向左移动一步*/
    public void step(){
        x--;
        /*当柱子移动到屏幕最左端，且正好完全消失的时候，改变其x轴坐标，
        使其移动到屏幕右端新柱子该出现的位置，并重新随机设置y轴坐标*/
        if (x==-width/2){
            x=distance*2-width/2;
            y=random.nextInt(218)+132;
        }
    }
}
