package com.ovit.jcw.utils;

import java.io.PrintStream;
import java.math.BigInteger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

public class AESUtils {
	private static final String KEY = "n49ujGhCdxNeYFTYUEoxBxNKZjMH0Gaj";
	private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

	public static void main(String[] args) throws Exception {
		//String content = "wanglu@51bsi.com";//2019-9-4
		String content ="6216f8a75fd5bb3d5f22b6f9958cdede3fc086c2";
		System.out.println("加密前：" + content);

		System.out.println("加密密?和解密密?：n49ujGhCdxNeYFTYUEoxBxNKZjMH0Gaj");

		String encrypt = aesEncrypt(content, "n49ujGhCdxNeYFTYUEoxBxNKZjMH0Gaj");
		System.out.println("加密后：" + encrypt);

		String decrypt = aesDecrypt(encrypt, "n49ujGhCdxNeYFTYUEoxBxNKZjMH0Gaj");
		System.out.println("解密后：" + decrypt);
	}

	public static String aesEncrypt(String content) throws Exception {
		return aesEncrypt(content, "n49ujGhCdxNeYFTYUEoxBxNKZjMH0Gaj");
	}

	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(1, new SecretKeySpec(encryptKey.getBytes(), "AES"));

		return cipher.doFinal(content.getBytes("utf-8"));
	}

	public static String aesDecrypt(String encrypt) throws Exception {
		return aesDecrypt(encrypt, "n49ujGhCdxNeYFTYUEoxBxNKZjMH0Gaj");
	}

	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}

	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(2, new SecretKeySpec(decryptKey.getBytes(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		return new String(decryptBytes);
	}

	public static String base64Encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public static byte[] base64Decode(String base64Code) throws Exception {
		return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
	}

	public static String binary(byte[] bytes, int radix) {
		return new BigInteger(1, bytes).toString(radix);
	}
}