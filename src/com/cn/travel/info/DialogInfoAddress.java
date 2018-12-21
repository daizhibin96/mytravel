package com.cn.travel.info;


import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import com.cn.travle.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class DialogInfoAddress extends Dialog {

    private Context context;
    private String province;
    private DialogInfoAddress.ClickListenerInterface clickListenerInterface;
    private String[] provinces,cities;
    private Resources res;
    private String selectedProvince;
    private int currentProvince,currentCity;

    private ListView lv_city,lv_province;
    private ArrayAdapter adapter;


    //实例化Diolog1，传入参数（调用Activity的context，之前选择过得省份城市）
    public DialogInfoAddress(Context context , String province) {
        super(context);
        this.context = context;
        res =context.getResources();
        selectedProvince = province;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        //获取省份String数组
        provinces=res.getStringArray(R.array.province);

        //判断传进来的字符串是否为空
        if (selectedProvince != null && !selectedProvince.isEmpty()){
            //传进来的字符串不为空，用分隔符裁切成字符串数组
            //传进来的字符串是类似这样的"北京  东城"，我设置的是用两个空格分隔
            String[] s = selectedProvince.split("  ");//这个分隔符要与UserInfoActivity设置的间隔符一致

            //for循环对比是哪个省份，获取省份在数组中的第i位
            for (int i = 0 ; i < provinces.length ; i++){
                if (s[0].equals(provinces[i])){
                    currentProvince = i;
                    break;
                }
            }
            //根据省份获取城市的字符串数组
            cities = getCities(currentProvince -1);
            //for循环对比是哪个城市，获取城市在数组中的第i位
            for (int i = 0 ; i < cities.length ; i++){
                if ( s[1] .equals(cities[i])){
                    currentCity = i;
                    break;
                }
            }
            //设置默认的省份名称
            province = s[0];
        }else {
            //传进来的省份城市字符串为空，则设置默认的省份为北京
            cities = res.getStringArray(R.array.北京);
            province = "北京";
        }

        //加载dialog布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_address, null);
        setContentView(view);

        lv_province = (ListView)view.findViewById(R.id.lv_dialog1_1);
        lv_city = (ListView)view.findViewById(R.id.lv_dialog1_2);
        Button bt = (Button)view.findViewById(R.id.bt_dialog1);
        bt.setOnClickListener(new clickListener());

        //给省份listview设置适配器，并设置滑动到默认位置
        // （注意：我们要让目标省份显示在中间，所以滑动到 currentProvince - 1 的位置）
        lv_province.setAdapter(new ArrayAdapter<String>(context,R.layout.list_item_address,provinces));
        lv_province.setSelection(currentProvince - 1);

        //给省城市listview设置适配器，并设置滑动到默认位置
        adapter = new ArrayAdapter<String>(context,R.layout.list_item_address,cities);
        lv_city.setAdapter(adapter);
        lv_city.setSelection(currentCity - 1);

        /*
        对省份listview设置滑动监听，滑动停止的时候给城市listview重新获取字符串数组，
        并重新设置设置适配器（这一步应该是可以让适配器执行notifyDataSetChanged()的）,
        但是我对ArrayAdapter的notifyDataSetChanged()方法不太确定怎么用
         */
        lv_province.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                OnScrollListener.SCROLL_STATE_IDLE：滚动停止时的状态
//                OnScrollListener.SCROLL_STATE_STOUCH_SCROLL：触摸正在滚动，手指还没离开界面时的状态
//                OnScrollListener.SCROLL_STATE_FLING：用户在用力滑动后，ListView由于惯性将继续滑动时的状态
                    if (scrollState == SCROLL_STATE_IDLE){
                        int position = lv_province.getFirstVisiblePosition() ;
                        province = provinces[position+1];
                        cities = getCities(position);
                        adapter = new ArrayAdapter<String>(context,R.layout.list_item_address,cities);
                        lv_city.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
                    }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    public interface ClickListenerInterface {
        //这里只设置了一个按钮，有需要可以自己添加
        void doConfirm();
        void doCancel();//可以设置多个方法和多个按钮对应
    }

    //监听点击按钮的回调
    public void setClicklistener(DialogInfoAddress.ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.bt_dialog1:
                    clickListenerInterface.doConfirm();
                    break;
                    //多个按钮在这添加case
                default:
                    break;
            }
        }
    }

    //两个get方法
    public String getProvince() {
        return province;
    }
    public String getCity() {
        return cities[lv_city.getFirstVisiblePosition()+1];
    }

    //根据省份获取城市字符串数组
    private String[] getCities(int position){
        switch (position){
            case 0 :
                cities = res.getStringArray(R.array.北京);
                break;
            case 1 :
                cities = res.getStringArray(R.array.天津);
                break;
            case 2 :
                cities = res.getStringArray(R.array.河北);
                break;
            case 3 :
                cities = res.getStringArray(R.array.山西);
                break;
            case 4 :
                cities = res.getStringArray(R.array.内蒙古);
                break;
            case 5 :
                cities = res.getStringArray(R.array.辽宁);
                break;
            case 6 :
                cities = res.getStringArray(R.array.吉林);
                break;
            case 7 :
                cities = res.getStringArray(R.array.黑龙江);
                break;
            case 8 :
                cities = res.getStringArray(R.array.上海);
                break;
            case 9 :
                cities = res.getStringArray(R.array.江苏);
                break;
            case 10 :
                cities = res.getStringArray(R.array.浙江);
                break;
            case 11 :
                cities = res.getStringArray(R.array.安徽);
                break;
            case 12 :
                cities = res.getStringArray(R.array.福建);
                break;
            case 13 :
                cities = res.getStringArray(R.array.江西);
                break;
            case 14 :
                cities = res.getStringArray(R.array.山东);
                break;
            case 15 :
                cities = res.getStringArray(R.array.河南);
                break;
            case 16 :
                cities = res.getStringArray(R.array.湖北);
                break;
            case 17 :
                cities = res.getStringArray(R.array.湖南);
                break;
            case 18 :
                cities = res.getStringArray(R.array.广东);
                break;
            case 19 :
                cities = res.getStringArray(R.array.广西);
                break;
            case 20 :
                cities = res.getStringArray(R.array.海南);
                break;
            case 21 :
                cities = res.getStringArray(R.array.重庆);
                break;
            case 22 :
                cities = res.getStringArray(R.array.四川);
                break;
            case 23 :
                cities = res.getStringArray(R.array.贵州);
                break;
            case 24 :
                cities = res.getStringArray(R.array.云南);
                break;
            case 25 :
                cities = res.getStringArray(R.array.西藏);
                break;
            case 26 :
                cities = res.getStringArray(R.array.陕西);
                break;
            case 27 :
                cities = res.getStringArray(R.array.甘肃);
                break;
            case 28 :
                cities = res.getStringArray(R.array.青海);
                break;
            case 29 :
                cities = res.getStringArray(R.array.宁夏);
                break;
            case 30 :
                cities = res.getStringArray(R.array.新疆);
                break;
            case 31 :
                cities = res.getStringArray(R.array.台湾);
                break;
            case 32 :
                cities = res.getStringArray(R.array.香港);
                break;
            case 33 :
                cities = res.getStringArray(R.array.澳门);
                break;
        }
        return cities;
    }
}