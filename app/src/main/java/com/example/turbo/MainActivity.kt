package com.example.turbo

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.Html
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.turbo.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var glide: RequestManager

    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var bottom: BottomNavigationView
    lateinit var navController: NavController
    lateinit var listener: NavController.OnDestinationChangedListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()


        /**
         *       Settings drawer
         */
        binding.navView.bringToFront()
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        setOf(R.id.view1Fragment,R.id.view3Fragment,R.id.view3Fragment)
        appBarConfiguration = AppBarConfiguration(
            navGraph = navController.graph,
            drawerLayout = binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        actionBar?.setDisplayShowTitleEnabled(true)


//        actionBar?.subtitle = Html.fromHtml("<font color='#FFBF00'>Here</font>")
//        actionBar?.title = "nnn"
//        val s = binding.navView.menu.findItem(R.id.menu_logout).title
//        val ss = SpannableString(s)
//        ss.setSpan(ForegroundColorSpan(Color.MAGENTA), 0, s.length, 0)
//        binding.navView.menu.findItem(R.id.menu_logout).title = ss



        binding.navView.menu.findItem(R.id.menu_button).setActionView(R.layout.button)
        binding.navView.menu.findItem(R.id.menu_button).actionView.setPadding(50,0,100,0)

        binding.navView.menu.findItem(R.id.menu_button).actionView.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Clicked item 3", Toast.LENGTH_SHORT
            ).show()
            finish()
        }


        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.view1Fragment -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked item 1", Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(R.id.view1Fragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.view2Fragment -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked item 2", Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(R.id.view2Fragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.view3Fragment -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked item 3", Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(R.id.view3Fragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }

        /**
         *       Settings bottom
         */

        bottom = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottom.setupWithNavController(navController)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
        bottom.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
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
                else -> false
            } }
//        bottom.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.miHome -> {
//                    binding.tv.text = "Home"
//                    true
//                }
//                R.id.view1Fragment -> {
//                    binding.tv.text = "Search"
//                    navController.navigate(R.id.view1Fragment)
//                    true
//                }
//                R.id.view2Fragment -> {
//                    binding.tv.text = "Profile"
//                    navController.navigate(R.id.view2Fragment)
//                    true
//                }
//                R.id.view3Fragment -> {
//                    binding.tv.text = "Settings"
//                    navController.navigate(R.id.view3Fragment)
//                    true
//                }
//                else -> false
//            }
//        }

        /**
         *       Settings fab
         */

        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.miHome)
        var count = 1
        fun countMe(): String {
            return count++.toString()
        }
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Here", Snackbar.LENGTH_LONG)
                .setAction("Action") { count - 1 }
                .show()
            binding.tv.text = "${badge.isVisible}"
            binding.tv.append(countMe())
        }

        badge.badgeGravity = BadgeDrawable.TOP_START
        badge.backgroundColor = Color.GREEN
        badge.isVisible = true
        // count%2==0
        badge.number = count
        binding.button.setOnClickListener {
            binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            binding.bottomAppBar.cradleVerticalOffset = 250F

            binding.bottomAppBar.cradleVerticalOffset = 1F
            binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

            badge.clearNumber()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            navController.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}