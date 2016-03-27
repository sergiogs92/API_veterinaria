package validators;

import javax.validation.ConstraintValidator;

import play.data.validation.Constraints;
import play.libs.F.Tuple;

public class DniValidator extends Constraints.Validator<String> implements ConstraintValidator<Dni, String> {

	java.util.regex.Pattern regex;

	@Override
	public void initialize(Dni annotation) {
		regex = java.util.regex.Pattern.compile("[0-9]{8}[A-Z]{1}");
	}

	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return null;
	}

	@Override
	public boolean isValid(String value) {
		if (value == null || value.length() == 0) {
			return false;
		}

		return regex.matcher(value).matches();
	}

}