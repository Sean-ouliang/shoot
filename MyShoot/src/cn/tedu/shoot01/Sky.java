package cn.tedu.shoot01;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**天空*/
public class Sky extends FlyingObject {
	private static BufferedImage image;
	
	static{
		image = loadImage("background.png");
	}
	
	private int y1;//第2张图的y坐标
	private int speed;//移动速度
	
	/**构造方法*/
	public Sky(){
		super(400,700,0,0);
		speed = 1;
		y1 = -700;
	}
	
	/**重写step()移动*/
	public void step(){
		this.y += speed;
		this.y1 += speed;
		if(y>=World.HEIGHT){
			y = -World.HEIGHT;
		}
		if(y1>=World.HEIGHT){
			y1 = -World.HEIGHT;
		}
	}
	
	/**重写getImage()获取图片*/
	public BufferedImage getImage(){
		return image;
	}
	
	/**重写paintObject()画笔*/
	public void paintObject(Graphics g){
		g.drawImage(image,x,y,null);
		g.drawImage(image,x,y1,null);
	}
}








