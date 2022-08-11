package com.example.loginform

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.MenuCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginform.databinding.ActivityUserDataBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserDataActivity : AppCompatActivity() {
    var binding: ActivityUserDataBinding? = null
    var database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        getUserData("name")

    }


    fun getUserData(sortedBy: String) {
        var list = ArrayList<DummyData>()
        val adapter = UserDataAdapter(this, list)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding!!.recyclerView.layoutManager = layoutManager
        binding!!.recyclerView.adapter = adapter

        database.child("userDetails").orderByChild(sortedBy)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            Log.d("DataSnap", dataSnapshot.toString())
                            val userData = dataSnapshot.getValue(DummyData::class.java)
                            list.add(userData!!)

                        }

                        adapter.notifyDataSetChanged()

                    }

                }

                override fun onCancelled(snapshot: DatabaseError) {
                }

            })
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.sort_menu, menu)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
            MenuCompat.setGroupDividerEnabled(menu, true);

        }
        return super.onCreateOptionsMenu(menu)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.nameSort -> {
                getUserData("name")
            }
            R.id.ageSort -> {
                getUserData("age")
            }
            R.id.citySort -> {
                getUserData("city")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}