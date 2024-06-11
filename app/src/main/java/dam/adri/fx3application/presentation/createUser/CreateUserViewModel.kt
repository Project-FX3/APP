package dam.adri.fx3application.presentation.createUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.domain.useCase.user.CreateUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _onCreateUserSuccess = MutableLiveData<Unit>()
    val onCreateUserSuccess: LiveData<Unit> get() = _onCreateUserSuccess

    private val _onCreateUserError = MutableLiveData<String>()
    val onCreateUserError: LiveData<String> get() = _onCreateUserError

    fun createUser(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                createUserUseCase.createUser(username, password)
                _onCreateUserSuccess.postValue(Unit)
            } catch (throwable: Throwable) {
                _onCreateUserError.postValue(throwable.message ?: "unknown_error")
            }
        }
    }
}
