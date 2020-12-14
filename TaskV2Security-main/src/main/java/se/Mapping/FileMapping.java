package se.Mapping;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import se.domain.File;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

//парсер
public class FileMapping implements RowMapper<File> {

    @Override
    public File mapRow(ResultSet resultSet, int i) throws SQLException {
        File file = new File();
        file.setId(resultSet.getInt("id"));
        file.setUser_id(resultSet.getInt("user_id"));
        file.setName(resultSet.getString("name"));
        file.setDate(resultSet.getString("date"));
        file.setFile_user(resultSet.getInt("file_user"));
        file.setContent(resultSet.getBytes("content"));
        return file;
    }
}
