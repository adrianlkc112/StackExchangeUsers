package com.adrianlkc112.stackexchangeusers.activity

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.adrianlkc112.stackexchangeusers.R
import com.kaopiz.kprogresshud.KProgressHUD


open class BaseActivity : AppCompatActivity() {

    private var hud: KProgressHUD? = null
    private var alertDialog: AlertDialog? = null

    //Progress Dialog
    protected fun showLoading(label: String = getString(R.string.message_processing), cancellable: Boolean = false) {
        if(isLoading()) {
            hud!!.setLabel(label)       //update label if already showing
            return
        }

        if (!this.isFinishing) {
            hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(cancellable)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show().apply {
                    if(label.isNotEmpty()) {
                        setLabel(label)
                    }
                }
        }
    }

    protected fun hideLoading() {
        if(!isFinishing) {
            hud?.dismiss()
        }
    }

    protected fun isLoading(): Boolean {
        return hud?.isShowing?: false
    }

    //Alert Dialog to display warning / error message
    protected fun showMessageDialog(title: String = "", message: String, cancelable: Boolean = false,
                                onClickListener: DialogInterface.OnClickListener? = null) {
        if (!isFinishing) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(getString(R.string.button_ok), onClickListener)
            builder.setCancelable(cancelable)
            if (cancelable) {
                builder.setNegativeButton(getString(R.string.button_cancel), null)
            }
            alertDialog = builder.create().apply { show() }
        }
    }

    protected fun hideMessageDialog() {
        if(isMessageDialogShowing() && !isFinishing) {
            alertDialog!!.dismiss()
        }
    }

    protected fun isMessageDialogShowing(): Boolean {
        return alertDialog?.isShowing?: false
    }
}