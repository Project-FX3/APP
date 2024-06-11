package dam.adri.fantasy.presentation.userLeagueDescription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.domain.modelo.entities.Driver
import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.domain.useCase.driver.GetDriversByUserLeagueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLeagueDescriptionViewModel @Inject constructor(
    private val getDriversByUserLeagueUseCase: GetDriversByUserLeagueUseCase
) : ViewModel() {

    private val _userLeague = MutableLiveData<UserLeague>()
    val userLeague: LiveData<UserLeague> get() = _userLeague

    private val _drivers = MutableLiveData<List<Driver>>()
    val drivers: LiveData<List<Driver>> get() = _drivers

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun setUserLeague(userLeague: UserLeague) {
        _userLeague.value = userLeague
        userLeague.user?.let { userLeague.league?.let { it1 -> loadDrivers(it, it1) } }
    }

    private fun loadDrivers(userId: Int, leagueId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pilots = getDriversByUserLeagueUseCase.getDriversByUserLeague(userId, leagueId)
                _drivers.postValue(pilots)
            } catch (throwable: Throwable) {
                _error.postValue(throwable.message ?: "unknown_error")
            }
        }
    }
}
