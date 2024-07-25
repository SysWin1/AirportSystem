package org.example.onesteponestamp.autoapply;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UUIDShortener {

  public static String generateShortUUID() {
    UUID uuid = UUID.randomUUID();
    byte[] hash = hashUUID(uuid);
    return encodeBase62(hash, 8); // 길이 8자리의 신청서번호.
  }

  private static byte[] hashUUID(UUID uuid) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      String uuidString = uuid.toString();
      return digest.digest(uuidString.getBytes(StandardCharsets.UTF_8));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static String encodeBase62(byte[] hashBytes, int length) {
    final String base62Chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder base62 = new StringBuilder();
    for (byte b : hashBytes) {
      int unsignedByte = b & 0xFF;
      base62.append(base62Chars.charAt(unsignedByte % 62));
    }
    return base62.substring(0, length); // 원하는 길이로 잘라내기
  }
}
