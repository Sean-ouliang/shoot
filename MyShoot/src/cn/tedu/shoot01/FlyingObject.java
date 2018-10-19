package cn.tedu.shoot01;

import java.util.Random;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**飞行物*/
public abstract class FlyingObject {
	public static final int LIFE = 0;//活着的
	public static final int DEAD = 1;//死了的
	public static final int REMOVE = 2;//删除的
	protected int state = LIFE;//当前状态
	
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
	
	/**专门给小敌机、大敌机、小蜂蜜提供的*/
	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random();
		x = rand.nextInt(World.WIDTH-this.width);
		y = -height;
	}
	
	/**飞行物移动*/
	public abstract void step();
	
	/**读取图片*/
	public static BufferedImage loadImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("图片加载失败！");
		}
	}
	
	/**获取图片*/
	public abstract BufferedImage getImage();
	
	/**判断当前状态是否为活着的*/
	public boolean isLife(){
		return state==LIFE;
	}
	
	/**判断当前状态是否为死了的*/
	public boolean isDead(){
		return state==DEAD;
	}
	
	/**判断当前状态是否为删除的*/
	public boolean isRemove(){
		return state==REMOVE;
	}
	
	/**画笔*/
	public void paintObject(Graphics g){
		g.drawImage(this.getImage(),this.x,this.y,null);
	}
	
	/**
	 * 越界检测（小蜜蜂，小敌机，大敌机）
	 * 返回true表示已经越界
	 * @return
	 */
	public boolean outOfBounds(){
		return this.y>=World.HEIGHT;
	}
	
	/**
	 * 检测敌人与子弹/英雄机碰撞
	 * this:敌人，other:子弹/英雄机
	 * 返回true表示已经撞上
	 * @return
	 */
	public boolean hit(FlyingObject other){
		int x1 = this.x - other.width;
		int x2 = this.x + this.width;
		int y1 = this.y - other.height;
		int y2 = this.y + this.height;
		int x = other.x;
		int y = other.y;
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;
	}
	
	/**飞行物去死*/
	public void goDead(){
		state = DEAD;
	}
}









