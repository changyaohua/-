 package com.chang.registerdemo;

import java.util.HashMap;
import java.util.Map;
import com.chang.nethelp.NetHelp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText username;
	private EditText userpwd;
	private Button login;

	/**
	 * 用于处理checkLogin()方法与服务器交互的返回值
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.arg1) {
			case 1:
				Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT)
						.show();
				break;

			case 2:
				Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT)
						.show();
				break;

			case -1:
				Toast.makeText(MainActivity.this, "服务器异常", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.username = (EditText) findViewById(R.id.username);
		this.userpwd = (EditText) findViewById(R.id.userpwd);
		this.login = (Button) findViewById(R.id.login);

		this.login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = MainActivity.this.username.getText().toString();
				String password = MainActivity.this.userpwd.getText()
						.toString();
				Map<String,String> loginInfo = new HashMap<String,String>();
				loginInfo.put("username", name);
				loginInfo.put("userpwd", password);
				new NetHelp().checkLogin(loginInfo,handler);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
