package com.example.codechallengesngular.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.codechallengesngular.R
import com.example.codechallengesngular.base.NavigationItem

class NavigationTabViewHolder(private val view: View) {
    @BindView(R.id.icon)
    protected lateinit var iconView: ImageView

    @BindView(R.id.name)
    protected lateinit var nameView: TextView

    var type: NavigationItem? = null

    init {
        ButterKnife.bind(this, view)
    }

    fun bind(item: NavigationItem) {
        type = item
        iconView.setImageResource(item.iconId)
        nameView.text = view.context.getString(item.tabName)
    }
}