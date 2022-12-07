package com.example.kotlincartfirebase.listener

import com.example.kotlincartfirebase.model.CartModel

interface ICartLoadListener {
    fun onLoadCartSuccess(cartModelList: List<CartModel>)
    fun onLoadCartFailed(message:String?)
}