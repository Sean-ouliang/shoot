package cn.tedu.shoot;
/**奖励接口*/
public interface Award {
	public int DOUBLE_FIRE = 0;
	public int LIFE = 1;
	/**获取奖励类型（0或1）*/
	public int getAwardType();
}
