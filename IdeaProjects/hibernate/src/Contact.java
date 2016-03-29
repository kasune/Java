/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Oct 17, 2010
 * Time: 2:57:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Contact {
  private String firstName;
  private String lastName;
  private String email;
  private long id;

  /**
   * @return Email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return First Name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return Last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param string Sets the Email
   */
  public void setEmail(String string) {
    email = string;
  }

  /**
   * @param string Sets the First Name
   */
  public void setFirstName(String string) {
    firstName = string;
  }

  /**
   * @param string sets the Last Name
   */
  public void setLastName(String string) {
    lastName = string;
  }

  /**
   * @return ID Returns ID
   */
  public long getId() {
    return id;
  }

  /**
   * @param l Sets the ID
   */
  public void setId(long l) {
    id = l;
  }

}
