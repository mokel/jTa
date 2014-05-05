package fr.mokel.trade.gui2.util;


/**
 * Simple object examples
 */
public class PersonProfile {
	private String salutation;
	private String firstName;
	private String lastName;
	private String email;
	private int age;
	private boolean married;

	public String getSalutation() {
		return salutation;
	}

	@SwingField(order = 1)
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstName() {
		return firstName;
	}

	@SwingField(order = 2)
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@SwingField(order = 3)
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	@SwingField(order = 4)
	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	@SwingField(order = 5)
	public void setAge(int age) {
		this.age = age;
	}

	public boolean isMarried() {
		return married;
	}

	@SwingField(order = 6)
	public void setMarried(boolean married) {
		this.married = married;
	}
}