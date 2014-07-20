package com.example.safe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ForgetActivity extends Activity {
	
	private EditText et_question,et_answer,et_pass1,et_pass2;
	private Button btn;
	private ImageButton back;
	private SharedPreferences spf = null;
	String answer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forget_layout);
		
		spf = getSharedPreferences("pass.xml", MODE_PRIVATE);
		init();
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String pass1 = et_pass1.getText().toString();
				String pass2 = et_pass2.getText().toString();
				
				
				if (pass1.equals("")
						|| pass2.equals("")) {

					Toast.makeText(ForgetActivity.this, "密码不能为空",
							Toast.LENGTH_LONG).show();
				} else if (pass1.length() != 6) {

					Toast.makeText(ForgetActivity.this, "密码必须为6位",
							Toast.LENGTH_LONG).show();

				} else if (!pass1
						.equals(pass2)) {

					Toast.makeText(ForgetActivity.this, "两次密码不一致",
							Toast.LENGTH_LONG).show();

				} else {
					if(et_answer.getText().toString().equals("")){
						Toast.makeText(ForgetActivity.this, "答案不能为空！",
								Toast.LENGTH_LONG).show();
					}
					else if(et_answer.getText().toString().equals(answer)){
						Editor editor = spf.edit();
						editor.putString("password", pass1);
						boolean b = editor.commit();
						if(b){
							Toast.makeText(ForgetActivity.this, "修改成功！",
									Toast.LENGTH_LONG).show();
							ForgetActivity.this.finish();
						}
						
					}else{
						Toast.makeText(ForgetActivity.this, "请输入正确的答案！",
								Toast.LENGTH_LONG).show();
					}
					
				}
				
			}
		});
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ForgetActivity.this,LoginActivity.class);
				ForgetActivity.this.startActivity(intent);
				
			}
		});
	}
	private void init() {
		// TODO Auto-generated method stub
		et_question=(EditText)this.findViewById(R.id.forget_question);
		et_answer=(EditText)this.findViewById(R.id.forget_answer);
		et_pass1=(EditText)this.findViewById(R.id.forget_pass1);
		et_pass2=(EditText)this.findViewById(R.id.forget_pass2);
		
		btn=(Button)this.findViewById(R.id.forget_btn_on);
		back = (ImageButton)this.findViewById(R.id.forget_back);
		String question = spf.getString("question", "");
		answer = spf.getString("answer", "");
		et_question.setText(question);
	}
}

