package cn.tedu.shoot02;

import java.util.Random;
import java.awt.image.BufferedImage;

/**小蜜蜂*/
public class Bee extends FlyingObject implements Award{
	private static BufferedImage[] images;
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("bee"+i+".png");
		}
	}
	
	private int xSpeed;//x坐标移动速度
	private int ySpeed;//y坐标移动速度
	private int awardType;//奖励类型
	
	/**构造类型*/
	public Bee(){
		super(60,50);
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random();
		awardType = rand.nextInt(2);
	}
	
	/**重写*/
	public void step(){
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
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	/**重写outOfBounds()检查是否越界*/
	public boolean outOfBounds(){
		return this.y>=World.HEIGHT;
	}
	
	/**重写getAwardType()获取奖励类型*/
	public int getAwardType(){
		return awardType;
	}
}
