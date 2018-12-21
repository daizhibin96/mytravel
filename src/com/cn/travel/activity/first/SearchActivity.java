package com.cn.travel.activity.first;


import java.util.ArrayList;
import java.util.List;

import com.cn.travel.bean.Bean;
import com.cn.travel.title.CustomTitle;
import com.cn.travle.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
public class SearchActivity extends Activity implements SearchView.SearchViewListener{
	
	/** 
     * ��������б�view 
     */  
    private ListView lvResults;  
  
    /** 
     * ����view 
     */  
    private SearchView searchView;  
  
  
    /** 
     * ���ѿ��б�adapter 
     */  
    private ArrayAdapter<String> hintAdapter;  
  
    /** 
     * �Զ���ȫ�б�adapter 
     */  
    private ArrayAdapter<String> autoCompleteAdapter;  
  
    /** 
     * ��������б�adapter 
     */  
    private SearchAdapter resultAdapter;  
  
    /** 
     * ���ݿ����ݣ������� 
     */  
    private List<Bean> dbData;  
  
    /** 
     * ���Ѱ����� 
     */  
    private List<String> hintData;  
  
    /** 
     * �����������Զ���ȫ���� 
     */  
    private List<String> autoCompleteData;  
  
    /** 
     * ������������� 
     */  
    private List<Bean> resultData;  
  
    /** 
     * Ĭ����ʾ����ʾ��ĸ��� 
     */  
    private static int DEFAULT_HINT_SIZE = 4;  
  
    /** 
     * ��ʾ����ʾ��ĸ��� 
     */  
    private static int hintSize = DEFAULT_HINT_SIZE;  
  
    /** 
     * ������ʾ����ʾ��ĸ��� 
     * 
     *  hintSize ��ʾ����ʾ���� 
     */  
    public static void setHintSize(int hintSize) {  
       SearchActivity.hintSize = hintSize;  
    }  
  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       
        setContentView(R.layout.activity_search);  
        initData();  //��ʼ�����ѣ��Զ���ȫ�������б�����
        initViews();  
    }  
  
    /** 
     * ��ʼ����ͼ 
     */  
    private void initViews() {  
        lvResults = (ListView) findViewById(R.id.main_lv_search_results);  
        searchView = (SearchView) findViewById(R.id.main_search_layout);  
        //���ü���  
        searchView.setSearchViewListener(this);  
        //����adapter  
        searchView.setTipsHintAdapter(hintAdapter);  
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);  
  
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {  
                Toast.makeText(SearchActivity.this, position + "", Toast.LENGTH_SHORT).show();  
            }  
        });  
    }  
  
    /** 
     * ��ʼ������ 
     */  
    private void initData() {  
        //�����ݿ��ȡ����  
        getDbData();  
        //��ʼ�����Ѱ�����  
        getHintData();  
        //��ʼ���Զ���ȫ����  
        getAutoCompleteData(null);  
        //��ʼ�������������  
        getResultData(null);  
    }  
  
    /** 
     * ��ȡdb ���� 
     */  
    private void getDbData() {  
        int size = 100;  
        dbData = new ArrayList<Bean>(size);  
        for (int i = 0; i < size; i++) {  
            dbData.add(new Bean(R.drawable.icon, "���ξ���" + (i + 1), "��������������", i * 20 + 2 + ""));  
        }  
    }  
  
    /** 
     * ��ȡ���Ѱ�data ��adapter 
     */  
    private void getHintData() {  
        hintData = new ArrayList<String>(hintSize);  
        for (int i = 1; i <= hintSize; i++) {  
            hintData.add("����" + i + "�����ξ���");  
        }  
        hintAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hintData);  
    }  
  
    /** 
     * ��ȡ�Զ���ȫdata ��adapter 
     */  
    private void getAutoCompleteData(String text) {  
        if (autoCompleteData == null) {  
            //��ʼ��  
            autoCompleteData = new ArrayList<String>(hintSize);  
        } else {  
            // ����text ��ȡauto data  
            autoCompleteData.clear();  
            for (int i = 0, count = 0; i < dbData.size()  
                    && count < hintSize; i++) {  
                if (dbData.get(i).getTitle().contains(text.trim())) {  
                    autoCompleteData.add(dbData.get(i).getTitle());  
                    count++;  
                }  
            }  
        }  
        if (autoCompleteAdapter == null) {  
            autoCompleteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, autoCompleteData);  
        } else {  
            autoCompleteAdapter.notifyDataSetChanged();  
        }  
    }  
  
    /** 
     * ��ȡ�������data��adapter 
     */  
    private void getResultData(String text) {  
        if (resultData == null) {  
            // ��ʼ��  
            resultData = new ArrayList<Bean>();  
        } else {  
            resultData.clear();  
            for (int i = 0; i < dbData.size(); i++) {  
                if (dbData.get(i).getTitle().contains(text.trim())) {  
                    resultData.add(dbData.get(i));  
                }  
            }  
        }  
        if (resultAdapter == null) {  
            resultAdapter = new SearchAdapter(this, resultData, R.layout.list_item_searchresult);  
        } else {  
            resultAdapter.notifyDataSetChanged();  
        }  
    }  
  
    /** 
     * �������� �ı��ı�ʱ �����Ļص� ,�����Զ���ȫ���� 
     * 
     */  
    @Override  
    public void onRefreshAutoComplete(String text) {  
        //��������  
        getAutoCompleteData(text);  
    }  
  
    /** 
     * ���������ʱedit text�����Ļص� 
     * 
     * @param text 
     */  
    @Override  
    public void onSearch(String text) {  
        //����result����  
        getResultData(text);  
        lvResults.setVisibility(View.VISIBLE);  
        //��һ�λ�ȡ��� ��δ����������  
        if (lvResults.getAdapter() == null) {  
            //��ȡ�������� ����������  
            lvResults.setAdapter(resultAdapter);  
        } else {  
            //������������  
            resultAdapter.notifyDataSetChanged();  
        }  
        Toast.makeText(this, "�������", Toast.LENGTH_SHORT).show();  
    } 

}
