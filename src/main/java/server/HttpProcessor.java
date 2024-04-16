package server;

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
public class HttpProcessor {
	public HttpProcessor(){

	}
	public void process(Socket socket){
		InputStream input = null;
		OutputStream output = null;
		try {
			input = socket.getInputStream();
			output = socket.getOutputStream();
			Request request = new Request(input);
			request.parse();
			Response response = new Response(output);
			response.setRequest(request);

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
}
