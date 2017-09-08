package com.unionpay.acp.demo;
/**
 * @author: Fengc
 * @date:2017-8-16 下午4:42:20
 * @version :0.0.1
 * @dis:
 */
public class Main {

	public static void main(String[] args) {
		String total = String.valueOf(1235232.0f);
		String txnAmt = total.substring(0, total.indexOf("."));
		System.out.println(txnAmt);
	}
}
