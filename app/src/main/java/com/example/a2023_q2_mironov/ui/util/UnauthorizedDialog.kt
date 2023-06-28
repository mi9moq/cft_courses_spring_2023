package com.example.a2023_q2_mironov.ui.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.a2023_q2_mironov.R

fun showUnauthorizedDialog(
    context: Context,
    onClick: () -> Unit
) {
    AlertDialog.Builder(context).apply {
        setTitle(context.getString(R.string.authorisation_error))
        setMessage(context.getString(R.string.authorisation_error_message))
        setPositiveButton(context.getString(R.string.login)) { _, _ ->
            onClick()
        }
        setOnDismissListener {
            onClick()
        }
        create()
        show()
    }
}