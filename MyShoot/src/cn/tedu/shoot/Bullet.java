package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** �ӵ� */
public class Bullet extends FlyingObject {
	private static BufferedImage image; //ͼƬ
	static {
		image = loadImage("bullet.png");
	}
	
	private int speed; //�ƶ��ٶ�
	/** ���췽�� */
	public Bullet(int x,int y){
		super(8,14,x,y);
		speed = 3;
	}
	
	/** ��дstep()�ƶ� */
	public void step() {
		y -= speed;
	}
	
	/**��дgetImage()��ȡͼƬ*/
	public BufferedImage getImage(){
		if(isLife()){
			return image;
		}else if(isDead()){
			state=REMOVE;//����ǰ״̬�޸�Ϊɾ����
		}
		return null;
	}
	/**��дoutOfBounds()Խ����*/
	public boolean outOfBounds(){
		return this.y<=-this.height;
	}
	
}














