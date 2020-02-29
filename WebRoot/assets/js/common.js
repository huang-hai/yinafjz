app_path = getPath();
var loadingStr = '<div class="loading"><img src="'+app_path+'/assets/images/loading.svg" /><span>加载中..</span></div>';
document.write(loadingStr);
//监听加载状态改变
document.onreadystatechange = completeLoading;
//加载状态为complete时移除loading效果
function completeLoading() {
  if (document.readyState == "complete") {
      var load = $('div.loading');
      // 测试。。
      setTimeout(function () {
        load.remove();
      }, 300);
  }
}

//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
//    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht);
}

// 截取url
function urlSearch() {
  var name, value;
  var str = location.href;
  var num = str.indexOf('?');
  str = str.substr(num + 1);

  var arr = str.split("&"); 
    for (var i = 0; i < arr.length; i++) {
        num = arr[i].indexOf("=");
        if (num > 0) {
            name = arr[i].substring(0, num);
            value = arr[i].substr(num + 1);
            this[name] = value;
        }
    }
}

// 当前定位 用户不同意定位就没用
function currentLocation (el) {
    $('body').append(loadingStr);
    // 获取定位
    $(el).html('正在定位...');
    var geo = new jQuery.AMUI.Geolocation({
      enableHighAccuracy: true,
      timeout: 5000,
      maximumAge: 60000
    });

    geo.get().then(function (position) {
    // 成功回调，position 为返回的位置对象
    var latlon = position.coords.latitude + ',' + position.coords.longitude;
    var url = 'http://api.map.baidu.com/geocoder/v2/?ak=U1HUz3FnxHcy6zY8W3sYmlnTPHG2zVq3&callback=renderReverse&location=' + latlon + '&output=json&pois=0';
    $.ajax({
      type: "GET",
      url: url,
      dataType: 'jsonp',
      beforeSend: function beforeSend() {
        $(el).html('正在定位...');
      },
      success: function success(res) {
        if (res.status == 0) {
          $(el).html(res.result.formatted_address);
          $('div.loading').remove();
        }
        if (res.status == 'OK') {
          var result = res.result;
          $.each(result, function (index, array) {
              if (index == 0) {
                var locationStr = array['formatted_address'];
                $('div.loading').remove();
                $(el).html(locationStr);
              }
          });
        }
      },
      error: function error(err) {
        setTimeout(function () {
          $(el).html('地址位置获取失败');
          $('div.loading').remove();
        },1000);
        }
      });
    }, function (err) {
      // 不支持或者发生错误时回调，err 为错误提示信息
      console.log(err);
      setTimeout(function () {
          $(el).html('地址位置获取失败');
          $('div.loading').remove();
        },1000);
      // 用户定位信息为空
      // localStorage.setItem('address', 0);
  });
}


