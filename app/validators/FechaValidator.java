package validators;

import javax.validation.ConstraintValidator;

import play.data.validation.Constraints;
import play.libs.F.Tuple;

public class FechaValidator extends Constraints.Validator<String> implements ConstraintValidator<Fecha, String> {

	java.util.regex.Pattern regex;

	@Override
	public void initialize(Fecha annotation) {
		regex = java.util.regex.Pattern.compile("([0][1-9]|[1][0-9]|[2][0-9]|[3][0-1])[/]([0][1-9]|[1][0-2])[/]([1][9]|[2][0])[0-9]{2}");
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