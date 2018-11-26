package users;
import university.Degree;
import university.Module;

public class Registrar extends User {

	public boolean moduleRegister(Module module, Student student) {
		return false;
	}

	public boolean degreeRegister(Degree degree, Student student) {
		return false;
	}

	public boolean checkRegistration() {
		return false;
	}

	public boolean checkCredits() {
		return false;
	}

	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		return false;
	}

}
