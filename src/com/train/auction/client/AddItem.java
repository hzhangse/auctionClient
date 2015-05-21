package com.train.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.train.auction.client.R;
import com.train.auction.client.util.DialogUtil;
import com.train.auction.client.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class AddItem extends Activity
{
	// ����������ı���
	EditText itemName, itemDesc,itemRemark,initPrice;
	Spinner itemKind , availTime;
	// ���������������ť
	Button bnAdd, bnCancel;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);
		// ��ȡ�����е��ı���
		itemName = (EditText) findViewById(R.id.itemName);
		itemDesc = (EditText) findViewById(R.id.itemDesc);
		itemRemark = (EditText) findViewById(R.id.itemRemark);
		initPrice = (EditText) findViewById(R.id.initPrice);
		itemKind = (Spinner) findViewById(R.id.itemKind);
		availTime = (Spinner) findViewById(R.id.availTime);
		// ���巢������ĵ�ַ
		String url = HttpUtil.BASE_URL + "viewKind.jsp";
		JSONArray jsonArray = null;
		try
		{
			// ��ȡϵͳ�����е���Ʒ����
			// ��ִ��URL�������󣬲��ѷ�������Ӧ��װ��JSONArray
			jsonArray = new JSONArray(HttpUtil.getRequest(url));
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		// ��JSONArray��װ��Adapter
		JSONArrayAdapter adapter = new JSONArrayAdapter(
			this , jsonArray , "kindName" , false);
		// ��ʾ��Ʒ�����б�
		itemKind.setAdapter(adapter);
		// ��ȡ�����е�������ť
		bnAdd = (Button) findViewById(R.id.bnAdd);
		bnCancel = (Button) findViewById(R.id.bnCancel);
		// Ϊȡ����ť�ĵ����¼����¼�������
		bnCancel.setOnClickListener(new FinishListener(this));
		bnAdd.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// ִ������У��
				if (validate())
				{
					// ��ȡ�û��������Ʒ������Ʒ��������Ϣ
					String name = itemName.getText().toString();
					String desc = itemDesc.getText().toString();
					String remark = itemRemark.getText().toString();
					String price = initPrice.getText().toString();
					JSONObject kind = (JSONObject) itemKind.getSelectedItem();
					int avail = availTime.getSelectedItemPosition();
					//�����û�ѡ����Чʱ��ѡ�ָ��ʵ�ʵ���Чʱ��
					switch(avail)
					{
						case 5 :
							avail = 7;
							break;
						case 6 :
							avail = 30;
							break;
						default :
							avail += 1;
							break;
					}
					try
					{
						// �����Ʒ
						String result = addItem(name, desc
							, remark , price , kind.getString("id") , avail);
						// ��ʾ�Ի���
						DialogUtil.showDialog(AddItem.this
							, result , true);
					}
					catch (Exception e)
					{
						DialogUtil.showDialog(AddItem.this
							, "��������Ӧ�쳣�����Ժ����ԣ�" , false);
						e.printStackTrace();
					}
				}
			}
		});
	}

	// ���û��������Ʒ�������ļ۸����У��
	private boolean validate()
	{
		String name = itemName.getText().toString().trim();
		if (name.equals(""))
		{
			DialogUtil.showDialog(this , "��Ʒ�����Ǳ����" , false);
			return false;
		}
		String price = initPrice.getText().toString().trim();
		if (price.equals(""))
		{
			DialogUtil.showDialog(this , "���ļ۸��Ǳ����" , false);
			return false;
		}
		try 
		{
			// ���԰����ļ۸�ת��Ϊ������
			Double.parseDouble(price);
		}
		catch(NumberFormatException e)
		{
			DialogUtil.showDialog(this , "���ļ۸��������ֵ��" , false);
			return false;
		}
		return true;
	}

	private String addItem(String name, String desc
		, String remark , String initPrice , String kindId , int availTime)
		throws Exception
	{
		// ʹ��Map��װ�������
		Map<String , String> map = new HashMap<String, String>();
		map.put("itemName" , name);
		map.put("itemDesc" , desc);
		map.put("itemRemark" , remark);
		map.put("initPrice" , initPrice);
		map.put("kindId" , kindId );
		map.put("availTime" , availTime + "");
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "addItem.jsp";
		// ��������
		return HttpUtil.postRequest(url , map);
	}
	
	@Override
	public void finish() {
		Intent intent = getIntent();
		setResult(ActivityCode.addItem_ResultCode , intent);	
		super.finish();
	}
}