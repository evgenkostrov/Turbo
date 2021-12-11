package com.example.turbo.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.RequestManager
import com.example.turbo.R
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
        supportActionBar?.setDisplayShowTitleEnabled(true)


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
                "Выход из приложения", Toast.LENGTH_SHORT
            ).show()
            finish()
        }


        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.viewFragment -> {
                    Toast.makeText(
                        applicationContext,
                        "Вертикальный каталог", Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(R.id.viewFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.categoryFragment -> {
                    Toast.makeText(
                        applicationContext,
                        "По категориям", Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(R.id.categoryFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.cardFragment -> {
                    Toast.makeText(
                        applicationContext,
                        "Карточный список", Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(R.id.cardFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.pagerFragment -> {
                    Toast.makeText(
                        applicationContext,
                        "Горизонтальный каталог", Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(R.id.pagerFragment)
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
        bottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.viewFragment -> {
                    navController.navigate(R.id.viewFragment)
                    true
                }
                R.id.categoryFragment -> {
                    navController.navigate(R.id.categoryFragment)
                    true
                }
                R.id.cardFragment -> {
                    navController.navigate(R.id.cardFragment)
                    true
                }
                R.id.pagerFragment -> {
                    navController.navigate(R.id.pagerFragment)
                    true
                }
                else -> false
            } }

        /**
         *       Settings fab
         */

        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.viewFragment)
        var count = 1
        fun countMe(): String {
            return count++.toString()
        }
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Корзина", Snackbar.LENGTH_LONG)
                .setAction("Купить") { count - 1 }
                .show()

        }

        badge.badgeGravity = BadgeDrawable.TOP_START
        badge.backgroundColor = Color.GREEN
        badge.isVisible = true
        // count%2==0
        badge.number = count
        binding.root.setOnClickListener {
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