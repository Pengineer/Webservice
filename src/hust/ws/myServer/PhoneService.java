package hust.ws.myServer;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * 手机的业务类，该业务类通过webservice 对外提供服务
 * 1. 声明一个webservice服务： @webservice， 默认发布public定义的所有方法
 * 2. 发布一个webservice服务：EndPoint
 * 
 * 为了使得发布后的WSDL可读性更强，可以通过注解的方式对其进行修改，修改内容主要如下：
 * 	（1）输入参数名、返回参数名：@WebParam、@WebResult
 * 	（2）服务名、方法名：@WebService、@WebMethod
 * 	（3）声明某些public方法不对外发布：@WebMethod
 */

@WebService(serviceName="PhoneManager",//修改服务名
			targetNamespace="http://hust.ws.server") //修改命名空间 ，默认包名，取反
public class PhoneService {
	
	@WebMethod(operationName="getPhoneInfo")
	public @WebResult(name="Phone") Phone getPhoneInfo(@WebParam(name="osName") String osName) {
		Phone phone = new Phone();
		if (osName.equals("Android")) {
			phone.setOsName("Android");
			phone.setOwner("Google");
			phone.setTotal("80%");
		} else {
			phone.setOsName("IOS");
			phone.setOwner("Google");
			phone.setTotal("20%");
		}
		return phone;
	}
	
	//将某些public方法排除在发布之外
	@WebMethod(exclude=true)
	public void excludeTest() {}
	
	public static void main(String[] args) {
		String address1 = "http://127.0.0.1:8888/ws/PhoneService";
		Endpoint.publish(address1, new PhoneService());
		System.out.println("wsdl地址 : "+address1+"?WSDL"); //可以在浏览器中直接访问，注意后面要加?WSDL
		
	}
}
