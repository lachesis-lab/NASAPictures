package ru.lachesis.nasapictures.view

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import ru.lachesis.nasapictures.R

class SplashActivity : AppCompatActivity() {

 //   val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val splashView = findViewById<ImageView>(R.id.splash_view)
        splashView.animate()
            .rotationBy(720F)
            .setInterpolator(LinearInterpolator())
            .setDuration(2000)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {                }

                override fun onAnimationEnd(animation: Animator?) {
                    startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                    finish()
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationRepeat(animation: Animator?) {}

            })

    }
}