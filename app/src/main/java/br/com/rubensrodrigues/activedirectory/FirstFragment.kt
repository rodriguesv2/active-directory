package br.com.rubensrodrigues.activedirectory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.rubensrodrigues.activedirectory.databinding.FragmentFirstBinding
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var singleAccountApp: ISingleAccountPublicClientApplication? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPublicClient()
        setListeners()
    }

    private fun setListeners() = binding.run {
        buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            signIn()
        }
    }

    private fun setupPublicClient() {
        PublicClientApplication.createSingleAccountPublicClientApplication(
            requireContext(),
            R.raw.auth_config_single_account,
            object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                override fun onCreated(application: ISingleAccountPublicClientApplication?) {
                    singleAccountApp = application
                }

                override fun onError(exception: MsalException?) {
                    Toast.makeText(requireContext(), exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun signIn() {
        singleAccountApp?.signIn(
            requireActivity(),
            "",
            listOf("user.read").toTypedArray(),
            object : AuthenticationCallback {
                override fun onSuccess(authenticationResult: IAuthenticationResult?) {
                    Log.d("ACCESS TOKEN", "**************************************************")
                    Log.d("ACCESS TOKEN", authenticationResult?.accessToken ?: "")
                    Log.d("ACCESS TOKEN", "**************************************************")
                }

                override fun onError(exception: MsalException?) {}

                override fun onCancel() {}
            }
        )
    }

    private fun acquireToken() {
        singleAccountApp?.acquireToken(
            requireActivity(),
            listOf("user.read").toTypedArray(),
            object : AuthenticationCallback {
                override fun onSuccess(authenticationResult: IAuthenticationResult?) {
                    Log.d("ACCESS TOKEN", "**************************************************")
                    Log.d("ACCESS TOKEN", authenticationResult?.accessToken ?: "")
                    Log.d("ACCESS TOKEN", "**************************************************")
                }

                override fun onError(exception: MsalException?) {}

                override fun onCancel() {}
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}