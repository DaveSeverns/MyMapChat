package com.sevdev.mymapchat.Controller

import android.app.FragmentManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sevdev.mymapchat.R

class ChatActivity : AppCompatActivity(), ChatListFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var fm : FragmentManager
    private var chatListFragment : ChatListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        fm = fragmentManager
        chatListFragment = ChatListFragment()
        fm.beginTransaction().add(R.id.fragment_container,chatListFragment).commit()
    }
}
