package com.example.tugasakhir.View

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasakhir.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mAuth : FirebaseAuth
    protected lateinit var mDatabase : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag(this::class.java.simpleName)
        }
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        setContentView(getResourceLayout())
        onViewReady(savedInstanceState)
    }

    abstract fun getResourceLayout() : Int
    abstract fun onViewReady(savedInstanceState: Bundle?)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun showBackButton() {
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    protected fun hideActionBar() {
        supportActionBar!!.hide()
    }

    protected fun setTitle(title : String) {
        supportActionBar!!.title = title
    }
}