package dam.adri.fantasy.presentation.createLeague

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.domain.modelo.entities.League
import dam.adri.domain.useCase.league.CreateLeagueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateLeagueViewModel @Inject constructor(
            private val createLeagueUseCase: CreateLeagueUseCase
) : ViewModel() {

    private val _leagueCreated = MutableLiveData<League>()
    val leagueCreated: LiveData<League> get() = _leagueCreated

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun createLeague(leagueName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val league = createLeagueUseCase.createLeague(leagueName)
                _leagueCreated.postValue(league)
            } catch (throwable: Throwable) {
                _error.postValue("Error creating league: ${throwable.message}")
            }
        }
    }
}
