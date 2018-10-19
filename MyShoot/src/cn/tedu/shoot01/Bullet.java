package cn.tedu.shoot01;

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
	
	/**重写step()移动*/
	public void step(){
		this.y -= speed;
	}
	
	/**重写getImage()获取图片*/
	public BufferedImage getImage(){
		if(isLife()){
			return image;
		}
		return null;
	}
	
	/**越界检测，返回true表示已经越界*/
	public boolean outOfBounds(){
		return this.y<=-this.height;
	}
}
