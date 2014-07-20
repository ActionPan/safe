package com.example.safe;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LocationMap extends Activity {
	
	LocationListener locationListener;
	String str = null, phone_number = null;
	Handler handler;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		intent = getIntent();
		phone_number = intent.getStringExtra("number");

		System.out.println("�ѽ�����λ����");

		myThread mythread = new myThread();

		mythread.start();

		handler = new firstHandler();
	}

	class myThread extends Thread {

		@Override
		public void run() {

			str = getLocationAdress(LocationMap.this);

			Message msg = handler.obtainMessage();
			msg.obj = str;
			handler.sendMessage(msg);

		}

	}

	private String getLocationAdress(Context context) {
		String Address = "";
		// ��ȡ��LocationManager����
		LocationManager locationManager = (LocationManager) context
				.getSystemService(context.LOCATION_SERVICE);
		// ����һ��Criteria����
		Criteria criteria = new Criteria();
		// ���ô��Ծ�ȷ��
		criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
		// �����Ƿ���Ҫ���غ�����Ϣ
		criteria.setAltitudeRequired(false);
		// �����Ƿ���Ҫ���ط�λ��Ϣ
		criteria.setBearingRequired(false);
		// �����Ƿ������ѷ���
		criteria.setCostAllowed(true);
		// ���õ������ĵȼ�
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		// �����Ƿ���Ҫ�����ٶ���Ϣ
		criteria.setSpeedRequired(false);
		// �������õ�Criteria���󣬻�ȡ����ϴ˱�׼��provider���� 41
		String currentProvider = locationManager
				.getBestProvider(criteria, true);

		Log.d("Location", "currentProvider: " + currentProvider);
		// ���ݵ�ǰprovider�����ȡ���һ��λ����Ϣ 44
		Location currentLocation = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		// ���λ����ϢΪnull�����������λ����Ϣ 46
		if (currentLocation == null) {
			//ע��һ�������Ե�λ�ø���
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			Log.d("Location", "currentLocation:" + currentLocation);
		}
		Log.d("Location", "currentLocation:" + currentLocation);

		// ֱ��������һ��λ����ϢΪֹ�����δ������һ��λ����Ϣ������ʾĬ�Ͼ�γ�� 50
		// ÿ��10���ȡһ��λ����Ϣ 51
		while (true) {
			// currentLocation = locationManager
			// .getLastKnownLocation(currentProvider);
			Log.d("Location", "while (true): " + currentLocation);

			if (currentLocation != null) {
				Log.d("Location", "Latitude: " + currentLocation.getLatitude());
				Log.d("Location", "location: " + currentLocation.getLongitude());
				break;
			} else {
				Log.d("Location", "Latitude: " + 0);
				Log.d("Location", "location: " + 0);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Log.e("Location", e.getMessage());
			}
		}
		// ������ַ����ʾ 69
		Geocoder geoCoder = new Geocoder(context);
		try {
			double latitude = currentLocation.getLatitude();
			double longitude = currentLocation.getLongitude();
			List<Address> list = geoCoder.getFromLocation(latitude, longitude,
					2);
			for (int i = 0; i < list.size(); i++) {
				Address address = list.get(i);

				for (int j = 0; i < address.getMaxAddressLineIndex(); i++) {
					Log.e("Location",
							"line:(" + j + ")" + address.getAddressLine(j));
					Address = address.getAddressLine(j);
					// builder.append(address.getAddressLine(i)).append("\n");
				}
				return Address;
				// Log.e("Location", "1:" + address.getCountryName());
				// Log.e("Location", "2:" + address.getAdminArea());
				// Log.e("Location", "3:" + address.getFeatureName());

			}
		} catch (IOException e) {
			// Toast.makeText(PositionActivity.this, e.getMessage(),
			// Toast.LENGTH_LONG).show();
		}
		return "��ȡʧ��";

	}

	class firstHandler extends Handler {
		public void handleMessage(Message msg) {
			String s = msg.obj.toString();

			SMS_Send map = new SMS_Send();
			map.sms_send(LocationMap.this, phone_number, "�����ѵ��ֻ���ǰλ����:" + s);

		}

	}

}
