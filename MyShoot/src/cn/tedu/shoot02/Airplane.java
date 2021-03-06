package cn.tedu.shoot02;

import java.awt.image.BufferedImage;

/**小敌机*/
public class Airplane extends FlyingObject implements Enemy{
	private static BufferedImage[] images;
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("airplane"+i+".png");
		}
	}
	
	private int speed;//移动速度
	
	/**构造方法*/
	public Airplane(){
		super(49,36);
		speed = 2;
	}
	
	/**重写*/
	public void step(){
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
	
	/**重写getScore()获取得分*/
	public int getScore(){
		return 1;
	}
}
