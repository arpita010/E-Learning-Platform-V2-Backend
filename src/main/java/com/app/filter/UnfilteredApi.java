package com.app.filter;

import java.util.ArrayList;
import java.util.List;

public class UnfilteredApi {

  private static List<String> unfilteredApiList = new ArrayList<>();

  static {
    //    unfilteredApiList.add("^/api/v1/auth/.*$");
    unfilteredApiList.add("^/api/v1/health");
    unfilteredApiList.add("^/api/v1/user/signup/init");
    unfilteredApiList.add("^/api/v1/user/signin/init");
    unfilteredApiList.add("^/api/v1/user/signup/validate");
    unfilteredApiList.add("^/api/v1/user/signin/validate");
    unfilteredApiList.add("^/api/v1/shortened/.*$");
    unfilteredApiList.add("^/api/v1/classroom/\\d+/user/\\d+/join");
  }

  public static boolean hasPath(String url) {
    return unfilteredApiList.stream().anyMatch(url::matches);
  }
}
