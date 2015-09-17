package com.chang.registerdemo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterSuccess extends Activity{
	private TextView tv_birthday_show;
	private EditText et_name_show,et_password_show,et_email_show;
	private RadioButton rb_man_show,rb_feman_show;
	private Spinner sp_hometown_show;
	private CheckBox cb_play_show, cb_sleep_show, cb_eat_show;
	private Button bt_birthday_show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_register_success);
		
		et_name_show = (EditText) findViewById(R.id.et_name_show);
		et_password_show = (EditText) findViewById(R.id.et_password_show);
		sp_hometown_show = (Spinner) findViewById(R.id.sp_hometown_show);
		tv_birthday_show = (TextView) findViewById(R.id.tv_birthday_show);
		et_email_show = (EditText) findViewById(R.id.et_email_show);
		rb_man_show = (RadioButton) findViewById(R.id.rb_man_show);
		rb_feman_show = (RadioButton) findViewById(R.id.rb_feman_show);
		
		cb_play_show = (CheckBox) findViewById(R.id.cb_play_show);
		cb_sleep_show = (CheckBox) findViewById(R.id.cb_sleep_show);
		cb_eat_show = (CheckBox) findViewById(R.id.cb_eat_show);
		
		bt_birthday_show = (Button) findViewById(R.id.bt_birthday_show);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		String password = intent.getStringExtra("password");
		String sex = intent.getStringExtra("sex");
		String love = intent.getStringExtra("love");
		String hometown = intent.getStringExtra("hometown");
		String birthday = intent.getStringExtra("birthday");
		String email = intent.getStringExtra("email");
		
		et_name_show.setText(name);
		et_password_show.setText(password);
		tv_birthday_show.setText(birthday);
		et_email_show.setText(email);
		
		if ("ÄÐ".equals(sex)) {
			rb_man_show.setChecked(true);
		} else {
			rb_feman_show.setChecked(true);
		}
		
		String[] loves = love.split(",");
		for (String str : loves) {
			switch (str) {
			case "ÍæË£":
				cb_play_show.setChecked(true);
				break;
			case "Ë¯¾õ":
				cb_sleep_show.setChecked(true);
				break;
			case "³Ô·¹":
				cb_eat_show.setChecked(true);
				break;
			}
		}
		
		int position = Integer.parseInt(hometown);             
		sp_hometown_show.setSelection(position, true);
		                                                   
		bt_birthday_show.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog dialog = new DatePickerDialog(RegisterSuccess.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								tv_birthday_show.setText(year + "-"
										+ (monthOfYear + 1) + "-" + dayOfMonth);
							}
						}, 2015, 8, 10);
				dialog.show();
			}
		});
	}
	
}
