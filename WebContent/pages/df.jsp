<%@ page language="java" contentType="text/html; charset=UTF-8"  import="java.text.*" import="java.util.*" 
    pageEncoding="UTF-8"%>

<form class="api-form" method="post" action="<%request.getContextPath();%>/ACPSample_DaiFu/form10_6_2_DF" target="_blank">
<p>
<label>商户号：</label>
<input id="merId" type="text" name="merId" placeholder="" value="777290058150459" title="商户号码（商户号码777290058150459仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号" required="required"/>
</p>
<p>
<label>交易金额：</label>
<input id="txnAmt" type="text" name="txnAmt" placeholder="交易金额" value="1000" title="单位为分,不能带小数点 " required="required"/>
</p>
<p>
<label>订单发送时间：</label>
<input id="txnTime" type="text" name="txnTime" placeholder="订单发送时间，YYYYMMDDhhmmss格式" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" title="取北京时间,，YYYYMMDDhhmmss格式" required="required"/>
</p>
<p>
<label>商户订单号：</label>
<input id="orderId" type="text" name="orderId" placeholder="商户订单号" value="<%=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) %>" title="自行定义，8-32位数字字母 " required="required"/>
</p>
<p>
<label>&nbsp;</label>
<input type="submit" class="button" value="代付" />
<input type="button" class="showFaqBtn" value="遇到问题？" />
</p>
</form>

<div class="question">
<hr />
<h4>代付交易您可能会遇到...</h4>
<p class="faq">
1.<a href="https://open.unionpay.com/ajweb/help/respCode/respCodeList?respCode=9100004">交易失败 11[9100004]Signature verification failed</a><br><br>
2.报文格式错误[6100030]:一般是customerInfo上送的字段或格式错误。<br>
</p>
<hr />
 <jsp:include  page="/pages/more_faq.jsp"/>
</div>
