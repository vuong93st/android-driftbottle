package com.douya.base.dao.pagination;

import java.util.Map;

import com.douya.base.util.WebUtils;

public class PaginationService {
	private static final String TAG = "MainService";
	private static PaginationService paginationService;

	private PaginationService() {
	}

	public static PaginationService getInstance() {
		if (null == paginationService) {
			paginationService = new PaginationService();
		}
		return paginationService;
	}

	public String loadPaginationData(String actionUrl,
			Map<String, String> fields) {
		return WebUtils.httpPost(actionUrl, fields);
	}
	
}
