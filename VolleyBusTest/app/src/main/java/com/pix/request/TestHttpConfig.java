package com.pix.request;
import com.pix.http.IHttpConfig;

import java.util.HashMap;

public class TestHttpConfig implements IHttpConfig {

	@Override
	public HashMap<String, String> getHeader() {
		HashMap<String, String> headers = new HashMap<String, String>();
		String MOBILE = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;// 操作系统版本
		if (MOBILE.length() > 20) { //服务器数据库表字段设置最多20个字符
			MOBILE = MOBILE.substring(0, 20);
		}
		String SV = android.os.Build.VERSION.RELEASE;// 操作系统版本
		headers.put("sy", SV);// 系统版本 如：2.0，2.2，2.2.1，3.1，3.1.1，3.1.2
		headers.put("mobile", MOBILE);// 设备型号
		return headers;
	}
}
