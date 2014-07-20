package com.example.safe;

import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class DeleteActivity extends Activity {

	private ImageButton delete_back, delete_settings;
	private Button delete_btn_on;
	SharedPreferences spf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.delete_layout);

		delete_back = (ImageButton) this.findViewById(R.id.delete_back);
		delete_settings = (ImageButton) this.findViewById(R.id.delete_settings);
		delete_btn_on = (Button)this.findViewById(R.id.delete_btn_on);

		delete_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DeleteActivity.this,
						SecurityActivity.class);
				DeleteActivity.this.startActivity(intent);
			}
		});
		delete_settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DeleteActivity.this,
						SettingsActivity.class);
				DeleteActivity.this.startActivity(intent);
			}
		});
		delete_btn_on.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				spf = getSharedPreferences("pass",
						Context.MODE_PRIVATE);

//				password = spf.getString("pass", "");
//				
//				String password = "shanchu*" + password;
//
//				System.out.println(password);
//				if (number.getText().toString().equals("")) {
//					Toast.makeText(Remote_shanchu.this, "手机号码不能为空",
//							Toast.LENGTH_LONG).show();
//				} else if (xinxi.getText().toString().equals("")) {
//					Toast.makeText(Remote_shanchu.this, "短信指令不能为空",
//							Toast.LENGTH_LONG).show();
//				} else if (!xinxi.getText().toString().equals(password)) {
//					Toast.makeText(Remote_shanchu.this, "防盗密码错误",
//							Toast.LENGTH_LONG).show();
//				} else {
//					SMS_send sms =new SMS_send();
//					sms.sms_send(Remote_shanchu.this, number.getText().toString(), s+password);
//				}
//			}
//				
				delete();
			}
		});
		
		
	}
	
	public void delete() {
		
		Log.i("test", "delete...");
		// 删除联系人
		deleteContact();
		// 删除音频
		deleteAudio();
		// 删除视频
		deleteVideo();
		// 删除图片
		deleteImage();
	}

	private void deleteImage() {
		ContentResolver cr = getContentResolver();
		Cursor c = cr.query(
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { android.provider.MediaStore.Images.Media.DATA },
				null, null, null);
		if (c != null) {
			int count = c.getCount();
			for (int i = 0; i < count; i++) {
				c.moveToPosition(i);
				String path = c.getString(0);
				File f = new File(path);
				f.delete();
			}
		}
	}
	private void deleteVideo() {
		ContentResolver cr = getContentResolver();
		Cursor c = cr.query(
				android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { android.provider.MediaStore.Video.Media.DATA },
				null, null, null);
		if (c != null) {
			int count = c.getCount();
			for (int i = 0; i < count; i++) {
				c.moveToPosition(i);

				String path = c
						.getString(c.getColumnIndex(android.provider.MediaStore.Video.Media.DATA));
				File f = new File(path);
				f.delete();
			}
		}
	}
	private void deleteAudio() {
		ContentResolver cr = getContentResolver();
		Cursor c = cr.query(
				android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { android.provider.MediaStore.Audio.Media.DATA },
				null, null, null);
		if (c != null) {
			int count = c.getCount();
			for (int i = 0; i < count; i++) {
				//当前游标的位置
				c.moveToPosition(i);
				// 音频的实际路径
				String path = c.getString(0);
				File f = new File(path);
				f.delete();
			}
		}
	}

	private void deleteContact() {
		ContentResolver cr = getContentResolver();
		Uri deleteUri = ContactsContract.RawContacts.CONTENT_URI
				.buildUpon()
				.appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER,
						"true").build();
		// ContentResolver cr
		cr.delete(deleteUri, null, null);
	}


}
