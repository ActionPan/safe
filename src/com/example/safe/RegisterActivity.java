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
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	private Button setPassbutton;
	private EditText passEditText=null;
	private EditText pass2EditText=null;
	private EditText questioneditText=null;
	private EditText answerText=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.register_layout);
		
		passEditText=(EditText) findViewById(R.id.passEditText);
        pass2EditText=(EditText) findViewById(R.id.pass2EditText);
        questioneditText=(EditText) findViewById(R.id.questioneditText);
        answerText=(EditText) findViewById(R.id.answerText4);
        setPassbutton=(Button) findViewById(R.id.setPassbutton);
        
        setPassbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String pass = passEditText.getText().toString();
				String pass2 = pass2EditText.getText().toString();
				String question = questioneditText.getText().toString();
				String answer = answerText.getText().toString();
				
				//�����ݴ�����SharedPreferences��
				SharedPreferences spf = getSharedPreferences("pass", MODE_PRIVATE);
				
				if (passEditText.equals("")
						|| pass2EditText.equals("")) {

					Toast.makeText(RegisterActivity.this, "���벻��Ϊ��",
							Toast.LENGTH_LONG).show();
				} else if (passEditText.length() != 6) {

					Toast.makeText(RegisterActivity.this, "�������Ϊ6λ",
							Toast.LENGTH_LONG).show();

				} else if (!pass
						.equals(pass2)) {

					Toast.makeText(RegisterActivity.this, "�������벻һ��",
							Toast.LENGTH_LONG).show();

				} else if (question.equals("")
						|| answer.equals("")) {
					Toast.makeText(RegisterActivity.this, "����𰸲���Ϊ��",
							Toast.LENGTH_LONG).show();
				} else {
				//���Editor����
				Editor editor = spf.edit();
				editor.putString("pass", pass);
				editor.putString("question", question);
				editor.putString("answer", answer);
				editor.putString("key", "0");
				//�ύ
				Boolean b = editor.commit();
				if(b == true)
				{
					//��ת����ҳ��
					Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
					RegisterActivity.this.startActivity(intent);
					finish();
				}
				else
				{
					Toast.makeText(RegisterActivity.this, "�������", Toast.LENGTH_SHORT).show();
					return;
				}
				}
				
			}
		});
	}
}
