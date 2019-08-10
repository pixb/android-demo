package com.pix.testandroid.view.common.spinner;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.pix.testandroid.R;

/**
 * 使用Spinner实现联动菜单的Demo
 */
public class TestSpinnerConnectionMenuActivity extends Activity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "TestSpinnerConnectionMenuActivity";
    private Spinner mProvinceSpinner;
    private Spinner mCitySpinner;
    //省级数组
    private String [] arrProvinces ;
    //市级数组
    private String [] [] arrCitys ;
    private ArrayAdapter<String> cityAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_spinner_connection_menu);
        setTitle(TAG);
        arrProvinces = new String[] {
                "北京","上海","天津"
        } ;
        arrCitys = new String [] [] {{"海淀","朝阳","石景山","东城","西城"},{"宝山","浦东","闵行"},
                {"宝坻","武清","塘沽"}} ;
        mProvinceSpinner = (Spinner) findViewById(R.id.spinner_main_province) ;
        mCitySpinner = (Spinner) findViewById(R.id.spinner_main_city);
        //创建省级适配器
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,arrProvinces) ;
        mProvinceSpinner.setAdapter(provinceAdapter);
        //市级适配器
        cityAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1) ;
        //为省级的加选择的监听器
        mProvinceSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long id) {
        cityAdapter.clear(); 		//清空适配器中的数据,处理二级菜单中的上次的数据
        //为城市适配器增加数据
        cityAdapter.addAll(arrCitys[position]);
        mCitySpinner.setAdapter(cityAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }
}
