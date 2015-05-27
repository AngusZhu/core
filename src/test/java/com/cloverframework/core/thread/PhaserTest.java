package com.cloverframework.core.thread;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PhaserTest {
	
	//    http://user.qzone.qq.com/454936533/main
	public static void main(String[] args) {
		try {
			new WebPageImageDownloader().download("http://user.qzone.qq.com/454936533/4", null, null);
			System.out.println("finished...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
class WebPageImageDownloader{
	private final Phaser phaser=new Phaser(1);
	String reg="src=['\"]?(.*?(\\.jpg|\\.gif|\\.png))['\"]??[\\s>]+";
	private final Pattern imageUrlPattern=Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
	
	public void download(String url,final Path path,Charset charset) throws IOException{
		if(charset==null){
			charset=StandardCharsets.UTF_8;
		}
		String content=getConent(url,charset);
		List<URL> imageUrls=extractImageUrls(content);
		for(final URL imageUrl:imageUrls){
			phaser.register();
			new Thread(){
				public void run(){
					phaser.arriveAndAwaitAdvance();
					try {
						InputStream is = imageUrl.openStream();
						
						Files.copy(is,getSavedPath(path,imageUrl),StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		}
		phaser.arriveAndAwaitAdvance();
		phaser.arriveAndAwaitAdvance();
		phaser.arriveAndDeregister();
		
	}

	private List<URL> extractImageUrls(String content) {
		List<URL> result=new ArrayList<URL>();
		Matcher matcher=imageUrlPattern.matcher(content);
		while(matcher.find()){
			try {
				result.add(new URL(matcher.group(1)));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}finally{
				phaser.arriveAndDeregister();
			}
		}
		System.out.println(Arrays.deepToString(result.toArray()));
		return result;
	}

	private String getConent(String url, Charset charset) throws IOException {
		//return IOUtils.toString(new InputStreamReader(url.openStream(),charset));
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		 HttpGet httpGet=new HttpGet(url);
		 httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
         CloseableHttpResponse response1 = httpclient.execute(httpGet);
         String result = EntityUtils.toString(response1.getEntity(), "utf-8");
        // System.out.println(result);
		return  result;
	}
	private Path getSavedPath(Path path, URL imageUrl) {
			return    Paths.get("C:\\Pic\\"); 
	}
}
