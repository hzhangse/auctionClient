package com.train.auction.client;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;

import com.train.auction.client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ManageKind extends Activity {
	Button bnHome, bnAdd;
	ListView kindList;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_kind);
		// ��ȡ���沼���ϵ�������ť
		bnHome = (Button) findViewById(R.id.bn_home);
		bnAdd = (Button) findViewById(R.id.bnAdd);
		kindList = (ListView) findViewById(R.id.kindList);
		// Ϊ���ذ�ť�ĵ����¼����¼�������
		bnHome.setOnClickListener(new FinishListener(this));
		bnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View source) {
				// ����AddKind Activity��
				Intent intent = new Intent(ManageKind.this, AddKind.class);
				ManageKind.this.startActivityForResult(intent, ActivityCode.addKind_RequestCode);
				;

			}
		});
		
		setKindList();
	}

	private void setKindList() {
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "viewKind.jsp";
		try {
			// ��ָ��URL�������󣬲�����Ӧ��װ��JSONArray����
			final JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			// ��JSONArray�����װ��Adapter
			kindList.setAdapter(new KindArrayAdapter(jsonArray, ManageKind.this));
		} catch (Exception e) {
			DialogUtil.showDialog(this, "��������Ӧ�쳣�����Ժ����ԣ�", false);
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ActivityCode.addKind_RequestCode
				&& resultCode == ActivityCode.addKind_ResultCode) {
			// refresh result
			setKindList();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}