package dam.adri.fx3application.presentation.createUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.fx3application.databinding.DialogCreateUserBinding

@AndroidEntryPoint
class CreateUserDialogFragment : DialogFragment() {

    private val viewModel: CreateUserViewModel by viewModels()
    private var _binding: DialogCreateUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCreateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.buttonCreate.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            Log.d("CreateUserDialog", "Username: $username, Password: $password")
            viewModel.createUser(username, password)
        }

        binding.buttonCancel.setOnClickListener {
            Log.d("CreateUserDialog", "Cancel button clicked")
            dismiss()
        }
    }

    private fun observeViewModel() {
        viewModel.onCreateUserSuccess.observe(viewLifecycleOwner) {
                Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show()
                dismiss()
        }

        viewModel.onCreateUserError.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): CreateUserDialogFragment {
            return CreateUserDialogFragment()
        }
    }
}
