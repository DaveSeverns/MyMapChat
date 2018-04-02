package com.sevdev.mymapchat.Controller

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sevdev.mymapchat.Adapters.RecyclerAdapter
import com.sevdev.mymapchat.Model.Partner

import com.sevdev.mymapchat.R
import com.sevdev.mymapchat.Utility.ERROR_HERE_TAG
import com.sevdev.mymapchat.Utility.NetworkManager
import kotlinx.android.synthetic.main.fragment_partner_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PartnerListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class PartnerListFragment : Fragment(), RecyclerAdapter.ClickListener {
    override fun listItemClicked(partner: Partner) {
        mListener?.itemClicked(partner.username)
    }

    private lateinit var adapter : RecyclerAdapter
    internal lateinit var view: View
    private var partnerList : ArrayList<Partner> = ArrayList<Partner>()
    private var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
    private lateinit var recyclerView: RecyclerView

    private var mListener: OnParnterListFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        container?.removeAllViews()


        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_partner_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView

        adapter = RecyclerAdapter(partnerList,this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager



        return view
    }



    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnParnterListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnParnterListFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
        fun itemClicked(partner : String?)
    }

    open fun addPartnersToList(partners: ArrayList<Partner>){
        partnerList.addAll(partners)
        adapter.notifyDataSetChanged()
    }






}// Required empty public constructor
