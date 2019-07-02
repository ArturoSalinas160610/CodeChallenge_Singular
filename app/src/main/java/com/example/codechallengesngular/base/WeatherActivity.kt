package com.example.codechallengesngular.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.codechallengesngular.R
import com.example.codechallengesngular.settings.SettingsFragment
import com.example.codechallengesngular.utils.NavigationTabViewHolder
import com.google.android.material.tabs.TabLayout

class WeatherActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private val items: List<NavigationItem> = listOf(
        NavigationItem.Actual,
        NavigationItem.Next,
        NavigationItem.Settings
    )
    lateinit var navigationBars: TabLayout
    var currentCity: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        navigationBars = findViewById(R.id.navigation_bars)
        navigationBars.addOnTabSelectedListener(this)

        currentCity = intent.getStringExtra(ACTUAL_CITY)

        navigationBars.showTabs(Pair(items, NavigationItem.Actual))
    }

    private fun TabLayout.showTabs(tabState: Pair<List<NavigationItem>, NavigationItem>) {
        val (items, selectedTab) = tabState
        removeAllTabs()
        items.forEach { navigationItem ->
            val tabView =
                LayoutInflater.from(context).inflate(R.layout.dashboard_navigation_tab, this, false)
            val tabViewHolder = NavigationTabViewHolder(tabView).also { it.bind(navigationItem) }
            addTab(
                newTab().apply {
                    customView = tabView
                    tag = tabViewHolder
                }
            )
        }
        getTabAt(selectedTab.index)?.select()
    }

    override fun onTabReselected(tab: TabLayout.Tab?) = selectTab(tab!!)

    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

    override fun onTabSelected(tab: TabLayout.Tab?) = selectTab(tab!!)

    private fun selectTab(tab: TabLayout.Tab) {
        (tab.tag as NavigationTabViewHolder).type?.let {
            val fragment = if (it.index == 0) WeatherActualFragment.newInstance(
                currentCity
            )
            else if (it.index == 1) WeatherNextDaysFragment.newInstance()
            else SettingsFragment.newInstance()

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, fragment)
            }.commit()
        }
    }
}

private const val ACTUAL_CITY = "actual_city"