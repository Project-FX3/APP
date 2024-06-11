package dam.adri.fantasy.presentation.userLeagues.searchLeagueDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.domain.useCase.userLeague.GetUserLeagueByLeagueIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueSearchedDialogViewModel @Inject constructor(
    private val getUserLeagueByLeagueIdUseCase: GetUserLeagueByLeagueIdUseCase
) : ViewModel(){

    private val _usersSize = MutableLiveData<Int>()
    val usersSize: LiveData<Int> get() = _usersSize

    fun getNumberOfUsersByLeagueId(leagueId: Int) {
        viewModelScope.launch {
            try {
               _usersSize.postValue(getUserLeagueByLeagueIdUseCase.getNumberOfUsersByLeagueId(leagueId))
            } catch (e: Throwable) {
                throw e
            }
        }
    }

}