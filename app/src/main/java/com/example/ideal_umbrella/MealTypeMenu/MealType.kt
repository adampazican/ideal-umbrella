package com.example.ideal_umbrella.MealTypeMenu

/**
 * Enum predstavujuci typ jedla
 */
enum class MealType(val value: Int) {
    DAILY_MENU(0),
    SOUP(1),
    MAIN_MEAL(2)
    ;

    /**
     * Metoda, ktora vytvori meal type z cisla
     */
    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}