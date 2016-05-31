package org.wuzl.client.es.jdbc.dao;

import java.util.List;
import java.util.Map;

import org.wuzl.client.es.jdbc.dao.support.BaseDao;
import org.wuzl.client.es.jdbc.dao.support.BaseMapMapper;

public class UserDao extends BaseDao {

	public List<Map<String, Object>> getUserListByIdAndTime(long minId,
			long limit) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select  user.uid as userUid, user.password as userPassword, user.nick as userNick, user.sex as userSex, user.regtime as userRegtime, user.loginnum as userLoginnum, user.lastlogin as userLastlogin,");
		sql.append("         user.onlinetime as userOnlinetime, user.lastactivity as userLastactivity, user.regip as userRegip, user.init_password as userInitPassword, user.regcountry as userRegcountry, user.lastloginip as userLastloginip, ");
		sql.append("         user.lastlogincountry as userLastlogincountry, user.logindays as userLogindays, user.group_id as userGroupId, user.nicklastupdate as userNicklastupdate, user.platform as userPlatform, user.push as userPush,");
		sql.append("         user.topicnum as userTopicnum, user.replynum as userReplynum, user.favnum as userFavnum, user.score as userScore, user.credit as userCredit, user.nick as userFullNick,");
		// mobile email
		sql.append("         user_mobile.mobile as userMobile, user_mobile.verifytime as userMobileVerifytime, user_email.email as userEmail,user_email.dateline as userEmailDataline,");
		// 扩展信息
		sql.append("         user_extra.period as userExtraPeriod, user_extra.cycle as userExtraCycle, user_extra.keyword as userExtraKeyword, user_extra.dateline as userExtraDateline, user_extra.updatetime as userExtraUpdatetime, ");
		sql.append("         user_extra.age as userExtraAge, user_extra.menarche as userExtraMenarche, user_extra.last_period as userExtraLastPeriod, user_extra.recent_symptom as userExtraRecentSymptom,");
		sql.append("         user_extra.recent_symptom_user as userExtraRecentSymptomUser, user_extra.extra_info as userExtraExtraInfo, user_extra.group_change_time as userExtraGroupChangeTime, ");
		sql.append("         user.uid as _id");
		sql.append("   from user");
		sql.append("   left join user_mobile ON user.uid = user_mobile.uid");
		sql.append("   left join user_email ON user.uid = user_email.uid");
		sql.append("   left join user_extra ON user.uid = user_extra.uid");
		sql.append("  where user.uid > ?");
		sql.append("  order by user.uid asc");
		sql.append(" limit ? ");

		return executeQuery(sql.toString(), new Object[] { minId, limit },
				new BaseMapMapper());
	}
}
