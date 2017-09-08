<%@ page language="java" contentType="text/html; charset=UTF-8"  import="java.text.*" import="java.util.*" 
    pageEncoding="UTF-8"%>

<form class="api-form" method="post" action="<%request.getContextPath();%>/ACPSample_DaiFu/form10_6_3_Query" target="_blank">
<p>
<label>商户号：</label>
<input id="merId" type="text" name="merId" placeholder="" value="777290058150459" title="默认商户号仅作为联调测试使用，正式上线还请使用正式申请的商户号" required="required"/>
</p>
<p>
<label>订单发送时间：</label>
<input id="txnTime" type="text" name="txnTime" placeholder="填写被查询交易的订单发送时间" value="" title="填写被查询交易的订单发送时间，YYYYMMDDhhmmss格式" required="required"/>
</p>
<p>
<label>商户订单号：</label>
<input id="orderId" type="text" name="orderId" placeholder="填写被查询交易的商户订单号" value="" title="填写被查询交易的商户订单号" required="required"/>
</p>
<p>
<label>&nbsp;</label>
<input type="submit" class="button" value="提交" />
<input type="button" class="showFaqBtn" value="遇到问题？"  />
</p>
</form>

<div class="question" >
<hr />
<h4>交易状态查询您可能会遇到...</h4>
<p class="faq">
 交易状态查询提示2600000：这个是正常的交易未查询到的状态，如您确定交易成功，请和原交易核对商户号+订单号+时间这3个字段匹配一下。<br><br>
</p>
<hr />
 <jsp:include  page="/pages/more_faq.jsp"/>
</div>

