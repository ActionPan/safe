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
			// ������������70���Զ�����
			List<String> ms = smsManager.divideMessage(phone_content);

			for (String str : ms) {
				/*
				 * ���Ͷ���
				 * 
				 * smsManager.sendTextMessage(destinationAddress, scAddress,
				 * text, sentIntent, deliveryIntent) --
				 * destinationAddress��Ŀ��绰���� -- scAddress���������ĺ��룬���Կ��Բ��� -- text:
				 * �������� -- sentIntent������ -->�й��ƶ� --> �й��ƶ�����ʧ�� --> ���ط��ͳɹ���ʧ���ź�
				 * --> �������� ���������ͼ��װ�˶��ŷ���״̬����Ϣ -- deliveryIntent�� ���� -->�й��ƶ� -->
				 * �й��ƶ����ͳɹ� --> ���ضԷ��Ƿ��յ������Ϣ --> ��������
				 * ���������ͼ��װ�˶����Ƿ񱻶Է��յ���״̬��Ϣ����Ӧ���Ѿ����ͳɹ������ǶԷ�û���յ�����
				 */
				
				// ���ŷ���
				smsManager.sendTextMessage(phone_number, null, str, sentIntent,
						null);
			}
		} else {
			smsManager.sendTextMessage(phone_number, null, phone_content,
					sentIntent, null);
		}

		Toast.makeText(context, "���ŷ��ͳɹ���", Toast.LENGTH_LONG).show();
	}
}
