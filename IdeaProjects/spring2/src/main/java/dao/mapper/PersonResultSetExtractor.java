package dao.mapper;



/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 6, 2011
 * Time: 2:29:54 PM
 * To change this template use File | Settings | File Templates.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

//import org.springframework.jdbc.core.ResultSetExtractor;

import domainmodel.Person;
import org.springframework.jdbc.core.ResultSetExtractor;

public class PersonResultSetExtractor implements ResultSetExtractor {


	public Object extractData(ResultSet rs) throws SQLException {
		Person person = new Person();
		person.setFirstName(rs.getString(1));
		person.setLastName(rs.getString(2));
		return person;
	}

}

