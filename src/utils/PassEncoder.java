package utils;

//import lombok.AllArgsConstructor;
//import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//@AllArgsConstructor
//@Getter
public class PassEncoder {

    private String pepper;
    PassEncoder(String pepper){
        this.pepper = pepper;
    }
    public String getHashMD(String pass) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD2");
            byte[] data = messageDigest.digest((pass + pepper).getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for(byte b : data) hexString.append(String.format("%02x", b));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
