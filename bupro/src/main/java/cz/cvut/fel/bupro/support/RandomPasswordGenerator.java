package cz.cvut.fel.bupro.support;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomPasswordGenerator {

	private final Random random = new Random();

	public String generate(int length) {
		String src = "abcdgefhijklmnoprqstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()=-_+;.,?";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(src.charAt(random.nextInt(src.length())));
		}
		return sb.toString();
	}
}
