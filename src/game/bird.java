package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class bird {
    BufferedImage image;   //鸟的初始图片
    int x,y;  //位置
    int width,height;  //宽和高
    int size;   //大小，用于碰撞检测
    double g;  //重力加速度
    double t;  //位移间隔
    double v0;  //最初（上抛）速度
    double speed;  //当前（上抛）速度
    double s;    //经过时间t后的位移
    double alpha;  //小鸟的弧度
    BufferedImage[] images;  //小鸟的动画帧
    int index;   //记录动画帧下标
    public bird() throws IOException {
        /*初始化基本参数*/
        image= ImageIO.read(getClass().getResource("/resources/huaji.png"));
        width=image.getWidth();
        height=image.getHeight();
        x=132;
        y=280;
        size=30;
        /*初始化位移参数*/
        g=4;
        v0=20;
        t=0.25;
        speed=v0;
        s=0;
        alpha=0;
        /*初始动画帧参数*/
        images=new BufferedImage[8];
        for (int i=0;i<8;i++){
            images[i]=ImageIO.read(getClass().getResource("/resources/"+i+".png"));
        }
        index=0;
    }
    /*飞行一次变化一帧*/
    public void fly(){
        index++;
        image = images[(index / 12) % 8];
    }
/*
   由于小鸟的移动是相对于柱子及地面来说的，地面与柱子已经向左移动（即x轴移动）了，小鸟只需进行y轴移动
*/
    public void step(){
        double v0=speed;
        /*上抛运动位移*/
        s=v0*t+g*t*t/2;
        /*计算坐标*/
        y=y-(int)s;
        /*计算下次移动速度*/
        double v=v0-g*t;
        speed=v;
        /*计算倾角*/
        alpha=Math.atan(s/8);
    }
    /*每点一次就向上飞，即将速度重置*/
    public void flappy() throws IOException {
        speed=v0;
    }
    /*检测碰撞地面*/
    public boolean hit(ground ground){
        boolean hit=y+size/2>ground.y;
        /*如果发生碰撞，让小鸟头朝下*/
        if (hit){
            y=ground.y-size/2;
            alpha=-3.14159265358979232/2;
        }
        return hit;
    }
    /*检测碰撞柱子*/
    public boolean hit(column column){
        if (x>column.x-column.width/2-size/2&&x<column.x+column.width/2+size/2){
            if (y>column.y-column.gap/2+size/2&&y<column.y+column.gap/2-size/2){
                return false;
            }
            return true;
        }
        return false;
    }
}
