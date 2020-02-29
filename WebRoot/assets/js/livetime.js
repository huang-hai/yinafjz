/*计算订单的剩余时间*/
var globeNowTime = new Date();
var orderId = null;
var liveTime = {
	initData : function(orderIdArgs) {
		orderId = orderIdArgs;
	},
	convertStrToDate : function(dateString) { //将字符串时间转化
		if (dateString) { //2017-12-01 13:12:15
			var arg1 = dateString.split(" "); //分割日期和时分秒 
			var arg2 = arg1[0].split('-');
			var arg3 = arg1[1].split(':');
			var date = new Date(arg2[0], arg2[1] - 1, arg2[2], arg3[0],
					arg3[1], arg3[2]); //分别对应 年 月 日 时 分 秒
			return date;
		}
	},
	addLeftZero : function(tempNum) { //两位数，左边补0
		if (tempNum < 10) {
			return "0" + tempNum;
		} else if (tempNum == 0) {
			return "00";
		} else {
			return tempNum;
		}
	},
	formatDate : function(num) { //将毫秒数转化为倒计时 eg :29:30
		if (num > 0) {
			var minute = parseInt(num / 60 / 1000);
			var yushu = num % (60 * 1000);
			var second = parseInt(yushu / 1000);
			return liveTime.addLeftZero(minute) + ":"
					+ liveTime.addLeftZero(second);
		} else {
			return "00:00";
		}
	},
	updateOrderInfo : function(app_path) { //更新数据库订单状态
		$.ajax({
			url : app_path + '/order/closeOrder',
			type : 'POST',
			data : {
				"orderId" : orderId
			},
			dataType : 'json',
			success : function(res) {
				console.info(res.msg);
			},
			err : function(err) {
				console.log(err)
			}
		});
	},
	calculateTime : function(initTime) { //定时器调用此方法
		var remainTime = "00:00"; //剩余时间
		var nowDate = new Date();
		var orderTime = liveTime.convertStrToDate(initTime);
		//下单时间加上30分钟
		orderTime.setMinutes(orderTime.getMinutes() + 30);

		var timeLength = orderTime.getTime() - nowDate.getTime(); //求当时间和下单时间的差（毫秒）
		remainTime = liveTime.formatDate(timeLength);
		return remainTime;
	}
}