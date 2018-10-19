package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** ��л� */
public class BigAirplane extends FlyingObject implements Enemy{
	private static BufferedImage[] images; //ͼƬ����
	static {
		images = new BufferedImage[5]; //5��ͼƬ
		for(int i=0;i<images.length;i++) {
			images[i] = loadImage("bigplane"+i+".png"); 
		}
	}
	
	private int speed; //�ƶ��ٶ�
	/** ���췽�� */
	public BigAirplane(){
		super(69,99);
		speed = 2;
	}
	
	/** ��дstep()�ƶ� */
	public void step() {
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
				state=REMOVE;
			}
			return img;
		}
		return null;
	}
	
	/**��дgetScore()�÷�*/
	public int getScore(){
		return 3;
	}
}













