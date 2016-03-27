package validators;

import javax.validation.ConstraintValidator;

import play.data.validation.Constraints;
import play.libs.F.Tuple;

public class ApellidosValidator extends Constraints.Validator<String> implements ConstraintValidator<Apellidos, String>  {

	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return null;
	}

	@Override
	public boolean isValid(String value) {
        if(value == null || value.length() == 0) {
            return false;
        }

        return value.contains(" ");
	}

	@Override
	public void initialize(Apellidos arg0) {
		// TODO Auto-generated method stub
		
	}

}