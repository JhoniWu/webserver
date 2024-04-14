import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: MiniTomcat
 * @description:
 * @author: Max Wu
 * @create: 2024-04-14 16:22
 **/
public class Request {
	private InputStream input;
	private String uri;
	public Request(InputStream input){
		this.input = input;
	}
	public void parse(){
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = input.read(buffer);
		} catch (IOException e){
			e.printStackTrace();
			i = -1;
		}
		for(int j = 0; j < i; j++){
			request.append((char) buffer[j]);
		}
		uri = parseUri(request.toString());
	}

	private String parseUri(String requestString) {
		int idx1, idx2;
		idx1 = requestString.indexOf(' ');
		if(idx1!=-1){
			idx2 = requestString.indexOf(' ', idx1+1);
			if(idx2 > idx1) return requestString.substring(idx1+1, idx2);
		}
		return null;
	}
	public String getUri(){
		return uri;
	}
}
