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
 * 产品：代收产品<br>
 * 交易：实名认证：前台交易<br>
 * 日期： 2015-09<br>
 * 版本： 1.0.0
 * 版权： 中国银联<br>
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 * 该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《代收产品接口规范》<br>
 *              《平台接入接口规范-第5部分-附录》（内包含应答码接口规范）<br>
 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
 * 							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 *                             测试过程中产生的7位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
 *                          2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
 * 交易说明:前台交易，只有前台通知。
 */

public class Form10_6_4_RealAuth_Front  extends HttpServlet  {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html; charset="+ DemoBase.encoding);

		String merId = req.getParameter("merId");
		String orderId = req.getParameter("orderId");
		String txnTime = req.getParameter("txnTime");

		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		data.put("version", DemoBase.version); // 版本号
		data.put("encoding", DemoBase.encoding); // 字符集编码，可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "72"); // 交易类型 12 实名认证
		data.put("txnSubType", "10"); // 交易子类型10
		data.put("bizType", "000501"); // 业务类型 代收产品
		data.put("channelType", "07"); // 渠道类型07-PC 08 WAP

		/*** 商户接入参数 ***/
		data.put("merId", merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		data.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
		data.put("orderId", orderId); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		data.put("txnTime", txnTime); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		data.put("accType", "01"); // 账号类型

		data.put("reserved", "{checkFlag=11100}"); // 必送，无特殊需求，默认送这个，不需要改动

		// 如下卡的验证信息如果可以采集到了，也可以上送，上送后在银联实名认证页面回显
		 Map<String,String> customerInfoMap = new HashMap<String,String>();
		 customerInfoMap.put("certifTp", "01"); //证件类型
		 customerInfoMap.put("certifId", "341126197709218366"); //证件号码
		 customerInfoMap.put("customerNm", "全渠道"); //姓名
		 customerInfoMap.put("phoneNo", "13552535506"); //手机号

		//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对
		//////////// accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
		String accNo = AcpService.encryptData("6216261000000000018","UTF-8"); //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		 data.put("accNo", accNo);
		 data.put("encryptCertId",AcpService.getEncryptCertId());
		//////////// //加密证书的certId，配置在acp_sdk.properties文件
		//////////// acpsdk.encryptCert.path属性下
		 String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap,"6216261000000000018",DemoBase.encoding);

		///////// 如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
		// contentData.put("accNo", "6216261000000000018");
		// String customerInfoStr =
		///////// AcpService.getCustomerInfo(customerInfoMap,null,DemoBase.encoding_UTF8);
		///////// //前台实名认证送支付验证要素 customerInfo中要素不要加密
		////////

		data.put("customerInfo", customerInfoStr);

		// 前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”的时候将异步通知报文post到该地址
		// 如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
		// 通知参数详见open.unionpay.com帮助中心 下载 产品接口规范 代收产品接口规范 实名认证 应答报文
		data.put("frontUrl", DemoBase.frontUrl);

		// 后台通知地址
		data.put("backUrl", DemoBase.backUrl);

		/** 请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面 **/
		Map<String, String> reqData = AcpService.sign(data, DemoBase.encoding); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl(); // 获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl, reqData, DemoBase.encoding); // 生成自动跳转的Html表单

		LogUtil.writeLog("打印请求HTML，此为请求报文，为联调排查问题的依据：" + html);
		resp.getWriter().write(html); // 将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
