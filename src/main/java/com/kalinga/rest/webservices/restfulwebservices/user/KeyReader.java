package com.kalinga.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Value;

public class KeyReader {

    public static String getReadEx;
    @Value("${key.private}")
    private String PRIVATE_KEY;

    @Value("${key.public}")
    private String PUBLIC_KEY;

    private final String readEx="pgp_pub_decrypt(asymdata, dearmor('" + PRIVATE_KEY + "'),'kalinga')";
    private final String writeEx= "pgp_pub_encrypt(?, dearmor('"+PUBLIC_KEY+"'))";

    public String getReadEx() {
        return readEx;
    }

    public String getWriteEx() {
        return writeEx;
    }
}
