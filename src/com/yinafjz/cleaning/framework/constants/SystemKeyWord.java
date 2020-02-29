package com.yinafjz.cleaning.framework.constants;

//系统常量配置
public final class SystemKeyWord {
	
	public static String APP_WEB_INFO_PATH   = "";                         //WEB-INFO的绝对路径 程序启动时赋值
	public static String FILE_SEPARATOR      = "/";                        //文件分割符 程序启动时赋值
    public static final String USER_KEY_CACHE = "user_key";
    public static final String VALIDATION_CODE = "validation_code";
    public static final String JSON_NAME = "json";
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String JSON_VIEW = "/yinaf/json.jsp";
    public static final String PP_SYSPATH_PREFIX = "syspath_";
    
    public static int    SMS_VALID_CODE_LENGTH = 6;                               	//手机短信验证码的长度
	public static String DEFAULT_CACHE_PREFIX_KEY_TASK = "task"; 					//任务列表
	public static String DEFAULT_CACHE_PREFIX_KEY_MSG_NUM ="unread_msg_num";   		//消息缓存前缀
	
	public static String MOBILE_SEND_CODE_LENGTH="6";						 //短信验证码的长度
	public static String MOBILE_SEND_NONCE = "yinaf";						 //网易云信短信随机数
	public static final String MOBLIE_CACHE_SEND_CODE = "MOBLIE_CACHE_SEND_CODE";  //短信验证码在app缓存中的KEY前缀+watch
	
	//des3ecd加密解密key
	public static byte[] DES3_DES_KEY = {0x11,0x22,0x4F,0x58,(byte)0x88,0x10,0x40,0x38,0x28,0x25,0x79,0x51,(byte)0xCB,(byte)0xDD,0x55,0x66,0x77,0x29,0x74,(byte)0x98,0x30,0x40,0x36,(byte)0xE2};//des3EncodeECB加密key(key长度有限制24位)
	public static String DICT_SPLIT_KEY = "@#@";//字典表－－dictName字符分割符，主要用来需要多个值时放在dict_value字段里用这个分割符隔开。如：快速录入页面
	public static final String REDISUSER = "user";
	public static final Long USERTIME = 43200L;	 //设置用户免登陆15天时间
	
	public static int PAY_STATUS_UN_PAY = 0; //支付状态　未支付
	public static int PAY_STATUS_PAY_SUCC = 1; //支付状态　支付成功
	public static int PAY_WAY_WECHATPAY = 1; //付款渠道 微信
	public static int PAY_WAY_ALIPAY = 2; //付款渠道 支付宝 
	
	public static String HEAD_IMAGE_TEMPORARY_FILE_STR = "_tmp";  //头像上传临时文件字符标识
	
	
}
