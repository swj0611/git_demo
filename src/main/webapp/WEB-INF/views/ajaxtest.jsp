<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="resources/jquery-2.1.3.js"></script>  
<script type="text/javascript" src="resources/jquery-2.1.3.min.js"></script>  
<meta http-equiv="Content-Type" content="text/html; charset=utf8">  
<title>Insert title here</title>  
<script type="text/javascript">  
$(document).ready(ajaxTest);
$(document).ready(autoAjax);
 
  setInterval(autoAjax,3000);
   
   function ajaxTest(){  
        $.ajax({  
        	 data:"name="+$("#name").val(),  
  	       //用GET方法当请求参数不变时会因部分浏览器缓存而无法更新
  	       type:"POST",  
  	       dataType: 'json',  
  	        url:"autoajax",  
  	        error:function(data){  
  	            alert("出错了！！:"+data[0].name);  
  	        },  
  	        success:function(data){  
  	          
  	           var htmlstr="<table border='1'>手动异步刷新"+
  	           "<tr><td>编号</td><td>姓名</td><td>地址</td><td>电话</td><td>邮箱</td><td>id号</td></tr>";
  	           $.each(data,function(idx,obj){
  	        	 htmlstr=htmlstr+"<tr><td>";
  	        	 htmlstr=htmlstr+idx;
  	        	 htmlstr=htmlstr+"</td>"+"<td>"+obj.name+"</td><td>"+obj.address+"</td><td>"+obj.telephone+"</td><td>"
  	        	 +obj.email+"</td><td>"+obj.id+"</td>"+"</tr>";
  	           })
  	          // var htmlstr="</table>"
  	          // htmlstr=htmlstr+data.msg;
  	           htmlstr=htmlstr+"</table>";
  	           $("#cloneTr0").html(htmlstr);
  	                        
  	                               
  	    }  
        })
   }
   
   function autoAjax(){
	   $.ajax({  
	       data:"name="+$("#name").val(),  
	       //用GET方法当请求参数不变时会因部分浏览器缓存而无法更新
	       type:"POST",  
	       dataType: 'json',  
	        url:"autoajax",  
	        error:function(data){  
	            alert("出错了！！:"+data[0].name);  
	        },  
	        success:function(data){  
	          
	           var htmlstr="<table border='1'>自动异步刷新"+
	           "<tr><td>编号</td><td>姓名</td><td>地址</td><td>电话</td><td>邮箱</td><td>id号</td></tr>";
	           $.each(data,function(idx,obj){
	        	 htmlstr=htmlstr+"<tr><td>";
	        	 htmlstr=htmlstr+idx;
	        	 htmlstr=htmlstr+"</td>"+"<td>"+obj.name+"</td><td>"+obj.address+"</td><td>"+obj.telephone+"</td><td>"
	        	 +obj.email+"</td><td>"+obj.id+"</td>"+"</tr>";
	           })
	          // var htmlstr="</table>"
	          // htmlstr=htmlstr+data.msg;
	           htmlstr=htmlstr+"</table>";
	           $("#cloneTr1").html(htmlstr);
	                        
	                               
	    }  
	        })   
   }
</script>  
</head>
<body>
   <h1>Ajax访问测试</h1>
   <input type="text" name="name" id="name"/>  
   <input type="submit" value="点击刷新表格" onclick="ajaxTest();"/>  
   <table><tr><td><div id="cloneTr0"></div></td><td><div id="cloneTr1"> </div></td></tr></table>
           

   
</body>
</html>




