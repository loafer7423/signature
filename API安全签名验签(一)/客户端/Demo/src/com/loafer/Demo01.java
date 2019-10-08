package com.loafer;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Demo01 {

	public static void main(String[] args) {
		String randomStr = getRandomString(16);
		String jsonstr = "{\"base\":{" + "\"nonce\":\"" + randomStr + "\"," + "\"timestamp\":"
				+ System.currentTimeMillis() + "," + "\"userId\":12," + "\"from\":\"android\"" + "},"
				+ "\"name\":\"李四2\"" + "}";
		JSONObject jsonObject = JSON.parseObject(jsonstr);
		JSONObject base = (JSONObject) jsonObject.get("base");
		Map map = generateSignStr(base);
		String param = formatUrlMap(map, true, true);
		String signature = md5(param);
//		System.out.println("客户端签名：" + signature);
		base.put("signature", signature);
		System.out.println("客户端生成的请求签名参数："+jsonObject);

	}

	/**
	 * @description: 将参数按照字段名排序
	 * @author wangdong
	 */
	public static String formatUrlMap(Map<String, Object> paraMap, boolean urlEncode, boolean keyToLower) {
		String buff = "";
		Map<String, Object> tmpMap = paraMap;
		try {
			List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(tmpMap.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
				@Override
				public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			// 构造URL 键值对的格式
			StringBuilder buf = new StringBuilder();
			for (Map.Entry<String, Object> item : infoIds) {
				if (StringUtils.isNotBlank(item.getKey())) {
					String key = item.getKey();
					Object val = item.getValue();
					if (urlEncode) {
						val = URLEncoder.encode(val.toString(), "utf-8");
					}
					if (keyToLower) {
						buf.append(key.toLowerCase() + "=" + val);
					} else {
						buf.append(key + "=" + val);
					}
					buf.append("&");
				}
			}

			buff = buf.toString();
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			return null;
		}
		return buff;
	}

	/**
	 * @description: 将json格式转换为map对象
	 * @author wangdong
	 */
	private static Map generateSignStr(JSONObject base) {
		String timestamp = base.getString("timestamp");
		String nonce = base.getString("nonce");
		String userId = base.getString("userId");
		Map map = new HashMap();
		map.put("nonce", nonce);
		map.put("timestamp", timestamp);
		map.put("userId", userId);
		return map;
	}

	/**
	 * @description: 客户端生成随机数
	 * @author wangdong
	 */
	public static String getRandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
    /**
     * @description: md5加密
     * @author wangdong
     */
	public static String md5(String content) {
		// 用于加密的字符
		char[] md5String = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			// 使用平台默认的字符集将md5String编码为byte序列,并将结果存储到一个新的byte数组中
			byte[] byteInput = content.getBytes();
			// 信息摘要是安全的单向哈希函数,它接收任意大小的数据,并输出固定长度的哈希值
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// MessageDigest对象通过使用update方法处理数据,使用指定的byte数组更新摘要
			mdInst.update(byteInput);
			// 摘要更新后通过调用digest() 执行哈希计算,获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成16进制的字符串形式
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = md5String[byte0 >>> 4 & 0xf];
				str[k++] = md5String[byte0 & 0xf];
			}
			// 返回加密后的字符串
			return new String(str);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
