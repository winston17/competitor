package com.yzc.bigdata.competitor.util.baseUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
	public static String postUrlByString(String postUrl, Object path) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(postUrl);
			httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
			HttpEntity entity = new StringEntity(path.toString(), "UTF-8");
			httpPost.setEntity(entity);
			HttpResponse result = httpclient.execute(httpPost);
			String out = EntityUtils.toString(result.getEntity());
			httpclient.getConnectionManager().shutdown();
			return out;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 灏哻ookie灏佽鍒癕ap閲岄潰
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> ReadCookieMap(HttpServletRequest request) {
		Map<String, String> cookieMap = new HashMap<String, String>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie.getValue());
			}
		}
		return cookieMap;
	}

	public static boolean IsCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param postUrl
	 * @param obj
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T postUrlByModel(String postUrl, Object obj, Class<T> clazz) throws Exception {
		
		//post 鏁版嵁濡傛灉鍙傛暟涓虹┖锛屽彲鑳戒細鏈夊紓甯革紝鐗瑰仛浠ヤ笅澶勭悊
		if(obj == null || obj == ""){
			obj = " ";
		}
		
		Object json;
		if (obj.getClass().equals(String.class)) {
			json = obj; //String绫诲瀷鐨勫崟涓弬鏁帮紝鐩存帴褰撳唴瀹筽ost杩囧幓
		} else {
			json = JsonUtil.obj2json(obj); //瀵硅薄杞寲涓簀son
		}
		// System.out.println("杈撳叆鍙傛暟锛�" + json.toString());

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(postUrl);
		httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
		HttpEntity entity = new StringEntity(json.toString(), "UTF-8");
		httpPost.setEntity(entity);
		HttpResponse result = httpclient.execute(httpPost);
		String out = EntityUtils.toString(result.getEntity() , "UTF-8").trim();
		httpclient.getConnectionManager().shutdown();

		// 瑙ｆ瀽缁撴灉
		// JSONObject jsonOut = JSONObject.fromObject(out);

		// System.out.println("鎺ュ彛杩斿洖锛�" + out);

		if (String.class.equals(clazz)) {
			return (T) out;
		}

		// 瑙ｆ瀽缁撴灉
		T t = (T) JsonUtil.json2pojo(out, clazz);

		return t;

	}

	@SuppressWarnings("unchecked")
	public static <T> T getUrlByModel(String postUrl, Class<T> clazz) throws Exception {

		// System.out.println("杈撳叆鍙傛暟锛�" + json.toString());

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(postUrl);
		httpGet.setHeader("Content-Type", "application/json; charset=UTF-8");
		HttpResponse result = httpclient.execute(httpGet);
		String out = EntityUtils.toString(result.getEntity());
		httpclient.getConnectionManager().shutdown();
		System.err.println(out);

		// 瑙ｆ瀽缁撴灉
		// JSONArray jsonOut = JSONArray.fromObject(out);

		if (String.class.equals(clazz)) {
			return (T) out;
		}

		// 瑙ｆ瀽缁撴灉
		T t = (T) JsonUtil.json2pojo(out, clazz);

		return t;

	}

	/**
	 * 鑾峰彇ip鍦板潃
	 * 
	 * @return
	 * @author douqr 2018-01-16
	 */
	public static String getCurrentIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (!StringUtil.isNullOrEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 澶氭鍙嶅悜浠ｇ悊鍚庝細鏈夊涓猧p鍊硷紝绗竴涓猧p鎵嶆槸鐪熷疄ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (!StringUtil.isNullOrEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	/**
	 * get鏂瑰紡鍙戦�佽姹�
	 * @author douqr 2018-05-30
	 */
	public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 鎵撳紑鍜孶RL涔嬮棿鐨勮繛鎺�
			URLConnection connection = realUrl.openConnection();
            // 璁剧疆閫氱敤鐨勮姹傚睘鎬�
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 寤虹珛瀹為檯鐨勮繛鎺�
            connection.connect();
            
            // 瀹氫箟 BufferedReader杈撳叆娴佹潵璇诲彇URL鐨勫搷搴�
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("鍙戦�丟ET璇锋眰鍑虹幇寮傚父锛�" + e);
            e.printStackTrace();
        }
        // 浣跨敤finally鍧楁潵鍏抽棴杈撳叆娴�
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
