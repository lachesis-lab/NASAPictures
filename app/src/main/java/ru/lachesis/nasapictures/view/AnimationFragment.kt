package ru.lachesis.nasapictures.view

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Transition
import androidx.transition.TransitionManager
import ru.lachesis.nasapictures.R

class AnimationFragment: Fragment() {
    private val rv: RecyclerView by lazy { requireActivity().findViewById<RecyclerView>(R.id.animation_test_rv) }

    companion object {
        fun newInstance(): AnimationFragment{
            return AnimationFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.animation_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv.adapter = AnimationAdapter()
    }
    inner class AnimationAdapter(): RecyclerView.Adapter<AnimationViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimationViewHolder {
            return AnimationViewHolder(LayoutInflater.from(parent.context).inflate( R.layout.animation_test_rv_item,parent,false))
        }

        override fun onBindViewHolder(holder: AnimationViewHolder, position: Int) {
            holder.itemView.setOnClickListener{explode(it)}
        }

        override fun getItemCount(): Int {
            return 32
        }


    }

    private fun explode(itemView: View) {
        val rect = Rect()
        itemView.getGlobalVisibleRect(rect)
        val explode = Explode()
        explode.epicenterCallback = object: Transition.EpicenterCallback(){
            override fun onGetEpicenter(transition: Transition): Rect {
                return rect
            }
        }
        explode.duration =1000
        rv?.let {
            TransitionManager.beginDelayedTransition(it,explode)
        }
        rv?.adapter = null
    }

    inner class AnimationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}