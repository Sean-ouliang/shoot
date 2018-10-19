package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 子弹 */
public class Bullet extends FlyingObject {
	private static BufferedImage image; //图片
	static {
		image = loadImage("bullet.png");
	}
	
	private int speed; //移动速度
	/** 构造方法 */
	public Bullet(int x,int y){
		super(8,14,x,y);
		speed = 3;
	}
	
	/** 重写step()移动 */
	public void step() {
		y -= speed;
	}
	
	/**重写getImage()获取图片*/
	public BufferedImage getImage(){
		if(isLife()){
			return image;
		}else if(isDead()){
			state=REMOVE;//将当前状态修改为删除的
		}
		return null;
	}
	/**重写outOfBounds()越界检查*/
	public boolean outOfBounds(){
		return this.y<=-this.height;
	}
	
}














