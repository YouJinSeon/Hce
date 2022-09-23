package com.tmonet.hcetest

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentFilter.MalformedMimeTypeException
import android.content.pm.PackageManager
import android.nfc.NfcAdapter
import android.nfc.tech.IsoDep
import android.os.Bundle
import android.os.FileObserver
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.tmonet.hce.HceInstance
import com.tmonet.hcetest.databinding.ActivityMainBinding
import com.tmonet.hcetest.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.io.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), HceInstance.OnNfcListener {
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var hceInstance:HceInstance

    lateinit var mNfcAdapter: NfcAdapter
    lateinit var mPendingIntent:PendingIntent
    lateinit var mTechLists: Array<Array<String>>
    lateinit var mIntentFilter: Array<IntentFilter>


    private val PERMISSIONS = arrayOf(
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_EXTERNAL_STORAGE"
    )
    private val REQUEST_PERMISSION_CODE = 1515
    lateinit var fileObserver: FileObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        initValue()
        initOnClick()
        initNFC()
        initLog()
//        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun initHceService() {
        hceInstance.initialize()
    }

    private fun initValue() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        hceInstance = HceInstance.getInstance(this)
        hceInstance.setOnNfcListener(this)
        hceInstance.setEnableLog(true)
        mNfcAdapter = NfcAdapter.getDefaultAdapter(applicationContext)
    }

    private fun initOnClick() {
        binding.btnHce.setOnClickListener {
            initHceService()
        }

        binding.btnNfc.setOnClickListener {
            initNfcService()
        }

        binding.btnLog.setOnClickListener {
            readLog()
        }

        binding.btnDeleteLog.setOnClickListener {
            initLog()
        }

    }

    private fun initNfcService() {
        hceInstance.startNfcTagging()
    }

    /**
     * NFC 기본 설정
     */
    private fun initNFC() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(applicationContext)
        mTechLists = arrayOf(arrayOf(IsoDep::class.java.name))
        mPendingIntent = PendingIntent.getActivity(this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE)
        // Setup an intent filter for all MIME based dispatches
        val ndef = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        try {
            ndef.addDataType("*/*")
        } catch (e: MalformedMimeTypeException) {
            throw RuntimeException("fail", e)
        }
        mIntentFilter = arrayOf(ndef)
        onNewIntent(intent)
    }

    private fun initLog() {
        deleteLog()
        val dirPath: String = filesDir.path
        val file = File(dirPath)
        if (!file.exists()) file.mkdirs()
        BufferedWriter(FileWriter("$dirPath/LOG_Hce.txt", true))

        fileObserver = object : FileObserver(file) {
            override fun onEvent(event: Int, path: String?) {
                if (event == MODIFY) {
                    readLog()
                }
            }
        }
        fileObserver.startWatching()
    }

    override fun onDestroy() {
        super.onDestroy()
        hceInstance.finalize()
        fileObserver.stopWatching()
    }

    override fun onResume() {
        super.onResume()
        mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilter, mTechLists)
    }

    override fun onAfterTagResult(cardInfo: com.tmonet.hce.dto.CardInfo) {
        dataSaveLog("nfcCardNum : ${cardInfo.cardNum} / ${cardInfo.cardType.toString()}")
        hceInstance.setHCECard(cardInfo.cardNum)
        hceInstance.setHCECardType(cardInfo.cardType.toString())
    }

    override fun onPause() {
        super.onPause()
        mNfcAdapter.disableForegroundDispatch(this)
    }

    private fun requestPermissions(activity: Activity, permission: String) {

        val deniedList = ArrayList<String>()
        for (permission in PERMISSIONS) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                deniedList.add(permission)
            }
        }

        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), REQUEST_PERMISSION_CODE)
            }
        }

        if (deniedList.size > 0) {
            val deniedPermissions = deniedList.toTypedArray()
            activity.requestPermissions(deniedPermissions, REQUEST_PERMISSION_CODE)
        }

    }

    private fun checkPermissionsResult(grantResults: IntArray, callback: (Boolean) -> Unit) {
        grantResults.forEach { grantResult ->
            if (grantResult == -1) {
                callback(false)
                return
            }
        }
        callback(true)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        checkPermissionsResult(grantResults) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "권한을 허용해 주세요", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun dataSaveLog(_log: String?) {
        val dirPath: String = filesDir.path
        val file = File(dirPath)

        // 일치하는 폴더가 없으면 생성
        if (!file.exists()) file.mkdirs()

        // txt 파일 생성
        File(dirPath + "LOG_Hce.txt")
        try {
            val bfw = BufferedWriter(FileWriter("$dirPath/LOG_Hce.txt", true))
            bfw.write(_log)
            bfw.write("\n")
            bfw.flush()
            bfw.close()
        } catch (e: IOException) {
            /* Exception */
            Log.e(TAG,"writerLog Fail")
        }
    }

    private fun deleteLog() {
        val dirPath: String = filesDir.path
        val file = File("$dirPath/LOG_Hce.txt")

        try {
            file.delete()
            binding.tvLog.text = ""
        } catch (e: Exception) {
            /* Exception */
            Log.e(TAG,"deleteLog Fail")
        }
    }

    private fun readLog() {
        try {
            val fis = openFileInput("LOG_Hce.txt")
            val inputStreamReader = InputStreamReader(fis)
            val bufferedReader = BufferedReader(inputStreamReader)
            val sb = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                sb.append("\n")
                sb.append(line)
            }
            inputStreamReader.close()
            runOnUiThread {
                binding.tvLog.text = sb
            }
        } catch (e: Exception) {
            Log.e(TAG,"exception : $e")
        }
    }

}