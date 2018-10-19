package cn.tedu.shoot02;

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
	private int awardType;//��������
	
	/**��������*/
	public Bee(){
		super(60,50);
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random();
		awardType = rand.nextInt(2);
	}
	
	/**��д*/
	public void step(){
		x += xSpeed;
		y += ySpeed;
		if(x<=0 || x>=World.WIDTH-this.width){
			xSpeed *= -1;
		}
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
	
	/**��дoutOfBounds()����Ƿ�Խ��*/
	public boolean outOfBounds(){
		return this.y>=World.HEIGHT;
	}
	
	/**��дgetAwardType()��ȡ��������*/
	public int getAwardType(){
		return awardType;
	}
}
