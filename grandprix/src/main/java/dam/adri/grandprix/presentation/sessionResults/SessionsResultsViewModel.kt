package dam.adri.grandprix.presentation.sessionResults

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.domain.modelo.entities.Driver
import dam.adri.domain.useCase.gp.GetSessionResultsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionResultsViewModel @Inject constructor(
    private val getSessionResultsUseCase: GetSessionResultsUseCase
) : ViewModel() {

    private val _results = MutableLiveData<List<Driver>>()
    val results: LiveData<List<Driver>> get() = _results

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loadSessionResults(sessionType: String, gpId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val results = getSessionResultsUseCase.getSessionResults(sessionType, gpId)
                _results.postValue(results)
            } catch (throwable: Throwable) {
                _error.postValue("Session is unavailable")
            }
        }
    }
}
