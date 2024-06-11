package dam.adri.fantasy.presentation.leagueQualification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.core.presentation.sessionModule.SessionUseCase
import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.domain.useCase.userLeague.GetUserLeagueByLeagueIdUseCase
import dam.adri.domain.useCase.userLeague.GetUserLeagueByUserIdAndLeagueIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QualificationLeagueViewModel @Inject constructor(
    private val getUserLeagueByLeagueIdUseCase: GetUserLeagueByLeagueIdUseCase,
    private val sessionUseCase: SessionUseCase,
    private val getUserLeagueByUserIdAndLeagueIdUseCase: GetUserLeagueByUserIdAndLeagueIdUseCase
) : ViewModel() {

    private val _clasificacion = MutableLiveData<List<UserLeague>>()
    val clasificacion: LiveData<List<UserLeague>> get() = _clasificacion
    private val _deleted = MutableLiveData<Unit>()
    val deleted: LiveData<Unit> get() = _deleted

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

    fun deleteLeague(leagueId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = sessionUseCase.getUserId()
                if (userId != null) {
                    getUserLeagueByUserIdAndLeagueIdUseCase.deleteUserLeague(userId, leagueId).let {
                        _deleted.postValue(Unit)
                        _error.postValue("Liga eliminada")
                    }
                }
            } catch (throwable: Throwable) {
                _error.postValue("Error al eliminar Liga")
            }
        }
    }

}