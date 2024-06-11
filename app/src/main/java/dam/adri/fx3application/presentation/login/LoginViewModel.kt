package dam.adri.fx3application.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dam.adri.core.presentation.sessionModule.SessionUseCase
import dam.adri.domain.modelo.entities.User
import dam.adri.domain.useCase.user.GetUserByIdUseCase
import dam.adri.domain.useCase.user.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sessionUseCase: SessionUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _onLoginSuccess = MutableLiveData<Boolean>()
    val onLoginSuccess: LiveData<Boolean> get() = _onLoginSuccess

    private val _onLoginError = MutableLiveData<String>()
    val onLoginError: LiveData<String> get() = _onLoginError


    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val user = loginUseCase.login(username, password)
                _onLoginSuccess.postValue(true)
                user.id?.let { sessionUseCase.logIn(it)
                }
            } catch (throwable: Throwable) {
                _onLoginError.postValue("user_not_found")
            }
        }
    }
}
