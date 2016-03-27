package validators;

import javax.validation.ConstraintValidator;

import play.data.validation.Constraints;
import play.libs.F.Tuple;


public class CodigoClienteValidator extends Constraints.Validator<String> 
		implements ConstraintValidator<CodigoCliente, String> {
	
	java.util.regex.Pattern regex;

	@Override
	public void initialize(CodigoCliente annotation) {
		regex = java.util.regex.Pattern.compile("([0-9]|[A-Z]|[a-z]){8}[-](CLI)");
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
