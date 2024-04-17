package server;

import javax.management.Notification;
import javax.xml.stream.events.EntityReference;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @program: MiniTomcat
 * @description:
 * @author: Max Wu
 * @create: 2024-04-16 20:32
 **/
public class HttpProcessor implements Runnable{
	boolean available = false;
	Socket socket;
	HttpConnector connector;

	@Override
	public void run() {
		while (true){
			Socket socket = await();
			if(socket == null) continue;
			process(socket);
			connector.recycle(this);
		}
	}

	private synchronized Socket await() {
		while (!available){
			try {
				wait();
			} catch (InterruptedException e){

			}
		}
		Socket socket = this.socket;
		available = false;
		notifyAll();
		return socket;
	}

	public HttpProcessor(){

	}
	public HttpProcessor(HttpConnector connector){
		this.connector = connector;
	}
	public void process(Socket socket){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}

		InputStream input = null;
		OutputStream output = null;
		try {
			input = socket.getInputStream();
			output = socket.getOutputStream();
			//创建请求对象request，并解析
			Request request = new Request(input);
			request.parse();
			//创建响应对象response
			Response response = new Response(output);
			response.setRequest(request);

			//判断请求的是静态资源还是servlet
			if(request.getUri().startsWith("/servlet/")){
				ServletProcessor processor = new ServletProcessor();
				processor.process(request, response);
			} else {
				StaticResourceProcessor processor = new StaticResourceProcessor();
				processor.process(request, response);
			}
			socket.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	synchronized void assign(Socket socket){
		while (available){
			try {
				wait();
			} catch (InterruptedException e){

			}
		}
		this.socket = socket;
		available = true;
		notifyAll();
	}

	public void start(){
		Thread t = new Thread(this);
		t.start();
	}
}
