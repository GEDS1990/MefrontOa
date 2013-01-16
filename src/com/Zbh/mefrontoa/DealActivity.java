package com.Zbh.mefrontoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class DealActivity extends Activity {

	private List<Map<String, Object>> data;
	private ListView listView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String UserID=null;
		 Bundle extras = getIntent().getExtras();
	        if (extras != null) {
	        	UserID = extras.getString("activityMain");
	        }
		
		PrepareData(UserID);
		listView = new ListView(this);
		// ����ϵͳ��layout��ʾ����
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, new String[] { "����", "����" }, new int[] {
				android.R.id.text1, android.R.id.text2 });
		listView.setAdapter(adapter);
		setContentView(listView);	
		
		OnItemClickListener listener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				setTitle(parent.getItemAtPosition(position).toString());
			}
		};
		listView.setOnItemClickListener(listener);
		OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				setTitle("��ѡ�еı�:  "+parent.getItemAtPosition(position).toString());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				setTitle("");
				
			}
			
		};
		listView.setOnItemSelectedListener(itemSelectedListener);
	}

	private void PrepareData(String UserID) {
		data = new ArrayList<Map<String, Object>>();
		Map<String, Object> item;
		item = new HashMap<String, Object>();
		item.put("����", "����С����");
		item.put("�Ա�", "��");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "����ͬѧ");
		item.put("�Ա�", "��");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("����", "С��ʦ��");
		item.put("�Ա�", "Ů");
		data.add(item);
	}
}