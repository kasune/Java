package dao;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 6, 2011
 * Time: 2:27:53 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;

import javax.sql.DataSource;

import domainmodel.Person;

public interface Idao {

	void setDataSource(DataSource ds);

	void create(int ID,String firstName, String lastName);

	List<Person> select(String firstname, String lastname);

	List<Person> selectAll();

	void deleteAll();

	void delete(String firstName, String lastName);

}
