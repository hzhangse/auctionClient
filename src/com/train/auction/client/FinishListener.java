/**
 * 
 */
package com.train.auction.client;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;


// ����һ��������ǰActivity��
public class FinishListener implements OnClickListener
{
	private Activity activity;
	public FinishListener(Activity activity)
	{
		this.activity = activity;
	}
	@Override
	public void onClick(View source)
	{
		// ������ǰActivity
		activity.finish();
	}
}
