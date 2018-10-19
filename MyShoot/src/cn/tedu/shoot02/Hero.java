package cn.tedu.shoot02;

import java.awt.image.BufferedImage;

/**英雄机*/
public class Hero extends FlyingObject {
	private static BufferedImage[] images;
	static{
		images = new BufferedImage[2];
		images[0] = loadImage("hero0.png");
		images[1] = loadImage("hero1.png");
	}
	
	private int life;//命数
	private int doubleFire;//火力值

	/**构造方法*/
	public Hero(){
		super(97,124,140,400);
		life = 3;
		doubleFire = 0;
	}
	
	/**英雄机随着鼠标移动，x:鼠标的x坐标，y:鼠标的y坐标*/
	public void moveTo(int x,int y){
		this.x = x-this.width/2;
		this.y = y-this.height/2;
	}
	
	/**重写*/
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
	
	/**生成子弹*/
	public Bullet[] shoot(){
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire>0){
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep);
			doubleFire -= 2;//控制双倍火力发射次数，每发射一次火力值减2
			return bs;
		}else{
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x+2*xStep,this.y-yStep);
			return bs;
		}
	}
	
	/**重写outOfBounds()检查是否越界*/
	public boolean outOfBounds(){
		return false;//永不越界
	}
	
	/**英雄机增命*/
	public void addLife(){
		life++;
	}
	
	/**获取英雄机的命数*/
	public int getLife(){
		return life;
	}
	
	/**英雄机减命*/
	public void subtractLife(){
		life--;
	}
	
	/**英雄机清空火力值*/
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	
	/**英雄机增火力*/
	public void addDoubleFire(){
		doubleFire += 40;
	}
}






