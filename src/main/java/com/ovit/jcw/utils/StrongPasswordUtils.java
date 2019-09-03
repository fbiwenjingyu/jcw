package com.ovit.jcw.utils;

import java.io.PrintStream;
import org.owasp.esapi.Authenticator;
import org.owasp.esapi.ESAPI;

public class StrongPasswordUtils {
	public static String generateStrongPassword() {
		Authenticator passInstance = ESAPI.authenticator();
		return passInstance.generateStrongPassword();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			System.out.println(generateStrongPassword());
		}
	}
}