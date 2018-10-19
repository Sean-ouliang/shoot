package cn.tedu.shoot02;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**天空*/
public class Sky extends FlyingObject{
	private static BufferedImage image;
	static{
		image = loadImage("background.png");
	}
	
	private int y1;//另外一张图片的y坐标
	private int speed;//移动速度
	
	/**构造方法*/
	public Sky(){
		super(400,700,0,0);
		y1 = -700;
		speed = 1;
	}
	
	/**重写*/
	public void step(){
		y += speed;
		y1 += speed;
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
	
	/**重写paintObject()*/
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
	
	/**重写outOfBounds()检查是否越界*/
	public boolean outOfBounds(){
		return false;//永不越界
	}
}












