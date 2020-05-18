package com.example.tugasakhir.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tugasakhir.BuildConfig
import timber.log.Timber

abstract class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(getResourceLayout(), container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onViewReady()
    }

    abstract fun getResourceLayout(): Int

    abstract fun onViewReady()

    fun replace(containerId : Int, fragment : Fragment, addToBackStack : Boolean) {
        if(addToBackStack){
            fragmentManager?.beginTransaction()!!
                    .replace(containerId, fragment)
                    .addToBackStack(null)
                    .commit()
        } else {
            fragmentManager?.beginTransaction()!!
                    .replace(containerId, fragment)
                    .commit()
        }
    }
}