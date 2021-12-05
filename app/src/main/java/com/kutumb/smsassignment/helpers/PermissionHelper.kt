package com.kutumb.smsassignment.helpers

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kutumb.smsassignment.R


object PermissionHelper {
    val REQUEST_CODE_SMS_PERMISSION by lazy { 3000 }

    fun checkIfAlreadyHaveSmsPermission(activity: Activity): Boolean {
        val resultOfReadSms = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS)
        val resultOfReceiveSms = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS)
        return resultOfReadSms == PackageManager.PERMISSION_GRANTED && resultOfReceiveSms == PackageManager.PERMISSION_GRANTED
    }



    fun displayGrantPermissionDialog(
        activity: Activity?,
        message: String?,
        onSettingsTapped: () -> Unit,
        onCancelTapped: () -> Unit
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton(
            activity?.getString(R.string.go_to_settings)
        ) { dialog, _ ->
            dialog.dismiss()
            onSettingsTapped()
            try {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri =
                    Uri.fromParts("package", activity?.packageName, null)
                intent.data = uri
                activity?.startActivity(intent)
            } catch (e: Exception) {
            }
        }

        builder.setNegativeButton(activity?.getString(R.string.cancel))
            { dialog, _ ->
                dialog.dismiss()
                onCancelTapped()
        }
        val dialog: AlertDialog = builder.show()
        activity?.baseContext?.let { ContextCompat.getColor(it, R.color.colorRed) }?.let {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(it)
        }
        activity?.baseContext?.let { ContextCompat.getColor(it, R.color.colorTextGreen) }?.let {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(it)
        }
    }

    fun askForSmsPermission(activity: Activity?) {
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(
                Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS),
            REQUEST_CODE_SMS_PERMISSION
        )
    }

    private fun isPermissionDialogShown(activity: Activity, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.shouldShowRequestPermissionRationale(permission)
        } else {
            return true
        }
    }
}
