package github.porwalv42hue.budgetit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BudgetViewModel() : ViewModel() {
    private val _incomeAmount = MutableLiveData(1000)
    val incomeAmount: LiveData<Int> get() = _incomeAmount

    fun getBudget(type: DivisionType): LiveData<Int> {
        return when (type) {
            DivisionType.INCOME -> incomeAmount
            DivisionType.HOUSEHOLD -> incomeAmount
            DivisionType.GROOMING -> incomeAmount
            DivisionType.DONATION -> incomeAmount
            DivisionType.TRIP -> incomeAmount
            DivisionType.INVESTMENT -> incomeAmount
        }
    }

    fun updateEntry(type: DivisionType, amount: Int) {
        _incomeAmount.value = amount
    }
}