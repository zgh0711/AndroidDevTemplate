package com.zgh.appdevtemplate.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @创建时间：2015-3-5 下午3:03:25
 * @项目名称：WithBall
 * @author：Er Chen
 * @version：1.0
 * @since：JDK 1.6.0_65
 * @文件名称：MD5Utils.java
 * @copyright：上海海盎信息技术有限公司
 * @类说明：
 */

public class MD5Utils {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest md = null;
	protected static Mac mac = null;

	static {
		try {
			md = MessageDigest.getInstance("MD5");
			mac = Mac.getInstance("HmacMD5");
		} catch (NoSuchAlgorithmException e) {
		}
	}

	public static byte[] getMD5Digest(byte[] bytes) {
		md.update(bytes);
		return md.digest();
	}

	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		md.update(bytes);
		return bufferToHex(md.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static String getFileMD5StringNew(InputStream in, long size)
			throws IOException {
		byte[] byteBuffer = new byte[4096];
		int byteRead = 0;
		while ((byteRead = in.read(byteBuffer)) != -1) {
			size -= byteRead;
			if (size < 0) {
				byteRead += size;
			}
			md.update(byteBuffer, 0, byteRead);
		}
		return bufferToHex(md.digest());
	}

	// HMAC-MD5
	public static byte[] HMAC_MD5(byte[] key, byte[] data) {
		//
		SecretKeySpec sk = new SecretKeySpec(key, "HmacMD5");
		try {
			mac.init(sk);
			return mac.doFinal(data);

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		//
		byte[] key = { 0x01, 0x02, 0x03, 0x02, 0x03, 0x02, 0x03, 0x02, 0x03,
				0x02, 0x03 };
		byte[] data = { 0x01, 0x02, 0x03, 0x02, 0x03, 0x02, 0x03, 0x02, 0x03,
				0x02, 0x03 };
		byte[] out = MD5Utils.HMAC_MD5(key, data);
		System.out.println(out);
	}
}
