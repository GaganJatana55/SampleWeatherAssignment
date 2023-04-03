package com.example.jumpingmind_assignment.feature.registration.ui.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.jumpingmind_assignment.R
import com.example.jumpingmind_assignment.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        _binding?.viemodel = loginViewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservables()


    }

    private fun setObservables() {
        loginViewModel.apply {

            loginResult.observe(viewLifecycleOwner,
                Observer { loginResult ->
                    loginResult ?: return@Observer
                    if (!loginResult.status) {

                        showLoginFailed(loginResult.error)
                    }

                })

            loginError.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    return@observe
                }

                showLoginFailed(it)
            }
            action.observe(viewLifecycleOwner) {

                if (it == LoginViewModel.Action.goSignUp) {
                    goSignUp()
                } else if (it == LoginViewModel.Action.goDashBoard) {
                    signInSuccess()
                }
            }
        }
    }

    private fun signInSuccess() {
        findNavController().navigate(R.id.toLanding)
    }

    private fun goSignUp() {
        findNavController().navigate(R.id.toRegistration)
    }

    private fun showLoginFailed(errorString: String) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}