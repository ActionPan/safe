package com.example.safe;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class TrackActivity extends Activity {

	private ImageButton track_back, track_settings;
	private EditText track_edit1, track_edit2;
	private Button track_btn;
	SharedPreferences spf;
	String password;
	String str;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.track_layout);

		track_back = (ImageButton) this.findViewById(R.id.track_back);
		track_settings = (ImageButton) this.findViewById(R.id.track_settings);
		track_edit1 = (EditText) this.findViewById(R.id.track_edit1);
		track_edit2 = (EditText) this.findViewById(R.id.track_edit2);
		track_btn = (Button) this.findViewById(R.id.track_btn);
		spf = getSharedPreferences("pass",
				Context.MODE_PRIVATE);
		password = spf.getString("pass", "");
		str="baojing*"+password;

		track_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TrackActivity.this,
						SecurityActivity.class);
				TrackActivity.this.startActivity(intent);
			}
		});

		track_settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TrackActivity.this,
						SettingsActivity.class);
				TrackActivity.this.startActivity(intent);
			}
		});
		track_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				if (track_edit1.getText().toString().equals("")) {
					Toast.makeText(TrackActivity.this, "·ÀµÁºÅÂë²»ÄÜÎª¿Õ!!",
							Toast.LENGTH_SHORT).show();
				}
				else if (track_edit2.getText().toString().equals("")) {
					Toast.makeText(TrackActivity.this, "·ÀµÁÖ¸Áî²»ÄÜÎª¿Õ!!",
							Toast.LENGTH_SHORT).show();
				} else if (!track_edit2.getText().toString().equals(str)) {
					Toast.makeText(TrackActivity.this, "·ÀµÁÖ¸Áî´íÎó",
							Toast.LENGTH_LONG).show();
				}
				else {
					String phone_num = track_edit1.getText().toString();
					String content = track_edit2.getText().toString();

					SMS_Send send = new SMS_Send();

					send.sms_send(TrackActivity.this, phone_num, content);

					track_edit1.setText("");
					track_edit2.setText("");
				}

			}
		});

	}
}
