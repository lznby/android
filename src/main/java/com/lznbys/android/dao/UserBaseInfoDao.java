package com.lznbys.android.dao;

import com.lznbys.android.entity.UserBaseInfoEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 用户基本信息 CURD接口类
 * <p>
 * 对应表格:app_user_base_info
 * 字段：
 */
@Repository
public interface UserBaseInfoDao {

    /**
     * 新增用户基本信息
     *
     * @param userBaseInfoEntity    用户基本信息
     */
    @Insert("INSERT INTO app_user_base_info" +
            "(userId,userNickName,userTel,userAge,userCounty,userCity,userSex,userSchool,userEdu,userEmail,userBirthday)  " +
            "VALUES (#{userBaseInfoEntity.userId},#{userBaseInfoEntity.userNickName},#{userBaseInfoEntity.userTel},#{userBaseInfoEntity.userAge},#{userBaseInfoEntity.userCounty},#{userBaseInfoEntity.userCity},#{userBaseInfoEntity.userSex},#{userBaseInfoEntity.userSchool},#{userBaseInfoEntity.userEdu},#{userBaseInfoEntity.userEmail},#{userBaseInfoEntity.userBirthday});")
    Integer insertUserBaseInfo(@Param("userBaseInfoEntity") UserBaseInfoEntity userBaseInfoEntity);

    /**
     * 修改用户基本信息
     *
     * @param userBaseInfoEntity    用户基本信息
     * @param userCookies           用户cookies
     * @return 返回修改记录数
     */
    @Update("UPDATE app_user_base_info,app_user_account_info " +
            "SET " +
            "app_user_base_info.userNickName = #{userBaseInfoEntity.userNickName} ," +
            "app_user_account_info.userNickName = #{userBaseInfoEntity.userNickName} ," +
            "app_user_base_info.userTel = #{userBaseInfoEntity.userTel}," +
            "app_user_base_info.userAge = #{userBaseInfoEntity.userAge}," +
            "app_user_base_info.userCounty = #{userBaseInfoEntity.userCounty}," +
            "app_user_base_info.userCity = #{userBaseInfoEntity.userCity}," +
            "app_user_base_info.userSex = #{userBaseInfoEntity.userSex}," +
            "app_user_base_info.userSchool = #{userBaseInfoEntity.userSchool}," +
            "app_user_base_info.userEdu = #{userBaseInfoEntity.userEdu}," +
            "app_user_base_info.userEmail = #{userBaseInfoEntity.userEmail}," +
            "app_user_base_info.userBirthday = #{userBaseInfoEntity.userBirthday}" +
            "WHERE (app_user_account_info.userCookies = #{userCookies} and app_user_base_info.userId = app_user_account_info.userId);")
    Integer updateUserBaseInfo(@Param("userBaseInfoEntity") UserBaseInfoEntity userBaseInfoEntity, @Param("userCookies") String userCookies);

    /**
     * 修改用户统计数据-关注、粉丝、主题
     *
     * @param userBaseInfoEntity    用户统计数据
     * @return  返回修改数量
     */
    @Update("UPDATE app_user_base_info " +
            "SET " +
            "followCount = #{userBaseInfoEntity.followCount}," +
            "followerCount = #{userBaseInfoEntity.followerCount}," +
            "themeCount = #{userBaseInfoEntity.themeCount} " +
            "WHERE (app_user_base_info.userId = #{userBaseInfoEntity.userId})"
    )
    Integer updateUserCountInfo(@Param("userBaseInfoEntity") UserBaseInfoEntity userBaseInfoEntity);

    /**
     * 1.查询 cookies 是否过期
     * 2.根据 cookies 查询用户基本信息
     *
     * @param userCookies           用户 cookies
     * @return                      成功:用户基本信息
     */
    @Select("SELECT * FROM app_user_base_info WHERE app_user_base_info.userId = (SELECT app_user_account_info.userId FROM app_user_account_info WHERE app_user_account_info.userCookies = #{userCookies})")
    @ResultType(UserBaseInfoEntity.class)
    UserBaseInfoEntity findUserInfoByCookies(@Param("userCookies") String userCookies);

    /**
     * 根据用户userId查询用户基本信息
     *
     * @param userId          用户昵称
     * @return                      成功:用户基本信息
     */
    @Select("SELECT * FROM app_user_base_info WHERE userId = #{userId}")
    @ResultType(UserBaseInfoEntity.class)
    UserBaseInfoEntity findUserInfoByUserId(@Param("userId") String userId);

    /**
     * TODO 修改用户头像的接口
     */


    /**
     * 获取所有用户基本信息
     *
     * @return
     */
    @Select("SELECT * FROM app_user_base_info")
    @ResultType(UserBaseInfoEntity.class)
    List<UserBaseInfoEntity> findAllUserBaseInfo();
}
