package cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/** ��� */
public class Sky extends FlyingObject{
	private static BufferedImage image; //ͼƬ
	static {
		image = loadImage("background.png");
	}
	
	private int speed; //�ƶ��ٶ�
	private int y1;    //��2��ͼ��y����
	/** ���췽�� */
	public Sky(){
		super(400,700,0,0);
		speed = 1;
		y1 = -700;
	}
	
	/** ��дstep()�ƶ� */
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
	
	/**��дgetImage()��ȡͼƬ*/
	public BufferedImage getImage(){
		return image;//ֱ�ӷ���image
	}
	
	/**��дpaintObject*/
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
}




















