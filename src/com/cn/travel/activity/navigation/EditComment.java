package com.cn.travel.activity.navigation;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cn.travel.activity.me.ReportOrderActivity;
import com.cn.travel.bean.OrderCommentBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.service.me.AddComment;
import com.cn.travle.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditComment extends Activity {

	private ImageView back;
	private TextView commentName;
	private EditText comment;
	private TextView limit;
	private TextView submit;
	private String editnum;
	private int num=0;
	private String edit;
	private String hotelname;
	private String cType;
	private IDclass id;
	private String orderid;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comment);
		Intent intent = getIntent();
		hotelname = intent.getStringExtra("hotelname");
		cType = intent.getStringExtra("cType");
		orderid = intent.getStringExtra("orderid");
		initView();
		initListener();
		commentName.setText(hotelname);
	}
	
	public void initView(){
		back = (ImageView)this.findViewById(R.id.back);
		commentName = (TextView)this.findViewById(R.id.name);
		comment = (EditText)this.findViewById(R.id.edit_comment);
		limit = (TextView)this.findViewById(R.id.edit_limit);
		submit = (TextView)this.findViewById(R.id.submit);
	}
	
	public void initListener(){
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditComment.this.finish();
			}
		
		});
		comment.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				num=comment.getText().length();
				editnum = Integer.toString(num);
				limit.setText(editnum+"/150");
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
		});
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(num>150){
					Toast.makeText(EditComment.this, "字数超越上限", Toast.LENGTH_SHORT).show();
				}else{
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
			        Date date = new Date(System.currentTimeMillis());
			        String time = simpleDateFormat.format(date);
					OrderCommentBean ocBean = new OrderCommentBean();
					if(orderid!=null){
						ocBean.setOrderid(orderid);
					}
					ocBean.setName(hotelname);
					ocBean.setType(cType);
					ocBean.setMessage(comment.getText().toString());
			        ocBean.setTime(time);
			        ocBean.setUsername(id.UserName);
			        ocBean.setUserimg(id.iconImage);
			        AddComment add = new AddComment();
			        add.addComment(EditComment.this, ocBean);
			        Toast.makeText(EditComment.this, "评价成功", Toast.LENGTH_SHORT).show();
			        EditComment.this.finish();
			        
				}
			}
		
		});
	}
	
}
