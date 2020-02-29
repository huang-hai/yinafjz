package com.yinafjz.cleaning.user.model;

/**
 * @Title: EvaluationModel.java
 * @Package com.yinafjz.cleaning.user.model
 * @Description: 服务评价实体类
 * @author Huanghai
 * @date 2018-12-3 下午6:00:39
 * @version V1.0
 */
public class EvaluationModel {

	private int evalId;// ` int(11) NOT NULL AUTO_INCREMENT COMMENT '服务评价',
	private int userId;// ` int(11) DEFAULT NULL COMMENT '用户id',
	private int orderId;// ` int(11) DEFAULT NULL COMMENT '订单id',
	private int eval;// ` int(1) DEFAULT '5' COMMENT '服务指数(1一颗星 2两颗星 3三颗星 4四颗星
						// 5五颗星)',
	private String opinion;// ` varchar(250) DEFAULT NULL COMMENT '服务意见',
	private int auntId; // 服务人员id
	private Integer goodsId;//` int(11) DEFAULT NULL COMMENT '商品id',
	private String userName;//` varchar(200) DEFAULT NULL COMMENT '用户昵称',
	private Integer nannyId;//保姆ID
	private String createTime;

	/**
	 * 一颗星
	 */
	public static int EVAL_ONE = 1;
	/**
	 * 两颗星
	 */
	public static int EVAL_TWO = 2;
	/**
	 * 三颗星
	 */
	public static int EVAL_THREE = 3;
	/**
	 * 四颗星
	 */
	public static int EVAL_FOUR = 4;
	/**
	 * 五颗星
	 */
	public static int EVAL_FIVE = 5;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getNannyId() {
		return nannyId;
	}

	public void setNannyId(Integer nannyId) {
		this.nannyId = nannyId;
	}

	public int getEvalId() {
		return evalId;
	}

	public void setEvalId(int evalId) {
		this.evalId = evalId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getEval() {
		return eval;
	}

	public void setEval(int eval) {
		this.eval = eval;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public int getAuntId() {
		return auntId;
	}

	public void setAuntId(int auntId) {
		this.auntId = auntId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
