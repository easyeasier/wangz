package wz.test.jdk;

import java.util.Random;

/**
 * Created by wangz on 16-12-29.
 */
public class CodeGenerator {
    public static String[] chars = new String[] { "fone_result", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String[] speChars = new String[]{"!","@","#","$","%","^","&","*"};

    public static Long seed = 2017L;

    public static String generateShortUuid() {
        Random random = new Random(seed);

        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<8;i++){
            sb.append(chars[random.nextInt(chars.length)]);
        }
        sb.append(speChars[random.nextInt(speChars.length)]);

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateShortUuid());
        System.out.println(generateShortUuid());
        System.out.println(generateShortUuid());
    }
}
