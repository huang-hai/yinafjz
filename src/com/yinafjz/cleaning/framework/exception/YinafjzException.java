package com.yinafjz.cleaning.framework.exception;

import com.yinafjz.cleaning.framework.base.ExceptionBase;

public class YinafjzException extends ExceptionBase{

	public YinafjzException(String code,String msg,String exception){
		super(code,msg,exception);
	}
	public YinafjzException(String code,String msg,String other,String exception){
		super(code,msg,other,exception);
	}
	public YinafjzException(String code,String msg,String other,Throwable e){
		super(code,msg,other,e);
	}
	public YinafjzException(String code,String msg,Throwable e){
		super(code,msg,e);
	}	
}
