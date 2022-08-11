package com.example.loginform

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginform.databinding.RecyclerItemViewBinding

class UserDataAdapter(context: Context, var list: ArrayList<DummyData>) :
    RecyclerView.Adapter<UserDataAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_view, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userData : DummyData = list[position]
        Log.d("RecyclerView",userData.toString())
//        val user: DummyData = list[position]
        holder.binding.name.text = userData.name
        holder.binding.age.text = userData.age.toString()
        holder.binding.city.text = userData.city
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerItemViewBinding.bind(itemView)
    }
}