package com.example.safe;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button loginButton, findPass;
	private EditText loginPass;
	SharedPreferences spf =null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		spf = getSharedPreferences("pass", MODE_PRIVATE);
		
		String pass = spf.getString("pass", "");
		if(pass != null&&!pass.equals(""))
		{
			setContentView(R.layout.login_layout);
		}else{
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent);
			return;
		}
		
		loginButton = (Button) this.findViewById(R.id.loginButton);
		findPass = (Button) this.findViewById(R.id.findPass);
		loginPass = (EditText)this.findViewById(R.id.loginPass);

		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pass = loginPass.getText().toString();
				String pass2 = spf.getString("pass", "");
				
				if(pass.equals(pass2)){
					//������ȷ��ת��ϵͳ��ҳ��
					Intent intent = new Intent(LoginActivity.this,SecurityActivity.class);
					LoginActivity.this.startActivity(intent);
					finish();
					
				}else{
					//��ʾ�������
					Toast.makeText(LoginActivity.this, "�������", Toast.LENGTH_SHORT).show();
					return;

				}

			}
		});
		findPass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,ForgetActivity.class);
				LoginActivity.this.startActivity(intent);
				finish();

			}
		});

	}

	// ���ε�����˳�
		boolean isExit = false;

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if (isExit) {
					finish();
				} else {
					Toast.makeText(this, "��������˳�", Toast.LENGTH_SHORT).show();
					isExit = true;
					// ��ʱ����3���ӵ�������ϴε��ʧЧ��
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							isExit = false;
						}
					}, 3000);
				}
			}
			return false;// ��ֹһ�ε���󣬵��÷������˳�
		}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
