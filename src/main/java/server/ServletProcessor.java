package server;

import org.apache.commons.lang3.text.StrSubstitutor;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: MiniTomcat
 * @description:
 * @author: Max Wu
 * @create: 2024-04-16 09:23
 **/
public class ServletProcessor {
	private static String OKMessage = "HTTP/1.1 ${StatusCode} ${StatusName}\r\n"+
		"Content-Type: ${ContentType}\r\n"+
		"Server: minit\r\n"+
		"Date: ${ZonedDateTime}\r\n"+
		"\r\n";

	public void process(Request request, Response response){
		//get uri
		String uri = request.getUri();
		//简易获取
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		URLClassLoader loader = null;
		PrintWriter writer = null;
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(HttpServer.WEB_ROOT);
			String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
			urls[0] = new URL(null, repository, streamHandler);
			loader = new URLClassLoader(urls);
        }
        catch (IOException e) {
			System.out.println(e.toString());
		}

		//获取PrintWriter
		try {
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		//load servlet
		Class<?> servletClass = null;
		try {
			servletClass = loader.loadClass(servletName);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		String head = composeResponseHead();
		writer.println(head);
		Servlet servlet = null;
		try {
			servlet = (Servlet) servletClass.newInstance();
			servlet.service(request, response);
        }
        catch (Exception e) {
            System.out.println(e.toString());
		}
        catch (Throwable e) {
            System.out.println(e.toString());
        }

	}

	private String composeResponseHead() {
		Map<String, Object> valuesMap = new HashMap<>();
		valuesMap.put("StatusCode","200");
		valuesMap.put("StatusName","OK");
		valuesMap.put("ContentType","text/html;charset=UTF-8");
		valuesMap.put("ZonedDateTime", DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now()));
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String responseHead = sub.replace(OKMessage);
        return responseHead;
	}
}
