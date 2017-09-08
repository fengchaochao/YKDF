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
 * 交易：批量代付：后台交易<br>
 * 日期： 2015-09<br>
 * 版权： 中国银联<br>
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 * 提示：该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《代付产品接口规范》，<br>
 *                  《全渠道平台接入接口规范 第3部分 文件接口》（4.批量文件基本约定）<br>
 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
 * 							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 *                             测试过程中产生的7位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
 *                          2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
 *                          3）  测试环境测试支付请使用测试卡号测试， FAQ搜索“测试卡号”
 *                          4） 切换生产环境要点请FAQ搜索“切换”
 * 交易说明:   1)确定批量结果请调用批量交易状态查询交易,无后台通知。
 *          2)批量文件格式请参考 《全渠道平台接入接口规范 第3部分 文件接口》（4.批量文件基本约定）
 *          3）批量代付文件示例DF00000000777290058150459201507140002I.txt，注意：使用的时候需修改文件内容的批次号，日期（与txnTime前八位相同）总笔数，总金额等于下边参数中batchNo，txnTime，totalQty，totalAmt设定的一致。
 */
public class Form10_6_5_BatchDF extends HttpServlet {

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
		String txnTime = req.getParameter("txnTime");
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", DemoBase.version);           //版本号
		data.put("encoding", DemoBase.encoding);         //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "21");		       //取值：21 批量交易
		data.put("txnSubType", "03");           //填写：01：退货02：代收03：代付
		data.put("bizType", "000401");		   //代付 000401
		data.put("channelType", "07");          //渠道类型
		
		/***商户接入参数***/
		data.put("accessType", "0");		       			//接入类型，商户接入填0 ，不需修改（0：直连商户 2：平台商户）
		data.put("merId", merId);   					    //商户号码，请改成自己申请的商户号，【测试777开通的商户号不支持代收产品】
		
		/**与批量文件内容相关的参数**/
		data.put("batchNo", "0002");            			//批量交易时填写，当天唯一,0001-9999，商户号+批次号+上交易时间确定一笔交易
		data.put("txnTime", txnTime);  					//前8位需与文件中的委托日期保持一致
		data.put("totalQty", "10");             			//批量交易时填写，填写批量文件中总的交易比数		
		data.put("totalAmt", "1000");           			//批量交易时填写，填写批量文件中总的交易金额
		
		//使用DEFLATE压缩算法压缩后，Base64编码的方式传输经压缩编码的文件内容，文件中的商户号必须与merId一致
		//示例文件位置在src/assets下
		String fileContent = AcpService.enCodeFileContent("D:\\eclipse workspace\\workspace2017\\ACPSample_DaiFu\\src\\assets\\DF00000000777290058110097201507140002I.txt",DemoBase.encoding);
		data.put("fileContent", fileContent);
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		String requestBatchTransUrl = SDKConfig.getConfig().getBatchTransUrl();		      		   //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的acpsdk.batchTransUrl
		Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		
		Map<String, String> rspData = AcpService.post(reqData,requestBatchTransUrl,DemoBase.encoding);   //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				if(("00").equals(respCode) ||
				   ("03").equals(respCode) ||
				   ("04").equals(respCode) ||
				   ("05").equals(respCode)){
					//00：交易已受理
					//其他：03 04 05
					//都需发起交易批量状态查询交易（Form10_6_6_BatchQuery）确定交易状态【建议1小时后查询】
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
		resp.getWriter().write("批量代付交易</br>请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
