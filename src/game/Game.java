package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends JPanel {
    BufferedImage Background;

    BufferedImage startImage;   //游戏开始界面
    BufferedImage gameoverImage;  //游戏结束界面
    ground ground;   //地面
    column column1,column2;   //柱子，因为屏幕上同时最多出现两根
    bird bird;   //鸟
    int score;   //分数
    /*状态常量，即用来显示游戏状态*/
    int state;
    public static final int START=0;  //开始
    public static final int RUNNING=1;  //正在运行
    public static final int OVER=2;  //结束
    /*游戏界面*/
    public Game() throws IOException {
        Background = ImageIO.read(getClass().getResource("/resources/bg.png"));
        startImage =ImageIO.read(getClass().getResource("/resources/start.png"));
        gameoverImage=ImageIO.read(getClass().getResource("/resources/gameover.png"));
        ground=new ground();
        column1=new column(1);
        column2=new column(2);
        bird= new bird();
        score=0;
        state=START;

    }
    public void paint(Graphics graphics) {
        graphics.drawImage(Background,0,0,null);
        graphics.drawImage(ground.image,ground.x,ground.y,null);
        graphics.drawImage(column1.image,column1.x-column1.width/2,column1.y-column1.height/2,null);
        graphics.drawImage(column2.image,column2.x-column2.width/2,column2.y-column2.height/2,null);
        Graphics2D graphics2D=(Graphics2D) graphics;
        graphics2D.rotate(-bird.alpha,bird.x,bird.y);
        graphics.drawImage(bird.image,bird.x-bird.width/2,bird.y-bird.height/2,null);
        graphics2D.rotate(bird.alpha,bird.x,bird.y);
        Font font=new Font(Font.SANS_SERIF,Font.BOLD,40);
        graphics.setFont(font);
        graphics.drawString(""+score,40,60);
        graphics.setColor(Color.white);
        graphics.drawString(""+score,40-3,60-3);
        switch (state){
            case START:
                graphics.drawImage(startImage,0,0,null);
                break;
            case OVER:
                graphics.drawImage(gameoverImage,0,0,null);
        }
    }
    public void action() throws InterruptedException, IOException {
        MouseListener listener=new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {
                    switch (state){
                        case START:
                            state=RUNNING;
                            break;
                        case  RUNNING:
                            bird.image= ImageIO.read(getClass().getResource("/resources/tule.png"));
                            bird.flappy();
                            break;
                        case OVER:
                            column1=new column(1);
                            column2=new column(2);
                            bird=new bird();
                            score=0;
                            state=START;
                            break;
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        };
        addMouseListener(listener);
        while (true){
            switch (state){
                case START:
                  //  bird.fly();
                    ground.step();
                    break;
                case  RUNNING:
                    ground.step();
                    column1.step();
                    column2.step();
                  //  bird.fly();
                    bird.step();
                    if (bird.x==column1.x||bird.x==column2.x){
                        score++;
                    }
                    if (bird.hit(ground)||bird.hit(column1)||bird.hit(column2)){
                        bird.image=ImageIO.read(getClass().getResource("/resources/kuku.png"));
                        state=OVER;
                    }
                    break;
            }
            repaint();
            Thread.sleep(1000/60);
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        JFrame frame=new JFrame();
        Game game=new Game();
        frame.add(game);
        frame.setSize(440,670);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.action();
    }
}
