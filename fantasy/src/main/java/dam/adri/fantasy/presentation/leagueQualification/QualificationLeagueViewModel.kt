package dam.adri.fantasy.presentation.leagueQualification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.domain.useCase.userLeague.GetUserLeagueByLeagueIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QualificationLeagueViewModel @Inject constructor(
    private val getUserLeagueByLeagueIdUseCase: GetUserLeagueByLeagueIdUseCase
) : ViewModel() {

    private val _clasificacion = MutableLiveData<List<UserLeague>>()
    val clasificacion: LiveData<List<UserLeague>> get() = _clasificacion

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loadQualification(leagueId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val usuarios = getUserLeagueByLeagueIdUseCase.getUserLeagueByLeagueId(leagueId)
                _clasificacion.postValue(usuarios)
            } catch (throwable: Throwable) {
                _error.postValue(throwable.message ?: "unknown_error")
            }
        }
    }
}