package match.football.submission2.submission2.app.match

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import match.football.submission2.submission2.R
import match.football.submission2.submission2.app.model.Match
import match.football.submission2.submission2.app.util.Constant
import match.football.submission2.submission2.app.util.Time
import match.football.submission2.submission2.app.util.invisible
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class LastMatchAdapter(private val eventList:List<Match>, private val context: Context, val listener: (Match) -> Unit)
    : RecyclerView.Adapter<LastMatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.match_list, p0, false))
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.bindMatch(eventList[position], listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val date: TextView = view.find(R.id.dateScheduleTv)
        private val homeName: TextView = view.find(R.id.homeNameTv)
        private val awayName: TextView = view.find(R.id.awayNameTv)
        private val homeScore: TextView = view.find(R.id.homeScoreTv)
        private val awayScore: TextView = view.find(R.id.awayScoreTv)
        private val alert: ImageView = view.find(R.id.alert)

        fun bindMatch(event: Match, listener: (Match) -> Unit){
            val dateTime = Time.formatUTCtoGMT(event.dateEvent.toString(), event.strTime.toString().substring(0, 5), Constant.DATE_TIME)
            date.text = dateTime
            homeName.text = event.strHomeTeam?: ""
            awayName.text = event.strAwayTeam?: ""
            homeScore.text = event.intHomeScore?: ""
            awayScore.text = event.intAwayScore?: ""
            alert.invisible()

            itemView.onClick {
                listener(event)
            }
        }
    }
}