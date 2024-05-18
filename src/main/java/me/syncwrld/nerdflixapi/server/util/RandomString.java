package me.syncwrld.nerdflixapi.server.util;

public class RandomString {

    private final String numbersString = "0123456789";
    private final String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
    private final String specialChars = "!@#$%^&*()_+";

    private final boolean numbers;
    private final boolean upperCase;
    private final boolean lowerCase;
    private final boolean special;

    public RandomString() {
        this(true, false, true, false);
    }

    public RandomString(boolean numbers, boolean upperCase, boolean lowerCase) {
        this(numbers, upperCase, lowerCase, false);
    }

    public RandomString(boolean numbers, boolean upperCase, boolean lowerCase, boolean special) {
        this.numbers = numbers;
        this.upperCase = upperCase;
        this.lowerCase = lowerCase;
        this.special = special;
    }

    public String generate(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(getRandomChar());
        }

        int maxStrength = getMaxStrength();

        if (getStrength(sb.toString()) < maxStrength) {
            return generate(length);
        }

        return sb.toString();
    }

    private char getRandomChar() {
        String chars = numbers ? numbersString : "";
        chars += upperCase ? upperCaseLetters : "";
        chars += lowerCase ? lowerCaseLetters : "";
        chars += special ? specialChars : "";

        return chars.charAt((int) (Math.random() * chars.length()));
    }

    public int getStrength(String string) {
        int maxStrength = getMaxStrength();
        int strength = 0;

        for (int i = 0; i < string.length(); i++) {
            if (numbers && string.charAt(i) >= '0' && string.charAt(i) <= '9') strength++;
            if (upperCase && string.charAt(i) >= 'A' && string.charAt(i) <= 'Z') strength++;
            if (lowerCase && string.charAt(i) >= 'a' && string.charAt(i) <= 'z') strength++;
            if (special && specialChars.indexOf(string.charAt(i)) != -1) strength++;
        }

        return strength - maxStrength;
    }

    public int getMaxStrength() {
        int maxStrength = 0;
        if (numbers) maxStrength++;
        if (upperCase) maxStrength++;
        if (lowerCase) maxStrength++;
        if (special) maxStrength++;
        return maxStrength;
    }
}
