package com.train.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONObject;

import com.train.auction.client.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class AddBid extends Activity
{
	// ����������ı���
	EditText itemName, itemDesc,itemRemark,itemKind
		,initPrice , maxPrice ,endTime , bidPrice;
	// ���������������ť
	Button bnAdd, bnCancel;
	// ���嵱ǰ������������Ʒ
	JSONObject jsonObj;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_bid);
		// ��ȡ�����б༭��
		itemName = (EditText) findViewById(R.id.itemName);
		itemDesc = (EditText) findViewById(R.id.itemDesc);
		itemRemark = (EditText) findViewById(R.id.itemRemark);
		itemKind = (EditText) findViewById(R.id.itemKind);
		initPrice = (EditText) findViewById(R.id.initPrice);
		maxPrice = (EditText) findViewById(R.id.maxPrice);
		endTime = (EditText) findViewById(R.id.endTime);
		bidPrice = (EditText) findViewById(R.id.bidPrice);
		// ��ȡ�����е�������ť
		bnAdd = (Button) findViewById(R.id.bnAdd);
		bnCancel = (Button) findViewById(R.id.bnCancel);
		// Ϊȡ����ť�ĵ����¼����¼�������
		bnCancel.setOnClickListener(new FinishListener(this));
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "getItem.jsp?itemId="
			+ getIntent().getStringExtra("itemId");
		try
		{
			// ��ȡָ����������Ʒ
			jsonObj = new JSONObject(HttpUtil.getRequest(url));
			// ʹ���ı�������ʾ������Ʒ������
			itemName.setText(jsonObj.getString("name"));
			itemDesc.setText(jsonObj.getString("desc"));
			itemRemark.setText(jsonObj.getString("remark"));
			itemKind.setText(jsonObj.getString("kind"));
			initPrice.setText(jsonObj.getString("initPrice"));
			maxPrice.setText(jsonObj.getString("maxPrice"));
			endTime.setText(jsonObj.getString("endTime"));
		}
		catch (Exception e1)
		{
			DialogUtil.showDialog(AddBid.this, "��������Ӧ�����쳣��", false);
			e1.printStackTrace();
		}
		bnAdd.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try 
				{
					// ִ������ת��
					double curPrice = Double.parseDouble(
						bidPrice.getText().toString());
					// ִ������У��
					if( curPrice <  jsonObj.getDouble("maxPrice"))
					{
						DialogUtil.showDialog(AddBid.this, "������ľ��۱�����ڵ�ǰ����", false);
					}
					else
					{
						// ��Ӿ���
						String result = addBid(jsonObj.getString("id") 
							, curPrice + "");
						// ��ʾ�Ի���
						DialogUtil.showDialog(AddBid.this
							, result , true);
					}
				}
				catch(NumberFormatException ne)
				{
					DialogUtil.showDialog(AddBid.this, "������ľ��۱�������ֵ", false);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					DialogUtil.showDialog(AddBid.this, "��������Ӧ�����쳣�������ԣ�", false);
				}
			}
		});
	}
	
	private String addBid(String itemId , String bidPrice)
		throws Exception
	{
		// ʹ��Map��װ�������
		Map<String , String> map = new HashMap<String, String>();
		map.put("itemId" , itemId);
		map.put("bidPrice" , bidPrice);
		// �������󽫻ᷢ�͵�addKind.jspҳ��
		String url = HttpUtil.BASE_URL + "addBid.jsp";
		// ��������
		return HttpUtil.postRequest(url , map);
	}
}