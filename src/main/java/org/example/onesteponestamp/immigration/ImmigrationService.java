package org.example.onesteponestamp.immigration;

import java.util.List;

public interface ImmigrationService {

  String Immigration(String applyNo);

  List<ImmigrationDTO> ImmigrationListSearch(String countryCode,String selectDate);
}
