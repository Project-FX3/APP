package dam.adri.grandprix.presentation.grandPrixDescription
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.domain.modelo.entities.Gp
import dam.adri.domain.useCase.gp.GetGpByIdUseCase
import dam.adri.domain.useCase.gp.GetGpsWithoutResultsUseCase
import dam.adri.domain.useCase.gp.GetNextGpUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NextGrandPrixViewModel @Inject constructor(
    private val getNextGpUseCase: GetNextGpUseCase,
    private val getGpByIdUseCase: GetGpByIdUseCase,
    private val getGpsWithoutResultsUseCase: GetGpsWithoutResultsUseCase
) : ViewModel() {

    private val _grandPrix = MutableLiveData<Gp>()
    val grandPrix: LiveData<Gp> get() = _grandPrix

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun loadNextGrandPrix(idGp : Int) {
        viewModelScope.launch {
            val nextGrandPrix : Gp
            try {
                nextGrandPrix = if(idGp != 0){
                    getGpsWithoutResultsUseCase.getGpsWithoutResults().first { it.id == idGp }
                } else{
                    getNextGpUseCase.getNextGp()
                }
                _grandPrix.postValue(nextGrandPrix)
            } catch (throwable: Throwable) {
                _error.postValue("Error loading next Grand Prix: ${throwable.message}")
            }
        }
    }
}
