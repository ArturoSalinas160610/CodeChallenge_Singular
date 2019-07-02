package com.example.codechallengesngular.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.codechallengesngular.R

class SettingsFragment : Fragment() {
    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        ButterKnife.bind(this, rootView)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val linearLayout = view!!.findViewById<LinearLayout>(R.id.linearLayout)
        val imageView = ImageView(this.requireContext())
        linearLayout.addView(imageView)

        Glide.with(this)
            .load("https://lh4.googleusercontent.com/_i7FmG68UiuRG172ZXkCJD_lP5jMlJQNEvb0FN9zZuBR92d4qPXwZX2cwejIdrzglrp_b84Z9mp3J_tw81A29S1UWVpB6yPDchhDNSwlVaVjDGSYaGQGV-PGLd5T7EJL6ZJ-IsxM")
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }
}