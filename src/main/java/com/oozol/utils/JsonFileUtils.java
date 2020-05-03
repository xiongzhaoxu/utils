
package com.oozol.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class JsonFileUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonFileUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonFileUtils() {
    }

    public static <T> List<T> read(String filePath, Class<T> clazz) {
        Resource jsonConfig = new ClassPathResource(filePath);
        String content = readContent(jsonConfig);
        return (List)parseJson(content, List.class, clazz);
    }

    public static String readContent(Resource config) {
        return readContent(config, false);
    }

    public static String readContent(Resource config, boolean ln) {
        StringBuffer stringBuffer = new StringBuffer();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(config.getInputStream(), "UTF-8"));
            Throwable var4 = null;

            try {
                String b;
                try {
                    while((b = bufferedReader.readLine()) != null) {
                        stringBuffer.append(b);
                        if (ln) {
                            stringBuffer.append("\n");
                        }
                    }
                } catch (Throwable var14) {
                    var4 = var14;
                    throw var14;
                }
            } finally {
                if (bufferedReader != null) {
                    if (var4 != null) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable var13) {
                            var4.addSuppressed(var13);
                        }
                    } else {
                        bufferedReader.close();
                    }
                }

            }
        } catch (IOException var16) {
            log.error(var16.getMessage(), var16);
        }

        return stringBuffer.toString();
    }

    public static <T> T parseJson(String jsonStr, Class<?> collectionClass, Class... elementClasses) {
        Object result = null;

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            result = mapper.readValue(jsonStr, javaType);
        } catch (Exception var5) {
            log.error("Error in readJson ===> ", var5);
        }

        return (T) result;
    }
}
