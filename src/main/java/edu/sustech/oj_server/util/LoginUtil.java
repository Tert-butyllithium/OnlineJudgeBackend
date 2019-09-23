package edu.sustech.oj_server.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.security.MD5Encoder;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class LoginUtil {
    static final Charset charset = StandardCharsets.ISO_8859_1;

    static String md5(String str){
        try {
            var dig = MessageDigest.getInstance("MD5");
            dig.update(str.getBytes(charset));
            return new BigInteger(1,dig.digest()).toString(16).substring(0,32);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    static String sha1(String str){
        try {
            var dig = MessageDigest.getInstance("SHA-1");
            dig.update(str.getBytes(charset));
            return new String(dig.digest(),0,20,charset);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    static String base64encode(String str){
        return Base64.getEncoder().encodeToString(str.getBytes(charset));
    }

    static String base64decode(String str){
        return new String(Base64.getDecoder().decode(str.getBytes(charset)),charset);
    }

    /**
     * check the password
     * @param pass the password
     * @param saved saved words in database
     * @return true if match
     */
    public static boolean passwordCheck(String pass,String saved){
        String svd= base64decode(saved);
        String salt=svd.substring(20);
        pass= md5(pass);

        String hash= base64encode(sha1(pass+salt)+salt);
        return hash.equals(saved);
    }

    /**
     * function pwGen($password,$md5ed=False)
     * {
     * 	if (!$md5ed) $password=md5($password);
     * 	$salt = sha1(rand());
     * 	$salt = substr($salt, 0, 4);
     * 	$hash = base64_encode( sha1($password . $salt, true) . $salt );
     * 	return $hash;
     *
     * @param password
     * @return
     */
    public static String passwordGen(String password){
        password = md5(password);
        Random rnd= new MTRandom(System.currentTimeMillis());
        long val=Math.abs(rnd.nextInt());
        String salt=sha1(Long.toString(val+10000)).substring(0,4);
        String hash = base64encode(sha1(password+salt)+salt);
        return hash;
    }
}
