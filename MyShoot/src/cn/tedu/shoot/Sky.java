package cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/** 天空 */
public class Sky extends FlyingObject{
	private static BufferedImage image; //图片
	static {
		image = loadImage("background.png");
	}
	
	private int speed; //移动速度
	private int y1;    //第2张图的y坐标
	/** 构造方法 */
	public Sky(){
		super(400,700,0,0);
		speed = 1;
		y1 = -700;
	}
	
	/** 重写step()移动 */
	public void step() {
		y += speed;
		y1 += speed;
		if(y>=World.HEIGHT){
			y = -World.HEIGHT;
		}
		if(y1>=World.HEIGHT){
			y1 = -World.HEIGHT;
		}
	}
	
	/**重写getImage()获取图片*/
	public BufferedImage getImage(){
		return image;//直接返回image
	}
	
	/**重写paintObject*/
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
}




















