package cn.tedu.shoot01;

import java.awt.image.BufferedImage;

/**�ӵ�*/
public class Bullet extends FlyingObject {
	private static BufferedImage image;
	
	static{
		image = loadImage("bullet.png");
	}
	
	private int speed;//�ƶ��ٶ�
	
	/**���췽��*/
	public Bullet(int x,int y){
		super(8,14,x,y);
		speed = 3;
	}
	
	/**��дstep()�ƶ�*/
	public void step(){
		this.y -= speed;
	}
	
	/**��дgetImage()��ȡͼƬ*/
	public BufferedImage getImage(){
		if(isLife()){
			return image;
		}
		return null;
	}
	
	/**Խ���⣬����true��ʾ�Ѿ�Խ��*/
	public boolean outOfBounds(){
		return this.y<=-this.height;
	}
}
