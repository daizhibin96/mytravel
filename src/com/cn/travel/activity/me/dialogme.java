package com.cn.travel.activity.me;



import com.cn.travle.R;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class dialogme extends Dialog {
	private Button btn1;
	private TextView textView1;

	public dialogme(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_me_more);
		btn1 = (Button)this.findViewById(R.id.button1);
		textView1=(TextView)this.findViewById(R.id.textView1);
		textView1.setText("这是一个东莞旅游app,能够给你带来最好的旅游体验，如如果你有更好的建议请发送邮件到我们的邮箱");
         btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialogme.this.dismiss();
			}
		});
	}

}
