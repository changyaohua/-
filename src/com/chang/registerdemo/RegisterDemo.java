package com.chang.registerdemo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterDemo extends Activity {
	private EditText et_name, et_password, et_email;
	private TextView tv_birthday;
	private CheckBox cb_play, cb_sleep, cb_eat;
	private Spinner sp_hometown;
	private RadioGroup rg_sex;
	private Button bt_register,bt_birthday;
	private ImageView iv_email;

	private String hometown;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_register);

		et_name = (EditText) findViewById(R.id.et_name);
		et_password = (EditText) findViewById(R.id.et_password);
		et_email = (EditText) findViewById(R.id.et_email);
		tv_birthday = (TextView) findViewById(R.id.tv_birthday);
		cb_play = (CheckBox) findViewById(R.id.cb_play);
		cb_sleep = (CheckBox) findViewById(R.id.cb_sleep);
		cb_eat = (CheckBox) findViewById(R.id.cb_eat);
		sp_hometown = (Spinner) findViewById(R.id.sp_hometown);
		rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
		bt_birthday = (Button) findViewById(R.id.bt_birthday);
		bt_register = (Button) findViewById(R.id.bt_register);
		
		sp_hometown.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				hometown = "" + position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

		bt_birthday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog dialog = new DatePickerDialog(RegisterDemo.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								tv_birthday.setText(year + "-"
										+ (monthOfYear + 1) + "-" + dayOfMonth);
							}
						}, 2015, 8, 10);
				dialog.show();
			}
		});

		bt_register.setOnClickListener(new OnClickListenerImpl());

	}

	class OnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			boolean flag = true; //判断提交时是否符合要求

			String name = et_name.getText().toString();
			if ("".equals(name)) {
				flag = false;
				et_name.setHint("姓名不能为空");
			}
			String password = et_password.getText().toString();
			if ("".equals(password)) {
				flag = false;
				et_password.setHint("密码不能为空");
			} else if (password.length() < 6) {
				flag = false;
				et_password.setText("");
				et_password.setHint("密码不能少于六位");
			}
			String msg = et_email.getText().toString(); // 取得输入的文字信息
			if ("".equals(msg)) {
				flag = false;
				et_email.setHint("邮箱不能为空");
			} else if (!msg.matches("\\w+@\\w+\\.\\w+")) { // 验证未通过
				flag = false;
				et_email.setText("");
				et_email.setHint("输入邮箱不符合规则");
			}
			
			if (flag) {
				String sex = null;
				if (R.id.rb_feman == rg_sex.getCheckedRadioButtonId()) {
					sex = "女";
				} else {
					sex = "男";
				}
				StringBuilder love = new StringBuilder();
				if (cb_play.isChecked()) {
					love.append("玩耍,");
				}
				if (cb_sleep.isChecked()) {
					love.append("睡觉,");
				}
				if (cb_eat.isChecked()) {
					love.append("吃饭,");
				}
				String email = msg;
				String birthday = tv_birthday.getText().toString();
				
				
				Intent intent = new Intent();
				intent.putExtra("name", name);
				intent.putExtra("password", password);
				intent.putExtra("sex", sex);
				intent.putExtra("love", love.toString());
				intent.putExtra("email", email);
				intent.putExtra("birthday", birthday);
				intent.putExtra("hometown", hometown);
				intent.setClass(RegisterDemo.this, RegisterSuccess.class);
				startActivity(intent);
			}
			
		}

	}
}
