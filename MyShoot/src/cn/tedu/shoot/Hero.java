package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 英雄机 */
public class Hero extends FlyingObject{
	private static BufferedImage[] images; //图片数组
	static {
		images = new BufferedImage[2]; //2张图片
		images[0] = loadImage("hero0.png");
		images[1] = loadImage("hero1.png");
	}
	
	private int life; //命
	private int doubleFire; //火力值
	/** 构造方法 */
	public Hero(){
		super(97,124,140,400);
		life = 3;
		doubleFire = 0;
	}
	
	/** 英雄机随着鼠标移动  x/y:鼠标的x坐标/y坐标 */
	public void moveTo(int x,int y) {
		this.x = x-this.width/2;
		this.y = y-this.height/2;
	}
	
	/** 重写step()移动 */
	public void step() {
		
	}
	
	int index = 0;
	/**重写getImage()获取图片*/
	public BufferedImage getImage(){//每10毫秒走一次
		if(isLife()){
			return images[index++%2];
		}
		return null;
		/*
		 *                    index=0;
		 * 10ms 返回images[0]  index=1;
		 * 20ms 返回images[1]  index=2;
		 * 30ms 返回images[0]  index=3;
		 * 40ms 返回images[1]  index=4;
		 * 50ms 返回images[0]  index=5;
		 */
	}
	
	/**生成子弹对象(英雄机发射子弹)*/
	public Bullet[] shoot(){
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire>0){
			Bullet[] bs = new Bullet[3];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+2*xStep,this.y-yStep);
			bs[2] = new Bullet(this.x+3*xStep,this.y-yStep);
			doubleFire -= 2;
			return bs;
		}else{
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep);
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
	
	/**清空火力值*/
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	
	/**英雄机增火力值*/
	public void addDoubleFire(){
		doubleFire += 40;
	}
}















