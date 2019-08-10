package com.pix.anim.project.guagua;

import android.content.Context;
import android.graphics.Bitmap;

import com.pix.anim.bean.GuaGuaAnimView;


/**
 * 获取动画工厂类，可以获取座驾动画和礼物动画
 * @author lixianfeng
 *
 */
public class GuaGuaAnimFactory {

	private static Integer[] drivers = { 13015, 13019, 13020, 13021, 13022, 13034, 13035,
			13500, 13501, 13502, 13503, 13504, 13506, 13507, 13509, 13068,
			13070, 13071, 13072, 13038, 13039, 13040, 13041, 13042, };
	
	/**
	 * 获取座驾动画
	 * 
	 * @param context
	 * @param id
	 * @return 根据id获取相应座驾动画，如果不存在id对应的动画则为null
	 */
	public static GuaGuaAnimView getDriverAnimView(Context context, int id) {
		GuaGuaAnimView animView = null;
		switch (id) {
		case 13015:
			// carname="幽灵战警车";
		case 13019:
			// carname="蓝色幽灵.改";
		case 13020:
			// carname="青之焰.改";
		case 13021:
			// carname="速度之星.改";
		case 13022:
			// carname="暗夜骑士.改";
		case 13034:
			// carname="爆裂天使";
		case 13035:
			// carname="王道霸主";
		case 13500:
			// carname="保时捷918";
		case 13501:
			// carname="奇瑞QQme";
		case 13502:
			// carname="奥迪A4L";
		case 13503:
			// carname="红旗-盛世";
		case 13504:
			// carname="宾利-慕尚";
		case 13506:
			// carname="奔驰S600L";
		case 13507:
			// carname="宝马535i";
		case 13509:
			// carname="奔驰C200";
		case 13068:
			// carname="别克-凯越";
		case 13070:
			// carname="大众-途锐V6";
		case 13071:
			// carname="大众-帕萨特";
		case 13072:
			// carname="法拉利F12";
		case 13038:
			// carname="大斗挖掘机
		case 13039:
			// carname="儿童三轮车";
			animView = new LandDriverView(context, id);
			break;
		case 13040:
			// carname="驴头扫把";
		case 13041:
			// carname="神秘飞碟";
		case 13042:
			// carname="探险火箭";
			animView = new FlyDriverView(context, id);
			break;
		default:
			animView = null;
		}
		return animView;
	}

	/**
	 * 获取单个礼物动画
	 * @param context
	 * @param bitmap
	 * @return
	 */
	public static GuaGuaAnimView getSingleGiftView(Context context, Bitmap bitmap){
		return new SingleGiftView(context, bitmap);
	}
	
	/**
	 * 获取座驾id数组（int类型）
	 * @return
	 */
	public static Integer[] getDriverArray(){
		return drivers;
	}
}
