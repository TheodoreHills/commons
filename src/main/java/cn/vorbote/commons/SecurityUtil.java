package cn.vorbote.commons;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * This tool class can convert String to Base64-String or MD5-String
 * @author TheodoreHills
 */
public class SecurityUtil {

    private static final int KEY_SIZE = 128;
    private static final String ALGORITHM = "AES";
    private static final String RNG_ALGORITHM = "SHA1PRNG";

    /**
     * Encrypt the string via MD5
     * <hr>
     * <p><b><i>Be advised: You will not able to recover this encrypted String</i></b></p>
     * @param value The string will be encrypted
     * @return The encrypted String
     */
    public static String Md5Encrypt(String value) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(value.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest();
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                String str = Integer.toHexString(b & 0xff);
                if (str.length() == 1) {
                    builder.append("0");
                }
                builder.append(str);
            }
            result = builder.toString();
            // It will get no exception, so ignore it
        } catch (NoSuchAlgorithmException ignored) { }
        return result;
    }

    /**
     * Encrypt the string via Base64
     *
     * @param value The string will be encrypted
     * @return The encrypted String
     */
    public static String Base64Encode(String value) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedString = encoder.encode(value.getBytes(StandardCharsets.UTF_8));

        return new String(encodedString);
    }

    /**
     * Decode the string via Base64
     *
     * @param value The string will be encrypted
     * @return The encrypted String
     */
    public static String Base64Decode(String value) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedString = decoder.decode(value.getBytes(StandardCharsets.UTF_8));

        return new String(decodedString);
    }

    /**
     * Generate an object of key
     * @return The Key object
     */
    private static SecretKey generateKey(byte[] key) throws Exception {
        // 创建安全随机数生成器
        SecureRandom random = SecureRandom.getInstance(RNG_ALGORITHM);
        // 设置 密钥key的字节数组 作为安全随机数生成器的种子
        random.setSeed(key);

        // 创建 AES算法生成器
        KeyGenerator gen = KeyGenerator.getInstance(ALGORITHM);
        // 初始化算法生成器
        gen.init(KEY_SIZE, random);

        // 生成 AES密钥对象, 也可以直接创建密钥对象: return new SecretKeySpec(key, ALGORITHM);
        return gen.generateKey();
    }

    /**
     * Encrypt the plain by the key
     * @param plainBytes The string you need to encrypt
     * @param key The key you used
     * @return The encoded byte array
     */
    public static byte[] Encrypt(byte[] plainBytes, byte[] key) throws Exception {
        // 生成密钥对象
        SecretKey secKey = generateKey(key);

        // 获取 AES 密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化密码器（加密模型）
        cipher.init(Cipher.ENCRYPT_MODE, secKey);

        // 加密数据, 返回密文
        return cipher.doFinal(plainBytes);
    }

    /**
     * 数据解密: 密文 -> 明文
     */
    public static byte[] decrypt(byte[] cipherBytes, byte[] key) throws Exception {
        // 生成密钥对象
        SecretKey secKey = generateKey(key);

        // 获取 AES 密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化密码器（解密模型）
        cipher.init(Cipher.DECRYPT_MODE, secKey);

        // 解密数据, 返回明文
        return cipher.doFinal(cipherBytes);
    }

    private static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                // nothing
            }
        }
    }

}
