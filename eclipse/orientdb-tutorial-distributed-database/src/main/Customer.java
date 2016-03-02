package main;

public class Customer {

	private String sSurname;
	private String sName;
	private String sStreet;
	private String sCity;

	public Customer(){}
	
	public Customer(String Surname,String Name,String Street,String City)
	{
		this.sSurname = Surname;
		this.sName = Name;
		this.sStreet= Street;
		this.sCity =  City;		
	}
	
	public String getsSurname() {
		return sSurname;
	}

	public void setsSurname(String sSurname) {
		this.sSurname = sSurname;
	}

	public String getsStreet() {
		return sStreet;
	}

	public void setsStreet(String sStreet) {
		this.sStreet = sStreet;
	}

	public String getsCity() {
		return sCity;
	}

	public void setsCity(String sCity) {
		this.sCity = sCity;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	@Override
	public String toString() {
		return String.format("Surname: %s  |  Name: %s  |  Address: %s %s",sSurname, sName, sCity, sStreet);
	}

}
