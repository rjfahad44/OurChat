package com.ft.ourchat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_RECEIVE = 1
    private val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_RECEIVE){
            //inflate receive
            val view = LayoutInflater.from(context).inflate(R.layout.receive_layout, parent, false)
            ReceiveViewHolder(view)
        }else{
            //inflate sent
            val view = LayoutInflater.from(context).inflate(R.layout.sent_layout, parent, false)
            SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java){
            //do stuff for sent view holder
            holder as SentViewHolder
            holder.sentMessage.text = message.message
        }else{
            //do stuff for receive view holder
            holder as ReceiveViewHolder
            holder.receiveMessage.text = message.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(message.senderId)){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage: TextView = itemView.findViewById<TextView>(R.id.tv_sentMessage)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage: TextView = itemView.findViewById<TextView>(R.id.tv_receiveMessage)
    }
}