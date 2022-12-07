package com.example.kotlincartfirebase.listener

import com.example.kotlincartfirebase.model.DrinkModel

interface IDrinkLoadListener {
    fun onDrinkLoadSuccess(drinkModelList:List<DrinkModel>?)
    fun onDrinkLoadFailed(message:String?)
}