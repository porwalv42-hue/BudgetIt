package github.porwalv42hue.budgetit

import androidx.lifecycle.ViewModel

class BudgetViewModel : ViewModel() {

    fun getBudget(type: DivisionType): Int {
        return when (type) {
            DivisionType.INCOME -> 1000
            DivisionType.HOUSEHOLD -> 500
            DivisionType.GROOMING -> 50
            DivisionType.DONATION -> 50
            DivisionType.TRIP -> 50
            DivisionType.INVESTMENT -> 350
        }
    }
}