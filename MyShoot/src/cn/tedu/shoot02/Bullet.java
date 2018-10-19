package cn.tedu.shoot02;

import java.awt.image.BufferedImage;

/**子弹*/
public class Bullet extends FlyingObject {
	private static BufferedImage image;
	static{
		image = loadImage("bullet.png");
	}
	
	private int speed;//移动速度
	
	/**构造方法*/
	public Bullet(int x,int y){
		super(8,14,x,y);
		speed = 3;
	}
	
	/**重写*/
	public void step(){
		y -= speed;
	}
	
	/**重写getImage()获取图片*/
	public BufferedImage getImage(){
		if(isLife()){
			return image;
		}else if(isDead()){
			state = REMOVE;
		}
		return null;
	}
	
	/**重写outOfBounds()检查是否越界*/
	public boolean outOfBounds(){
		return this.y<=-this.height;
	}
}
