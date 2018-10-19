package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 大敌机 */
public class BigAirplane extends FlyingObject implements Enemy{
	private static BufferedImage[] images; //图片数组
	static {
		images = new BufferedImage[5]; //5张图片
		for(int i=0;i<images.length;i++) {
			images[i] = loadImage("bigplane"+i+".png"); 
		}
	}
	
	private int speed; //移动速度
	/** 构造方法 */
	public BigAirplane(){
		super(69,99);
		speed = 2;
	}
	
	/** 重写step()移动 */
	public void step() {
		y += speed;
	}
	
	int index = 1;
	/**重写getImage()获取图片*/
	public BufferedImage getImage(){
		if(isLife()){
			return images[0];
		}else if(isDead()){
			BufferedImage img = images[index++];
			if(index==images.length){
				state=REMOVE;
			}
			return img;
		}
		return null;
	}
	
	/**重写getScore()得分*/
	public int getScore(){
		return 3;
	}
}














