package com.adrianlkc112.stackexchangeusers.activity

import android.content.DialogInterface
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.adrianlkc112.stackexchangeusers.R
import com.adrianlkc112.stackexchangeusers.util.LogD
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.toolbar.*

open class BaseActivity : AppCompatActivity() {

    private var hud: KProgressHUD? = null
    private var alertDialog: AlertDialog? = null

    override fun onStart() {
        super.onStart()
        initToolBar()
    }

    private fun initToolBar() {
        if(this !is MainActivity) {
            setSupportActionBar(toolbar)

            back_button.setOnClickListener {
                finish()
            }
        } else {
            back_button.visibility = View.GONE
        }
    }

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

    fun isLoading(): Boolean {
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