package com.yeputra.moviecatalaguecunsumer.base

import androidx.lifecycle.ViewModel
import java.util.*

abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    private val TAG = BaseViewModel::class.java.simpleName

    protected var view: IBaseView? = null

    abstract fun onResponseSuccess(data: Any)

    fun setupView(view: IBaseView) {
        this.view = view
    }

    override fun onDestroy() {
    }

    fun getLocale() : String {
        val lang = Locale.getDefault().language
        return if (lang == "in")
            "id"
        else lang
    }
}