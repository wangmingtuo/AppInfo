package cn.appsys.service.dataDictionary;

import java.util.List;


import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
	
	/**
	 * 根据类型编码查询数据
	 * @param typeCode
	 * @return
	 */
	public List<DataDictionary> getDataDictionaryList(String typeCode);

}
