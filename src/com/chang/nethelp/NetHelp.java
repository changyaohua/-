package com.chang.nethelp;

import java.util.Map;

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
					int flag = Integer.parseInt(response);
					msg.arg1 = flag;
				} catch (Exception e) {
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
