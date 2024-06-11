package dam.adri.knowledge.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.domain.modelo.entities.Gp
import dam.adri.domain.useCase.gp.GetGpsWithoutResultsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KnowledgeViewModel @Inject constructor(
    private val getGpsWithoutResultsUseCase: GetGpsWithoutResultsUseCase
) : ViewModel() {

    private val _allGps = MutableLiveData<List<Gp>>()
    val allGps: LiveData<List<Gp>> get() = _allGps

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        loadCircuits()
    }

    private fun loadCircuits() {
        viewModelScope.launch {
            try {
                val circuits = getGpsWithoutResultsUseCase.getGpsWithoutResults()
                _allGps.postValue(circuits)
            } catch (throwable: Throwable) {
                _error.postValue("Error loading gps")
            }
        }
    }
}
