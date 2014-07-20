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

		System.out.println("已进到定位代码");

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
		// 获取到LocationManager对象
		LocationManager locationManager = (LocationManager) context
				.getSystemService(context.LOCATION_SERVICE);
		// 创建一个Criteria对象
		Criteria criteria = new Criteria();
		// 设置粗略精确度
		criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
		// 设置是否需要返回海拔信息
		criteria.setAltitudeRequired(false);
		// 设置是否需要返回方位信息
		criteria.setBearingRequired(false);
		// 设置是否允许付费服务
		criteria.setCostAllowed(true);
		// 设置电量消耗等级
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		// 设置是否需要返回速度信息
		criteria.setSpeedRequired(false);
		// 根据设置的Criteria对象，获取最符合此标准的provider对象 41
		String currentProvider = locationManager
				.getBestProvider(criteria, true);

		Log.d("Location", "currentProvider: " + currentProvider);
		// 根据当前provider对象获取最后一次位置信息 44
		Location currentLocation = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		// 如果位置信息为null，则请求更新位置信息 46
		if (currentLocation == null) {
			//注册一个周期性的位置更新
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			Log.d("Location", "currentLocation:" + currentLocation);
		}
		Log.d("Location", "currentLocation:" + currentLocation);

		// 直到获得最后一次位置信息为止，如果未获得最后一次位置信息，则显示默认经纬度 50
		// 每隔10秒获取一次位置信息 51
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
		// 解析地址并显示 69
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
		return "获取失败";

	}

	class firstHandler extends Handler {
		public void handleMessage(Message msg) {
			String s = msg.obj.toString();

			SMS_Send map = new SMS_Send();
			map.sms_send(LocationMap.this, phone_number, "您好友的手机当前位置是:" + s);

		}

	}

}
