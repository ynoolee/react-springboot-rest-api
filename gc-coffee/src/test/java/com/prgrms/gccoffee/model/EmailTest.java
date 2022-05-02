package com.prgrms.gccoffee.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailTest {

	@Test
	public void testInvalidEmail() {
		assertThrows(IllegalArgumentException.class, () -> new Email("accccc"));
	}

	@Test
	public void testValidEmail() {
		String email1 = "abc@gmail.com";
		String email2 = "abcd@gmail.com";
		Email email = new Email("abc@gmail.com");
		assertTrue(email.getEmail().equals(email1));
		assertFalse(email.getEmail().equals(email2));
	}

}