<%@ page language="java" contentType="text/html; charset=UTF-8"  import="java.text.*" import="java.util.*" 
    pageEncoding="UTF-8"%>

<form class="api-form" method="post" action="<%request.getContextPath();%>/ACPSample_DaiFu/form10_6_4_RealAuth_Front" target="_blank">
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
<input type="submit" class="button" value="跳转页面实名认证" />
<input type="button" class="showFaqBtn" value="遇到问题？" />
</p>
</form>

<div class="question">
<hr />
<h4>前台实名认证交易您可能会遇到...</h4>
<p class="faq">
1.打开页面出现"此网站的安全证书有问题"或"您的连接不是私密连接"等阻止打开支付页面跳转的内容: 测试环境的证书不被信任，生产没有这个问题，可以点击继续打开<br><br>
2.测试环境跳转报http501错误：测试环境证书不信任，部分浏览器点击信任/继续后会改为get方式访问导致501，这个时候请再回到原生成订单的html重新跳转一下就能正常跳转了，生产无此问题<br><br>
3.<a href="https://open.unionpay.com/ajweb/help/respCode/respCodeList?respCode=9100004">交易失败 11[9100004]Signature verification failed</a><br><br>

</p>
<hr />
 <jsp:include  page="/pages/more_faq.jsp"/>
</div>
