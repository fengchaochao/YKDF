<%@ page language="java" contentType="text/html; charset=UTF-8"  import="java.text.*" import="java.util.*" 
    pageEncoding="UTF-8"%>

<form class="api-form" method="post" action="<%request.getContextPath();%>/ACPSample_DaiFu/form10_6_5_BatchDF" target="_blank">
<p>
<label>商户号：</label>
<input id="merId" type="text" name="merId" placeholder="" value="777290058150459" title="商户号码（商户号码777290058150459仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号" required="required"/>
</p>
<p>
<label>订单发送时间：</label>
<input id="txnTime" type="text" name="txnTime" placeholder="订单发送时间，YYYYMMDDhhmmss格式" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" title="取北京时间,，YYYYMMDDhhmmss格式" required="required"/>
</p>

<p>
<label>&nbsp;</label>
<input type="submit" class="button" value="批量代付" />
<input type="button" class="showFaqBtn" value="遇到问题？" />
</p>
</form>

<div class="question">
<hr />
<h4>批量代付交易您可能会遇到...</h4>
<p class="faq">
1.文件格式问题：使用UTF-8格式的批量代付请注意使用无BOM头的文件，否则会报错。<br><br>
2.<a href="https://open.unionpay.com/ajweb/help/respCode/respCodeList?respCode=7101401">7101401批量文件格式错</a>
</p>
<hr />
 <jsp:include  page="/pages/more_faq.jsp"/>
</div>
