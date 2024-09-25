package es.uvigo.esei.letta.dataset;

import java.util.Arrays;
import java.util.Base64;

import es.uvigo.esei.letta.entities.User;

public final class UsersDataset {
	private UsersDataset() {
	}

	public static User[] users() {
		return new User[] {
				new User(normalLogin(), "7bf24d6ca2242430343ab7e3efb89559a47784eea1123be989c1b2fb2ef66e83") };
	}

	public static User user(String login) {
		return Arrays.stream(users()).filter(user -> user.getLogin().equals(login)).findAny()
				.orElseThrow(IllegalArgumentException::new);
	}

	public static String normalLogin() {
		return "normal";
	}

	public static String nonExistentLogin() {
		return "noExisto";
	}

	public static String userToken(String login) {
		final String chain = login + ":" + login + "pass";

		return Base64.getEncoder().encodeToString(chain.getBytes());
	}
}
