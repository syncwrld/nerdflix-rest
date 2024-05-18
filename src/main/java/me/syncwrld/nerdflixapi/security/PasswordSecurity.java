package me.syncwrld.nerdflixapi.security;

import java.util.Arrays;
import java.util.List;

public class PasswordSecurity {

  private final char[] uppercaseLetters = {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
    'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
  };
  private final char[] lowercaseLetters = {
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
    't', 'u', 'v', 'w', 'x', 'y', 'z'
  };
  private final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
  private final char[] specialCharacters = {
    '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=', '[', ']', '{', '}', '|',
    '\\', ':', ';', '<', '>', '?', '/', '.'
  };
  private final List<String> commonCombinations = Arrays.asList("123", "mudar", "cafe");

  public static void main(String[] args) {
    PasswordSecurity passwordSecurity = new PasswordSecurity();
    System.out.println(passwordSecurity.getRecommendedPassword());
    System.out.println(passwordSecurity.getStrength(passwordSecurity.getRecommendedPassword()));
  }

  public int getStrength(String password) {
    int strength = 0;

    /*
    0 = very weak
    1 = weak
    2 = medium
    3 = strong
    4 = very strong
    5 = extremely strong
    6 = human impossible
    */

    if (password.length() >= 8) strength += 1;
    if (password.matches(".*[a-z].*")) strength += 1;
    if (password.matches(".*[A-Z].*")) strength += 1;
    if (password.matches(".*\\d.*")) strength += 1;
    if (password.matches(".*[!@#$%^&*()].*")) strength += 1;
    if (commonCombinations.contains(password)) strength -= 1;
    return strength;
  }

  public String getRecommendedPassword() {
    int length = 24;
    StringBuilder password = new StringBuilder();

    /*
    0 = uppercase
    1 = lowercase
    2 = digits
    3 = special characters
    */

    for (int i = 0; i < length; i++) {
      int type = (int) (Math.random() * 4);
      switch (type) {
        case 0 -> password.append(uppercaseLetters[(int) (Math.random() * uppercaseLetters.length)]);
        case 1 -> password.append(lowercaseLetters[(int) (Math.random() * lowercaseLetters.length)]);
        case 2 -> password.append(digits[(int) (Math.random() * digits.length)]);
        case 3 -> password.append(specialCharacters[(int) (Math.random() * specialCharacters.length)]);
      }
    }

    return getStrength(password.toString()) >= 3 ? password.toString() : getRecommendedPassword();
  }
}
