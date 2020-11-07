package be.tim.beers.beers

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.tim.beers.R
import be.tim.beers.data.*
import be.tim.beers.data.local.SessionManager
import be.tim.beers.data.remote.ApiClient
import be.tim.beers.di.Injection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BeersFragment : Fragment() {

    private val TAG = BeersFragment::class.qualifiedName

    private lateinit var viewModel: BeersViewModel

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    private lateinit var rvBeers : RecyclerView
    private lateinit var allBeers : List<Beer>
    private lateinit var beers : List<Beer>
    private lateinit var adapter: BeersAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(BeersViewModel::class.java)

        viewModel.beers.observe(viewLifecycleOwner, renderBeers)
//        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
//        viewModel.onMessageError.observe(this, onMessageErrorObserver)
//        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    //observers
    private val renderBeers = Observer<List<Beer>> {
        Log.v(TAG, "data updated $it")
//        layoutError.visibility = View.GONE
//        layoutEmpty.visibility = View.GONE
//        adapter.update(it)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.beers_frag, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_filter -> {
                showFilterPopUpMenu()
                true
            }
            R.id.menu_refresh -> {
                getBeers()
                true
            }
            else -> false
        }

    private fun showFilterPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_beers, menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.all -> {
                        (beers as ArrayList<Beer>).clear()
                        (beers as ArrayList<Beer>).addAll(allBeers)
                        adapter.notifyDataSetChanged()
                    }
                    R.id.rated -> {
                        (beers as ArrayList<Beer>).clear()
                        (beers as ArrayList<Beer>).addAll(allBeers.filter { it.rating != null })
                        adapter.notifyDataSetChanged()
                    }
                    R.id.best -> {
                        (beers as ArrayList<Beer>).clear()
                        (beers as ArrayList<Beer>).addAll(allBeers.filter { it.rating?:0 > 4})
                        adapter.notifyDataSetChanged()
                    }
                }
                true
            }
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.beers_fragment_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBeers = view.findViewById<View>(R.id.rv_beers) as RecyclerView
        rvBeers.layoutManager = LinearLayoutManager(view.context)
        rvBeers.addItemDecoration(
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        )
    }

    override fun onResume() {
        super.onResume()

//        apiClient = ApiClient
//        sessionManager = SessionManager(requireContext())
//
//        if (sessionManager.fetchAuthToken() == null) {
//            login()
//        } else {
//            getBeers()
//        }

        viewModel.loadBeers()
    }

    private fun login() {
        val userInfo = UserInfo( userName = "star_developer@icapps.com", password = "developer")

//        apiClient.getApiService(requireContext()).login(userInfo).enqueue(object : Callback<ResponseWrapper<LoginData>> {
//            override fun onFailure(call: Call<ResponseWrapper<LoginData>>?, t: Throwable?) {
//                Log.d(TAG, "Login call failed")
//            }
//
//            override fun onResponse(call: Call<ResponseWrapper<LoginData>>?, response: Response<ResponseWrapper<LoginData>>?) {
//                if (response?.code() == 200) {
//                    val response = response.body() as ResponseWrapper<LoginData>
//                    val token = response.data.accessToken
//                    sessionManager.saveAuthToken(token)
//                    Log.d(TAG, "Login success with token: $token")
//
//                    getBeers()
//                } else {
//                    // TODO: 05/11/2020 handle error
//                }
//            }
//        })
    }

    private fun getBeers() {
//        apiClient.getApiService(requireContext()).getBeers().enqueue(object : Callback<ResponseWrapper<List<Beer>>> {
//            override fun onFailure(call: Call<ResponseWrapper<List<Beer>>>?, t: Throwable?) {
//                Log.d(TAG, "Get beers call failed")
//            }
//
//            override fun onResponse(call: Call<ResponseWrapper<List<Beer>>>?, response: Response<ResponseWrapper<List<Beer>>>?) {
//                val response = response!!.body() as ResponseWrapper<List<Beer>>
//                val beerData = response.data
//
//                allBeers = beerData.toMutableList()
//                beers = beerData
//
//                Log.d(TAG, "Get beers success: loaded ${beers.size} beers")
//
//                adapter = BeersAdapter(beers)
//                adapter.onItemClick = { beer ->
//                    Log.d(TAG, "Clicked beer: ${beer.name}")
//
//                    val action = BeersFragmentDirections.actionBeersFragmentToBeersDetailFragment(beer.id)
//                    findNavController().navigate(action)
//                }
//                rvBeers.adapter = adapter
//            }
//        })
    }
}