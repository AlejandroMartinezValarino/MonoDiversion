package com.example.monodiversion.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.MediaController
import android.widget.MultiAutoCompleteTextView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.monodiversion.R
import com.example.monodiversion.databinding.ActivityChaosBinding
import com.example.monodiversion.helper.Country
import com.example.monodiversion.helper.adapter.UserSearchAdapter
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Arrays
import java.util.Calendar

@AndroidEntryPoint
class ChaosActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChaosBinding
    private val userViewModel:UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChaosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ibBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        sendInfo()
        setSearch()
        setMulti()
        setInternetWidget()
        setButAnimation()
    }

    private fun setButAnimation(){
        binding.butSpin.setOnClickListener {
            ObjectAnimator.ofFloat(binding.tvFavorites,"rotation",0f,360f).apply {
                duration = 2000
            }.start()
        }
        val originalColor = ContextCompat.getColor(this,R.color.matrix_theme_light_primary)
        val finalColor = Color.RED

        binding.butColor.setOnClickListener {
            ObjectAnimator.ofObject(binding.butColor,"backgroundColor",ArgbEvaluator(),originalColor,finalColor).apply {
                duration = 2000
                addListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator) {
                        ObjectAnimator.ofObject(binding.butColor,"backgroundColor",ArgbEvaluator(),finalColor,originalColor).apply {
                            duration = 2000
                            start()
                        }
                    }
                })
            }.start()
        }
    }
    private fun setSearch() {
        userViewModel.getUsers()
        val container: RecyclerView = binding.rvUserContainer
        var userAdapter = UserSearchAdapter(this, emptyList())
        userViewModel.users.observe(this){users->
            userAdapter = UserSearchAdapter(this,users)
            container.adapter = userAdapter
        }

        binding.svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return false
            }
        })
    }
    private fun sendInfo(){
        val items = arrayOf("Agility", "Memory", "Combination")
        val selectedList = ArrayList<Int>()
        var date = "<p>Date: "
        binding.cvDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            date += "$dayOfMonth/${month+1}/$year</p><br>"
        }
        binding.butSend.setOnClickListener {
            val header = "<h1>Your information is:</h1><br>"
            val email = "<p>Your email: "+binding.etEmail.text+"</p><br>"
            val phone = "<p>Your phone number: "+binding.etPhone.text+"</p><br>"
            val fav = "<h2>Your favorites are:</h2><br>"
            val country = "<p>From country: "+binding.mactvCountries.text.dropLast(2)+"</p><br>"
            val rating = "<p>Rating: "+binding.ratBarChaos.rating+"/"+binding.ratBarChaos.max+"</p><br>"
            val builder = AlertDialog.Builder(this).apply {
                setTitle("Is this information correct?")
                setMessage(Html.fromHtml(header+email+phone+date+fav+country+rating))
                setPositiveButton("Ok"){_,_->}
                setNegativeButton("No"){_,_->}
            }
            builder.create().show()
        }
        binding.butCheck.setOnClickListener {
            val builder = AlertDialog.Builder(this).apply {
                setTitle("Which games did you like?")
                setMultiChoiceItems(items,null){_, which, isChecked ->
                    if (isChecked) {
                        selectedList.add(which)
                    } else if (selectedList.contains(which)) {
                        selectedList.remove(which)
                    }
                }
                setPositiveButton("DONE") { _, _ ->
                    val selectedStrings = ArrayList<String>()

                    for (j in selectedList.indices) {
                        selectedStrings.add(items[selectedList[j]])
                    }

                    Toast.makeText(applicationContext, "You liked: " + selectedStrings.toTypedArray()
                        .contentToString(), Toast.LENGTH_SHORT).show()
                }
            }
            builder.show()
        }
    }

    private fun setMulti(){
        val countries = Country().countriesList()
        val countriesAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        binding.mactvCountries.setAdapter(countriesAdapter)
        binding.mactvCountries.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    }
    private fun setInternetWidget(){
        val uri = Uri.parse("android.resource://$packageName/${R.raw.monkey}")
        val mediaControl = MediaController(this).apply {
            setAnchorView(binding.vvMonkey)
        }

        binding.vvMonkey.apply {
            setMediaController(mediaControl)
            setVideoURI(uri)
        }
        binding.wvWiki.apply {
            loadUrl("https://w.wiki/9C8F")
            isVerticalScrollBarEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    view?.loadUrl(request?.url.toString())
                    return true
                }
            }
        }
    }
}