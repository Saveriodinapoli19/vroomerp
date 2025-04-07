package com.vroomerp.common.util;


import java.security.SecureRandom;
import java.util.Random;

public class GenerateRandomPassword {

	static char[] SYMBOLS = "$*?!@#%&".toCharArray();
	static char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	static char[] UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	static char[] NUMBERS = "0123456789".toCharArray();
	static char[] ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$*!@#%&".toCharArray();
	static Random rand = new SecureRandom();

	public String getPassword() {

		int randomInt = (int) (Math.random() * (15 - 8 + 1) + 8);

		char[] password = new char[randomInt];

		password[0] = LOWERCASE[rand.nextInt(LOWERCASE.length)];
		password[1] = UPPERCASE[rand.nextInt(UPPERCASE.length)];
		password[2] = NUMBERS[rand.nextInt(NUMBERS.length)];
		password[3] = SYMBOLS[rand.nextInt(SYMBOLS.length)];

		for (int i = 4; i < randomInt; i++) {
			password[i] = ALL_CHARS[rand.nextInt(ALL_CHARS.length)];
		}

		for (int i = 0; i < password.length; i++) {
			int randomPosition = rand.nextInt(password.length);
			char temp = password[i];
			password[i] = password[randomPosition];
			password[randomPosition] = temp;
		}

		return new String(password);
	}

	public String getPasswordPin() {

		int pinLength = 10;

		char[] password = new char[pinLength];

		password[0] = NUMBERS[rand.nextInt(NUMBERS.length)];
		password[1] = NUMBERS[rand.nextInt(NUMBERS.length)];
		password[2] = NUMBERS[rand.nextInt(NUMBERS.length)];
		password[3] = NUMBERS[rand.nextInt(NUMBERS.length)];

		for (int i = 4; i < pinLength; i++) {
			password[i] = NUMBERS[rand.nextInt(NUMBERS.length)];
		}

		for (int i = 0; i < password.length; i++) {
			int randomPosition = rand.nextInt(password.length);
			char temp = password[i];
			password[i] = password[randomPosition];
			password[randomPosition] = temp;
		}

		return new String(password);
	}

}

