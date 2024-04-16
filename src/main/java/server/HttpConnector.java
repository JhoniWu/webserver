package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: MiniTomcat
 * @description:
 * @author: Max Wu
 * @create: 2024-04-16 20:35
 **/
public class HttpConnector implements Runnable{
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e){
			e.printStackTrace();
		}
		while (true){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				HttpProcessor processor = new HttpProcessor();
				processor.process(socket);
				socket.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public void start(){
		Thread t = new Thread(this);
		this.start();
	}
}