package cn.tedu.shoot;
import java.util.Random;
import java.awt.image.BufferedImage;
/** 小蜜蜂 */
public class Bee extends FlyingObject implements Award{
	private static BufferedImage[] images; //图片数组
	static {
		images = new BufferedImage[5]; //5张图片
		for(int i=0;i<images.length;i++) {
			images[i] = loadImage("bee"+i+".png"); 
		}
	}
	
	private int xSpeed; //x坐标移动速度
	private int ySpeed; //y坐标移动速度
	private int awardType; //奖励类型(0或1)
	/** 构造方法 */
	public Bee(){
		super(60,50);
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random();
		awardType = rand.nextInt(2); //0或1之间随机生成
	}
	
	/** 重写step()移动 */
	public void step() {
		x += xSpeed;
		y += ySpeed;
		if(x<=0 || x>=World.WIDTH-this.width){
			xSpeed *= -1;
		}
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
	
	/**重写getAwardType()获取奖励类型*/
	public int getAwardType(){
		return awardType;
	}

}
















