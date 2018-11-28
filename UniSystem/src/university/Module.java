package university;

public class Module {

	String codeOfModule;
	String name;
	int numberOfCredits;
	
	public Module(String code, String n, int credits) {
		codeOfModule = code;
		name = n;
		numberOfCredits = credits;
	}
	
	public String getCodeOfModule() {
		return codeOfModule;
	}
	public String getName() {
		return name;
	}
	public int getNumberOfCredits() {
		return numberOfCredits;
	}
	
	
}
