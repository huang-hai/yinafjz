package com.yinafjz.cleaning.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.user.model.MsgModel;

/**
 * @Title: MsgDao.java
 * @Package com.yinafjz.cleaning.user.dao
 * @Description: 消息dao
 * @author Huanghai
 * @date 2018-12-21 下午2:35:00
 * @version V1.0
 */
public interface MsgDao {

	/**
	 * @Title: addMsg
	 * @Description: 添加消息
	 * @param model
	 * @return
	 * @throws
	 */
	public int addMsg(MsgModel model) throws YinafjzException;

	/**
	 * @Title: addAuntMsg
	 * @Description: 添加一条阿姨消息
	 * @param model
	 * @return
	 * @throws
	 */
	public int addAuntMsg(MsgModel model) throws YinafjzException;

	/**
	 * @Title: findMsgsByUserId
	 * @Description: 获取用户的消息列表
	 * @param userId
	 * @param type
	 *            0交易提醒 1平台通知
	 * @return
	 * @throws
	 */
	public List<MsgModel> findMsgsByUserId(@Param("userId") int userId,
			@Param("type") int type) throws YinafjzException;

	/**
	 * @Title: findNotReaderByUserId
	 * @Description: 查询用未读信息数量
	 * @param userId
	 * @return
	 * @throws
	 */
	public int findNotReaderByUserId(int userId) throws YinafjzException;

	/**
	 * @Title: markRead
	 * @Description: 标为已读
	 * @param userId
	 * @return
	 * @throws
	 */
	public int markRead(int userId);

}
