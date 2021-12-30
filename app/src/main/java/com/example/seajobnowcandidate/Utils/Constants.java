package com.example.seajobnowcandidate.Utils;

import java.util.Scanner;

public class Constants {
     public static final String SHARED_PREFRENCE_NAME = "AppData";
     public static final String IS_LOGGED_IN = "login";
     public static final String indosFlag = "0";
     public static final String cdcFlag = "1";
     public static final int RESPONSE_CODE = 200;// http response code
     public static final int RESPONSE_CODE1 = 201;// http response code
     public static final int RESPONSE_CODE_UNAUTHORISED = 203;// http response code
     public static final int RESPONSE_STATUS = 1;//server response code
     public static final int RESPONSE_ERROR = 500;


     public interface INTENT_KEYS {
          public  String KEY_CANDIDATE_ID = "candidate_id";
          public  String KEY_CANDIDATE_FNAME = "candidate_fname";
          public  String KEY_CANDIDATE_MNAME = "candidate_mname";
          public  String KEY_CANDIDATE_LNAME = "candidate_lname";
          public  String KEY_CANDIDATE_FullNAME = "candidate_fullname";
          public  String KEY_CANDIDATE_EMAIL = "candidate_email";
          public  String KEY_CANDIDATE_INDOS = "candidate_indos";
     }


     public String nameInitials(String name){
          StringBuilder initials = new StringBuilder();
          boolean addNext = true;
          if (name != null) {
               for (int i = 0; i < name.length(); i++) {
                    char c = name.charAt(i);
                    if (c == ' ' || c == '-' || c == '.') {
                         addNext = true;
                    } else if (addNext) {
                         initials.append(c);
                         addNext = false;
                    }
               }
          }
          return initials.toString();
     }
}
