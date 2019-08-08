package match.football.submission2.submission2.app.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import match.football.submission2.submission2.app.match.LastMatchFragment
import match.football.submission2.submission2.app.match.NextMatchFragment

class MatchPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LastMatchFragment()
            }
            else -> {
                NextMatchFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Last Match"
            else -> {
                return "Next Match"
            }
        }
    }
}