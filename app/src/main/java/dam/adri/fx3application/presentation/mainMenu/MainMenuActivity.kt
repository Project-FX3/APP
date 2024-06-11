package dam.adri.fx3application.presentation.mainMenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.core.data.utils.viewBinding
import dam.adri.fantasy.presentation.userLeagues.UserLeaguesActivity
import dam.adri.fx3application.databinding.MainMenuActivityBinding
import dam.adri.grandprix.presentation.grandPrixDescription.NextGrandPrixActivity
import dam.adri.knowledge.presentation.KnowledgeActivity


@AndroidEntryPoint
class MainMenuActivity : AppCompatActivity(){
    private val binding by viewBinding(MainMenuActivityBinding::inflate)
    private val viewModel: MainMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
        observeViewModel()
    }


    private fun setupUI() {
        binding.apply {
            buttonLiga.setOnClickListener {
            startActivity(UserLeaguesActivity.buildIntent(this@MainMenuActivity))
            }
            buttonGrandPrix.setOnClickListener {
            startActivity(NextGrandPrixActivity.buildIntent(this@MainMenuActivity))
            }
            buttonKnowledge.setOnClickListener {
                startActivity(KnowledgeActivity.buildIntent(this@MainMenuActivity))
            }
        }
    }

    private fun observeViewModel() {
        viewModel.userName.observe(this) { userName ->
            binding.editTextUserName.text = userName?:"pepe"
        }
    }

    companion object{
        fun buildIntent(context: Context) = Intent(context, MainMenuActivity::class.java)
    }

}