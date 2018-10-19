package cn.tedu.shoot01;

import java.util.Random;
import java.awt.image.BufferedImage;

/**С�۷�*/
public class Bee extends FlyingObject implements Award{
	private static BufferedImage[] images;
	
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("bee"+i+".png");
		}
	}
	
	private int xSpeed;//x�����ƶ��ٶ�
	private int ySpeed;//y�����ƶ��ٶ�
	private int awardType;//��������(0��1)
	
	/**���췽��*/
	public Bee(){
		super(60,50);
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random();
		awardType = rand.nextInt(2);
	}
	
	/**��дstep()�ƶ�*/
	public void step(){
		this.y += ySpeed;
		if(this.x<=0){
			xSpeed = 1;
		}
		if(this.x>=400-this.width){
			xSpeed = -1;
		}
		this.x += xSpeed;
	}
	
	int index = 1;
	/**��дgetImage()��ȡͼƬ*/
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
	
	/**��ȡ��������*/
	public int getAwardType(){
		return awardType;
	}
}





