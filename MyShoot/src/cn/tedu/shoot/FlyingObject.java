package cn.tedu.shoot;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
/** 飞行物 */
public abstract class FlyingObject {
	public static final int LIFE = 0;//活着的
	public static final int DEAD = 1;//死了的
	public static final int REMOVE = 2;//删除的
	protected int state = LIFE;//当前状态（默认为活着的）
	
	protected int width;   //宽
	protected int height;  //高
	protected int x;       //x坐标
	protected int y;       //y坐标
	/** 专门给英雄机、天空、子弹提供的 */
	public FlyingObject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	/** 专门给小敌机、大敌机、小蜜蜂提供的 */
	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random(); //随机数对象
		x = rand.nextInt(World.WIDTH-width); //x:0到(400-小敌机宽)之内的随机数
		y = -this.height; //y:负的小敌机的高
	}
	
	/** 飞行物移动 */
	public abstract void step();
	
	/** 读取图片 */
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName)); //同包中读取图片        
			return img;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**获取图片*/
	public abstract BufferedImage getImage();
	
	/**判断对象是否是活着的*/
	public boolean isLife(){
		return state==LIFE;
	}
	
	/**判断对象是否是死了的*/
	public boolean isDead(){
		return state==DEAD;
	}
	
	/**判断对象是否是删除的*/
	public boolean isRemove(){
		return state==REMOVE;
	}
	
	/**画对象 g:画笔*/
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);
	}
	
	/**检测越界*/
	public boolean outOfBounds(){
		return this.y>=World.HEIGHT;
	}
	
	/**检测敌人与子弹/英雄机的碰撞，this：敌人，other：子弹/英雄机*/
	public boolean hit(FlyingObject other){
		int x1 = this.x-other.width;
		int x2 = this.x+this.width;
		int y1 = this.y-other.height;
		int y2 = this.y+this.height;
		return other.x>=x1 && other.x<=x2 && other.y>=y1 && other.y<=y2;
	}
	
	/**飞行物去死*/
	public void goDead(){
		state = DEAD;
	}
}























