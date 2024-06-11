package dam.adri.fx3application.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.core.data.utils.viewBinding
import dam.adri.domain.initializer.RepositoryInitializer
import dam.adri.fx3application.databinding.ActivityMainBinding
import dam.adri.fx3application.presentation.login.LoginActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repositoryInitializer: RepositoryInitializer

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeRepositories()
    }

    private fun initializeRepositories() {
        lifecycleScope.launch {
            try {
                repositoryInitializer.initializeRepositories()
                navigateToLogin()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity,
                    getString(dam.adri.core.styles.R.string.error_initializing_repositories), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
