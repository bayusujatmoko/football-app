package match.football.submission2.submission2.app.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import match.football.submission2.submission2.app.match.LastMatchFragment
import match.football.submission2.submission2.app.match.NextMatchFragment
import match.football.submission2.submission2.app.overview.OverViewFragment
import match.football.submission2.submission2.app.player.PlayerFragment


class TeamsPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                OverViewFragment()
            }
            else -> {
                PlayerFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Overview"
            else -> {
                return "Players"
            }
        }
    }
}