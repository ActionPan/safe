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

	// ���帡�����ڲ���
	LinearLayout mFloatLayout;
	WindowManager.LayoutParams wmParams;
	// ���������������ò��ֲ����Ķ���
	WindowManager mWindowManager;

	Button mFloatView;

	private static final String TAG = "FxService";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("�����˷���");
		createFloatView();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void createFloatView() {
		wmParams = new WindowManager.LayoutParams();
		// ��ȡ����WindowManagerImpl.CompatModeWrapper
		mWindowManager = (WindowManager) getApplication().getSystemService(
				getApplication().WINDOW_SERVICE);

		System.out.println("000000000000");

		// ����window type
		wmParams.type = LayoutParams.TYPE_PHONE;

		System.out.println("1111111111");

		// ����ͼƬ��ʽ��Ч��Ϊ����͸��
		wmParams.format = PixelFormat.RGBA_8888;

		System.out.println("22222222222");

		// ���ø������ڲ��ɾ۽���ʵ�ֲ���������������������ɼ����ڵĲ�����
		wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;

		System.out.println("3333333333");

		// ������������ʾ��ͣ��λ��Ϊ����ö�
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;

		System.out.println("44444444444");

		// ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ�������gravity
		wmParams.x = 0;
		wmParams.y = 0;

		System.out.println("5555555555");

		// �����������ڳ�������
		// wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		// wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		System.out.println("66666666");

		LayoutInflater inflater = LayoutInflater.from(getApplication());
		System.out.println("77777777777");

		// ��ȡ����������ͼ���ڲ���

		mFloatLayout = (LinearLayout) inflater
				.inflate(R.layout.fxservice, null);
		System.out.println("8888888");

		// ���mFloatLayout
		mWindowManager.addView(mFloatLayout, wmParams);
		System.out.println("99999999");

		// �������ڰ�ť
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
			// �Ƴ���������
			mWindowManager.removeView(mFloatLayout);
		}
	}

}