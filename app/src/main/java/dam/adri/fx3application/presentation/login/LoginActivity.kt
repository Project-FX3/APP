package dam.adri.fx3application.presentation.login
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.core.data.utils.viewBinding
import dam.adri.fx3application.databinding.LoginActivityBinding
import dam.adri.fx3application.presentation.createUser.CreateUserDialogFragment
import dam.adri.fx3application.presentation.mainMenu.MainMenuActivity

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding by viewBinding(LoginActivityBinding::inflate)
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.login(username, password)
        }

        binding.buttonCreateUser.setOnClickListener {
            CreateUserDialogFragment.newInstance().show(supportFragmentManager, CREATE_USER)
        }
    }

    private fun observeViewModel() {
        viewModel.onLoginSuccess.observe(this) {

            Toast.makeText(this, getString(dam.adri.core.styles.R.string.login_success), Toast.LENGTH_LONG).show()
            clearEditTexts()
            startActivity(MainMenuActivity.buildIntent(this))
        }

        viewModel.onLoginError.observe(this) { errorCode ->

            val errorMessage = when (errorCode) {
                "user_not_found" -> getString(dam.adri.core.styles.R.string.user_not_found)
                "unknown_error" -> getString(dam.adri.core.styles.R.string.unknown_error)
                else -> errorCode
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun clearEditTexts() {
        binding.editTextUsername.text.clear()
        binding.editTextPassword.text.clear()
    }

    companion object {
        private const val CREATE_USER = "CREATE_USER"
        fun buildIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}
