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
				+ "\"name\":\"����2\"" + "}";
		JSONObject jsonObject = JSON.parseObject(jsonstr);
		JSONObject base = (JSONObject) jsonObject.get("base");
		Map map = generateSignStr(base);
		String param = formatUrlMap(map, true, true);
		String signature = md5(param);
//		System.out.println("�ͻ���ǩ����" + signature);
		base.put("signature", signature);
		System.out.println("�ͻ������ɵ�����ǩ��������"+jsonObject);

	}

	/**
	 * @description: �����������ֶ�������
	 * @author wangdong
	 */
	public static String formatUrlMap(Map<String, Object> paraMap, boolean urlEncode, boolean keyToLower) {
		String buff = "";
		Map<String, Object> tmpMap = paraMap;
		try {
			List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(tmpMap.entrySet());
			// �����д�����������ֶ����� ASCII ���С���������ֵ���
			Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
				@Override
				public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			// ����URL ��ֵ�Եĸ�ʽ
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
	 * @description: ��json��ʽת��Ϊmap����
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
	 * @description: �ͻ������������
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
     * @description: md5����
     * @author wangdong
     */
	public static String md5(String content) {
		// ���ڼ��ܵ��ַ�
		char[] md5String = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			// ʹ��ƽ̨Ĭ�ϵ��ַ�����md5String����Ϊbyte����,��������洢��һ���µ�byte������
			byte[] byteInput = content.getBytes();
			// ��ϢժҪ�ǰ�ȫ�ĵ����ϣ����,�����������С������,������̶����ȵĹ�ϣֵ
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// MessageDigest����ͨ��ʹ��update������������,ʹ��ָ����byte�������ժҪ
			mdInst.update(byteInput);
			// ժҪ���º�ͨ������digest() ִ�й�ϣ����,�������
			byte[] md = mdInst.digest();
			// ������ת����16���Ƶ��ַ�����ʽ
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = md5String[byte0 >>> 4 & 0xf];
				str[k++] = md5String[byte0 & 0xf];
			}
			// ���ؼ��ܺ���ַ���
			return new String(str);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
