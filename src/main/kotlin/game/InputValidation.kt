package game

class InputValidation {

    companion object {
        fun validNumberInput(s: String): Boolean {
            if (s.isBlank() || s.isEmpty()) {
                return false
            }
            val trimmedString = s.trim { it <= ' ' }
            return try {
                trimmedString.toInt()
                true
            } catch (e: NumberFormatException) {
                false
            }
        }

        fun isValidRange(s: String): Boolean {
            return isInValidRange(s.toInt())
        }

        fun isInValidRange(i: Int): Boolean {
            return i in 0..5
        }
    }


}