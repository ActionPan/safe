package com.example.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class ChangeActivity extends Activity {

	private Button phone_location, phone_lock;
	private ImageButton change_back, change_settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.change_layout);

		phone_lock = (Button) this.findViewById(R.id.change_lock);
		phone_location = (Button) this.findViewById(R.id.change_location);
		change_back = (ImageButton) this.findViewById(R.id.change_back);
		change_settings = (ImageButton) this.findViewById(R.id.change_settings);

		phone_lock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ChangeActivity.this,
						LockActivity.class);
				ChangeActivity.this.startActivity(intent);
			}
		});
		phone_location.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ChangeActivity.this,
						LocationActivity.class);
				ChangeActivity.this.startActivity(intent);

			}
		});
		change_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ChangeActivity.this,
						SecurityActivity.class);
				ChangeActivity.this.startActivity(intent);

			}
		});
		change_settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ChangeActivity.this,
						SettingsActivity.class);
				ChangeActivity.this.startActivity(intent);

			}
		});
	}

}
