package com.example.kangur.MainFragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import com.example.kangur.R
import kotlinx.android.synthetic.main.fragment_main_page.*

/**
 * A simple [Fragment] subclass.
 */
class MainPageFragment : Fragment() {

    var whichFragment = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.frameLayoutSwapper, FriendsListFragment()).commit()

        buttonFriends.setOnClickListener{
            if(whichFragment!=1){
                whichFragment=1
                swapFragments()
            }
        }

        buttonSearch.setOnClickListener{
            if(whichFragment!=2){
                whichFragment=2
                swapFragments()
            }
        }
        buttonMyProfile.setOnClickListener{
            if(whichFragment!=3){
                whichFragment=3
                swapFragments()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_picture, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    fun swapFragments(){
        if(whichFragment==1){
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frameLayoutSwapper, FriendsListFragment()).commit()
        }
        else if(whichFragment==2){
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frameLayoutSwapper, SearchFriendFragment()).commit()
        }
        else if(whichFragment==3){
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frameLayoutSwapper, MyProfileFragment()).commit()
        }
    }

}
