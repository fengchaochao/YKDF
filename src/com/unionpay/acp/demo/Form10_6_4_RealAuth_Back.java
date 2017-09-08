package com.unionpay.acp.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;

/**
 * 重要：联调测试时请仔细阅读注释！
 * 
 * 产品：代付产品<br>
 * 交易：实名认证：后台交易，只有同步应答<br>
 * 日期： 2015-09<br>
 * 版本： 1.0.0
 * 版权： 中国银联<br>
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 * 该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《代付产品接口规范》<br>
 *              《平台接入接口规范-第5部分-附录》（内包含应答码接口规范）<br>
 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
 * 							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 *                             测试过程中产生的7位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
 *                          2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
 * 交易说明:同步交易，不需要接收后台通知或发起交易状态查询交易
 */
public class Form10_6_4_RealAuth_Back extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		/**
		 * 请求银联接入地址，获取证书文件，证书路径等相关参数初始化到SDKConfig类中
		 * 在java main 方式运行时必须每次都执行加载
		 * 如果是在web应用开发里,这个方法可使用监听的方式写入缓存,无须在这出现
		 */
		//这里已经将加载属性文件的方法挪到了web/AutoLoadServlet.java中
		//SDKConfig.getConfig().loadPropertiesFromSrc(); //从classpath加载acp_sdk.properties文件
		super.init();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String merId = req.getParameter("merId");
		String orderId = req.getParameter("orderId");
		String txnTime = req.getParameter("txnTime");
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", DemoBase.version);                  //版本号
		data.put("encoding", DemoBase.encoding);                //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "72");                              //交易类型 11-代收
		data.put("txnSubType", "01");                           //交易子类型  01-实名认证
		data.put("bizType", "000401");                          //业务类型 代收产品
		data.put("channelType", "07");                          //渠道类型07-PC

		/***商户接入参数***/
		data.put("merId", merId);                  			   //商户号码（商户号码777290058150459仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		data.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
		data.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		data.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		data.put("accType", "01");                              //账号类型
		
		//实名认证交易的customerInfo送什么验证要素是在银联后台到商户号上的，一般在入网的时候银联业务会与商户业务商谈好这些内容，请咨询您的业务人员或者银联业务运营的同事
		//以下上送要素是参考《测试商户号777290058150459实名认证交易必送验证要素配置说明.txt》借记卡（实名认证交易-后台）部分
		Map<String,String> customerInfoMap = new HashMap<String,String>();
		customerInfoMap.put("certifTp", "01");						//证件类型
		customerInfoMap.put("certifId", "341126197709218366");		//证件号码
		customerInfoMap.put("customerNm", "全渠道");					//姓名
		customerInfoMap.put("phoneNo", "13552535506");			    //手机号
		//customerInfoMap.put("cvn2", "123");           			//卡背面的cvn2三位数字
		//customerInfoMap.put("expired", "1711");  				    //有效期 年在前月在后
		
		////////////如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
		String accNo = AcpService.encryptData("6216261000000000018", "UTF-8");  //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		data.put("accNo", accNo);
		data.put("encryptCertId",AcpService.getEncryptCertId());       //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap,null,DemoBase.encoding);
		//////////
		
		/////////如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
		//contentData.put("accNo", "6216261000000000018");            		//这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		//String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,"6216261000000000018");
		////////
		
		data.put("customerInfo", customerInfoStr);
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);			//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();   								 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				if(("00").equals(rspData.get("respCode"))){
					//交易成功
					//TODO
				}else{
					//其他应答码为失败请排查原因或做失败处理
					//TODO
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		
		String reqMessage = DemoBase.genHtmlResult(reqData);
		String rspMessage = DemoBase.genHtmlResult(rspData);
		resp.getWriter().write("请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
