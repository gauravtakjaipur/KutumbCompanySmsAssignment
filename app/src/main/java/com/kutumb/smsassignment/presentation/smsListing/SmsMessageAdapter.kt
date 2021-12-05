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
import com.kutumb.smsassignment.data.modelClasses.SmsMessageData
import com.kutumb.smsassignment.data.modelClasses.SmsMessageType
import com.kutumb.smsassignment.extensions.getDateValueWithDateAndTime
import kotlinx.android.synthetic.main.sms_message_row_item.view.*

class SmsMessageAdapter(diffCallback: DiffUtil.ItemCallback<SmsMessageData>, var smsListItemClickInterface: SmsListItemClickInterface) :
    PagingDataAdapter<SmsMessageData, SmsMessageViewHolder>(diffCallback) {
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
    fun bindTo(messageData: SmsMessageData, smsListItemClickInterface: SmsListItemClickInterface) {
        senderView.text = messageData.address
        dateTimeView.text = messageData.time.getDateValueWithDateAndTime()
        bodyView.text = messageData.msgBodyText
        if(messageData.isShowHeader) {
            tvHeader.visibility = View.VISIBLE
            tvHeader.text = messageData.headerValue
        } else {
            tvHeader.visibility = View.GONE
        }
        Log.i("bindTo: ","Type ${SmsMessageType.RECEIVED.ordinal} ${SmsMessageType.SENT.ordinal} ${messageData.type}")
        if (messageData.type == SmsMessageType.RECEIVED.ordinal) {
            ivMessageType.setImageResource(R.drawable.ic_incoming)
        } else {
            ivMessageType.setImageResource(R.drawable.ic_outgoing)
        }
        if (messageData.readState == "0") {
            bodyView.setTextColor(ContextCompat.getColor(itemView.context,R.color.colorBlackText))
        } else {
            bodyView.setTextColor(ContextCompat.getColor(itemView.context,R.color.colorGrey))
        }
        itemView.setOnClickListener {
            smsListItemClickInterface.onItemClick(smsMessageData = messageData)
        }
    }
}

object SmsMessageComparator : DiffUtil.ItemCallback<SmsMessageData>() {
    override fun areItemsTheSame(oldItem: SmsMessageData, newItem: SmsMessageData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SmsMessageData, newItem: SmsMessageData): Boolean {
        return oldItem == newItem
    }
}