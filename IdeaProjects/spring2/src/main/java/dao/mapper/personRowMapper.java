package dao.mapper;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 6, 2011
 * Time: 2:36:29 PM
 * To change this template use File | Settings | File Templates.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class personRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int line) throws SQLException {
		PersonResultSetExtractor extractor = new PersonResultSetExtractor();
		return extractor.extractData(rs);
	}

}
