package com.train.auction.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.train.auction.client.R;
import com.train.auction.client.util.DialogUtil;
import com.train.auction.client.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class ChooseKind extends Activity
{
	Button bnHome;
	ListView kindList;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_kind);
		bnHome = (Button) findViewById(R.id.bn_home);
		kindList = (ListView) findViewById(R.id.kindList);
		// Ϊ���ذ�ť�ĵ����¼����¼�������
		bnHome.setOnClickListener(new FinishListener(this));
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "viewKind.jsp";
		try
		{
			// ��ָ��URL�������󣬲�����������Ӧ��װ��JSONArray����
			JSONArray jsonArray = new JSONArray(
				HttpUtil.getRequest(url));
			// ʹ��ListView��ʾ������Ʒ׼����
			kindList.setAdapter(new KindArrayAdapter(jsonArray
				, ChooseKind.this));	
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this , "��������Ӧ�쳣�����Ժ����ԣ�" , false);
			e.printStackTrace();
		}
		kindList.setOnItemClickListener(new OnItemClickListener()
		{
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				JSONObject jsonObj = (JSONObject) kindList
						.getAdapter().getItem(position);
				// ����ChooseItem Activity��
				Intent intent = new Intent(ChooseKind.this
					, ChooseItem.class);
				// ������ID��Ϊ�����������ȥ
				try {
					intent.putExtra("kindId" , jsonObj.getString("id"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				startActivity(intent);
			}
		});
	}
}