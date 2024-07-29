package org.example.onesteponestamp.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
@AllArgsConstructor
public class AdminDTO {

  @NonNull
  private String id;
  private String name;
}
