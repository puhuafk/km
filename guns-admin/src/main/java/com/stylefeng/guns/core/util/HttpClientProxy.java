package com.stylefeng.guns.core.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientProxy {

	// 连接超时
	private static int connectTimeOut = 5000;

	// 读取数据超时
	private static int readTimeOut = 10000;

	// 请求编码
	private static String requestEncoding = "UTF-8";

	/**
	 * <pre>
	 * 发送带参数的GET的HTTP请求
	 * </pre>
	 * @param reqUrl HTTP请求URL
	 * @param parameters 参数映射表
	 * @return HTTP响应的字符串
	 */
	@SuppressWarnings("deprecation")
	public static String doGet(String reqUrl, Map parameters) {
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry element = (Entry) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(), HttpClientProxy.requestEncoding));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}
			HttpGet httpGet = new HttpGet(reqUrl + (reqUrl.contains("?") ? "&" : "?") + params);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	/**
	 * <pre>
	 * 发送不带参数的GET的HTTP请求
	 * </pre>
	 * @param reqUrl HTTP请求URL
	 * @return HTTP响应的字符串
	 */
	@SuppressWarnings("deprecation")
	public static String doGet(String reqUrl) {
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(reqUrl);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	/**
	 * <pre>
	 * 发送带参数的POST的HTTP请求
	 * </pre>
	 * @param reqUrl HTTP请求URL
	 * @param parameters 参数映射表
	 * @return HTTP响应的字符串
	 */
	@SuppressWarnings("deprecation")
	public static String doPost(String reqUrl, Map<String, Object> parameters) {
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(reqUrl);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameters != null) {
				for (Entry<String, Object> entry : parameters.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HttpClientProxy.requestEncoding));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	/**
	 * @return 连接超时(毫秒)
	 * @see net.wit.util.hengpeng.common.web.HttpRequestProxy#connectTimeOut
	 */
	public static int getConnectTimeOut() {
		return HttpClientProxy.connectTimeOut;
	}

	/**
	 * @return 读取数据超时(毫秒)
	 * @see net.wit.util.hengpeng.common.web.HttpRequestProxy#readTimeOut
	 */
	public static int getReadTimeOut() {
		return HttpClientProxy.readTimeOut;
	}

	/**
	 * @return 请求编码
	 * @see net.wit.util.hengpeng.common.web.HttpRequestProxy#requestEncoding
	 */
	public static String getRequestEncoding() {
		return requestEncoding;
	}

	/**
	 * @param connectTimeOut 连接超时(毫秒)
	 * @see net.wit.util.hengpeng.common.web.HttpRequestProxy#connectTimeOut
	 */
	public static void setConnectTimeOut(int connectTimeOut) {
		HttpClientProxy.connectTimeOut = connectTimeOut;
	}

	/**
	 * @param readTimeOut 读取数据超时(毫秒)
	 * @see net.wit.util.hengpeng.common.web.HttpRequestProxy#readTimeOut
	 */
	public static void setReadTimeOut(int readTimeOut) {
		HttpClientProxy.readTimeOut = readTimeOut;
	}

	/**
	 * @param requestEncoding 请求编码
	 * @see net.wit.util.hengpeng.common.web.HttpRequestProxy#requestEncoding
	 */
	public static void setRequestEncoding(String requestEncoding) {
		HttpClientProxy.requestEncoding = requestEncoding;
	}

}
