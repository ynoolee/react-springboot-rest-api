package com.prgrms.gccoffee.model;

import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

public class Email {
	private final String email;
	private final Pattern pattern = Pattern.compile("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");

	public Email(String email) {
		// 검증
		Assert.notNull(email, "email should not be null");
		Assert.isTrue(email.length() >=4 && email.length() <=50, "email length must be between 4 and 50 characters");
		Assert.isTrue(checkAddress(email), "Invalid email address"); // Assert.isTrue(expression,message) expression 이 false 가 되면 메시지 발생
		this.email = email;
	}



	private boolean checkAddress(String email) {
		// Regex 사용 가능
		return pattern.matcher(email)
			.matches();
	}

	// VO 는 euqlas, hashcode 구현 해 주는게 좋음

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Email email1 = (Email)o;
		return Objects.equals(email, email1.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public String toString() {
		return "Email{" +
			"email='" + email + '\'' +
			'}';
	}

	public String getEmail() {
		return email;
	}
}
