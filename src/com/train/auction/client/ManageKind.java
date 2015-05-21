package com.train.auction.client;

import org.json.JSONArray;

import com.train.auction.client.R;
import com.train.auction.client.util.DialogUtil;
import com.train.auction.client.util.HttpUtil;

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
		// 获取界面布局上的两个按钮
		bnHome = (Button) findViewById(R.id.bn_home);
		bnAdd = (Button) findViewById(R.id.bnAdd);
		kindList = (ListView) findViewById(R.id.kindList);
		// 为返回按钮的单击事件绑定事件监听器
		bnHome.setOnClickListener(new FinishListener(this));
		bnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View source) {
				// 启动AddKind Activity。
				Intent intent = new Intent(ManageKind.this, AddKind.class);
				ManageKind.this.startActivityForResult(intent, ActivityCode.addKind_RequestCode);
				;

			}
		});
		
		setKindList();
	}

	private void setKindList() {
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "viewKind.jsp";
		try {
			// 向指定URL发送请求，并把响应包装成JSONArray对象
			final JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			// 把JSONArray对象包装成Adapter
			kindList.setAdapter(new KindArrayAdapter(jsonArray, ManageKind.this));
		} catch (Exception e) {
			DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
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