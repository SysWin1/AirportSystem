package org.example.onesteponestamp.common;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VisaType {
  NO_VISA(null, "비자없음"),
  TOURIST_VISA(3, "여행 비자"),
  WORK_VISA(6, "취업 비자"),
  STUDENT_VISA(9, "학생 비자");

  private final Integer allowDays;
  private final String frontMessage;

  private static final Map<String, VisaType> FRONT_TO_VISA_TYPE = new HashMap<>();

  static { // 초기 세팅
    for (VisaType v : VisaType.values()) {
      FRONT_TO_VISA_TYPE.put(v.frontMessage, v);
    }
  }

  public static VisaType getVisaType(String frontMessage) {
    if (!FRONT_TO_VISA_TYPE.containsKey(frontMessage)) {
      throw new IllegalArgumentException("매치되는 enum 값이 없습니다 : " + frontMessage);
    }
    return FRONT_TO_VISA_TYPE.get(frontMessage);
  }
}
