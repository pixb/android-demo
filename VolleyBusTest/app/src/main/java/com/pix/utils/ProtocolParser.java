package com.pix.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProtocolParser {
	private static final String nullString = "null";

	public static String getJsonStr(JSONObject jsonObj, String name) {
		return getJsonStr(jsonObj, name, null);
	}

	public static int getJsonInt(JSONObject jsonObj, String name) {
		return getJsonInt(jsonObj, name, -1);
	}
	
	public static long getJsonLong(JSONObject jsonObj, String name) {
		return getJsonLong(jsonObj, name, -1);
	}
	
	public static boolean getJsonBool(JSONObject jsonObj, String name) {
		return getJsonBool(jsonObj, name, false);
	}
	
	public static String getJsonStr(JSONObject jsonObj, String name, String defaultValue) {
		if (jsonObj == null){
			return  defaultValue;
		}
		try {
			if (jsonObj.has(name)) {
				if (jsonObj.getString(name).equals(nullString)) {
					return "";
				} else {
					return jsonObj.getString(name);
				}
			}
		} catch (Exception e){
		}
		
		return defaultValue;
	}

	public static double getJsonDouble(JSONObject jsonObject,String name,double defaultValue){
		if (jsonObject == null){
			return defaultValue;
		}
		try {
			if (jsonObject.has(name)) {
				return jsonObject.getDouble(name);
			}
		} catch (Exception e){
		}

		return defaultValue;
	}

	public static int getJsonInt(JSONObject jsonObj, String name, int defaultValue) {
		if (jsonObj == null){
			return defaultValue;
		}
		try {
			if (jsonObj.has(name)) {
				return jsonObj.getInt(name);
			}
		} catch (Exception e){
		}
		
		return defaultValue;
	}

	public static long getJsonLong(JSONObject jsonObj, String name, long defaultValue) {
		if (jsonObj == null){
			return defaultValue;
		}
		try {
			if (jsonObj.has(name)) {
				return jsonObj.getLong(name);
			}
		} catch (Exception e){
		}
		
		return defaultValue;
	}

	public static JSONObject getJsonObj(JSONObject jsonArr, String name) {
		if (jsonArr == null){
			return null;
		}
		try {
			if (jsonArr.has(name)) {
				return jsonArr.getJSONObject(name);
			}
		} catch (Exception e){
		}
		
		return null;
	}

	public static boolean getJsonBool(JSONObject jsonObj, String name, boolean defaultValue) {
		if (jsonObj == null){
			return defaultValue;
		}
		try {
			if (jsonObj.has(name)) {
				return jsonObj.getBoolean(name);
			}
		} catch (Exception e){
		}
		
		return defaultValue;
	}
	
	public static JSONArray getArray(JSONObject o, String key) {
		if(o == null || !o.has(key))
			return null;
		
		try {
			return o.getJSONArray(key);
		} catch (Exception e){
		}
		
		return null;
	}
	
	public static String getArrayString(JSONArray array, int index) {
		if (array == null){
			return "";
		}
		try {
			return array.getString(index);
		} catch (Exception e){
		}
		
		return "";
	}
	
}
