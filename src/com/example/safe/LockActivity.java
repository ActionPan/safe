package com.example.safe;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LockActivity extends Activity {
	
	//参考 http://blog.csdn.net/feng88724/article/details/6323544
	
	private ImageButton lock_back, lock_settings;
	private Button lock;
	private EditText phone_number,phone_pass;
	private DevicePolicyManager policyManager;
	private ComponentName componentName;
	SharedPreferences spf;
	String password;
	String str1, str2,str3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lock_layout);
		
		spf = getSharedPreferences("pass",
				Context.MODE_PRIVATE);

		password = spf.getString("pass", "");

		str1 = "suoping*" + password;
		str2 = "suoping2*" + password;
		

		//利用DevicePolicyManager执行屏幕锁定。这是设备管理的主类。通过它可以实现屏幕锁定、屏幕亮度调节、出厂设置等功能。
		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(this, LockReceiver.class);

		LockScreen();
		
		lock_back = (ImageButton) this.findViewById(R.id.lock_back);
		lock_settings = (ImageButton) this.findViewById(R.id.lock_settings);
		lock = (Button) this.findViewById(R.id.lock);
		phone_number = (EditText) this.findViewById(R.id.phone_number);
		phone_pass = (EditText) this.findViewById(R.id.phone_pass);
		lock_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LockActivity.this,
						ChangeActivity.class);
				LockActivity.this.startActivity(intent);
			}
		});

		lock_settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LockActivity.this,
						ChangeActivity.class);
				LockActivity.this.startActivity(intent);
			}
		});
		
		lock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String number = phone_number.getText().toString();
				String password = phone_pass.getText().toString();

				if (number.equals("")) {
					Toast.makeText(LockActivity.this, "被盗手机号码不能为空",
							Toast.LENGTH_LONG).show();
				} else if (password.equals("")) {
					Toast.makeText(LockActivity.this, "被盗手机指令不能为空",
							Toast.LENGTH_LONG).show();
				} else if ((!password.equals(str1))&&(!password.equals(str2))) {
					Toast.makeText(LockActivity.this, "防盗指令错误",
							Toast.LENGTH_LONG).show();
				} else {
					SMS_Send send = new SMS_Send();

					send.sms_send(LockActivity.this, number, password);

					phone_number.setText("");
					phone_pass.setText("");
				}
			}
			
		});
	}
	public void LockScreen() {

		if (policyManager.isAdminActive(componentName)) {// 判断是否有权限(激活了设备管理器)
			policyManager.lockNow();//直接锁屏
			android.os.Process.killProcess(android.os.Process.myPid());//杀掉进程
		} else {

			activeManager();// 激活设备管理器获取权限
		}
	}

	// 解除绑定
	public void Bind(View v) {
		if (componentName != null) {

			policyManager.removeActiveAdmin(componentName);

			activeManager();
		}
	}

	/** 
     * 禁用设备管理权限 
     * 成功执行禁用时，DeviceAdminReceiver中的 onDisabled 会响应 
     */   
	@Override
	protected void onResume() {//重写此方法用来在第一次激活设备管理器之后锁定屏幕

		if (policyManager != null && policyManager.isAdminActive(componentName)) {
			policyManager.lockNow();
			android.os.Process.killProcess(android.os.Process.myPid());//杀掉进程
		}
		super.onResume();
	}

	/** 
     * 激活设备管理权限 
     * 成功执行激活时，DeviceAdminReceiver中的 onEnabled 会响应 
     */   
	private void activeManager() {
		// 使用隐式意图调用系统方法来激活指定的设备管理器

		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁定");
		startActivity(intent);
	}

}