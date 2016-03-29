/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 6, 2011
 * Time: 2:47:49 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import dao.MySQLDAO;
import domainmodel.Person;

public final class Main {
	private Main() {
	}

	public static void main(String[] args) {
		MySQLDAO dao = new MySQLDAO();
		// Initialize the datasource, could /should be done of Spring
		// configuration
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/kasun?");
		dataSource.setUsername("kasun");
		dataSource.setPassword("kasun");
		// Inject the datasource into the dao
		dao.setDataSource(dataSource);

		dao.create(1,"kas", "great");
		dao.create(2,"kas", "one");
		dao.create(3,"kas", "2nd");
		dao.create(4,"kas", "super");
		System.out.println("Now select and list all persons");
		List<Person> list = dao.selectAll();
		for (Person myPerson : list) {
			System.out.print(myPerson.getFirstName() + " ");
			System.out.println(myPerson.getLastName());
		}

        String fName = "kas";
        String lName = "super";
		System.out.println("Now select and list all persons with have the "+fName+" "+lName);
		list = dao.select(fName, lName );
		for (Person myPerson : list) {
			System.out.print(myPerson.getFirstName() + " ");
			System.out.println(myPerson.getLastName());
		}

		// Clean-up
		dao.deleteAll();
	}
}

