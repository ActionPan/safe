package com.example.safe;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FxService extends Service {

	// 定义浮动窗口布局
	LinearLayout mFloatLayout;
	WindowManager.LayoutParams wmParams;
	// 创建浮动窗口设置布局参数的对象
	WindowManager mWindowManager;

	Button mFloatView;

	private static final String TAG = "FxService";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("开启了服务");
		createFloatView();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void createFloatView() {
		wmParams = new WindowManager.LayoutParams();
		// 获取的是WindowManagerImpl.CompatModeWrapper
		mWindowManager = (WindowManager) getApplication().getSystemService(
				getApplication().WINDOW_SERVICE);

		System.out.println("000000000000");

		// 设置window type
		wmParams.type = LayoutParams.TYPE_PHONE;

		System.out.println("1111111111");

		// 设置图片格式，效果为背景透明
		wmParams.format = PixelFormat.RGBA_8888;

		System.out.println("22222222222");

		// 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
		wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;

		System.out.println("3333333333");

		// 调整悬浮窗显示的停靠位置为左侧置顶
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;

		System.out.println("44444444444");

		// 以屏幕左上角为原点，设置x、y初始值，相对于gravity
		wmParams.x = 0;
		wmParams.y = 0;

		System.out.println("5555555555");

		// 设置悬浮窗口长宽数据
		// wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		// wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		System.out.println("66666666");

		LayoutInflater inflater = LayoutInflater.from(getApplication());
		System.out.println("77777777777");

		// 获取浮动窗口视图所在布局

		mFloatLayout = (LinearLayout) inflater
				.inflate(R.layout.fxservice, null);
		System.out.println("8888888");

		// 添加mFloatLayout
		mWindowManager.addView(mFloatLayout, wmParams);
		System.out.println("99999999");

		// 浮动窗口按钮
		mFloatView = (Button) mFloatLayout.findViewById(R.id.fxservice_button);

		System.out.println("101010101010");

		mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

		System.out.println("**************");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mFloatLayout != null) {
			// 移除悬浮窗口
			mWindowManager.removeView(mFloatLayout);
		}
	}

}