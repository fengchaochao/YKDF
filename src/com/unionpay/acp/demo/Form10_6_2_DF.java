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
 * 交易：单笔代付：后台异步交易，有同步应答和异步应答<br>
 * 日期： 2015-09<br>
 * 版权： 中国银联<br>
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 * 提示：该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《代付产品接口规范》，<br>
 *              《平台接入接口规范-第5部分-附录》（内包含应答码接口规范），
 *              《全渠道平台接入接口规范 第3部分 文件接口》（对账文件格式说明）<br>
 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
 * 							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 *                             测试过程中产生的7位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
 *                          2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
 *                          3）  测试环境测试支付请使用测试卡号测试， FAQ搜索“测试卡号”
 *                          4） 切换生产环境要点请FAQ搜索“切换”
 * 确定交易成功机制：商户必须开发后台通知接口和交易状态查询接口（Form10_6_3_Query）确定交易是否成功，建议发起查询交易的机制：代付交易发生后且交易状态不明确或未收到后台通知，3分钟后发起查询交易，可查询N次（不超过6次），每次时间间隔2N秒发起,即间隔1，2，4，8，16，32S查询（查询到03，04，05，01，12,34，60 继续查询，否则终止查询）
 */

public class Form10_6_2_DF extends HttpServlet {

	
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
		String txnAmt = req.getParameter("txnAmt");
		String orderId = req.getParameter("orderId");
		String txnTime = req.getParameter("txnTime");
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", DemoBase.version);            //版本号 全渠道默认值
		data.put("encoding", DemoBase.encoding);     //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "12");              		 	//交易类型 12：代付
		data.put("txnSubType", "00");           		 	//默认填写00
		data.put("bizType", "000401");          		 	//000401：代付
		data.put("channelType", "07");          		 	//渠道类型

		/***商户接入参数***/
		data.put("merId", merId);   		 				//商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0");            		 	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
		data.put("orderId", orderId);        	 	    	//商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		data.put("txnTime", txnTime);		 		    	//订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		data.put("accType", "01");					 		//账号类型 01：银行卡
		
		//sourcesOfFunds为01时payerVerifiInfo必送，其他情况不送payerVerifiInfo。
		//付款方账号        payerAccNo     1到19位数字
		//付款方姓名        payerNm 30字节以下，支持汉字，1个汉字算2字节
		//data.put("sourcesOfFunds", "01");
		//data.put("payerVerifiInfo", "{payerAccNo=6226090000000048&payerNm=张三}");
		
		
		//收款账号为对公时：测试卡使用 6212142600000000167（单位结算卡）
		//单位结算卡完整账户名称        comDebitCardAccName 120字节以下，支持汉字，1个汉字算2字节
		//营业执照注册号        businessLicenseRegNo 20字节以下，支持汉字，1个汉字算2字节
		//data.put("accType", "04"); //04表示对公账户,当04时不需要送customerInfo
		//data.put("reserved", "{comDebitCardAccName=中国银联单位结算卡&businessLicenseRegNo=1101888888}");
		
		//////////如果商户号开通了  商户对敏感信息加密的权限那么，需要对 卡号accNo加密使用：
		data.put("encryptCertId",AcpService.getEncryptCertId());      						//上送敏感信息加密域的加密证书序列号
		String accNo = AcpService.encryptData("6216261000000000018", DemoBase.encoding); 	//这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		data.put("accNo", accNo);
		//////////
		
		/////////商户未开通敏感信息加密的权限那么不对敏感信息加密使用：
		//contentData.put("accNo", "6216261000000000018");                  				//这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		////////
		
		//代付交易的上送的卡验证要素：姓名或者证件类型+证件号码
		Map<String,String> customerInfoMap = new HashMap<String,String>();
		customerInfoMap.put("certifTp", "01");						    //证件类型
		customerInfoMap.put("certifId", "341126197709218366");		    //证件号码
		//customerInfoMap.put("customerNm", "全渠道");					//姓名
		String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,"6216261000000002816",DemoBase.encoding);
		
		data.put("customerInfo", customerInfoStr);
		data.put("txnAmt", txnAmt);						 		    //交易金额 单位为分，不能带小数点
		data.put("currencyCode", "156");                    	    //境内商户固定 156 人民币
		//data.put("billNo", "保险");                    	            //银行附言。会透传给发卡行，完成改造的发卡行会把这个信息在账单、短信中显示给用户的，请按真实情况填写。
		
		
		//后台通知地址（需设置为外网能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5 分钟后会再次通知。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		data.put("backUrl", DemoBase.backUrl);
		
		// 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
//		data.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
//		data.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));
		
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);			 		 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();									 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		
		Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,DemoBase.encoding);        //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				if(("00").equals(respCode)){
					//交易已受理(不代表交易已成功），等待接收后台通知确定交易成功，也可以主动发起 查询交易确定交易状态。
					//TODO
					
					//如果返回卡号且配置了敏感信息加密，解密卡号方法：
					//String accNo1 = resmap.get("accNo");
					//String accNo2 = AcpService.decryptPan(accNo1, "UTF-8");	//解密卡号使用的证书是商户签名私钥证书acpsdk.signCert.path
					//LogUtil.writeLog("解密后的卡号："+accNo2);
				}else if(("03").equals(respCode) ||
						 ("04").equals(respCode) ||
						 ("05").equals(respCode) ||
						 ("01").equals(respCode) ||
						 ("12").equals(respCode) ||
						 ("60").equals(respCode) ){
					//后续需发起交易状态查询交易确定交易状态。
					//TODO
				}else{
					//其他应答码为失败请排查原因
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
		
		resp.getWriter().write("代付交易</br>请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}
