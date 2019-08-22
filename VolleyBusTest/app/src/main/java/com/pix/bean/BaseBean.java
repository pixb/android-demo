package com.pix.bean;


import com.pix.http.BaseServerParser;
import com.pix.utils.ProtocolParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class BaseBean extends BaseServerParser implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String getString(JSONObject o, String key) {
		return ProtocolParser.getJsonStr(o, key, "");
	}

	protected int getInt(JSONObject o, String key) {
		return ProtocolParser.getJsonInt(o, key);
	}
	protected double getDouble(JSONObject o,String key){
		return ProtocolParser.getJsonDouble(o,key,-1);
	}

	protected long getLong(JSONObject o, String key) {
		return ProtocolParser.getJsonLong(o, key);
	}

	protected int getInt(JSONObject o, String key, int defaultValue) {
		return ProtocolParser.getJsonInt(o, key, defaultValue);
	}

	protected boolean getBoolean(JSONObject o, String key) {
		return ProtocolParser.getJsonBool(o, key);
	}
	
	protected JSONArray getArray(JSONObject o, String key) {
		return ProtocolParser.getArray(o, key);
	}
	
	protected String getArrayString(JSONArray array, int index) {
		return ProtocolParser.getArrayString(array, index);
	}
	
	protected JSONObject getJSONObject(JSONObject jsonObject, String key) {
		if(jsonObject == null)
			return null;
		
		try {
			if (jsonObject.has(key)) {
				return jsonObject.getJSONObject(key);
			}
		} catch (Exception e) {
		}
		
		return null;
	}
	
	protected JSONObject getJSONObject(JSONArray jsonArray,int index){
		if(jsonArray == null)
			return null;
		
		try{
			return jsonArray.getJSONObject(index);
		} catch (Exception e){
		}
		
		return null;
	}

	@Override
	public void parse(JSONObject contentJson) throws Exception {

	}
}
