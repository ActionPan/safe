package com.example.safe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	private ImageButton settingsBack;
	private Button settings_contact_add, settings_ok, settings_cancel;
	private EditText settings_num = null;
	private ListView listView;
	private Button sendMsg = null;
	String number = null, phonename = null;
	
	private static final int PICK_CONTACT = 3;

	ArrayList<HashMap<String, Object>> listItem;
	SharedPreferences spf = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		init();
		settingsBack = (ImageButton) this.findViewById(R.id.settingsBack);
		settingsBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingsActivity.this,
						SecurityActivity.class);
				SettingsActivity.this.startActivity(intent);
				finish();
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		listView = (ListView) this.findViewById(R.id.listView1);

		listItem = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("title", "监控SIM卡更换");
				map.put("image", R.drawable.setting_notify_on);
				listItem.add(map);
			} else if (i == 1) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("title", "修改安全号码");
				map.put("image", R.drawable.arrow_right);
				listItem.add(map);
			} else if (i == 2) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("title", "修改防盗密码");
				map.put("image", R.drawable.arrow_right);
				listItem.add(map);
			}
		}
		SimpleAdapter adapter = new SimpleAdapter(this, listItem,
				R.layout.settings_list_layout,
				new String[] { "title", "image" }, new int[] {
						R.id.settings_title, R.id.settings_image });
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					

				} else if (arg2 == 1) {
					Dialog dialog = new SettingsDialog(SettingsActivity.this);
					dialog.show();
				} else if (arg2 == 2) {
					Dialog dialog = new SettingsDialog1(SettingsActivity.this);
					dialog.show();
				}
			}
		});

	}

	class SettingsDialog extends Dialog {

		public SettingsDialog(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.setContentView(R.layout.setting_num_dialog);

			settings_contact_add = (Button) this
					.findViewById(R.id.setting_num_add);
			settings_ok = (Button) this.findViewById(R.id.setting_num_btn_ok);
			settings_cancel = (Button) this
					.findViewById(R.id.setting_num_btn_cancel);
			settings_num = (EditText) this.findViewById(R.id.setting_num_et);

			settings_contact_add.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_PICK);
					intent.setData(ContactsContract.Contacts.CONTENT_URI);
					startActivityForResult(intent, 2);
				}
			});

			settings_ok.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

//					String number = settings_num.getText().toString();
//					sendMsg(number);
//					Toast.makeText(SettingsActivity.this, "短信发送完成",
//							Toast.LENGTH_LONG).show();
//					finish();
					String phone = number;
					String content = "尊敬的" + phonename
							+ ",您的手机号码将设置安全号码";

					SMS_Send send = new SMS_Send();

					send.sms_send(SettingsActivity.this, number,
							content);
				}
			});

			settings_cancel.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismiss();

				}
			});

		}
	}

	class SettingsDialog1 extends Dialog {
		Button btn_ok, btn_cancel;
		EditText et_pass, et_pass1, et_pass2;

		public SettingsDialog1(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.setContentView(R.layout.setting_pass_dialog);

			btn_ok = (Button) this.findViewById(R.id.setting_pass_btn_ok);
			btn_cancel = (Button) this
					.findViewById(R.id.setting_pass_btn_cancel);

			et_pass = (EditText) this.findViewById(R.id.setting_pass_password);
			et_pass1 = (EditText) this
					.findViewById(R.id.setting_pass_password1);
			et_pass2 = (EditText) this
					.findViewById(R.id.setting_pass_password2);

			btn_ok.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String password = et_pass.getText().toString();
					String password1 = et_pass1.getText().toString();
					String password2 = et_pass2.getText().toString();
					
					spf = getSharedPreferences("pass", MODE_PRIVATE);
					String pass = spf.getString("pass", "");
					System.out.println("sfasf:"+pass);

					if (password.equals("") ||password1.equals("") || password2.equals("")) {

						Toast.makeText(SettingsActivity.this, "密码不能为空",
								Toast.LENGTH_LONG).show();
					} else if (password.length() != 6 || password1.length() != 6) {

						Toast.makeText(SettingsActivity.this, "密码必须为6位",
								Toast.LENGTH_LONG).show();

					} else if (!password1.equals(password2)) {

						Toast.makeText(SettingsActivity.this, "两次密码不一致",
								Toast.LENGTH_LONG).show();
					} else {
						if (pass.equals(password)) {
							Editor editor = spf.edit();
							editor.putString("pass", password1);

							boolean b = editor.commit();
							if (b) {
								Toast.makeText(SettingsActivity.this, "修改成功!",
										Toast.LENGTH_LONG).show();
								dismiss();
							} else if (!b) {
								Toast.makeText(SettingsActivity.this, "修改失败!",
										Toast.LENGTH_LONG).show();
							}
						}
					}
				}
			});
			btn_cancel.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismiss();

				}
			});

		}
	}

//	/**
//	 * 发送短信
//	 * 
//	 * @param number
//	 * 
//	 */
//	private void sendMsg(String number) {
//		String strSms = "你好吗?";
//
//		SmsManager smsManager = SmsManager.getDefault();
//		PendingIntent sentIntent = PendingIntent.getBroadcast(
//				SettingsActivity.this, 0, new Intent(), 0);
//		if (strSms.length() > 70) {
//			List<String> msgs = smsManager.divideMessage(strSms);
//			for (String msg : msgs) {
//				/*
//				 * 发送短信
//				 * 
//				 * smsManager.sendTextMessage(destinationAddress, scAddress,
//				 * text, sentIntent, deliveryIntent) --
//				 * destinationAddress：目标电话号码 -- scAddress：短信中心号码，测试可以不填 -- text:
//				 * 短信内容 -- sentIntent：发送 -->中国移动 --> 中国移动发送失败 --> 返回发送成功或失败信号
//				 * --> 后续处理 即，这个意图包装了短信发送状态的信息 -- deliveryIntent： 发送 -->中国移动 -->
//				 * 中国移动发送成功 --> 返回对方是否收到这个信息 --> 后续处理
//				 * 即：这个意图包装了短信是否被对方收到的状态信息（供应商已经发送成功，但是对方没有收到）。
//				 */
//				smsManager.sendTextMessage(number, null, msg, sentIntent, null);
//			}
//		} else {
//			smsManager.sendTextMessage(number, null, strSms, sentIntent, null);
//		}
//
//	}

	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (2):
//			Log.d(TAG, "onActivityResult PICK_CONTACT");
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				ContentResolver c = getContentResolver();
				Cursor cursor = c.query(contactData, null, null, null, null);
				if (cursor.moveToFirst()) {

					String[] PHONES_PROJECTION = new String[] { "_id",
							"display_name", "data1", "data3" };
					String contactId = cursor.getString(cursor
							.getColumnIndex(PhoneLookup._ID));

					// 获取电话
					Cursor phoneCursor = c.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							PHONES_PROJECTION,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ "=" + contactId, null, null);
					// name type ..

                   String phoneNubmer="";
                   String name=null;
                   while (phoneCursor.moveToNext()) {  
                	   phoneNubmer= phoneCursor.getString(2);  
                                               
 
                   }  
                   
//                   Log.i(TAG, name+" "+phoneNubmer);
                   
					settings_num.setText(phoneNubmer);
					Editor editor = spf.edit();

					editor.putString("SMS_ADDRESS", phoneNubmer);
					boolean b = editor.commit();
					System.out.println("phoneNubmer: " + phoneNubmer);
                }  
            }  
       }  
	
	}


}
