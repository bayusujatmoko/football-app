package match.football.submission2.submission2.app.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.favorite_fragment.*
import match.football.submission2.submission2.R
import match.football.submission2.submission2.app.db.Favorite
import match.football.submission2.submission2.app.db.database
import match.football.submission2.submission2.app.detail.DetailActivity
import match.football.submission2.submission2.app.util.Constant
import match.football.submission2.submission2.app.util.invisible
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.find

class FavoriteMatchFragment : Fragment(),
    SearchView.OnQueryTextListener{

    private var favoriteList : MutableList<Favorite> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.favorite_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        setAdapter(favoriteList)
        showFavorite()

        swipeRefreshFavorite.setOnRefreshListener {
            favoriteList.clear()
            showFavorite()
        }
    }

    private fun initData() {
        recyclerView = find(R.id.rvFavorite)
        progressBar = find(R.id.progressBarFavorite)
        swipeRefresh = find(R.id.swipeRefreshFavorite)
    }

    private fun showFavorite() {
        progressBar.invisible()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            Log.d("__DEBUG",favorite.toString())
            favoriteList.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search, menu)
        val searchItem = menu.findItem(R.id.search_menu)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            val editext: EditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)
            editext.hint = getString(R.string.search)
            searchView.setOnQueryTextListener(this)
        }
    }

    override fun onQueryTextSubmit(search: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(search: String): Boolean {
        if (search.isNotEmpty()) {
            val find = search.toLowerCase()
            val data = favoriteList.filter {
                it.homeTeam.toString().toLowerCase().contains(find) ||
                        it.awayTeam.toString().toLowerCase().contains(find)
            }
            setAdapter(data)
        } else {
            setAdapter(favoriteList)
        }
        return true
    }

    private fun setAdapter(data: List<Favorite>) {
        adapter = FavoriteMatchAdapter(data,requireContext()) {
            requireActivity().startActivity<DetailActivity>(Constant.DETAIL to it.idEvent)
        }
        recyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter
    }
}