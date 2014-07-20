package com.example.safe;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.safe.R;
import com.example.safe.SuoPingActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract.DeletedContacts;
import android.telephony.SmsMessage;

public class SMS_BroadcastReceiver extends BroadcastReceiver {

	public static final String TAG = "ImiChatSMSReceiver";
	String phone_number = null;
	String phone_content = null;
	String time = null;
	MediaPlayer mp;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("监听的广播"+ intent.getAction());
		Bundle bundle = intent.getExtras();
		SharedPreferences spf = context.getSharedPreferences("pass",
				context.MODE_PRIVATE);
		String password = spf.getString("pass", "");
		
		SmsMessage sms = null;

			if (bundle != null) {
				Object[] smsObj = (Object[]) bundle.get("pdus");
				for (Object object : smsObj) {
					sms = SmsMessage.createFromPdu((byte[]) object);
					phone_number = sms.getOriginatingAddress();
					phone_content = sms.getMessageBody();
					Date date = new Date(sms.getTimestampMillis());
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					time = format.format(date);

					System.out.println("手机号码：" + phone_number);
					System.out.println("手机内容：" + phone_content);
					System.out.println("发送时间：" + time);
					System.out.println("防盗密码:" + password);

					String baojing = "baojing*" + password;
					String suoping = "suoping*" + password;
					String suoping2 = "suoping2*" + password;
					
					String dingwei = "dingwei*" + password;
					String shanchu = "shanchu*" + password;

					if (phone_content.equals(baojing)) {
						
						System.out.println("报警了");
						
						try {
							AudioManager mAudioManager =  (AudioManager)getSystemService(Context.AUDIO_SERVICE);
							// 调整音量
							mAudioManager.setMode(AudioManager.MODE_NORMAL);

							//音量最大音量
							mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
							//调整音量 到最高
							mAudioManager.adjustVolume(AudioManager.ADJUST_RAISE,0);

							mp = MediaPlayer
									.create(context, R.raw.baojingmusic);
							mp.start();
							mp.setLooping(true);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalStateException e) {
							e.printStackTrace();
						}
					} else if (phone_content.equals(suoping)) {
						System.out.println("手机锁屏了");
						
						Intent intent2 = new Intent(context,
								SuoPingActivity.class);
						intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent2);
					}else if (phone_content.equals(suoping2)) {

						System.out.println("手机锁屏了2");

						Intent intent2 = new Intent();
						intent2.setAction("com.suoping2");
						intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startService(intent2);

					} else if (phone_content.equals(dingwei)) {

						System.out.println("手机定位了");

						Intent intent2 = new Intent(context, LocationMap.class);
						intent2.putExtra("number", phone_number);
						intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent2);

					}else if (phone_content.equals(shanchu)) {

						System.out.println("数据删除了");

						DeleteActivity delete = new DeleteActivity();

					}
				}

			}
		}

	private AudioManager getSystemService(String audioService) {
		// TODO Auto-generated method stub
		return null;
	}
}
