package com.lms.library.misc;

import java.util.Scanner;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHash {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String password = sc.nextLine();
    String passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    System.out.println(passwordHash);
    sc.close();
  }
}
