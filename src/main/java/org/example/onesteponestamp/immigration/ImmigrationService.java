package org.example.onesteponestamp.immigration;

import java.time.LocalDate;
import java.util.List;

public interface ImmigrationService {

  Boolean Immigration(String applyNo);

  List<ImmigrationDTO> ImmigrationListSearch(String countryCode, LocalDate date, String applyNo);
}
