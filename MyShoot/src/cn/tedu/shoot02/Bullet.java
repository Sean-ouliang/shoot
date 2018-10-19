package cn.tedu.shoot02;

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
	
	/**��д*/
	public void step(){
		y -= speed;
	}
	
	/**��дgetImage()��ȡͼƬ*/
	public BufferedImage getImage(){
		if(isLife()){
			return image;
		}else if(isDead()){
			state = REMOVE;
		}
		return null;
	}
	
	/**��дoutOfBounds()����Ƿ�Խ��*/
	public boolean outOfBounds(){
		return this.y<=-this.height;
	}
}
