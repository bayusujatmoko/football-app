package match.football.submission2.submission2.app.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import match.football.submission2.submission2.app.favorites.FavoriteMatchFragment
import match.football.submission2.submission2.app.favorites.FavoriteTeamsFragment

class FavoritePageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavoriteMatchFragment()
            }
            else -> {
                FavoriteTeamsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Matches"
            else -> {
                return "Team"
            }
        }
    }
}