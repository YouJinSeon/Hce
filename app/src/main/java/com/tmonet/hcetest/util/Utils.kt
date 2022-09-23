package com.tmonet.hcetest.util

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.nfc.NfcAdapter

class Utils {

    companion object {
        fun getNfcEnable(application : Application) : Int {
            // 0 : NFC 미지원 기기, 1: 지원 및 기능 비활성 상태, 2: NFC 지원 및 기능 활성 상태, 3 : 오류
            var result = 0

            try {
                var nfcAdapter: NfcAdapter = NfcAdapter.getDefaultAdapter(application)
                if (null == nfcAdapter) {
                    // NFC 지원하지 않는 기기
                    result = 0
                } else {
                    if (nfcAdapter.isEnabled) {
                        // NFC 가능
                        result = 2
                    } else {
                        // NFC 비활성화
                        result = 1
                    }
                }
            } catch (e:java.lang.Exception) {
                result = 3
            }

            return result
        }

        fun getAlertDialog(
            context: Context,
            title:String,
            message:String,
            positiveText:String,
            negativeText:String,
            onClickPositive: ((Dialog) -> Unit)? = null,
            onClickNegative: ((Dialog) -> Unit)? = null
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false) // 외부 레이아웃 클릭시 팝업창 사라지지않음
                .setPositiveButton(positiveText,
                    DialogInterface.OnClickListener { dialog, which ->
                        onClickPositive
                    })
                .setNegativeButton(negativeText,
                    DialogInterface.OnClickListener { dialog, which ->
                        onClickNegative
                    })
            builder.show()
        }
    }
}