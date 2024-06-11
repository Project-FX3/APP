package dam.adri.fantasy.presentation.userLeagues

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.core.presentation.sessionModule.SessionUseCase
import dam.adri.domain.modelo.entities.League
import dam.adri.domain.useCase.league.GetLeagueByAccessCodeUseCase
import dam.adri.domain.useCase.league.GetLeaguesByUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLeaguesViewModel @Inject constructor(
    private val getLeaguesByUserUseCase: GetLeaguesByUserUseCase,
    private val getLeagueByAccessCodeUseCase: GetLeagueByAccessCodeUseCase,
    private val sessionUseCase: SessionUseCase
) : ViewModel() {

    private val _ligas = MutableLiveData<List<League>>()
    val ligas: LiveData<List<League>> get() = _ligas

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _ligaSearched = MutableLiveData<League>()
    val ligaSearched: LiveData<League> get() = _ligaSearched

    init {
        cargarLigasUsuario()
    }

    fun searchLeague(codigoAcceso: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val liga = getLeagueByAccessCodeUseCase.getLeagueByAccessCode(codigoAcceso)
                _ligaSearched.postValue(liga)
            } catch (throwable: Throwable) {
                _error.postValue(throwable.message ?: "unknown_error")
            }
        }
    }

    private fun cargarLigasUsuario() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val ligas = sessionUseCase.getUserId()
                    ?.let { getLeaguesByUserUseCase.getLeaguesByUser(it) }
                Log.d("UserLeaguesViewModel", "Fetched leagues: ${ligas?.size ?: 0}")
                _ligas.postValue(ligas ?: emptyList())
            } catch (throwable: Throwable) {
                _error.postValue(throwable.message ?: "unknown_error")
            }
        }
    }
}
