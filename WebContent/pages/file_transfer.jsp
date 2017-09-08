<%@ page language="java" contentType="text/html; charset=UTF-8"  import="java.text.*" import="java.util.*" 
    pageEncoding="UTF-8"%>

<form class="api-form" method="post" action="<%request.getContextPath();%>/ACPSample_DaiFu/form10_7_FileTransfer" target="_blank">
<p>
<label>商户号：</label>
<input id="merId" type="text" name="merId" placeholder="" value="700000000000001" title="请替换实际商户号测试，自助化平台注册的商户号（777开头的）无法测试此接口，如无真实商户号，请使用700000000000001测试此接口" required="required"/>
</p>
<p>
<label>订单发送时间：</label>
<input id="txnTime" type="text" name="txnTime" placeholder="订单发送时间" value="<%=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) %>" title="取北京时间，YYYYMMDDhhmmss格式" required="required"/>
</p>
<p>
<label>清算日期：</label>
<input id="settleDate" type="text" name="settleDate" placeholder="清算日期" value="0119" title="格式为MMDD" required="required"/>
</p>
<p>
<label>&nbsp;</label>
<input type="submit" class="button" value="提交" />
<input type="button" class="showFaqBtn" value="遇到问题？" />
</p>
</form>

<div class="question">
<hr />
<h4>对账文件下载您可能会遇到...</h4>
<p class="faq">
http应答500应答内容为空或者respcode=99：<br>
注：此接口与其他接口实现不同，所以其他接口能过此接口也可能会遇到问题。<br>
可能有如下原因：<br>
1. 可能用错签名证书，请确定使用目前开发包内的验签证书。<br>
2. 测试环境仅支持真实商户号测试此接口，自助化平台的777开头的商户不支持测试。<br>
3. 报文格式错误导致，请核对一下上送要素是否和规范一样。<br>
4. （使用开发包一般不会发生）签名的url编码的转义字符如%3D中的字母必须大写。<br>
5. （使用开发包一般不会发生）除了签名之外的字段不要做url编码。<br><br>
respcode=98：<br>
文件不存在，可能为送错清算日期（见下一问）、文件还没生成导致。<br><br>
对账文件什么时候能下载？<br>
测试环境一般下午5点出，文件内包含的交易的时间范围是13:30-13:30。生产环境一般早上9点出，文件内包含的交易的时间范围是23:00-23:00。<br><br>
清算日期settleDate是什么？<br>
为银联和入网机构间的交易结算日期。一般前一日23点至当天23点为一个清算日。也就是23点前的交易，当天23点之后开始结算，23点之后的交易，要第二天23点之后才会结算。测试环境为测试需要，13:30左右日切，所以13:30到13:30为一个清算日，测试环境今天下午为今天的日期，今天上午为昨天的日期。<br><br>
</p>
<hr />
 <jsp:include  page="/pages/more_faq.jsp"/>
</div>