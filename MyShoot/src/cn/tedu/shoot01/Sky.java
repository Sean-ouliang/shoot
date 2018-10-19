package cn.tedu.shoot01;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**���*/
public class Sky extends FlyingObject {
	private static BufferedImage image;
	
	static{
		image = loadImage("background.png");
	}
	
	private int y1;//��2��ͼ��y����
	private int speed;//�ƶ��ٶ�
	
	/**���췽��*/
	public Sky(){
		super(400,700,0,0);
		speed = 1;
		y1 = -700;
	}
	
	/**��дstep()�ƶ�*/
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
	
	/**��дgetImage()��ȡͼƬ*/
	public BufferedImage getImage(){
		return image;
	}
	
	/**��дpaintObject()����*/
	public void paintObject(Graphics g){
		g.drawImage(image,x,y,null);
		g.drawImage(image,x,y1,null);
	}
}








