package dam.adri.core.data.utils

class PasswordValidator {

    companion object {

        private val PATTERN_LETTER = Regex("[A-Za-z]")
        private val PATTERN_DIGIT = Regex("[0-9]")
        private val PATTERN_UPPERCASE = Regex("[A-Z]")
        private val PATTERN_LOWERCASE = Regex("[a-z]")
        private val PATTERN_SPECIAL_CHAR = Regex("[^A-Za-z0-9]")

        fun validate(password: String, minLength: Int = 8, requireUppercase: Boolean = true,
                     requireLowercase: Boolean = true, requireDigits: Boolean = true,
                     requireSpecialChar: Boolean = true): List<String> {
            val issues = mutableListOf<String>()

            if (password.length < minLength) {
                issues.add("Password must be at least $minLength characters.")
            }
            if (requireUppercase && !password.contains(PATTERN_UPPERCASE)) {
                issues.add("Password must contain an uppercase letter.")
            }
            if (requireLowercase && !password.contains(PATTERN_LOWERCASE)) {
                issues.add("Password must contain a lowercase letter.")
            }
            if (requireDigits && !password.contains(PATTERN_DIGIT)) {
                issues.add("Password must contain a digit.")
            }
            if (requireSpecialChar && !password.contains(PATTERN_SPECIAL_CHAR)) {
                issues.add("Password must contain a special character.")
            }

            return issues
        }
    }
}
