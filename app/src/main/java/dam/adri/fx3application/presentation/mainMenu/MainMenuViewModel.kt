package dam.adri.fx3application.presentation.mainMenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.core.presentation.sessionModule.SessionUseCase
import dam.adri.domain.useCase.user.GetUserByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val sessionUseCase: SessionUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                val userId = sessionUseCase.getUserId()
                if (userId != null) {
                    val user = getUserByIdUseCase.getUserById(userId)
                    _userName.postValue(user.name)
                } else {
                    _error.postValue("user_not_found")
                }
            } catch (throwable: Throwable) {
                _error.postValue(throwable.message ?: "unknown_error")
            }
        }
    }
}
