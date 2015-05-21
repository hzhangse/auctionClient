package com.train.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.train.auction.client.util.DialogUtil;
import com.train.auction.client.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegistUser extends Activity {

	// ��������������ı���
	EditText etName, etPass, etPassConfirm, etEmail;

	// ���������������ť
	Button bnCancel, bnRegist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);
		// ��ȡ�����������༭��
		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);
		etPassConfirm = (EditText) findViewById(R.id.confirmpwdEditText);
		etEmail = (EditText) findViewById(R.id.editEmail);

		// ��ȡ�����е�������ť
		bnCancel = (Button) findViewById(R.id.bnCancel);
		bnRegist = (Button) findViewById(R.id.bnregist);
		// ΪbnCancal��ť�ĵ����¼����¼�������
		bnCancel.setOnClickListener(new FinishListener(this));
		bnRegist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ִ������У��
				if (validate()) {
					// ����ɹ�
					if (registUser()) {
						// ����Main Activity
						Intent intent = new Intent(RegistUser.this, Login.class);
						Bundle bundle = new Bundle();
						String pwd = etPass.getText().toString();
						bundle.putString("pass",pwd);
						String username = etName.getText().toString().trim();
						bundle.putString("user",username);
						intent.putExtras(bundle);
						RegistUser.this.setResult(ActivityCode.registUser_ResultCode, intent);
						// ������Activity
						finish();
					}

				}
			}
		});
	}

	private boolean registUser() {
		// ��ȡ�û�������û���������
		String username = etName.getText().toString();
		String pwd = etPass.getText().toString();
		String email = etEmail.getText().toString();
		JSONObject jsonObj;
		try {
			jsonObj = query(username, pwd, email);
			// ���userId ����0

			DialogUtil.showDialog(this, jsonObj.getString("msg"), false);

			if (jsonObj.getInt("code") == 1) {
				return true;
			}
		} catch (Exception e) {
			DialogUtil.showDialog(this, "��������Ӧ�쳣�����Ժ����ԣ�", false);
			e.printStackTrace();
		}

		return false;
	}

	// ���û�������û������������У��
	private boolean validate() {
		String email = etEmail.getText().toString().trim();
		if (email.equals("")) {
			DialogUtil.showDialog(this, "email�����", false);
			return false;
		} else {

		}

		String username = etName.getText().toString().trim();

		if (username.equals("")) {
			DialogUtil.showDialog(this, "�û��˻��Ǳ����", false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();
		String confirmpwd = etPassConfirm.getText().toString().trim();
		String msg = "";
		if (pwd.isEmpty()) {
			msg = "�û������Ϊ��";
			DialogUtil.showDialog(this, msg, false);
			return false;
		} else if (!pwd.equals(confirmpwd)) {
			DialogUtil.showDialog(this, "����ǰ��һ�£�", false);
			return false;
		}
		return true;
	}

	// ���巢������ķ���
	private JSONObject query(String username, String password, String email)
			throws Exception {
		// ʹ��Map��װ�������
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", username);
		map.put("pass", password);
		map.put("email", email);
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "regist.jsp";
		// ��������
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}