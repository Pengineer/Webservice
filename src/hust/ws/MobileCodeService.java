package hust.ws;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * ms的ws服务站点：http://www.webxml.com.cn/zh_cn/index.aspx
 * 从查询电话号码归属地服务的WSDL中可以看出，wsdl:service name="MobileCodeWS"，这是服务名，也就是类名。
 * 该服务提供了三种常规访问方式：SOAP 1.1、 SOAP 1.2、 HTTP GET、 HTTP POST
 * 以上三种访问方式的缺点就是需要针对每一种服务的每一个方法都编写一个xml解析器。
 * 
 * 由于webservice纳入了w3c国际规范，因此大部分平台都支持该规范：JEE、PHP、.Net。对于Java语言，有wsimport工具将远程服务生成为本地Java代理类和接口
 * （当然，如果是PHP，也会有对应的工具生成对应的平台相关的调用接口），通过这种方式访问的服务就不需要Xml解析器了，本地代理类直接返回需要的结果。
 * 
 * 补充：
 * 关于Java API 提供的http操作
 * http://www.open-open.com/lib/view/open1390807080976.html
 * 
 */
public class MobileCodeService {

	//1. http-get方式访问webservice
	public void get(String mobileCode, String userID) throws Exception {
		URL url = new URL("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo?mobileCode=" + mobileCode + "&userID=" + userID);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();  //与服务端建立连接
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {           //连接建立成功
			InputStream inputStream = conn.getInputStream();                //getInputStream()函数会将http请求正式发送到服务器，同时获取服务端的响应流
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = -1;
			while((len = inputStream.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			System.out.println("GET请求获取的数据：" + baos.toString());
			baos.close();
			inputStream.close();
		}
	}
	
	/*
	 * 2.Post请求 ：通过Http-Client 框架来模拟实现 Http请求
	 *   Java API发送的post请求设置比get请求麻烦，post将请求参数封装在请求正文中了，因此客户端需要先获取conn的输出流将请求正文发送到缓冲区，
	 *   然后接下来就与get请求一样，通过getInputStream()发送请求。（get请求的参数放在URL后面了，如果没有正文数据传输，就不需要获取从哪的输出流了。）
	 */
	public void post(String mobileCode, String userID) throws Exception {
		/**HttpClient访问网络的实现步骤：
		 *  1. 准备一个请求客户端:浏览器 
		 *  2. 准备请求方式： GET 、POST
		 *  3. 设置要传递的参数
		 *  4. 执行请求
		 *  5. 获取结果
		 */
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo");
		//3.设置请求参数
		postMethod.setParameter("mobileCode", mobileCode);
		postMethod.setParameter("userID", userID);
		//4.执行请求 ,结果码
		int code=client.executeMethod(postMethod);
		//5. 获取结果
		String result = postMethod.getResponseBodyAsString();
		System.out.println("Post请求的结果："+result);
	}
	
	/*
	 * 3.SOAP请求 ：soap是一种http+xml的通信协议
	 *   请求格式和xml文档均来自网页请求说明
	 */
	public void soap() throws Exception{
		HttpClient client=new HttpClient();
		PostMethod postMethod=new PostMethod("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx");
		//3.设置请求参数
		postMethod.setRequestBody(new FileInputStream(System.getProperty("user.dir") + "/src/soap.xml")); 
		//修改请求的头部
		postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
		//4.执行请求 ,结果码
		int code=client.executeMethod(postMethod);
		System.out.println("结果码:"+code);
		//5. 获取结果
		String result=postMethod.getResponseBodyAsString();
		System.out.println("Post请求的结果："+result);
	}
	
	public static void main(String[] args) throws Exception {
		MobileCodeService service = new MobileCodeService();
		service.get("13125179513", "");
		service.post("13125179513", "");
		service.soap();
	}
}
