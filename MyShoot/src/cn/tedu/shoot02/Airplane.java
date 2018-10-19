package cn.tedu.shoot02;

import java.awt.image.BufferedImage;

/**С�л�*/
public class Airplane extends FlyingObject implements Enemy{
	private static BufferedImage[] images;
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("airplane"+i+".png");
		}
	}
	
	private int speed;//�ƶ��ٶ�
	
	/**���췽��*/
	public Airplane(){
		super(49,36);
		speed = 2;
	}
	
	/**��д*/
	public void step(){
		y += speed;
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
	
	/**��дgetScore()��ȡ�÷�*/
	public int getScore(){
		return 1;
	}
}