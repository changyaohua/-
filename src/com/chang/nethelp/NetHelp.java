package com.chang.nethelp;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.chang.util.HttpUtil;
import com.chang.util.HttpUtil.HttpCallbackListener;


public class NetHelp {
	public void checkLogin(Map<String,String> userInfo,Handler handler){
		String loginUrl = "http://192.168.207.26:8080/RegisterService/ClientLoginServlet";
		final Handler tempHandler = handler;
		final Message msg = new Message();
		HttpUtil.sendHttpRequest(loginUrl,userInfo,new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					int flag = jsonObject.getInt("flag");
					msg.arg1 = flag;
				} catch (JSONException e) {
					msg.arg1 = -1;
					e.printStackTrace();
				}
				tempHandler.sendMessage(msg);
			}
			@Override
			public void onError(Exception e) {
				msg.arg1 = -1;
				e.printStackTrace();
				tempHandler.sendMessage(msg);
			}
		});
	}
}
