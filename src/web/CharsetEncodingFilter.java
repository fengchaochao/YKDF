package web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.unionpay.acp.demo.DemoBase;

/**
 * 
 * @ClassName CharsetEncodingFilter
 * @Description
 * @date 2017-4-5 上午9:58:23
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 */
public class CharsetEncodingFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding(DemoBase.encoding);
		response.setContentType("text/html; charset="+ DemoBase.encoding);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
