package validators;

import javax.validation.ConstraintValidator;

import play.data.validation.Constraints;
import play.libs.F.Tuple;


public class CodigoMascotaValidator extends Constraints.Validator<String> 
		implements ConstraintValidator<CodigoMascota, String> {
	
	java.util.regex.Pattern regex;

	@Override
	public void initialize(CodigoMascota annotation) {
		regex = java.util.regex.Pattern.compile("([0-9]|[A-Z]|[a-z]){8}[-](PET)");
	}

	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return null;
	}

	@Override
	public boolean isValid(String value) {
        if(value == null || value.length() == 0) {
            return false;
        }

        return regex.matcher(value).matches();
	}

}
