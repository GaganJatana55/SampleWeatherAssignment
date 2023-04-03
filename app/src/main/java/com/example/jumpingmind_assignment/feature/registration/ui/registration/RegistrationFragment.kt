package com.example.jumpingmind_assignment.feature.registration.ui.registration

import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jumpingmind_assignment.databinding.FragmentRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    lateinit var binding: FragmentRegistrationBinding
    private lateinit var uri: Uri
    private val viewModel: RegistrationViewModel by viewModels()
    var startCamera: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            viewModel.updateImage(uri)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        binding.viemodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }


    private fun pickCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
        uri = requireActivity().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )!!
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)


        startCamera.launch(cameraIntent) // VERY NEW WAY
    }


    private fun setObservers() {
        viewModel.apply {
            action.observe(viewLifecycleOwner) {
                if (it == RegistrationViewModel.Action.gotoLogin) {
                    goBackLogin()
                } else if (it == RegistrationViewModel.Action.getImage) {
                    pickCamera()
                }
            }
            result.observe(viewLifecycleOwner) {
                if (it.status) {
                    goBackLogin()
                } else {
                    showError(it.error)
                }
            }

        }
    }

    private fun showError(errorString: String) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    private fun goBackLogin() {
        findNavController().popBackStack()
    }
}