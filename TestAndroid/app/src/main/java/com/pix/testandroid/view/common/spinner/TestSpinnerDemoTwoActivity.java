package com.pix.testandroid.view.common.spinner;

import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.pix.testandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestSpinnerDemoTwoActivity extends AppCompatActivity {
    private static final String TAG = "TestSpinnerDemoTwoActivity";
    private Spinner spinner_main_province;
    private Spinner spinner_main_city;

    private ArrayAdapter<String> cityAdapter = null;
    private XmlResourceParser pullParser = null;

    // 定义存放省份名称的集合、省份id的集合、城市名称的集合、城市id的集合
    private List<String> provinceNameList = new ArrayList<String>();
    private List<String> provinceIdList = new ArrayList<String>();
    private List<String> cityNameList = null;
    private List<String> cityIdList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_spinner_demo_two);
        setTitle(TAG);
        spinner_main_province = (Spinner) findViewById(R.id.spinner_main_province);
        spinner_main_city = (Spinner) findViewById(R.id.spinner_main_city);

        // 解析xml，获取到省份的数据
        List<Map<String, String>> list = getProvinceList();
        // 将省份的名称信息和id信息分别放到省份名称集合provinceNameList和省份id集合provinceIdList中。
        provinceNameList = new ArrayList<String>();
        provinceIdList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            provinceNameList.add(list.get(i).get("pn"));
            provinceIdList.add(list.get(i).get("p_id"));
        }

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, provinceNameList);
        spinner_main_province.setAdapter(provinceAdapter);

        cityAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        spinner_main_city.setAdapter(cityAdapter);

        // 给显示省份信息的spinner设置监听器
        spinner_main_province
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {

                        // 解析xml，获取到每个省份所对应的城市的数据
                        List<Map<String, String>> list = getCityList(provinceIdList
                                .get(position));
                        // 将城市的名称信息和id信息分别放到城市名称集合cityNameList和城市id集合cityIdList中。
                        cityNameList = new ArrayList<String>();
                        cityIdList = new ArrayList<String>();
                        for (int i = 0; i < list.size(); i++) {
                            cityNameList.add(list.get(i).get("d_name"));
                            cityIdList.add(list.get(i).get("d_id"));
                        }
                        // 清空适配器中数据
                        cityAdapter.clear();
                        // 往适配器中添加城市名称的集合数据
                        cityAdapter.addAll(cityNameList);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        // 给显示城市信息的spinner设置监听器
        spinner_main_city
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // 将城市的天气预告编号显示的titlebar上
                        setTitle("天气编号：" + cityIdList.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
    }


    // 解析xml，获取省份的名称和id
    public List<Map<String, String>> getProvinceList() {
        pullParser = this.getResources().getXml(R.xml.citys_weather);
        List<Map<String, String>> list = null;
        Map<String, String> map = null;
        try {
            int event = pullParser.getEventType();
            while (event != 1) {
                String nodeName = pullParser.getName();
                switch (event) {
                    case 0:
                        list = new ArrayList<Map<String, String>>();
                        break;
                    case 2:
                        if (nodeName.equals("p")) {
                            map = new HashMap<String, String>();
                            map.put("p_id", pullParser.getAttributeValue(0));
                            pullParser.next();
                            map.put("pn", pullParser.nextText());
                            list.add(map);
                        }
                        break;
                }
                event = pullParser.next();
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解析xml，获取城市的名称和id
    public List<Map<String, String>> getCityList(String p_id) {
        pullParser = this.getResources().getXml(R.xml.citys_weather);
        List<Map<String, String>> list = null;
        Map<String, String> map = null;
        try {
            int event = pullParser.getEventType();
            while (event != 1) {
                String nodeName = pullParser.getName();
                switch (event) {
                    case 0:
                        list = new ArrayList<Map<String, String>>();
                        break;
                    case 2:
                        if (nodeName.equals("d")) {
                            map = new HashMap<String, String>();
                            String d_id = pullParser.getAttributeValue(0);
                            if (d_id.substring(3).indexOf(p_id) == 0) {
                                map.put("d_id", d_id);
                                map.put("d_name", pullParser.nextText());
                                list.add(map);
                            }
                        }
                        break;
                }
                event = pullParser.next();
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
