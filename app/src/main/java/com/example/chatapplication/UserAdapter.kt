package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class UserAdapter(val users: ArrayList<User>, val context: Context) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users.get(position)
        holder.txtName.text = user.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", user.name)
            intent.putExtra("uid", user.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return this.users.size
    }

    class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val txtName : TextView = itemView.findViewById(R.id.txtName);
    }
}