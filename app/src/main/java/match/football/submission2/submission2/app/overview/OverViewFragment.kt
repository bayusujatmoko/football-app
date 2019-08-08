package match.football.submission2.submission2.app.overview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.last_match_fragment.*
import kotlinx.android.synthetic.main.overview.*
import match.football.submission2.submission2.R
import match.football.submission2.submission2.app.util.Constant
import org.jetbrains.anko.support.v4.find

class OverViewFragment : Fragment() {
    private lateinit var idTeam: String
    private lateinit var tvDescription: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.overview, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bindData = arguments
        idTeam = bindData?.getString(Constant.TAG_OVERVIEW) ?: ""
        init()
        tvDescription.text = idTeam
    }

    private fun init() {
        tvDescription = find(R.id.overview)
    }
}