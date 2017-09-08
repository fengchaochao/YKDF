<%@ page language="java" contentType="text/html; charset=UTF-8"  import="java.text.*" import="java.util.*" 
    pageEncoding="UTF-8"%>

<form class="api-form" method="post" action="<%request.getContextPath();%>/ACPSample_DaiFu/form10_6_4_RealAuth_Back" target="_blank">
<p>
<label>商户号：</label>
<input id="merId" type="text" name="merId" placeholder="" value="777290058150459" title="商户号码（商户号码777290058150459仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】" required="required"/>
</p>

<p>
<label>订单发送时间：</label>
<input id="txnTime" type="text" name="txnTime" placeholder="订单发送时间，YYYYMMDDhhmmss格式" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" title="取北京时间,YYYYMMDDhhmmss格式" required="required"/>
</p>
<p>
<label>商户订单号：</label>
<input id="orderId" type="text" name="orderId" placeholder="商户订单号" value="<%=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) %>" title="自行定义，8-32位数字字母 " required="required"/>
</p>
<p>
<label>&nbsp;</label>
<input type="submit" class="button" value="实名认证" />
<input type="button" class="showFaqBtn" value="遇到问题？" />
</p>
</form>

<div class="question">
<hr />
<h4>后台实名认证交易您可能会遇到...</h4>
<p class="faq">
1.<a href="https://open.unionpay.com/ajweb/help/respCode/respCodeList?respCode=9100004">交易失败 11[9100004]Signature verification failed</a><br><br>
2.6151084报文交易要素缺失:上送的customerInfo中的验证要素少于商户号在银联后台配置的验证要素，请按业务部门提供的上送
</p>
<hr />
 <jsp:include  page="/pages/more_faq.jsp"/>
</div>
