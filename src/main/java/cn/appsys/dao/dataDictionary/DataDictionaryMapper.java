package cn.appsys.dao.dataDictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.appsys.pojo.DataDictionary;

@Repository
public interface DataDictionaryMapper {
	
	/**
	 * 根据类型编码查询数据
	 * @param typeCode
	 * @return
	 */
	public List<DataDictionary> getDataDictionaryList(@Param("typeCode")String typeCode);

}
