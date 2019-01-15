package com.lznbys.android.dao;

import com.lznbys.android.entity.UserFollowInfoEntity;
import com.lznbys.android.entity.UserFollowerEntity;
import com.lznbys.android.entity.UserFollowerInfoEntity;
import com.lznbys.android.entity.UserFollowerSizeEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户关注、粉丝 CURD接口
 *
 * 对应表格:app_user_follower_info
 * 关联表格:app_user_base_info
 */
@Repository
public interface UserFollowerDao {
    /**
     * 新增关注
     * @param userFollowerEntity    关注信息
     * @return                      返回新增记录条数
     */
    @Insert("INSERT INTO app_user_follower_info" +
            "(id,userId,followId) " +
            "VALUES (#{userFollowerEntity.id},#{userFollowerEntity.userId},#{userFollowerEntity.followId});"
    )
    Integer insertUserFollowerInfo(@Param("userFollowerEntity")UserFollowerEntity userFollowerEntity);

    /**
     * 取消关注
     * @param followId              被取消关注的ID
     * @param userId                发起取消关注者ID
     * @return                      返回删除记录条数
     */
    @Delete("DELETE FROM app_user_follower_info WHERE (app_user_follower_info.userId = #{userId} AND app_user_follower_info.followId = #{followId});"
    )
    Integer deleteUserFollowerInfo(@Param("userId") String userId, @Param("followId") String followId);

    /**
     * 查询是否已经关注
     * @param userId                发起关注者ID
     * @param followId              被关注者ID
     * @return                      查询结果
     */
    @Select("SELECT * FROM app_user_follower_info WHERE (app_user_follower_info.userId = #{userId} AND app_user_follower_info.followId = #{followId})")
    @ResultType(UserFollowerEntity.class)
    List<UserFollowerEntity> isFollower(@Param("userId") String userId,@Param("followId") String followId);

    /**
     * 通过id查询是否已经关注
     * @param id                    记录id
     * @return                      查询结果
     */
    @Select("SELECT * FROM app_user_follower_info WHERE (app_user_follower_info.id = #{id})")
    @ResultType(UserFollowerEntity.class)
    List<UserFollowerEntity> isFollowerById(@Param("id") String id);

//    /**
//     * 返回某个人所有关注信息(基本信息)
//     * @param userId                用户ID
//     * @return                      所有关注信息的List
//     */
//    @Select("SELECT app_user_follower_info.followId,app_user_follower_info.id,app_user_base_info.userNickName,app_user_base_info.userMotto,app_user_base_info.userHeaderUrl,app_user_base_info.userSex,app_user_base_info.userCounty,app_user_base_info.userCity " +
//            "FROM app_user_follower_info,app_user_base_info " +
//            "WHERE (app_user_follower_info.followId = app_user_base_info.userId and app_user_follower_info.userId = #{userId})")
//    @ResultType(UserFollowInfoEntity.class)
//    List<UserFollowInfoEntity> findAllUserFollowByUserId(@Param("userId") String userId);

    /**
     * 返回某个人所有关注信息(基本信息)
     * @param userId                用户ID
     * @param queryId               发起查询者的ID
     * @return                      所有关注信息的List
     */
    @Select("SELECT " +
            "aa.id,aa.followId,aa.userNickName,aa.userMotto,aa.userHeaderUrl,aa.userSex,aa.userCounty,aa.userCity, " +
            "if (bb.followId is null,'0','1') as 'isFollow' " +
            "from (" +
            "select " +
            "app_user_follower_info.id,app_user_follower_info.followId,app_user_base_info.userId,app_user_base_info.userNickName,app_user_base_info.userMotto," +
            "app_user_base_info.userHeaderUrl,app_user_base_info.userSex,app_user_base_info.userCounty,app_user_base_info.userCity " +
            "from " +
            "android_graduation_design.app_user_follower_info,android_graduation_design.app_user_base_info " +
            "where " +
            " app_user_follower_info.userId = #{userId} and app_user_follower_info.followId = app_user_base_info.userId" +
            ") aa " +
            "left join (" +
            "select * from android_graduation_design.app_user_follower_info where app_user_follower_info.userId = #{queryId}" +
            ") bb on aa.followId = bb.followId")
    @ResultType(UserFollowInfoEntity.class)
    List<UserFollowInfoEntity> findAllUserFollowByUserId(@Param("userId") String userId,@Param("queryId") String queryId);

//    /**
//     * 返回某个人所有粉丝信息(基本信息)
//     * @param userId                用户ID
//     * @return                      所有粉丝信息的List
//     */
//    @Select("SELECT app_user_base_info.userId,app_user_follower_info.id,app_user_base_info.userNickName,app_user_base_info.userMotto,app_user_base_info.userHeaderUrl,app_user_base_info.userSex,app_user_base_info.userCounty,app_user_base_info.userCity " +
//            "FROM app_user_follower_info,app_user_base_info " +
//            "WHERE (app_user_follower_info.userId = app_user_base_info.userId AND app_user_follower_info.followId = #{userId})")
//    @ResultType(UserFollowerInfoEntity.class)
//    List<UserFollowerInfoEntity> findAllUserFollowerByUserId(@Param("userId") String userId);

    /**
     * 返回某个人所有粉丝信息(基本信息)
     * @param userId                用户ID
     * @return                      所有粉丝信息的List
     */
    @Select("SELECT " +
            "aa.id,aa.userId,aa.userNickName,aa.userMotto,aa.userHeaderUrl,aa.userSex,aa.userCounty,aa.userCity, " +
            "if (bb.userId is null,'0','1') as 'isFollow' " +
            "from (" +
            "select " +
            "app_user_follower_info.id,app_user_follower_info.followId,app_user_base_info.userId,app_user_base_info.userNickName,app_user_base_info.userMotto," +
            "app_user_base_info.userHeaderUrl,app_user_base_info.userSex,app_user_base_info.userCounty,app_user_base_info.userCity " +
            "from " +
            "android_graduation_design.app_user_follower_info,android_graduation_design.app_user_base_info " +
            "where " +
            " app_user_follower_info.followId = #{userId} and app_user_follower_info.userId = app_user_base_info.userId" +
            ") aa " +
            "left join (" +
            "select * from android_graduation_design.app_user_follower_info where app_user_follower_info.userId = #{queryId}" +
            ") bb on aa.userId = bb.followId")
    @ResultType(UserFollowerInfoEntity.class)
    List<UserFollowerInfoEntity> findAllUserFollowerByUserId(@Param("userId") String userId,
                                                             @Param("queryId") String queryId);

    /**
     * 返回某个人的粉丝数目和关注数目
     *
     * @param userId                用户ID
     * @return                      关注数及粉丝数
     */
    @Select("SELECT " +
            "(SELECT COUNT(1) FROM app_user_follower_info WHERE app_user_follower_info.userId = #{userId}) AS 'followSize'," +
            "(SELECT COUNT(1) FROM app_user_follower_info WHERE app_user_follower_info.followId = #{userId}) AS 'followerSize' " +
            "FROM app_user_follower_info " +
            "GROUP BY (app_user_follower_info.userId AND app_user_follower_info.followId);")
    @ResultType(UserFollowerSizeEntity.class)
    UserFollowerSizeEntity getUserFollowerSizeById(@Param("userId") String userId);


}
