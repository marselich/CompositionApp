package ru.kalievmars.compositionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kalievmars.compositionapp.fragments.WelcomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, WelcomeFragment.newInstance())
    }
}