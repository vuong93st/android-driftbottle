package com.douya.bottle.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDataGetApi extends WebDataGetApi {
	private static final String BASE_URL = "http://10.0.2.2:9999/AccountService/";
	private static final String EXTENSION = "Json/";;

	public JSONObject getObject(String sbj) throws JSONException, Exception {
		return new JSONObject(getRequest(BASE_URL + EXTENSION + sbj));
	}

	public JSONArray getArray(String sbj) throws JSONException, Exception {
		return new JSONArray(getRequest(BASE_URL + EXTENSION + sbj));
	}
}
