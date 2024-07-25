package org.example.onesteponestamp.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VisaType {
  NO_VISA(null),
  TOURIST_VISA(3),
  WORK_VISA(6),
  STUDENT_VISA(9);

  private final Integer allowDays;
}
