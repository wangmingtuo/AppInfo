package cn.appsys.service.appCategory;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {
	
	/**
	 * 根据父级节点查询三级分类
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> getAppCategoryList(Integer parentId);

}
