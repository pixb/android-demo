package com.pix.anim.particle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class FireWorkActivity extends Activity {

	/** Called when the activity is first created. */

	// EventListener mListener = new EventListener();

	static final String LOG_TAG = FireWorkActivity.class.getSimpleName();
	static int SCREEN_W = 720;// 当前窗口的大小
	static int SCREEN_H = 1280;

	FireworkView fireworkView;

	// get the current looper (from your Activity UI thread for instance

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		}
		fireworkView = new FireworkView(this);
		setContentView(fireworkView);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (fireworkView.isRunning()) {
			fireworkView.setRunning(false);
		}
	}
	
//	@Override
//	  public boolean onTouchEvent(MotionEvent event)
//	  {
//		Log.e("AABB", "event.getPointerCount()-->"+event.getPointerCount());
//	    return super.onTouchEvent(event);
//	  }
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event){
//		int action = event.getAction();
//		switch (action) {
//		case MotionEvent.ACTION_POINTER_1_DOWN:
//			showMessage("第一个手指按下");
//			break;
//		case MotionEvent.ACTION_POINTER_1_UP:
//			showMessage("第一个手指抬起");
//			break;
//		case MotionEvent.ACTION_POINTER_2_DOWN:
//			showMessage("第二个手指按下");
//			break;
//		case MotionEvent.ACTION_POINTER_2_UP:
//			showMessage("第二个手指抬起");
//			break;
//		case MotionEvent.ACTION_POINTER_3_DOWN:
//			showMessage("第三个手指按下");
//			break;
//		case MotionEvent.ACTION_POINTER_3_UP:
//			showMessage("第三个手指抬起");
//			break;
//		}
//		return true;
//
//	}
//
//	private void showMessage(String s) {
//		Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
//		toast.show();
//
//	}

}
