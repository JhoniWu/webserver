package test;
import javax.servlet.*;
import java.io.IOException;

/**
 * @program: MiniTomcat
 * @description:
 * @author: Max Wu
 * @create: 2024-04-16 11:14
 **/
public class HelloServlet implements Servlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {

	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		servletResponse.setCharacterEncoding("UTF-8");
		String doc = "<!DOCTYPE html> \n" +
			"<html>\n" +
			"<head><meta charset=\"utf-8\"><title>Test</title></head>\n"+
			"<body bgcolor=\"#f0f0f0\">\n" +
			"<h1 align=\"center\">" + "Hello World 你好" + "</h1>\n";
		servletResponse.getWriter().println(doc);
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void destroy() {

	}
}
