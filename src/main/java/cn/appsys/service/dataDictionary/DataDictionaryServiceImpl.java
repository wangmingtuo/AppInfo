package cn.appsys.service.dataDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.dataDictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Resource
	private DataDictionaryMapper dataDictionary;
	
	@Override
	public List<DataDictionary> getDataDictionaryList(String typeCode) {
		return dataDictionary.getDataDictionaryList(typeCode);
	}

}
