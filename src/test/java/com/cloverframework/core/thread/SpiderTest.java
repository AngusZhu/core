package com.cloverframework.core.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SpiderTest {
	private static final double TOTAL_THREAD=Runtime.getRuntime().availableProcessors()+1.0;
	private final static String url="http://image.baidu.com/channel?c=%E7%BE%8E%E5%A5%B3#%E7%BE%8E%E5%A5%B3";
	private final static CloseableHttpClient httpClient=HttpClients.createDefault();
	String reg="src=['\"]?(.*?(\\.jpg|\\.gif|\\.png))['\"]??[\\s>]+";
	private final Pattern imageUrlPattern=Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
	
	private final String filepath="C:\\Pic\\";
	
	private int range=0;
	
	private int totalPic=0;
	
	private CountDownLatch countDownLatch;

	public static void main(String[] args) {
		new SpiderTest().savePic(url);
	}

	private void savePic(String url2) {
		
		try {
			//1.发起请求,拿到结果
			CloseableHttpResponse response=executeRequest(url2);
			List<String> urls=getMatchUrls(response);
			totalPic=urls.size();
			countDownLatch=new CountDownLatch((int) Math.ceil(totalPic/TOTAL_THREAD));
			Arrays.deepToString(urls.toArray());
			mutilThread2DownLoad(urls);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void mutilThread2DownLoad(final List<String> urls) {
		ExecutorService executors = Executors.newFixedThreadPool((int)TOTAL_THREAD);
		executors.execute(new Runnable() {
			@Override
			public void run() {
				int range=getNextRange();
				int times=(int) Math.ceil(totalPic/TOTAL_THREAD);
				int start=range*times;
				int end=(range+1)*times;
				List<String> subList = urls.subList(start, end);
				for(String url:subList){
					try {
						CloseableHttpResponse response = executeRequest(url);
						HttpEntity entity = response.getEntity();  
						InputStream is = entity.getContent();  
			            File file = new File(filepath);  
			            file.getParentFile().mkdirs();  
			            FileOutputStream fileout = new FileOutputStream(file); 
			            byte[] buffer=new byte[2048];  
			            int ch = 0;  
			            while ((ch = is.read(buffer)) != -1) {  
			                fileout.write(buffer,0,ch);  
			            }  
			            is.close();  
			            fileout.flush();  
			            fileout.close();  
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						countDownLatch.countDown();
					}
				}
			}
		});
		
	}

	private List<String> getMatchUrls(CloseableHttpResponse response) throws ParseException, IOException {
		List<String> urls=new ArrayList<String>();
		String result = EntityUtils.toString(response.getEntity(), "utf-8");
		Matcher matcher=imageUrlPattern.matcher(result);
		while(matcher.find()){
			urls.add(matcher.group(1));
		}
		return urls;
	}

	private CloseableHttpResponse executeRequest(String url2) throws IOException,
			ClientProtocolException {
		HttpGet httpGet=new HttpGet(url2);
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
		return httpClient.execute(httpGet);
	}

	public synchronized int getNextRange(){
		return range++;
	}
}
