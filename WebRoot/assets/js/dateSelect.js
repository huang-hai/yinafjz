// 获取当前日期
	var dateZh = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
	// 这里写死了
	// 2小时
    var timeList_2 = ["10:00~12:00", "11:00~13:00", "13:00~15:00", "14:00~16:00", "15:00~17:00", "16:00~18:00"];
    // 3小时
    var timeList_3 = ["10:00~13:00", "11:00~14:00", "13:00~16:00", "14:00~17:00", "15:00~18:00", "16:00~19:00"];
    // 4小时	
    var timeList_4 = ["10:00~14:00", "11:00~15:00", "13:00~17:00", "14:00~18:00", "15:00~19:00", "16:00~20:00"];
	function getCurrentDate() {
		function setDate(num) {
			var date = new Date();
			var date2 = new Date(date);
			date2.setDate(date.getDate() + num);
			return date2;
		}

		var dateList = '';
		for (var i = 0; i < 7; i++) {
			var m = setDate(i).getMonth() + 1;
			var d = setDate(i).getDate();
			var w = setDate(i).getDay();
			
			//服务时间 传到后台用2018-12-18 14:00:00
			var y = setDate(i).getFullYear();
			var mm = m;
			if(m < 10){
				mm = "0" + m;
			}
			var dd = d;
			if(dd < 10){
				dd = "0" + d;
			}
			// 如果当天约满 添加样式full-active
			dateList += "<li class=\"";
			var sty = "";
			if(i == 0){
				sty = "active";
			}
			dateList += sty +"\" date=\""+ y + "-" + mm + "-" + dd +"\">    ";
			dateList += "	<span>";
			var day = "";
			if(i == 0){
				day = "今天";
			} else if(i == 1){
				day = "明天";
			} else {
				day = dateZh[w];
			}
			dateList += day+"</span>         ";
			dateList += "	<span>"+m+"月"+d+"日"+"</span>                                   ";
			dateList += "</li>                                                         ";
		}
		$('.date-wrap').append(dateList);
	}
	getCurrentDate()
	// 获取服务时间信息
	function getTimeList(name, time ,date) {
		var timeList = '';
		$.ajax({
			type: 'GET',
			url: '../mock/appointment_' + name + '.json',
			dataType: 'json',
			success: function success(res) {
				var list = res;
				var dayFull = "";
				
				//================
				var timeStr = "";
				//================
				
				var address = $("#communityId").val();
				$.each(list, function (index, item) {
					var date = $('.date-wrap li.active').attr("date");
					// 整点预约  根据商品类型显示 (空调类型)
					if (name == 1) {
						if(item.state != 2){
							//去后台查询这个地点时间是否已经约满
							date = date + " " + item.time + ":00";
							
							//===================
							if(index == 0){
								timeStr += date;
							} else {
								//以~分隔时间段
								timeStr = timeStr + "~" + date;
							}
							//===================
							
						}
						
						// timeList += `<li class="${item.state == 1?'time-full':'full-all-active'}">${item.time}</li>`
					} else if (name == 2) {
						date = date + " " + eval('timeList_' + time)[index].split("~")[0] + ":00";
						
						//===================
						if(index == 0){
							timeStr += date;
						} else {
							//以~分隔时间段
							timeStr = timeStr + "~" + date;
						}
						//===================
					}
					
					$('.date-wrap').children("li").removeClass("full-active");
				});
				
				//=======================
				var obj = isFull(address,timeStr);
				// 整点预约  根据商品类型显示 (空调类型)
				if(name == 1){
					$('.time-wrap').html('');
					$.each(list,function(k,d){
						var index = d.time;
						//是否状态为2
						if(d.state == 2){
							timeList += '<li class=""></li>';
						} else {
							$.each(obj,function(i,j){
								var index_ = i.substring(11,16);
								if(index == index_){
									console.log(d.time+"="+j);
									dayFull += j;
									
									//获取当前时间 
									var cTime = new Date().getTime();
									var sTime = parserDate(i).getTime();
									if(cTime > sTime){
										timeList += '<li class="' + (j == 1 ? 'time-full' : 'time-disabled') + '" type="' + j/*item.state*/ + '">' + d.time + '</li>';
									} else {
										timeList += '<li class="' + (j == 1 ? 'time-full' : '') + '" type="' + j/*item.state*/ + '">' + d.time + '</li>';
									}
								}
							});
						}
						index++;
					});
				} else {
					// 按时间范围预约(日常清洁类型)
					$('.time-wrap').html('');
					$.each(list,function(k,d){
						var index = d.time.split("-");
						$.each(obj,function(i,j){
							var index_ = i.substring(11,16);
							if(index[0] == index_){
								console.log(d.time+"="+j);
								dayFull += j;
								
								//获取当前时间 
								var cTime = new Date().getTime();
								var sTime = parserDate(i).getTime();
								
								if(cTime > sTime){
									timeList += '<li class="' + (j == 1 ? 'time-full period' : 'period lh-40 time-disabled') + '" type="' + j + '"><span>' + eval('timeList_' + time)[k] + '</span></li>';
								} else {
									timeList += '<li class="' + (j == 1 ? 'time-full period' : 'period lh-40') + '" type="' + j + '"><span>' + eval('timeList_' + time)[k] + '</span></li>';
								}
								
							}
						});
					});
				}
				console.log(dayFull);
				//=======================
				if(dayFull.indexOf("0") == -1){
					//找不到0 说明这天已经约满
					$('.date-wrap').children(".active").addClass("full-active");
				}

				setTimeout(function () {
					$(".time-wrap").html("");
					$('.time-wrap').append(timeList);
				}, 100);
			},
			err: function err(_err) {
				console.log(_err);
			}
		});
	}
	// 日期选择
	// 日期type类型， time时间范围，date提交时间
	function dateSelect(type,time, date) {
		var selDate;
		$('.date-wrap').on('click', 'li', function () {
			$(this).addClass('active').siblings().removeClass('active');
			// 点击获取当前对应时间信息
			selDate = $(this).text();
			if(type == 2) {
				getTimeList(type, time);
			} else {
				getTimeList(type);
			}
			
		});
	}
	// 时间选择
	function timeSelect() {
		var time = '';
		$('.time-wrap').on('click', 'li', function () {
			// 未约满状态可以选择
			var time = $(this).text();
			// 当满员状态 type=0 状态下可选择
			if ($(this).attr('type') == 0) {
				$(this).addClass('active').siblings().removeClass('active');
			}
		});
	}
	
	//查询时间是否已经约满
	function isFull(addr,date){
		var obj = "";
		$.ajax({
			type: "GET",
			url: app_path + "/order/isFull",
			//url: "/order/isFull",
			async: false,
			data: {"id":addr,"time":date},
			dataType: "json",
			success: function(res){
				if(res.code == 200){
					obj = res.obj;
				}
			}
		});
		return obj;
	}
	

	var parserDate = function (date) {
	    var t = Date.parse(date);
	    if (!isNaN(t)) {
	        return new Date(Date.parse(date.replace(/-/g, "/")));
	    } else {
	        return new Date();
	    }
	};
