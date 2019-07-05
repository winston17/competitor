package com.yzc.bigdata.competitor.util.baseUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * 璺濈璁＄畻宸ュ叿
 * 
 * @author douqr 2018-05-30
 *
 */
public class DistanceUtil {

	private static final String showLocationUrl = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=RguGdBfvanKG10lrLHtUAtka&address=";
	private static final String waypointsDistanceUrl = "http://api.map.baidu.com/telematics/v3/distance?output=json&ak=RguGdBfvanKG10lrLHtUAtka&waypoints=";
	private static final String getGeoCoderUrl1 = "http://api.map.baidu.com/geocoder/v2/?address=";
	private static final String getGeoCoderUrl2 = "&output=json&ak=fhnnXB8B6RK177Q6sIBh9sGIVTaVwDXK&callback=showLocation";
	private static final double EARTH_RADIUS = 6378.137;


	/**
	 * 鍒╃敤鐧惧害api璁＄畻涓ゅ湴涔嬮棿鐨勮窛绂�
	 * 
	 * @param startCityName
	 * @param endCityName
	 * @return
	 * @author douqr 2018-05-30
	 */
	@SuppressWarnings("unchecked")
	public static BigDecimal twoCitysDistance2(String startCityName, String endCityName) {
		
		if (StringUtil.isNullOrEmpty(startCityName) || StringUtil.isNullOrEmpty(endCityName)) {
			return null;
		}

		//鐢变簬鎺ュ彛api涓嶆敮鎸佺┖鏍硷紝鎵�浠ュ厛鍋氬鐞�
		startCityName = startCityName.replaceAll("\\s", "");
		endCityName = endCityName.replaceAll("\\s", "");
		
		//澶勭悊瀹屽啀鍒ゆ柇鏄惁涓虹┖
		if (StringUtil.isNullOrEmpty(startCityName) || StringUtil.isNullOrEmpty(endCityName)) {
			return null;
		}

		try {
			BigDecimal distance = new BigDecimal(0);

			Map<String, Object> startShowLocation = JsonUtil.json2pojo(
					HttpUtil.sendGet(showLocationUrl + startCityName, null), Map.class);

			// 鑾峰彇鍑哄彂鐐圭殑缁忕含搴�
			Map<String, Object> startResultLocation = (Map<String, Object>) startShowLocation.get("result");
			Map<String, Object> startLocation = (Map<String, Object>) startResultLocation.get("location");
			String startLng = startLocation.get("lng").toString();
			String startLat = startLocation.get("lat").toString();

			Map<String, Object> showLocation = JsonUtil.json2pojo(
					HttpUtil.sendGet(showLocationUrl + endCityName, null), Map.class);

			// 鑾峰彇缁堢偣鐨勭粡绾害
			Map<String, Object> endResultLocation = (Map<String, Object>) showLocation.get("result");
			Map<String, Object> endLocation = (Map<String, Object>) endResultLocation.get("location");
			String endLng = endLocation.get("lng").toString();
			String endLat = endLocation.get("lat").toString();

			String showWaypointsDistanceUrl = waypointsDistanceUrl + startLng + "," + startLat + ";" + endLng + ","
					+ endLat;

			// 鑾峰彇涓ょ偣涔嬮棿鐨勮窛绂�
			Map<String, Object> waypointsDistance = JsonUtil.json2pojo(
					HttpUtil.sendGet(showWaypointsDistanceUrl, null), Map.class);
			String distanceStr = waypointsDistance.get("results").toString();
			distanceStr = distanceStr.replaceAll("\\[", "").replaceAll("\\]", "");
			String[] distanceArray = distanceStr.split(",");

			distance = BigDecimal.valueOf(Double.valueOf(distanceArray[0]));
			distance = distance.divide(new BigDecimal(1000)); // 浠ュ崈绫充负鍗曚綅
			return distance.setScale(0, RoundingMode.HALF_UP); //鍘绘帀灏忔暟鐐瑰悗闈㈢殑鏁板瓧
		} catch (Exception e) {
			return null;
		}
	}
	
	
	//鍙﹀涓�涓猘k璁＄畻涓ゅ湴鐨勭粡绾害锛岄�氳繃缁忕含搴﹀湪鏈湴璁＄畻涓ゅ湴璺濈
	@SuppressWarnings("unchecked")
	public static BigDecimal twoCitysDistance(String startCityName, String endCityName) {
		
		if (StringUtil.isNullOrEmpty(startCityName) || StringUtil.isNullOrEmpty(endCityName)) {
			return null;
		}		
		startCityName = startCityName.replaceAll("\\s", "");
		endCityName = endCityName.replaceAll("\\s", "");
		if (StringUtil.isNullOrEmpty(startCityName) || StringUtil.isNullOrEmpty(endCityName)) {
			return null;
		}		
		try {
			BigDecimal distance = new BigDecimal(0);
			Map<String, Object> startInfo = JsonUtil.json2pojo(
			HttpUtil.sendGet(getGeoCoderUrl1 + startCityName + getGeoCoderUrl2, null), Map.class);
			Map<String, Object> startResult = (Map<String, Object>) startInfo.get("result");
			Map<String, Object> startLocation = (Map<String, Object>) startResult.get("location");
			double startLongtitude = (Double) startLocation.get("lng");
			double startLatitude = (Double) startLocation.get("lat");
			
			Map<String, Object> endInfo = JsonUtil.json2pojo(
			HttpUtil.sendGet(getGeoCoderUrl1 + endCityName + getGeoCoderUrl2, null), Map.class);
			Map<String, Object> endResult = (Map<String, Object>) endInfo.get("result");
			Map<String, Object> endLocation = (Map<String, Object>) endResult.get("location");
			double endLongtitude = (Double) endLocation.get("lng");
			double endLatitude = (Double) endLocation.get("lat");
			
			distance = BigDecimal.valueOf(GetDistance(startLongtitude, startLatitude, endLongtitude, endLatitude));
//			return distance;
			return distance.setScale(3, RoundingMode.HALF_UP); //鍘绘帀灏忔暟鐐瑰悗闈㈢殑鏁板瓧
			
		} catch(Exception e) {
			return null;
		}
	}
	
	  private static double rad(double d){
	      return d * Math.PI / 180.0;
	  }
	 //鏍规嵁涓ゅ湴缁忕含搴﹁繑鍥炰袱鍦拌窛绂伙紝鍗曚綅鍏噷
	  public static double GetDistance(double long1, double lat1, double long2, double lat2) {
	      double a, b, d, sa2, sb2;
	      lat1 = rad(lat1);
	      lat2 = rad(lat2);
	      a = lat1 - lat2;
	      b = rad(long1 - long2);
	 
	      sa2 = Math.sin(a / 2.0);
	      sb2 = Math.sin(b / 2.0);
	      d = 2   * EARTH_RADIUS
	              * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
	              * Math.cos(lat2) * sb2 * sb2));
	      return d;
	  }
	  
	  public static void main(String[] args) {
		  System.out.println(twoCitysDistance("安徽省招标集团", "合肥澳澜宝邸"));
	  }


}
