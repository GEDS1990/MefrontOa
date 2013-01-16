package com.Zbh.mefrontoa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class postUrl {
	/**��ȡ����ָ������ҳ���룬���䷵�ظ������ߣ��ɵ����߶������ 
	 * ����String 
	 * Chen.Zhidong 
	 * 2011-02-15*/ 
	public String posturl(String url){ 
		InputStream is = null; 
		String result = ""; 
		try{ 
			HttpClient httpclient = new DefaultHttpClient(); 
			HttpPost httppost = new HttpPost(url); 
			HttpResponse response = httpclient.execute(httppost); 
			HttpEntity entity = response.getEntity(); 
			is = entity.getContent(); 
		}catch(Exception e){ 
			return "Fail to establish http connection!"+e.toString(); 
		} 

		try{ 
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"GBK")); 
			StringBuilder sb = new StringBuilder(); 
			String line = null; 
			while ((line = reader.readLine()) != null) { 
				sb.append(line); 
			} 

			is.close(); 
			result=sb.toString(); 
		}catch(Exception e){ 
			return "Fail to convert net stream!"; 
		} 
		return result; 
	} 

	/**��ȡ����(ArrayList<NameValuePair> nameValuePairs,String url)��post��Զ�̷����� 
	 * ����õķ��ؽ��(String)���ظ������� 
	 * �����������ڲ�ѯ�������ٵ�ʱ�� 
	 * Chen.Zhidong 
	 * 2011-02-15*/ 
	public String posturl(ArrayList<NameValuePair> nameValuePairs,String url){ 
		String result = ""; 
		String tmp= ""; 
		InputStream is = null; 
		try{ 
			HttpClient httpclient = new DefaultHttpClient(); 
			HttpPost httppost = new HttpPost(url); 
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
			HttpResponse response = httpclient.execute(httppost); 
			HttpEntity entity = response.getEntity(); 
			is = entity.getContent(); 
		}catch(Exception e){ 
			return "Fail to establish http connection!"; 
		} 
		try{ 
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"GBK")); 
			StringBuilder sb = new StringBuilder(); 
			String line = null; 

			while ((line = reader.readLine()) != null) { 
				sb.append(line + "\n"); 
			} 
			is.close(); 
			tmp=sb.toString(); 
		}catch(Exception e){ 
			return "Fail to convert net stream!"; 
		} 
		try{ 
			JSONArray jArray = new JSONArray(tmp); 


			for(int i=0;i<jArray.length();i++){ 
				JSONObject json_data = jArray.getJSONObject(i); 
				Iterator<?> keys=json_data.keys(); 

				while(keys.hasNext()){ 
					result += json_data.getString(keys.next().toString()); 
				} 
			} 
		}catch(JSONException e){ 
			return "The URL you post is wrong!"; 
		} 
		return result; 
	} 
	/**��ȡָ����ַ����ҳ���� 
	 * ���������� 
	 * Chen.Zhidong 
	 * 2011-02-18*/ 

	public InputStream streampost(String remote_addr){ 
		URL infoUrl = null; 
		InputStream inStream = null; 
		try { 
			infoUrl = new URL(remote_addr); 
			URLConnection connection = infoUrl.openConnection(); 
			HttpURLConnection httpConnection = (HttpURLConnection)connection; 
			int responseCode = httpConnection.getResponseCode(); 
			if(responseCode == HttpURLConnection.HTTP_OK){ 
				inStream = httpConnection.getInputStream(); 
			} 
		} catch (MalformedURLException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} catch (IOException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
		return inStream; 
	} 
}
