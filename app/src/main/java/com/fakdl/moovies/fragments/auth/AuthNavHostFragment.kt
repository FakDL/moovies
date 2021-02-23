package com.fakdl.moovies.fragments.auth

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment
import com.fakdl.moovies.ui.auth.AuthActivity

class AuthNavHostFragment:  NavHostFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = (activity as AuthActivity).fragmentFactory
        super.onCreate(savedInstanceState)
    }
    companion object{

        const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"

        @JvmStatic
        fun create(
            @NavigationRes graphId: Int = 0
        ): AuthNavHostFragment {
            var bundle: Bundle? = null
            if(graphId != 0){
                bundle = Bundle()
                bundle.putInt(KEY_GRAPH_ID, graphId)
            }
            val result =
                AuthNavHostFragment()
            if(bundle != null){
                result.arguments = bundle
            }
            return result
        }
    }

}