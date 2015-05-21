package com.train.auction.client;

import java.util.HashMap;
import java.util.Map;

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


public class Login extends Activity
{
	// ��������������ı���
	EditText etName, etPass;
	// ���������������ť
	Button bnLogin, bnCancel,bnRegist;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// ��ȡ�����������༭��
		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);
		
		// ��ȡ�����е�������ť
		bnLogin = (Button) findViewById(R.id.bnLogin);
		bnCancel = (Button) findViewById(R.id.bnCancel);
		bnRegist = (Button) findViewById(R.id.bnregist);
		// ΪbnCancal��ť�ĵ����¼����¼�������
		bnCancel.setOnClickListener(new FinishListener(this));
		bnRegist.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(Login.this, RegistUser.class);
				startActivityForResult(intent, ActivityCode.registUser_RequestCode);
			}
		});
		bnLogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// ִ������У��
				if (validate())
				{
					// �����¼�ɹ�
					if (loginPro())
					{
						// ����Main Activity
						Intent intent = new Intent(Login.this, Main.class);
						startActivity(intent);
						// ������Activity
						finish();
					}
					else
					{
						DialogUtil.showDialog(Login.this
							, "�û����ƻ�������������������룡", false);
					}
				}
			}
		});
	}

	private boolean loginPro()
	{
		// ��ȡ�û�������û���������
		String username = etName.getText().toString();
		String pwd = etPass.getText().toString();
		JSONObject jsonObj;
		try
		{
			jsonObj = query(username, pwd);
			// ���userId ����0
			if (jsonObj.getInt("userId") > 0)
			{
				return true;
			}
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this, "��������Ӧ�쳣�����Ժ����ԣ�", false);
			e.printStackTrace();
		}

		return false;
	}

	// ���û�������û������������У��
	private boolean validate()
	{
		String username = etName.getText().toString().trim();
		if (username.equals(""))
		{
			DialogUtil.showDialog(this, "�û��˻��Ǳ����", false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();
		if (pwd.equals(""))
		{
			DialogUtil.showDialog(this, "�û������Ǳ����", false);
			return false;
		}
		return true;
	}

	// ���巢������ķ���
	private JSONObject query(String username, String password) throws Exception
	{
		// ʹ��Map��װ�������
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", username);
		map.put("pass", password);
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "login.jsp";
		// ��������
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ActivityCode.registUser_RequestCode
				&& resultCode == ActivityCode.registUser_ResultCode) {
		 Bundle bundle = data.getExtras();
		 String username = bundle.getString("user");
		 String pass = bundle.getString("pass");
		 etName.setText(username);
		 etPass.setText(pass);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}