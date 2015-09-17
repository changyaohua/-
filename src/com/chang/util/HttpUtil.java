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
						 //�����������������д������
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
						// �ص�onFinish()����
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (listener != null) {
						// �ص�onError()����
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
     * Function  :   ��װ��������Ϣ
     * Param     :   params���������ݣ�encode�����ʽ
     */
   public static String getRequestData(Map<String, String> params) {
      StringBuffer stringBuffer = new StringBuffer();        //�洢��װ�õ���������Ϣ
      try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                      .append("=")
                      .append(entry.getValue())
                      .append("&");
            }
           stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //ɾ������һ��"&"
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

