package cn.tedu.shoot01;

import java.awt.image.BufferedImage;
import java.util.Collection;

/**英雄机*/
public class Hero extends FlyingObject {
	private static BufferedImage[] images;
	
	static{
		images = new BufferedImage[2];
		images[0] = loadImage("hero0.png");
		images[1] = loadImage("hero1.png");
	}
	
	private int life;
	private int doubleFire;
	
	/**构造方法*/
	public Hero(){
		super(97,124,140,400);
		life = 3;
		doubleFire = 0;
	}
	
	/**英雄机随着鼠标移动*/
	public void moveTo(int x,int y){
		this.x = x-this.width/2;
		this.y = y-this.height/2;
	}
	
	/**重写step()移动*/
	public void step(){
		
	}
	
	int index = 0;
	/**重写getImage()获取图片*/
	public BufferedImage getImage(){
		if(isLife()){
			return images[index++%2];
		}
		return null;
	}
	
	/**生成子弹对象*/
	public Bullet[] shoot(){
		int xSpeed = this.width/4;//子弹的x坐标
		int ySpeed = 20;//子弹的y坐标
		if(doubleFire>0){
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+xSpeed,this.y-ySpeed);
			bs[1] = new Bullet(this.x+3*xSpeed,this.y-ySpeed);
			doubleFire -= 2;
			return bs;
		}else{
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x+2*xSpeed,this.y-ySpeed);
			return bs;
		}
	}
	
	/**英雄机增命*/
	public void addLife(){
		life++;
	}
	
	/**获取英雄机命数*/
	public int getLife(){
		return life;
	}
	
	/**英雄机减命*/
	public void subtractLife(){
		life--;
	}
	
	/**英雄机增火力*/
	public void addDoubleFire(){
		doubleFire += 40;
	}
	
	/**清空火力*/
	public void clearDoubleFire(){
		doubleFire = 0;
	}
}




