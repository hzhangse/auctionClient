package com.train.auction.client;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.train.auction.client.R;
import com.train.auction.client.util.DialogUtil;
import com.train.auction.client.util.HttpUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class ViewBid extends Activity
{
	Button bnHome;
	ListView bidList;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_bid);
		// ��ȡ�����ϵķ��ذ�ť
		bnHome = (Button) findViewById(R.id.bn_home);
		bidList = (ListView) findViewById(R.id.bidList);
		// Ϊ���ذ�ť�ĵ����¼����¼�������
		bnHome.setOnClickListener(new FinishListener(this));
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "viewBid.jsp";
		try
		{
			// ��ָ��URL�������󣬲��ѷ�������Ӧ��װ��JSONArray����
			JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			// ��JSONArray��װ��Adapter
			JSONArrayAdapter adapter = new JSONArrayAdapter(this, jsonArray,
				"item", true);
			bidList.setAdapter(adapter);
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this, "��������Ӧ�쳣�����Ժ����ԣ�", false);
			e.printStackTrace();
		}
		bidList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				// �鿴��������
				viewBidDetail(position);
			}
		});
	}

	private void viewBidDetail(int position)
	{
		// ����bid_detail.xml���沼�ִ������ͼ
		View detailView = getLayoutInflater()
			.inflate(R.layout.bid_detail, null);
		// ��ȡbid_detail�����е��ı���
		EditText itemName = (EditText) detailView
			.findViewById(R.id.itemName);
		EditText bidPrice = (EditText) detailView
			.findViewById(R.id.bidPrice);
		EditText bidTime = (EditText) detailView
			.findViewById(R.id.bidTime);
		EditText bidUser = (EditText) detailView
			.findViewById(R.id.bidUser);
		// ��ȡ��������Ŀ����װ��JSONObject
		JSONObject jsonObj = (JSONObject) bidList.getAdapter()
			.getItem(position);
		try
		{
			// ʹ���ı�������ʾ�������顣
			itemName.setText(jsonObj.getString("item"));
			bidPrice.setText(jsonObj.getString("price"));
			bidTime.setText(jsonObj.getString("bidDate"));
			bidUser.setText(jsonObj.getString("user"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		DialogUtil.showDialog(ViewBid.this, detailView);
	}
}