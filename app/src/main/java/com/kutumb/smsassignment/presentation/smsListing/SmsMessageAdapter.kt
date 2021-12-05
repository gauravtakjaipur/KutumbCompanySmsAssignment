package com.kutumb.smsassignment.presentation.smsListing

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kutumb.smsassignment.R
import com.kutumb.smsassignment.data.modelClasses.SmsMessage
import com.kutumb.smsassignment.data.modelClasses.SmsMessageType
import com.kutumb.smsassignment.extensions.getDateValueWithDateAndTime
import kotlinx.android.synthetic.main.sms_message_row_item.view.*

class SmsMessageAdapter(diffCallback: DiffUtil.ItemCallback<SmsMessage>,var smsListItemClickInterface: SmsListItemClickInterface) :
    PagingDataAdapter<SmsMessage, SmsMessageViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SmsMessageViewHolder {
        return SmsMessageViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SmsMessageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item!!,smsListItemClickInterface)
    }
}

class SmsMessageViewHolder(parent :ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.sms_message_row_item, parent, false)) {

    private val senderView = itemView.tvTitle
    private val dateTimeView = itemView.tvDateTime
    private val bodyView = itemView.tvDescription
    private val ivMessageType = itemView.ivMessageType
    private val tvHeader = itemView.tvHeaderLabel
    fun bindTo(message: SmsMessage, smsListItemClickInterface: SmsListItemClickInterface) {
        senderView.text = message.address
        dateTimeView.text = message.time.getDateValueWithDateAndTime()
        bodyView.text = message.msgBodyText
        if(message.isShowHeader) {
            tvHeader.visibility = View.VISIBLE
            tvHeader.text = message.headerValue
        } else {
            tvHeader.visibility = View.GONE
        }
        Log.i("bindTo: ","Type ${SmsMessageType.RECEIVED.ordinal} ${SmsMessageType.SENT.ordinal} ${message.type}")
        if (message.type == SmsMessageType.RECEIVED.ordinal) {
            ivMessageType.setImageResource(R.drawable.ic_incoming)
        } else {
            ivMessageType.setImageResource(R.drawable.ic_outgoing)
        }
        if (message.readState == "0") {
            bodyView.setTextColor(ContextCompat.getColor(itemView.context,R.color.colorBlackText))
        } else {
            bodyView.setTextColor(ContextCompat.getColor(itemView.context,R.color.colorGrey))
        }
        itemView.setOnClickListener {
            smsListItemClickInterface.onItemClick(smsMessageData = message)
        }
    }
}

object SmsMessageComparator : DiffUtil.ItemCallback<SmsMessage>() {
    override fun areItemsTheSame(oldItem: SmsMessage, newItem: SmsMessage): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SmsMessage, newItem: SmsMessage): Boolean {
        return oldItem == newItem
    }
}