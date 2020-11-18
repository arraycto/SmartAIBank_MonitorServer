package com.dcfs.smartaibank.manager.base.dao;

import com.dcfs.smartaibank.manager.base.domain.SpareDataDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**备选数据
 * @author wangtingo
 */
@Mapper
public interface SpareDataDao {
    /**
     * 获取机构id —— 名称
     * @return
     * @param branchNo
     */
    /*List<SpareDataDomain> getAllOrgList(@Param("branchNo") String branchNo);*/

    /**
     * 获取机构类型id —— 类型名称
     * @return
     */
    List<SpareDataDomain> getAllOrgTypeList();

    /**
     * 获取机构级别id —— 级别名称
     * @return
     */
    List<SpareDataDomain> getOrgLevelList();

    /**
     * 获取报表机构级别id —— 级别名称
     * @return
     */
    List<SpareDataDomain> getAllOrgLevelList();

    /**
     * 获取介质类型id —— 类型名称
     * @return
     */
    List<SpareDataDomain> getAllMediaTypeList();

    /**
     * 获取角色id —— 角色名称
     * @return
     */
    /*List<SpareDataDomain> getAllRoleList();*/

    /**
     * 获取用户id —— 用户名称
     * @return
     */
   /* List<SpareDataDomain> getAllUserList();*/

    /**
     * 获取系统id —— 系统名称
     * @return
     */
    List<SpareDataDomain> getAllSystemList();
    /**
     * 获取code —— 用户类型名称
     * @return
     */
    /*List<SpareDataDomain> getUserTypeList();*/

    /**
     * 获取部门id —— 部门名称
     * @return
     */
    /*List<SpareDataDomain> getDepartmentList();*/

    /**
     * 获取部门id —— 部门名称
     * @return
     */
    /*List<SpareDataDomain> getBusinBranchOrg();*/

}
