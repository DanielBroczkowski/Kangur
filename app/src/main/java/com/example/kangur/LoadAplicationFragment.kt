package com.example.kangur


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_load_aplication.*

/**
 * A simple [Fragment] subclass.
 */
class LoadAplicationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_load_aplication, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        testowybutton.setOnClickListener{
            activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.nav_login)
        }
    }





}
