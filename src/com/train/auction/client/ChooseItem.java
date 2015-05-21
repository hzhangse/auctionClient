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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class ChooseItem extends Activity
{
	Button bnHome;
	ListView succList;
	TextView viewTitle;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_item);
		// ��ȡ�����еķ��ذ�ť
		bnHome = (Button) findViewById(R.id.bn_home);
		succList = (ListView) findViewById(R.id.succList);
		viewTitle = (TextView)findViewById(R.id.view_titile);
		// Ϊ���ذ�ť�ĵ����¼����¼�������
		bnHome.setOnClickListener(new FinishListener(this));
		String kindId = getIntent().getStringExtra("kindId" );
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "itemList.jsp?kindId=" + kindId;
		try
		{
			// ��������ID��ȡ�������Ӧ��������Ʒ
			JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			JSONArrayAdapter adapter = new JSONArrayAdapter(
				this , jsonArray , "name" , true);
			// ʹ��ListView��ʾ��ǰ�����������Ʒ
			succList.setAdapter(adapter);
		}
		catch (Exception e1)
		{
			DialogUtil.showDialog(this , "��������Ӧ�쳣�����Ժ����ԣ�" , false);
			e1.printStackTrace();
		}
		// �޸ı���
		viewTitle.setText(R.string.item_list);
		succList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				// ��������AddBid��Intent
				Intent intent = new Intent(ChooseItem.this , AddBid.class);
				JSONObject jsonObj = (JSONObject) succList
					.getAdapter().getItem(position);
				try
				{
					// ����ǰ��ƷID��Ϊ����������һ��Activity
					intent.putExtra("itemId" , jsonObj.getString("id"));
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				startActivity(intent);
			}
		});
	}
}