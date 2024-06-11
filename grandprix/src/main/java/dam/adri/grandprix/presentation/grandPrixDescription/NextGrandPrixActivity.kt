package dam.adri.grandprix.presentation.grandPrixDescription

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.core.data.utils.viewBinding
import dam.adri.core.styles.databinding.ActivityFrameBinding

@AndroidEntryPoint
class NextGrandPrixActivity : AppCompatActivity() {


    private val binding by viewBinding(ActivityFrameBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(dam.adri.core.styles.R.id.fragment_container, NextGrandPrixFragment.newInstance(0))
            }
        }
    }

    companion object {
        fun buildIntent(context: Context) = Intent(context, NextGrandPrixActivity::class.java)
    }

}