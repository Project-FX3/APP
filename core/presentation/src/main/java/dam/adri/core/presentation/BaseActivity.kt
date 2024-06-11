package dam.adri.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

import dam.adri.presentation.R


abstract class BaseActivity : AppCompatActivity() {

    private val contentId = R.id.frame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        if (savedInstanceState == null) {
        launchFragment(mainFragment())
        }
    }

    @SuppressLint("CommitTransaction")
    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(contentId, fragment)
            .commit()
    }
    abstract fun mainFragment(): Fragment

    open fun requireFinish(){
        finish()
    }
}
