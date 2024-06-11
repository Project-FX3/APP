package dam.adri.fantasy.presentation.userLeagues.searchLeagueDialog.selectDrivers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.core.presentation.sessionModule.SessionUseCase
import dam.adri.domain.modelo.entities.Driver
import dam.adri.domain.modelo.entities.User
import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.domain.useCase.driver.ListAllDriversUseCase
import dam.adri.domain.useCase.user.GetUserByIdUseCase
import dam.adri.domain.useCase.userLeague.CreateUserLeagueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectDriversViewModel @Inject constructor(
    private val listAllDriversUseCase: ListAllDriversUseCase,
    private val createUserLeagueUseCase: CreateUserLeagueUseCase,
    private val sessionUseCase: SessionUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _drivers = MutableLiveData<List<Driver>>()
    val drivers: LiveData<List<Driver>> get() = _drivers

    private val _userCreated = MutableLiveData<Unit>()
    val userCreated: LiveData<Unit> get() = _userCreated

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        loadDrivers()
        loadUser()
    }

    private fun loadDrivers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val drivers = listAllDriversUseCase.listAllDrivers()
                _drivers.postValue(drivers)
            } catch (throwable: Throwable) {
                _error.postValue("Error loading drivers: ${throwable.message}")
            }
        }
    }

    fun createUserLeague(idLeague: Int, driverNumber1: Int, driverNumber2: Int, driverNumber3: Int, driverNumber4: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = _user.value ?: _error.postValue("User not found")
                val userLeague = UserLeague(
                    league = idLeague,
                    user = this@SelectDriversViewModel.user.value?.id ?: 0,
                    userName = this@SelectDriversViewModel.user.value?.name ?: "",
                    driver1Number = driverNumber1,
                    driver2Number = driverNumber2,
                    driver3Number = driverNumber3,
                    driver4Number = driverNumber4,
                    puntuation = 0
                )
                createUserLeagueUseCase.createUserLeague(userLeague)
                _userCreated.postValue(Unit)
            } catch (throwable: Throwable) {
                _error.postValue("Error adding user league: ${throwable.message}")
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = sessionUseCase.getUserId()
                if (userId != null) {
                    val user = getUserByIdUseCase.getUserById(userId)
                    _user.postValue(user)
                } else {
                    _error.postValue("User not found")
                }
            } catch (throwable: Throwable) {
                _error.postValue("Error loading user: ${throwable.message}")
            }
        }
    }
}
