//package com.yinaf.housewife.framework.utils;
//
//import java.io.File;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.aliyun.openservices.ClientConfiguration;
//import com.aliyun.openservices.ClientException;
//import com.aliyun.openservices.oss.OSSClient;
//import com.aliyun.openservices.oss.OSSException;
//import com.aliyun.openservices.oss.model.CannedAccessControlList;
//import com.aliyun.openservices.oss.model.GetObjectRequest;
//import com.aliyun.openservices.oss.model.ObjectMetadata;
//import com.yinaf.housewife.framework.constants.SystemKeyWord;
//
//
//
///**
// * 该示例代码展示了如果在OSS中创建和删除一个Bucket，以及如何上传和下载一个文件。
// * 
// * 该示例代码的执行过程是：
// * 1. 检查指定的Bucket是否存在，如果不存在则创建它；
// * 2. 上传一个文件到OSS；
// * 3. 下载这个文件到本地；
// * 4. 清理测试资源：删除Bucket及其中的所有Objects。
// * 
// * 尝试运行这段示例代码时需要注意：
// * 1. 为了展示在删除Bucket时除了需要删除其中的Objects,
// *    示例代码最后为删除掉指定的Bucket，因为不要使用您的已经有资源的Bucket进行测试！
// * 2. 请使用您的API授权密钥填充ACCESS_ID和ACCESS_KEY常量；
// * 3. 需要准确上传用的测试文件，并修改常量uploadFilePath为测试文件的路径；
// *    修改常量downloadFilePath为下载文件的路径。
// * 4. 该程序仅为示例代码，仅供参考，并不能保证足够健壮。
// *
// */
//public class OSSUtil {
//	
//    public static final String ACCESS_ID = Config.getValueByKey("access_key_id"); 
//    public static final String ACCESS_KEY = Config.getValueByKey("access_key_secret");
//    public static final String OSS_ENDPOINT = Config.getValueByKey("oss_endpoint");
//    public static final String BUCKET_NAME = Config.getValueByKey("bucket_name");
//    public static final String DEST_BUCKET_NAME = Config.getValueByKey("dest_bucket_name"); 
//    public static final String BUCKET_VOICE_NAME = Config.getValueByKey("bucket_voice_name"); 
//    public static final String URL_PREFIX = "com/";//用来判断的图片url地址前缀
//    public static final OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY, new ClientConfiguration());
//    
//    /**
//     * 上传文件
//     * @param key
//     * @param input
//     * @param filename
//     * @param length
//     */
//    public static void uploadFile(String key, InputStream input,String filename, long length,String bucketName) {
//    	key = genKey(key);
//		ObjectMetadata objectMeta = new ObjectMetadata();
//		objectMeta.setContentLength(length);
//		String metadata = getMetaData(filename.substring(filename.lastIndexOf("."), filename.length()));
//		objectMeta.setContentType(metadata); // 在metadata中标记文件类型
//		client.putObject(bucketName, key, input, objectMeta);
//	}
//    
//    /**
//     * 上传文件返回图片路径
//     * @param key
//     * @param input
//     * @param filename
//     * @param length
//     */
//    public static String uploadFileForUrl(String key,String path, InputStream input,String filename, long length) {
//    	key = getKey(key);
//    	ObjectMetadata objectMeta = new ObjectMetadata();
//		objectMeta.setContentLength(length);
//		String metadata = getMetaData(filename.substring(filename.lastIndexOf("."), filename.length()));
//		objectMeta.setContentType(metadata); // 在metadata中标记文件类型
//		client.putObject(BUCKET_NAME, key, input, objectMeta);
//		return OSS_ENDPOINT.replaceAll("^http://(?<=http://)", "http://"+BUCKET_NAME+".") + "/" + key ;
//		
//	}
//    
//    
//    /**
//     * 上传语音文件返回图片路径
//     * @param key
//     * @param input
//     * @param filename
//     * @param length
//     */
//    public static String uploadVoiceForUrl(String key,String path, InputStream input,String filename, long length) {
//    	key = getKey(key);
//    	ObjectMetadata objectMeta = new ObjectMetadata();
//		objectMeta.setContentLength(length);
//		String metadata = getMetaData(filename.substring(filename.lastIndexOf("."), filename.length()));
//		objectMeta.setContentType(metadata); // 在metadata中标记文件类型
//		client.putObject(BUCKET_VOICE_NAME, key, input, objectMeta);
//		return OSS_ENDPOINT.replaceAll("^http://(?<=http://)", "http://"+BUCKET_VOICE_NAME+".") + "/" + key ;
//	}
//    
//    
//    /**
//     * 简单上传文件
//     * @param bucketName 用户oss空间
//     * @param path 存储路径
//     * @param name 文件名
//     * @param file 文件对象
//     * @param contentType 文件类型：如image/png
//     * @return 返回文件路径
//     */
//    public static String uploadObject(String bucketName,String path,String name,InputStream input,String contentType){
//    	if(client == null){
//    		return null;
//    	}
//    	String key = path + File.separator + name;
//    	key = genKey(key);
//    	ObjectMetadata meta = null;
//    	if(contentType != null){
//    		meta = new ObjectMetadata();
//    		meta.setContentType(contentType);
//    	}
//    	client.putObject(bucketName, key, input,meta);
//    	return OSS_ENDPOINT.replaceAll("^http://(?<=http://)", "http://"+bucketName+".") + "/" + key ;
//    }
//    
//    /**
//     * 生成key
//     * @param key
//     * @return
//     */
//    private static String genKey(String key){
//    	key = key.replaceAll("\\\\", "/").replaceAll("^(?=/)/+", "");
//    	return key;
//    }
//    
//    
//    /**
//     * 获取上传到oss文件的文件名
//     * @param fileName
//     * @return
//     */
//    public static String getKey(String fileName){
//    	SimpleDateFormat sf=new SimpleDateFormat("yyyyMM");
//		Date date=new Date();
//		//设置文件名
//		String year=sf.format(date);
//		sf=new SimpleDateFormat("ddHH");
//		String hour=sf.format(date);
//		String newFileName = StringUtils.remove(fileName, SystemKeyWord.HEAD_IMAGE_TEMPORARY_FILE_STR);//去掉临时文件字符，生成最终要保存的文件名
//		String key= year+"/"+hour+"/"+newFileName;
//		return key;
//    }
//    
//    /**
//     * 获取重新命名的上传文件名
//     * @param fileName
//     * @return
//     */
//    public static String getNewFileName(String fileName){
//		String fileExtName = OSSUtil.getFileExtName(fileName);
//		//取得文件名字   缺少随机数
//		return DateUtils.formatDate(new Date(), "yyMMddHHmmss")+CommonUtils.random()+SystemKeyWord.HEAD_IMAGE_TEMPORARY_FILE_STR+fileExtName;
//    }
//    
//    /** 取得指定文件的文件扩展名 */
//	public synchronized static String getFileExtName(String filename){
//		int p = filename.indexOf(".");
//		return filename.substring(p);
//	}
//    
//    /**
//     * 如果Bucket不存在，则创建它。(先预留着)
//     * @param client
//     * @param bucketName
//     * @throws OSSException
//     * @throws ClientException
//     */
//    private static void ensureBucket(OSSClient client, String bucketName)
//			throws OSSException, ClientException {
//		if (client.doesBucketExist(bucketName)) {
//			return;
//		}
//		// 创建bucket
//		client.createBucket(bucketName);
//	}
//    
//    /**
//     * 把Bucket设置为所有人可读(先预留着)
//     * @param client
//     * @param bucketName
//     * @throws OSSException
//     * @throws ClientException
//     */
//    private static void setBucketPublicReadable(OSSClient client, String bucketName)
//            throws OSSException, ClientException {
//        //创建bucket
//        client.createBucket(bucketName);
//        //设置bucket的访问权限，public-read-write权限
//        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
//    }
//    
//    /**
//	 * 根据文件后缀转化文件格式
//	 * 
//	 * @param ext
//	 * @return
//	 */
//	private static String getMetaData(String ext) {
//		String metaData = "image/jpeg";
//		if (ext.equals("jpg")) {
//			metaData = "image/jpeg";
//		} else if (ext.equals("gif")) {
//			metaData = "image/gif";
//		} else if (ext.equals("amr")) {
//			metaData = "audio/amr";
//		} else if (ext.equals("png")) {
//			metaData = "image/png";
//		}
//		return metaData;
//	}
//
//	/**
//	 * @TODO 下载文件
//	 * @param client
//	 * @param bucketName
//	 * @param key
//	 * @param filename
//	 * @throws OSSException
//	 * @throws ClientException
//	 */
//	private static void downloadFile(String bucketName,String key, String filename) 
//		throws OSSException, ClientException {
//		client.getObject(new GetObjectRequest(bucketName, key), new File(filename));
//	}
//	
//	/**
//	 * 从临时云转正式云存储
//	 * @author linhy
//	 * @date 2018-3-1
//	 * @param sourceBucketName 			临时云Bucket
//	 * @param sourceKey					临时云文件路径
//	 * @param destinationBucketName		正式云Bucket
//	 * @param destinationKey			正式云文件路径
//	 */
//	public static String copyFile(String imgUrl){
//		String resultUrl = "";
//		if (imgUrl.indexOf(URL_PREFIX) > 0) {
//			imgUrl = imgUrl.substring(imgUrl.indexOf(URL_PREFIX)+4);
//		}
//		com.aliyun.oss.OSSClient ossClient = new com.aliyun.oss.OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);
//		//判断object文件是否存在
//		boolean found = ossClient.doesObjectExist(BUCKET_NAME,imgUrl);
//		if(found){
//			resultUrl = OSSUtil.objectUrl(imgUrl);
//	    	com.aliyun.oss.model.CopyObjectResult result = ossClient.copyObject(BUCKET_NAME, imgUrl, DEST_BUCKET_NAME, resultUrl);
//	    	if(result.getLastModified() != null){
//	    		//删除临时云的文件
//		    	ossClient.deleteObject(BUCKET_NAME, imgUrl);
//	    	}
//		}
//		ossClient.shutdown();
//		return OSS_ENDPOINT.replaceAll("^http://(?<=http://)", "http://"+DEST_BUCKET_NAME+".") + "/" + resultUrl ;
//	}
//	
//	
//	public static void deleteFile(String imgUrl){
//		com.aliyun.oss.OSSClient ossClient = new com.aliyun.oss.OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);
//		ossClient.deleteObject(BUCKET_NAME, imgUrl);
//	}
//	
//	/**
//	 * 截取字符串
//	 * @author linhy
//	 * @date 2018-3-1
//	 * @param sourceUrl
//	 * @return
//	 */
//	public static String objectUrl(String sourceUrl) {
//		//判断原生路径是否为空
//		if(sourceUrl == null && sourceUrl.length() == 1){
//			return null;
//		}
//		String[] split = sourceUrl.split("_tmp");
//		String resultUrl = "";
//		for (String str : split) {
//			resultUrl += str;
//		}
//		return resultUrl;
//	}
//}
