package cn.appsys.dao.appCategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.appsys.pojo.AppCategory;

@Repository
public interface AppCategoryMapper {
	
	/**
	 * 根据父级节点查询三级分类
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> getAppCategoryList(@Param("parentId")Integer parentId);

}
