$("#back").on("click",function(){
	window.location.href = "list";
});

function UpdateSize(appId){
	$.ajax({
		type:"GET",//请求类型
		url:"xia.json",//请求的url
		data:{appId:appId},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			if(data.result == "success"){
				alert("下载成功！");
			}else if(data.result == "failed"){
				alert("删除失败！");
			}
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			alert("请求错误！");
		}
	});  
}