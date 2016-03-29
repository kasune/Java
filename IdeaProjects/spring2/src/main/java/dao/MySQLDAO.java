package dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import dao.mapper.personRowMapper;
import domainmodel.Person;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 6, 2011
 * Time: 2:38:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MySQLDAO implements Idao {

	private DataSource dataSource;

	public void setDataSource(DataSource ds) {
		dataSource = ds;
	}

	public void create(int id,String firstName, String lastName) {
		JdbcTemplate insert = new JdbcTemplate(dataSource);
		insert.update("insert into person (ID,firstname, lastname) VALUES(?,?,?)",
				new Object[] { id,firstName, lastName });
	}

	public List<Person> select(String firstname, String lastname) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query("select  firstname,lastname from person where firstname = ? AND lastname= ?",
						new Object[] { firstname, lastname },
						new personRowMapper());
	}

	public List<Person> selectAll() {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		return select.query("select firstname, lastname from person",new personRowMapper());
	}

	public void deleteAll() {
		JdbcTemplate delete = new JdbcTemplate(dataSource);
		delete.update("DELETE from PERSON");
	}

	public void delete(String firstName, String lastName) {
		JdbcTemplate delete = new JdbcTemplate(dataSource);
		delete.update("DELETE from PERSON where FIRSTNAME= ? AND LASTNAME = ?",
				new Object[] { firstName, lastName });
	}

}
