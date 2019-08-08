package match.football.submission2.submission2.app.player

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import match.football.submission2.submission2.R
import match.football.submission2.submission2.R.id.*
import match.football.submission2.submission2.app.model.Player
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class PlayerAdapter(private val playerList:List<Player>, private val context: Context, val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.player_list, p0, false))
    }

    override fun getItemCount(): Int = playerList.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.bindPlayer(playerList[position], listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgPlayer: ImageView = view.find(ivPlayer)
        private val namePlayer: TextView = view.find(tvPlayerName)
        private val positionPlayer: TextView = view.find(tvPosition)

        fun bindPlayer(person: Player, listener: (Player) -> Unit){
            Picasso.get().load(person.strCutout).into(imgPlayer)
            namePlayer.text = person.strPlayer
            positionPlayer.text = person.strPosition

            itemView.onClick {
                listener(person)
            }
        }
    }
}