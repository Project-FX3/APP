package dam.adri.core.presentation

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected val contentViewId
        get() = (requireView().parent as ViewGroup).id

    fun addFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(contentViewId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(contentViewId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun closeFragment() {
        if (parentFragmentManager.backStackEntryCount > 0) {
            parentFragmentManager.popBackStack()
        } else {
            requireActivity().finish()
        }
    }
}
