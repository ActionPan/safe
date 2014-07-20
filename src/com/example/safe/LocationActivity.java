package com.example.safe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LocationActivity extends Activity {
	
	private ImageButton location_back, locction_settings;
	private Button dingwei;
	private EditText phone_number1,phone_pass;
	SharedPreferences spf;
	String password;
	LocationListener locationListener;
	String str = null, str3,phone_number = null;
	Handler handler;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.location_layout);
		
		phone_number1 = (EditText) this.findViewById(R.id.phone_number);
		phone_pass = (EditText) this.findViewById(R.id.phone_pass);
		location_back = (ImageButton) this.findViewById(R.id.location_back);
		locction_settings = (ImageButton) this.findViewById(R.id.location_settings);
		dingwei = (Button) this.findViewById(R.id.dingwei);
		
		spf = getSharedPreferences("pass",
				Context.MODE_PRIVATE);

		password = spf.getString("pass", "");
		
		str3 = "dingwei*" + password;
		
		dingwei.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				String number = phone_number1.getText().toString();
				String password = phone_pass.getText().toString();

				if (number.equals("")) {
					Toast.makeText(LocationActivity.this, "被盗手机号码不能为空",
							Toast.LENGTH_LONG).show();
				} else if (password.equals("")) {
					Toast.makeText(LocationActivity.this, "防盗指令不能为空",
							Toast.LENGTH_LONG).show();
				} else if (!password.equals(str3)) {
					Toast.makeText(LocationActivity.this, "防盗指令错误",
							Toast.LENGTH_LONG).show();
				} else {
					SMS_Send send = new SMS_Send();

					send.sms_send(LocationActivity.this, number, password);

					phone_number1.setText("");
					phone_pass.setText("");
				}
			}
		});
		location_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LocationActivity.this,
						ChangeActivity.class);
				LocationActivity.this.startActivity(intent);
			}
		});

		locction_settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LocationActivity.this,
						ChangeActivity.class);
				LocationActivity.this.startActivity(intent);
			}
		});
	}

}
