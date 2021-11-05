package com.example.turbo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.turbo.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    lateinit var navController:NavController
    lateinit var bottom:BottomNavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toggle=ActionBarDrawerToggle(this,binding.drawerLayout, R.string.open, R.string.close )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 -> Toast.makeText(applicationContext,
                    "Clicked item 1",Toast.LENGTH_SHORT).show()
                R.id.item2 -> Toast.makeText(applicationContext,
                    "Clicked item 2",Toast.LENGTH_SHORT).show()
                R.id.item3 -> Toast.makeText(applicationContext,
                    "Clicked item 3",Toast.LENGTH_SHORT).show()
            }
            true
        }
 bottom=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()
        bottom.setupWithNavController(navController)

//        binding.bottomNavigationView.background = null
//        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
//        binding.bottomNavigationView.menu.getItem(1).isEnabled = true
//
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.miHome)
        var count=1
        fun countMe():String{return count++.toString()}
        binding.fab.setOnClickListener{
                view->
            Snackbar.make(view,"Here", Snackbar.LENGTH_LONG)
                .setAction("Action"){count-1}
                .show()
            binding.tv.text="${badge.isVisible}"
            binding.tv.append(countMe())}

 bottom.setOnItemSelectedListener {     item ->
            when(item.itemId){

                R.id.miHome -> {
                    binding.tv.text = "Home"
                   true
                }
                R.id.view1Fragment -> {
                    binding.tv.text = "Search"
                    navController.navigate(R.id.view1Fragment)
                    true
                }
                R.id.view2Fragment -> {
                    binding.tv.text = "Profile"
                    navController.navigate(R.id.view2Fragment)
                    true
                }
                R.id.view3Fragment -> {
                    binding.tv.text = "Settings"
                    navController.navigate(R.id.view3Fragment)
                    true
                }
                else->false
            }
        }





        badge.badgeGravity= BadgeDrawable.TOP_START
        badge.backgroundColor= Color.GREEN
        badge.isVisible = true
           // count%2==0
        badge.number = count
        binding.button.setOnClickListener{
            binding.bottomAppBar.fabAlignmentMode= BottomAppBar.FAB_ALIGNMENT_MODE_END
            binding.bottomAppBar.cradleVerticalOffset= 250F

            binding.bottomAppBar.cradleVerticalOffset= 1F
            binding.bottomAppBar.fabAlignmentMode=BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

            badge.clearNumber()}

    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}