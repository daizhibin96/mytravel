package com.cn.travel.activity.me;

import com.cn.travle.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class dialogCore extends Dialog {
	private LinearLayout closeDialog;

	public dialogCore(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_core);
		closeDialog = (LinearLayout) this.findViewById(R.id.close_button);
		closeDialog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialogCore.this.dismiss();
			}
		});
	}

}
