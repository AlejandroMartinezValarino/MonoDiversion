package com.example.monodiversion.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import com.example.monodiversion.R
import com.example.monodiversion.databinding.ActivityMainBinding
import com.example.monodiversion.helper.BoxArrangement
import com.example.monodiversion.helper.Country
import com.example.monodiversion.model.Flag
import com.example.monodiversion.model.User
import com.example.monodiversion.view.fragment.BottomMenuFragment
import com.example.monodiversion.view.fragment.HeaderFragment
import com.example.monodiversion.viewModel.UserViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        userViewModel.isLightTheme.observe(this) { isLight ->
            updateUiTheme(!isLight)
        }

        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.flHeaderContainer.id, HeaderFragment.newInstance())
                .add(binding.flBottomContainer.id, BottomMenuFragment.newInstance())
                .setReorderingAllowed(true)
                .commit()
        }
        if(intent.hasExtra("id") && intent.getLongExtra("id",0L)!=0L){
            val id = intent.getLongExtra("id",0L)
            userViewModel.updateById(id)
        }
        initListeners()
        onUserCreation()
        saveUser()
        chooseUser()
    }

    private fun initListeners() {
        val countries = Country().countriesList()
        val countriesAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        binding.actvCountries.setAdapter(countriesAdapter)
    }

    private fun onUserCreation() {
        userViewModel.user.observe(this){user->

            val flag = user.flag
            var selectedChips:List<Int> = listOf(ContextCompat.getColor(this,R.color.matrix_theme_light_primary))
            var selectedArrangement:BoxArrangement = BoxArrangement.HORIZONTAL

            binding.etName.doAfterTextChanged { text ->
                user.name = text.toString()
                userViewModel.updateUser(user)
            }
            binding.actvCountries.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as? Pair<String, String>
                if (selectedItem != null) {
                    user.country = selectedItem
                    userViewModel.updateUser(user)
                }
            }
            binding.cgColors.setOnCheckedStateChangeListener { _, _ ->
                selectedChips = binding.cgColors.children
                    .filter { (it as Chip).isChecked }
                    .map { getColorResourceByName((it as Chip).text.toString(), this) }
                    .toList()
                if (flag != null) {
                    flag.colors = selectedChips
                    user.flag = flag
                    userViewModel.updateUser(user)
                }else{
                    user.flag = Flag(selectedChips,selectedArrangement)
                    userViewModel.updateUser(user)
                }
            }

            binding.rgColors.setOnCheckedChangeListener { group, id ->
                val rbArrangement = group.findViewById<RadioButton>(id)
                selectedArrangement = when (rbArrangement.text) {
                    "Horizontal" -> BoxArrangement.HORIZONTAL
                    "Vertical" -> BoxArrangement.VERTICAL
                    else -> BoxArrangement.HORIZONTAL
                }
                if (flag != null) {
                    flag.orientation = selectedArrangement
                    user.flag = flag
                    userViewModel.updateUser(user)
                }else{
                    user.flag = Flag(selectedChips,selectedArrangement)
                    userViewModel.updateUser(user)
                }
            }
        }
    }

    private fun getColorResourceByName(colorName: String, context: Context): Int {
        val resourceId = context.resources.getIdentifier(colorName, "color", context.packageName)

        return if (resourceId != 0) {
            ContextCompat.getColor(context, resourceId)
        } else {
            throw IllegalArgumentException("Color with name $colorName not found")
        }
    }

    private fun saveUser(){
        binding.butCreate.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            userViewModel.user.observe(this){user->
                val builder = AlertDialog.Builder(this)
                builder.setTitle("User created")
                builder.setMessage("Name: ${user.name}")
                builder.setPositiveButton("Accept") { dialog, which ->
                    dialog.dismiss()
                }
                userViewModel.save()
                builder.create()
                    .show()
                userViewModel.userId.observe(this){id->
                    intent.putExtra("id",id)
                    startActivity(intent)
                }
            }
        }
    }

    private fun chooseUser(){
        binding.butChoose.setOnClickListener {
            val intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUiTheme(isDarkTheme: Boolean) {
        val llMainAct = binding.llMainAct
        val chipGroup = binding.cgColors
        val radioGroup = binding.rgColors
        val radioButtonH = binding.rbHorizontal
        val radioButtonV = binding.rbVertical
        val textViewColors = binding.tvColors
        val buttonCreate = binding.butCreate
        val buttonChoose = binding.butChoose
        val editTextName = binding.etName
        val actvCountries = binding.actvCountries

        editTextName.setBackgroundColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_secondary else R.color.matrix_theme_light_secondary
            )
        )

        editTextName.setTextColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_onSecondary else R.color.matrix_theme_light_onSecondary
            )
        )

        actvCountries.setBackgroundColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_secondary else R.color.matrix_theme_light_secondary
            )
        )

        actvCountries.setTextColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_onSecondary else R.color.matrix_theme_light_onSecondary
            )
        )

        buttonCreate.setBackgroundColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_primary else R.color.matrix_theme_light_primary
            )
        )
        buttonCreate.setTextColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_onPrimary else R.color.matrix_theme_light_onPrimary
            )
        )

        buttonChoose.setBackgroundColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_primary else R.color.matrix_theme_light_primary
            )
        )
        buttonChoose.setTextColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_onPrimary else R.color.matrix_theme_light_onPrimary
            )
        )

        llMainAct.setBackgroundColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_background else R.color.matrix_theme_light_background
            )
        )

        chipGroup.setBackgroundColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_background else R.color.matrix_theme_light_background
            )
        )
        radioGroup.setBackgroundColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_background else R.color.matrix_theme_light_background
            )
        )
        radioButtonH.setTextColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_onBackground else R.color.matrix_theme_light_onBackground
            )
        )
        radioButtonV.setTextColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_onBackground else R.color.matrix_theme_light_onBackground
            )
        )
        textViewColors.setTextColor(
            ContextCompat.getColor(
                this,
                if (isDarkTheme) R.color.matrix_theme_dark_onBackground else R.color.matrix_theme_light_onBackground
            )
        )
    }
}
