package com.example.randomnumberapp.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.randomnumberapp.R
import com.example.randomnumberapp.main.sl.ProvideViewModel
import com.example.randomnumberapp.numbers.presentation.NumbersFragment

class MainActivity : AppCompatActivity(), ShowFragment, ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            show(NumbersFragment(), add = false)
        }
    }

    override fun show(fragment: Fragment) {
        show(fragment, add = true)
    }

    private fun show(fragment: Fragment, add: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        val containerId = R.id.mainContainer
        if (add) {
            transaction.addToBackStack(fragment::class.java.simpleName)
                .add(containerId, fragment)
        } else {
            transaction.replace(containerId, fragment)
        }
        transaction.commit()
    }

    override fun <T : ViewModel> provideViewModel(clasz: Class<T>, owner: ViewModelStoreOwner): T {
        return (application as ProvideViewModel).provideViewModel(clasz, owner)
    }
}

interface ShowFragment {
    fun show(fragment: Fragment)

    class Base : ShowFragment {
        override fun show(fragment: Fragment) = Unit
    }
}
