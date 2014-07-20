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
	
	//�ο� http://blog.csdn.net/feng88724/article/details/6323544
	
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
		

		//����DevicePolicyManagerִ����Ļ�����������豸��������ࡣͨ��������ʵ����Ļ��������Ļ���ȵ��ڡ��������õȹ��ܡ�
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
					Toast.makeText(LockActivity.this, "�����ֻ����벻��Ϊ��",
							Toast.LENGTH_LONG).show();
				} else if (password.equals("")) {
					Toast.makeText(LockActivity.this, "�����ֻ�ָ���Ϊ��",
							Toast.LENGTH_LONG).show();
				} else if ((!password.equals(str1))&&(!password.equals(str2))) {
					Toast.makeText(LockActivity.this, "����ָ�����",
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

		if (policyManager.isAdminActive(componentName)) {// �ж��Ƿ���Ȩ��(�������豸������)
			policyManager.lockNow();//ֱ������
			android.os.Process.killProcess(android.os.Process.myPid());//ɱ������
		} else {

			activeManager();// �����豸��������ȡȨ��
		}
	}

	// �����
	public void Bind(View v) {
		if (componentName != null) {

			policyManager.removeActiveAdmin(componentName);

			activeManager();
		}
	}

	/** 
     * �����豸����Ȩ�� 
     * �ɹ�ִ�н���ʱ��DeviceAdminReceiver�е� onDisabled ����Ӧ 
     */   
	@Override
	protected void onResume() {//��д�˷��������ڵ�һ�μ����豸������֮��������Ļ

		if (policyManager != null && policyManager.isAdminActive(componentName)) {
			policyManager.lockNow();
			android.os.Process.killProcess(android.os.Process.myPid());//ɱ������
		}
		super.onResume();
	}

	/** 
     * �����豸����Ȩ�� 
     * �ɹ�ִ�м���ʱ��DeviceAdminReceiver�е� onEnabled ����Ӧ 
     */   
	private void activeManager() {
		// ʹ����ʽ��ͼ����ϵͳ����������ָ�����豸������

		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "һ������");
		startActivity(intent);
	}

}