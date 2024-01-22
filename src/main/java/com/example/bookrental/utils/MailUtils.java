package com.example.bookrental.utils;

public class MailUtils {

    public static String setTemplet(String to,String subject){
        String template ="Subject-:"+subject+"\n\n"
                +"Hello, "+to+"\n\n"
                + "This is a message just for you to you about the expiry date of ypur renting service, "+to+". ";

        return template;
    }

}
