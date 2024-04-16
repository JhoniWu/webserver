package server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: MiniTomcat
 * @description:
 * @author: Max Wu
 * @create: 2024-04-14 16:22
 **/
public class HttpServer {
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector();
		connector.start();
	}
}
