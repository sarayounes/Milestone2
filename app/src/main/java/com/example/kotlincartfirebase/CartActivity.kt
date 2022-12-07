package com.example.kotlincartfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincartfirebase.adapter.MyCartAdapter
import com.example.kotlincartfirebase.listener.ICartLoadListener
import com.example.kotlincartfirebase.model.CartModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.mainLayout
import kotlinx.android.synthetic.main.activity_main.*

class CartActivity : AppCompatActivity(), ICartLoadListener {

    var cartLoadListener: ICartLoadListener? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
        loadCartFromFirebase()
    }

    private fun loadCartFromFirebase(){
        val cartModels : MutableList<CartModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("UNIQUE_USER_ID")
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children)
                    {
                        val cartModel = cartSnapshot.getValue(CartModel::class.java)
                        cartModel!!.key= cartSnapshot.key
                        cartModels.add(cartModel)
                    }
                    cartLoadListener!!.onLoadCartSuccess(cartModels)
                }

                override fun onCancelled(error: DatabaseError) {
                    cartLoadListener!!.onLoadCartFailed(error.message)
                }

            })
    }

    private fun init(){
        cartLoadListener = this
        val layoutManager = LinearLayoutManager(this)
        recycler_cart!!.layoutManager= layoutManager
        recycler_cart!!.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        btnBack!!.setOnClickListener{finish()}
    }

    override fun onLoadCartSuccess(cartModelList: List<CartModel>) {
        var sum = 0.0
        for (cartModel in cartModelList!!){
            sum += cartModel!!.totalPrice
        }
        txtTotal.text = StringBuilder("egp").append(sum)
        val adapter = MyCartAdapter(this, cartModelList)
        recycler_cart!!.adapter = adapter
    }

    override fun onLoadCartFailed(message: String?) {
        Snackbar.make(mainLayout , message!!, Snackbar.LENGTH_LONG).show()
    }
}