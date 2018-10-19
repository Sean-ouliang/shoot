package cn.tedu.shoot02;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**���*/
public class Sky extends FlyingObject{
	private static BufferedImage image;
	static{
		image = loadImage("background.png");
	}
	
	private int y1;//����һ��ͼƬ��y����
	private int speed;//�ƶ��ٶ�
	
	/**���췽��*/
	public Sky(){
		super(400,700,0,0);
		y1 = -700;
		speed = 1;
	}
	
	/**��д*/
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
	
	/**��дgetImage()��ȡͼƬ*/
	public BufferedImage getImage(){
		return image;
	}
	
	/**��дpaintObject()*/
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
	
	/**��дoutOfBounds()����Ƿ�Խ��*/
	public boolean outOfBounds(){
		return false;//����Խ��
	}
}












