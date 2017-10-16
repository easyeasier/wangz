package wangz.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.CharArrayBuffer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wangz on 16-12-24.
 */
@Data
@Slf4j
public class StreamUtils{

    public static String parseStreamToStr(InputStream inputStream){
        if(inputStream == null){
            return null;
        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        char[] arr = new char[1024];
        CharArrayBuffer buffer = new CharArrayBuffer(4096);

        try {
            while ( inputStreamReader.read(arr, 0, 1024) != -1){
                buffer.append(arr, 0, arr.length);
            }
        }catch (IOException e){
            log.error("input stream read error",e);
            throw new RuntimeException("input stream read error");
        }

        return buffer.toString();
    }

    public static void main(String[] args) throws Exception{
        String str = "this is a test";
        InputStream is = new ByteArrayInputStream(str.getBytes("utf-8"));
        System.out.println(StreamUtils.parseStreamToStr(is));
    }
}
