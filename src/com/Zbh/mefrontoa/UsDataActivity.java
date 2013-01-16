package com.Zbh.mefrontoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.Zbh.mefrontoa.Util.PreferencesUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("NewApi")
public class UsDataActivity extends TabActivity {

	private List<Map<String, Object>> data;
	private ListView listView = null;
	private ListView listView1 = null;
	private ListView listView2 = null;
	private ListView listView3 = null;
	private int totals;
	private String UserID;
	private String tabname="all";
	private static final int DIALOG1 = 1;
	
	private showListTask mshowListTask = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_usdata);
		if (mshowListTask != null) {
			return;
		}
		 Bundle extras = getIntent().getExtras();
	        if (extras != null) {
	        	UserID = extras.getString("UserID");
	        }
	        
		TabHost tabHost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.activity_usdata,
				tabHost.getTabContentView(), true);
		
		tabHost.addTab(tabHost.newTabSpec("all").setIndicator("ȫ��")
				.setContent(R.id.listView));
		tabHost.addTab(tabHost.newTabSpec("Urge").setIndicator("�߰�")
				.setContent(R.id.listView1));
		tabHost.addTab(tabHost.newTabSpec("normal").setIndicator("����")
				.setContent(R.id.listView2));
		tabHost.addTab(tabHost.newTabSpec("period").setIndicator("����")
				.setContent(R.id.listView3));
		//Ĭ����ʾ���д���
		mshowListTask= new showListTask();
		mshowListTask.execute((Void) null);
		//tab�л��¼�
		tabHost.setOnTabChangedListener(new OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                // TODO Auto-generated method stub
            	tabname=tabId;
        		mshowListTask= new showListTask();
        		mshowListTask.execute((Void) null);
            }            
        });		
//		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()       
//        .detectDiskReads()       
//        .detectDiskWrites()       
//        .detectNetwork()   // or .detectAll() for all detectable problems       
//        .penaltyLog()       
//        .build());       
// StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()       
//        .detectLeakedSqlLiteObjects()    
//        .penaltyLog()       
//        .penaltyDeath()       
//        .build());  
 		
		
		
		listView = (ListView) findViewById(R.id.listView);
		listView1 = (ListView) findViewById(R.id.listView1);
		listView2 = (ListView) findViewById(R.id.listView2);
		listView3 = (ListView) findViewById(R.id.listView3);
		
		OnItemClickListener listener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Toast.makeText(UsDataActivity.this, "������ϸ���ݡ���", 0).show();
				Intent intent = new Intent(UsDataActivity.this, DealActivity.class);
//				intent.putExtra("UserID", UserID);
//				intent.putExtra("FileID", parent.getItemAtPosition(position).toString());
//				startActivity(intent);
			}
		};
		listView.setOnItemClickListener(listener);
		listView1.setOnItemClickListener(listener);
		listView2.setOnItemClickListener(listener);
		listView3.setOnItemClickListener(listener);
	}

	public class showListTask extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return null;
			}
			postUrl posts=new postUrl();
			String ret=posts.posturl(PreferencesUtil.baseUrl+PreferencesUtil.OAUrl+"/z_Mlogin/UsDataList.jsp?user_id="+UserID+"&att="+tabname);
			return ret;
		}

		@Override
		protected void onPostExecute(final String success) {
			mshowListTask = null;
			String JSON=success;
			try {
				JSONTokener jsonParser = new JSONTokener(JSON);
				JSONObject jsonObj= (JSONObject) jsonParser.nextValue();
				totals=jsonObj.getInt("totals"); 
				JSONArray datas = jsonObj.getJSONArray("datas");
				if(totals>0){
					setTitle("����"+totals+"���������");
					data = new ArrayList<Map<String, Object>>();
					for(int i = 0; i < datas.length() ; i++){
				        JSONObject onedata = ((JSONObject)datas.opt(i));
						Map<String, Object> item;
						item = new HashMap<String, Object>();
						item.put("����", onedata.getString("title"));
						item.put("����", onedata.getString("keys"));
						item.put("fileid", onedata.getString("fileid"));
						item.put("UserID", UserID);
						data.add(item);
					}	
					SimpleAdapter adapter = new SimpleAdapter(UsDataActivity.this, data,
							R.layout.usdata_item, new String[] { "����", "����", "fileid","UserID" }, new int[] {
							R.id.mview1, R.id.mview2, R.id.fileid, R.id.userid });
					if(tabname.equals("all"))listView.setAdapter(adapter);
					else if(tabname.equals("Urge"))listView1.setAdapter(adapter);
					else if(tabname.equals("normal"))listView2.setAdapter(adapter);
					else if(tabname.equals("period"))listView3.setAdapter(adapter);
				}
				// ����ϵͳ��layout��ʾ����
//				SimpleAdapter adapter = new SimpleAdapter(this, data,
//						android.R.layout.simple_list_item_2, new String[] { "����", "����", "fileid" }, new int[] {
//						android.R.id.text1, android.R.id.text2 });
				
//				setContentView(listView);	
				Toast.makeText(UsDataActivity.this, "�û����ݻ�ȡ��ϣ�", 0).show();
			} catch (JSONException ex) {
				System.out.println("Jsons parse error !");
				ex.printStackTrace();
			}  

		}

		@Override
		protected void onCancelled() {
			mshowListTask = null;
		}
	}
	@Override
	/*
	 * menu.findItem(EXIT_ID);�ҵ��ض���MenuItem
	 * MenuItem.setIcon.��������menu��ť�ı���
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, Menu.FIRST, 0, "�˳�");
		menu.findItem(Menu.FIRST);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST: 
			showDialog(DIALOG1);
		break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG1:
			return buildDialog1(UsDataActivity.this);
		}
		return null;
	}

	private Dialog buildDialog1(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.alert_dialog_quit_title);
		builder.setMessage(R.string.Mess_dialog_quit_title);
		builder.setPositiveButton(R.string.alert_dialog_ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						SharedPreferences settings = getSharedPreferences(PreferencesUtil.SETTING_INFOS, 0);
						settings.edit().putString("UserID", "").commit();
						finish();
					}
				});
		builder.setNegativeButton(R.string.alert_dialog_cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
					}
				});
		return builder.create();

	}
}