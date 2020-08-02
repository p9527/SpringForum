package jqhk.ssm;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;

public class Utility {

    private static Logger logger = LoggerFactory.getLogger(Utility.class);

    static public void log(String format, Object...args) {
        logger.info(String.format(format, args));
    }


    public static void save(String path, String data) {
        try (FileOutputStream os = new FileOutputStream(path)) {
            os.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            String s = String.format("Save file <%s> error <%s>", path, e);
            throw new RuntimeException(s);
        }
    }

    public static String load(String path) {
        String content;
        try (FileInputStream is = new FileInputStream(path)) {
            content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            String s = String.format("Load file <%s> error <%s>", path, e);
            throw new RuntimeException(s);
        }
        return content;
    }

    public static String escapeScript(String content) {
        content = content.replaceAll("<script>", "&ltscript&gt");
        content = content.replaceAll("</script>", "&lt/script&gt");
        return content;
    }

    public static MysqlDataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("zkuvzkqi");
        dataSource.setServerName("127.0.0.1");
        dataSource.setDatabaseName("ssm");

        try {
            dataSource.setCharacterEncoding("UTF-8");
            dataSource.setServerTimezone("Asia/Shanghai");

            Utility.log("url: %s", dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }

    public static void main(String[] args) {

    }
}
