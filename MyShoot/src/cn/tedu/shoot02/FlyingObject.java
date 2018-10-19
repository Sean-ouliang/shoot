package cn.tedu.shoot02;

import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**飞行物类*/
public abstract class FlyingObject {
	public static final int LIFE = 0;//活着的
	public static final int DEAD = 1;//死了的
	public static final int REMOVE = 2;//删除的
	protected int state = LIFE;//当前状态（默认活着的）
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	/**专门给子弹、英雄机、天空提供的*/
	public FlyingObject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	/**专门给小敌机、大敌机、小蜜蜂提供*/
	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random();
		x = rand.nextInt(World.WIDTH-width);
		y = -height;
	}
	
	/**飞行物移动*/
	abstract public void step();
	
	/**读取图片*/
	public static BufferedImage loadImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**获取图片*/
	public abstract BufferedImage getImage();
	
	/**判断对象是否是活着的*/
	public boolean isLife(){
		return state == LIFE;
	}
	
	/**判断对象是否是死了的*/
	public boolean isDead(){
		return state == DEAD;
	}
	
	/**判断对象是否是删除的*/
	public boolean isRemove(){
		return state == REMOVE;
	}
	
	/**画对象  g:画笔*/
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);
	}
	
	/**检查敌人是否越界*/
	public abstract boolean outOfBounds();
	
	/**检测敌人与子弹/英雄机的碰撞，this:敌人，other:子弹/英雄机*/
	public boolean hit(FlyingObject other){
		int x1 = this.x-other.width;
		int x2 = this.x+this.width;
		int y1 = this.y-other.height;
		int y2 = this.y+this.height;
		int x = other.x;
		int y = other.y;
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;
	}
	
	/**飞行物去死*/
	public void goDead(){
		state = REMOVE;
	}
}



