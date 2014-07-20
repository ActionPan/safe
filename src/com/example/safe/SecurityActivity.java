package com.example.safe;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SecurityActivity extends Activity {

	private ImageButton settings;
	private Button btn;
	private ImageView image;
	private TextView title, content;
	private ListView listView;
	ArrayList<HashMap<String, Object>> listItem;
	SharedPreferences spf = null;
	String key1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.security_layout);
		
		spf = getSharedPreferences("pass", MODE_PRIVATE);
		key1 = spf.getString("key","");
		
		System.out.println("key: " + key1);
		init();
		
	
		
		if (key1.equals("0")) {
			title.setText(R.string.main_title1);
			title.setTextColor(Color.parseColor("#ff0000"));
			content.setText(R.string.main_content1);
			btn.setText(R.string.main_btn1);
			image.setImageResource(R.drawable.danger);
		}
		if (key1.equals("1")) {
			title.setText(R.string.main_title2);
			title.setTextColor(Color.parseColor("#1c7500"));
			content.setText(R.string.main_content2);
			btn.setText(R.string.main_btn2);
			image.setImageResource(R.drawable.duigou);
		}

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Editor editor = spf.edit();
				if (key1.equals("1")) {
					editor.putString("key", "0");
					editor.commit();
					key1 = spf.getString("key", "");
					System.out.println("key11: " + key1);
					title.setText(R.string.main_title1);
					title.setTextColor(Color.parseColor("#ff0000"));
					content.setText(R.string.main_content1);
					btn.setText(R.string.main_btn1);
					image.setImageResource(R.drawable.danger);

				} else if (key1.equals("0")) {
					editor.putString("key", "1");
					editor.commit();
					key1 = spf.getString("key", "");
					System.out.println("key22: " + key1);
					title.setText(R.string.main_title2);
					title.setTextColor(Color.parseColor("#1c7500"));
					content.setText(R.string.main_content2);
					btn.setText(R.string.main_btn2);
					image.setImageResource(R.drawable.duigou);
				}
			}
		});

	}

	private void init() {
		// TODO Auto-generated method stub
		btn = (Button) this.findViewById(R.id.main_btn_open);
		settings = (ImageButton) this.findViewById(R.id.settings);
		image = (ImageView) this.findViewById(R.id.main_imgview);
		title = (TextView) this.findViewById(R.id.main_tx_title);
		content = (TextView) this.findViewById(R.id.main_tx_content);
		listView = (ListView) this.findViewById(R.id.listView1);

		listItem = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < 3; i++) {
			if (i == 0) {

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ItemImage", R.drawable.sim);
				map.put("ItemTitle", "SIM卡更换通知");
				map.put("ItemContent", "手机换卡后发送短信至紧急联系人号码");
				listItem.add(map);
			} else if (i == 1) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ItemImage", R.drawable.shanchushuju);
				map.put("ItemTitle", "删除数据");
				map.put("ItemContent", "删除被盗手机电话本、通话记录、短彩信以及隐私数据");
				listItem.add(map);

			} else if (i == 2) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ItemImage", R.drawable.baojing);
				map.put("ItemTitle", "报警音追踪");
				map.put("ItemContent", "发送指令至被盗手机，响报警器提示音！追踪手机");
				listItem.add(map);
			}

		}
		SimpleAdapter adapter = new SimpleAdapter(this, listItem,
				R.layout.security_list_layout, new String[] { "ItemImage",
						"ItemTitle", "ItemContent" }, new int[] { R.id.image,
						R.id.title, R.id.content });
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				if (arg2 == 0) {
					Intent intent = new Intent(SecurityActivity.this,
							ChangeActivity.class);
					SecurityActivity.this.startActivity(intent);

				} else if (arg2 == 1) {
					Intent intent = new Intent(SecurityActivity.this,
							DeleteActivity.class);
					SecurityActivity.this.startActivity(intent);

				} else if (arg2 == 2) {
					Intent intent = new Intent(SecurityActivity.this,
							TrackActivity.class);
					SecurityActivity.this.startActivity(intent);

				}

			}
		});

		settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SecurityActivity.this,
						SettingsActivity.class);
				SecurityActivity.this.startActivity(intent);
			}
		});

	}
}
