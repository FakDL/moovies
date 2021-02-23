package com.fakdl.moovies.fragments.main

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment
import com.fakdl.moovies.ui.main.MainActivity

class MainNavHostFragment:  NavHostFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = (activity as MainActivity).fragmentFactory
        super.onCreate(savedInstanceState)
    }
    companion object{

        const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"

        @JvmStatic
        fun create(
            @NavigationRes graphId: Int = 0
        ): MainNavHostFragment {
            var bundle: Bundle? = null
            if(graphId != 0) {
                bundle = Bundle()
                bundle.putInt(KEY_GRAPH_ID, graphId)
            }
            val result =
                MainNavHostFragment()
            if(bundle != null) {
                result.arguments = bundle
            }
            return result
        }
    }

}