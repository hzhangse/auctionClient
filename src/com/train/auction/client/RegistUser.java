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

	// 定义界面中两个文本框
	EditText etName, etPass, etPassConfirm, etEmail;

	// 定义界面中两个按钮
	Button bnCancel, bnRegist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);
		// 获取界面中两个编辑框
		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);
		etPassConfirm = (EditText) findViewById(R.id.confirmpwdEditText);
		etEmail = (EditText) findViewById(R.id.editEmail);

		// 获取界面中的两个按钮
		bnCancel = (Button) findViewById(R.id.bnCancel);
		bnRegist = (Button) findViewById(R.id.bnregist);
		// 为bnCancal按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new FinishListener(this));
		bnRegist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 执行输入校验
				if (validate()) {
					// 如果成功
					if (registUser()) {
						// 启动Main Activity
						Intent intent = new Intent(RegistUser.this, Login.class);
						Bundle bundle = new Bundle();
						String pwd = etPass.getText().toString();
						bundle.putString("pass",pwd);
						String username = etName.getText().toString().trim();
						bundle.putString("user",username);
						intent.putExtras(bundle);
						RegistUser.this.setResult(ActivityCode.registUser_ResultCode, intent);
						// 结束该Activity
						finish();
					}

				}
			}
		});
	}

	private boolean registUser() {
		// 获取用户输入的用户名、密码
		String username = etName.getText().toString();
		String pwd = etPass.getText().toString();
		String email = etEmail.getText().toString();
		JSONObject jsonObj;
		try {
			jsonObj = query(username, pwd, email);
			// 如果userId 大于0

			DialogUtil.showDialog(this, jsonObj.getString("msg"), false);

			if (jsonObj.getInt("code") == 1) {
				return true;
			}
		} catch (Exception e) {
			DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}

		return false;
	}

	// 对用户输入的用户名、密码进行校验
	private boolean validate() {
		String email = etEmail.getText().toString().trim();
		if (email.equals("")) {
			DialogUtil.showDialog(this, "email必填项！", false);
			return false;
		} else {

		}

		String username = etName.getText().toString().trim();

		if (username.equals("")) {
			DialogUtil.showDialog(this, "用户账户是必填项！", false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();
		String confirmpwd = etPassConfirm.getText().toString().trim();
		String msg = "";
		if (pwd.isEmpty()) {
			msg = "用户口令不能为空";
			DialogUtil.showDialog(this, msg, false);
			return false;
		} else if (!pwd.equals(confirmpwd)) {
			DialogUtil.showDialog(this, "口令前后不一致！", false);
			return false;
		}
		return true;
	}

	// 定义发送请求的方法
	private JSONObject query(String username, String password, String email)
			throws Exception {
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", username);
		map.put("pass", password);
		map.put("email", email);
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "regist.jsp";
		// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}