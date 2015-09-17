package com.chang.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {
	public static void sendHttpRequest(final String address, final Map<String,String> map, final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setDoInput(true);
					connection.setConnectTimeout(8000);
					if (map == null) {
						connection.setRequestMethod("GET");
					} else {
						connection.setDoOutput(true);
						connection.setRequestMethod("POST");
						 //获得输出流，向服务器写入数据
			            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			            out.writeBytes(getRequestData(map));
					}
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if (listener != null) {
						// 回调onFinish()方法
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (listener != null) {
						// 回调onError()方法
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
	
	 /*
     * Function  :   封装请求体信息
     * Param     :   params请求体内容，encode编码格式
     */
   public static String getRequestData(Map<String, String> params) {
      StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
      try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                      .append("=")
                      .append(entry.getValue())
                      .append("&");
            }
           stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
           e.printStackTrace();
       }
       return stringBuffer.toString();
    }

	public interface HttpCallbackListener {

		void onFinish(String response);

		void onError(Exception e);

	}

}

