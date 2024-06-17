package com.example.challengelocaweb.presentation.common

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object BiometricPromptUtils {
    @RequiresApi(Build.VERSION_CODES.P)
    fun authenticate(
        context: Context,
        onSuccess: @Composable () -> Unit,
        onError: () -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt =
            BiometricPrompt(context as FragmentActivity, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        onError()
                    }

//                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                        super.onAuthenticationSucceeded(result)
//                        onSuccess()
//                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        onError()
                    }
                })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticação Biométrica")
            .setSubtitle("Autentique-se para acessar este email")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}