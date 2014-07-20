package com.example.safe;

import java.util.List;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMS_Send {
	public void sms_send(Context context, String phone_number,
			String phone_content) {

		SmsManager smsManager = SmsManager.getDefault();
		PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,
				new Intent(), 0);

		if (phone_content.length() >= 70) {
			// 短信字数大于70，自动分条
			List<String> ms = smsManager.divideMessage(phone_content);

			for (String str : ms) {
				/*
				 * 发送短信
				 * 
				 * smsManager.sendTextMessage(destinationAddress, scAddress,
				 * text, sentIntent, deliveryIntent) --
				 * destinationAddress：目标电话号码 -- scAddress：短信中心号码，测试可以不填 -- text:
				 * 短信内容 -- sentIntent：发送 -->中国移动 --> 中国移动发送失败 --> 返回发送成功或失败信号
				 * --> 后续处理 即，这个意图包装了短信发送状态的信息 -- deliveryIntent： 发送 -->中国移动 -->
				 * 中国移动发送成功 --> 返回对方是否收到这个信息 --> 后续处理
				 * 即：这个意图包装了短信是否被对方收到的状态信息（供应商已经发送成功，但是对方没有收到）。
				 */
				
				// 短信发送
				smsManager.sendTextMessage(phone_number, null, str, sentIntent,
						null);
			}
		} else {
			smsManager.sendTextMessage(phone_number, null, phone_content,
					sentIntent, null);
		}

		Toast.makeText(context, "短信发送成功！", Toast.LENGTH_LONG).show();
	}
}
