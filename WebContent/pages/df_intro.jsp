<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div style="font-size: 13px" >
测试卡号：与银联测试环境联调使用的卡号 <a href="https://open.unionpay.com/ajweb/help/faq/list?ha=%E6%B5%8B%E8%AF%95%E5%8D%A1%E5%8F%B7" target="_blank">测试卡号</a><br><br>
交易流程：前台/后台实名认证(一般流程中商户是不做实名认证交易的，如果需要做此交易需要联系银联的业务运营申请)->代付交易(包括接收处理后台通知)->交易状态查询交易<br><br>
代付：后台交易，以后台通知或者交易状态查询确定是否成功<br>
       上送的验证要素为：证件类型+证件号 或者 姓名<br><br>
交易状态查询说明：<br>
origrespcode=00、A6成功，03、04、05重新查询，其他为失败。<br>
对代付接口这些应答码也需重新查询：01、12、34、60<br><br>
对账文件下载：<br>
对账文件什么时候能下载？<br>
测试环境一般下午5点出，文件内包含的交易的时间范围是13:30-13:30。<br>
生产环境一般早上9点出，文件内包含的交易的时间范围是23:00-23:00。<br><br>
对账文件获取后会落地成一个zip文件，zip文件中的ZM，ZME文件各个字段的拆分解析可以参考DemoBase.java中的parseZMFile parseZMEFile 方法。<br>
</div>
<br>
<br>