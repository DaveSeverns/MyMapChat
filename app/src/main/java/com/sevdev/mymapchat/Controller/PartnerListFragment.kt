package com.sevdev.mymapchat.Controller

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sevdev.mymapchat.Adapters.RecyclerAdapter
import com.sevdev.mymapchat.Model.Partner

import com.sevdev.mymapchat.R
import com.sevdev.mymapchat.Utility.ERROR_HERE_TAG
import com.sevdev.mymapchat.Utility.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PartnerListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class PartnerListFragment : Fragment() {

    lateinit var adapter : RecyclerAdapter
    private var partnerList : ArrayList<Partner>? = null

    private var mListener: OnFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_partner_list, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
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
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    fun getPartnerListNetwork(adapter: RecyclerAdapter): ArrayList<Partner>{
        val call = NetworkManager.networkCall().getPartnerList()
        val partners = ArrayList<Partner>()
        call.enqueue(object : Callback<ArrayList<Partner>> {
            override fun onResponse(call: Call<ArrayList<Partner>>?, response: Response<ArrayList<Partner>>?) {
                partners.addAll( response!!.body()!!)
                adapter.notifyDataSetChanged()
                println(partners?.get(0)?.username)
            }

            override fun onFailure(call: Call<ArrayList<Partner>>?, t: Throwable?) {
                Log.e(ERROR_HERE_TAG, "onFailure")
            }
        })

        return partners
    }
}// Required empty public constructor
