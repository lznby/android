package com.lznbys.android.dao;

import com.lznbys.android.entity.UserAccountEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户账号信息 CURD接口类
 *
 * 对应表格:app_user_account_info
 * 字段：
 */
@Repository
public interface UserAccountDao {
    /**
     * 新增用户
     * @param userAccountEntity 用户账号信息
     */
    @Insert("INSERT INTO app_user_account_info(userNickName,userPassword) " +
            "VALUES(#{userAccountEntity.userNickName},#{userAccountEntity.userPassword})")
    // 将自动生成的主键重新设置到实体中，便于业务逻辑处理
    @Options(useGeneratedKeys = true, keyProperty = "userAccountEntity.userId")
    Integer insertUserAccount(@Param("userAccountEntity")UserAccountEntity userAccountEntity);

    /**
     * 查询用户信息（根据用户昵称）
     * 1.用于检测该昵称是否被占用
     * @param userNickName      用户昵称
     * @return                  返回查询到用户信息
     */
    @Select("SELECT * FROM app_user_account_info WHERE userNickName = #{userNickName}")
    @ResultType(UserAccountEntity.class)
    UserAccountEntity checkNickName(@Param("userNickName") String userNickName);

    /**
     * 修改用户密码
     * @param newUserCookies    新的cookies
     * @param newUserPassword   新密码
     * @param userPassword      用户原密码
     * @param userCookies       用户原cookies
     * @return
     */
    @Update("UPDATE app_user_account_info " +
            "SET userCookies = #{newUserCookies},userPassword = #{newUserPassword} " +
            "WHERE (userPassword = #{userPassword} AND userCookies = #{userCookies}); ")
    Integer updateUserPassword(@Param("newUserCookies")String newUserCookies, @Param("newUserPassword") String newUserPassword, @Param("userPassword") String userPassword, @Param("userCookies")String userCookies);

    /**
     * 修改用户昵称
     * @param newUserNickName   修改后的昵称
     * @param userCookies       修改昵称的账号cookies
     * @return                  返回修改的记录条数
     */
    @Update("UPDATE (app_user_base_info,app_user_account_info) " +
            "SET app_user_base_info.userNickName=#{newUserNickName} ,app_user_account_info.userNickName= #{newUserNickName} " +
            "WHERE (app_user_account_info.userCookies = #{userCookies} and app_user_base_info.userNickName = app_user_account_info.userNickName);")
    Integer updateUserNickName(@Param("newUserNickName")String newUserNickName,@Param("userCookies")String userCookies);


    /**
     * 修改session（用户登录）
     * @param userCookies       用户cookies（系统新生成的）
     * @param userNickName      用户昵称
     * @param userPassword      用户密码
     * @return                  >=1表示登录成功
     */
    @Update("UPDATE app_user_account_info " +
            "SET userCookies = #{userCookies} " +
            "WHERE (userNickName = #{userNickName} AND userPassword = #{userPassword})")
    Integer updateUserAccountInfo(@Param("userCookies") String userCookies,@Param("userNickName") String userNickName,@Param("userPassword") String userPassword);

    /**
     * 修改session（用户登录）
     * @param userCookies       用户cookies（旧Cookies）
     * @param newCookies        用户新cookies
     * @return                  >=1表示登出成功
     */
    @Update("UPDATE app_user_account_info " +
            "SET userCookies = #{newCookies} " +
            "WHERE userCookies = #{userCookies}")
    Integer updateUserCookies(@Param("userCookies") String userCookies,@Param("newCookies") String newCookies);


}
