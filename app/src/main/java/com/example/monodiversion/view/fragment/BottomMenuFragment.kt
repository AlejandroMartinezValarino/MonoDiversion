package com.example.monodiversion.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.monodiversion.R
import com.example.monodiversion.databinding.FragmentBottomMenuBinding
import com.example.monodiversion.view.AgilityActivity
import com.example.monodiversion.view.CombinationActivity
import com.example.monodiversion.view.MainActivity
import com.example.monodiversion.view.MemoryActivity
import com.example.monodiversion.view.ScoreActivity
import com.example.monodiversion.view.UsersActivity
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomMenuFragment : Fragment() {

    companion object {
        fun newInstance() = BottomMenuFragment()
    }

    private var _binding: FragmentBottomMenuBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomMenuBinding.inflate(inflater, container, false)
        initBackground()
        onImageClicked()
        return binding.root
    }

    private fun onImageClicked() {
        var intent: Intent
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            binding.cvHome.setOnClickListener {
                intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("id", user.id)
                startActivity(intent)
            }
            binding.cvMemory.setOnClickListener {
                if (user.id != 0L) {
                    intent = Intent(requireContext(), MemoryActivity::class.java)
                    intent.putExtra("id", user.id)
                    startActivity(intent)
                } else {
                    val dialog = AlertDialog.Builder(requireContext())
                        .setTitle("Empty user")
                        .setMessage("Choose or create a user")
                        .setPositiveButton("Ok") { _, _ -> }
                        .create()
                    dialog.show()
                }
            }
            binding.cvAgility.setOnClickListener {
                if (user.id != 0L) {
                    intent = Intent(requireContext(), AgilityActivity::class.java)
                    intent.putExtra("id", user.id)
                    startActivity(intent)
                } else {
                    val dialog = AlertDialog.Builder(requireContext())
                        .setTitle("Empty user")
                        .setMessage("Choose or create a user")
                        .setPositiveButton("Ok") { _, _ -> }
                        .create()
                    dialog.show()
                }
            }
            binding.cvCombination.setOnClickListener {
                if (user.id != 0L) {
                    intent = Intent(requireContext(), CombinationActivity::class.java)
                    intent.putExtra("id", user.id)
                    startActivity(intent)
                } else {
                    val dialog = AlertDialog.Builder(requireContext())
                        .setTitle("Empty user")
                        .setMessage("Choose or create a user")
                        .setPositiveButton("Ok") { _, _ -> }
                        .create()
                    dialog.show()
                }
            }
            binding.cvScore.setOnClickListener {
                if (user.id != 0L) {
                    intent = Intent(requireContext(), ScoreActivity::class.java)
                    intent.putExtra("id", user.id)
                    startActivity(intent)
                } else {
                    val dialog = AlertDialog.Builder(requireContext())
                        .setTitle("Empty user")
                        .setMessage("Choose or create a user")
                        .setPositiveButton("Ok") { _, _ -> }
                        .create()
                    dialog.show()
                }
            }
        }
    }


    private fun initBackground() {
        val activityName = activity?.localClassName
        userViewModel.isLightTheme.observe(viewLifecycleOwner) { isLight ->
            when (activityName) {
                "view.MainActivity" -> {
                    binding.cvHome.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_tertiary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_tertiary
                        )
                    )
                    binding.cvCombination.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvMemory.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvAgility.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvScore.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                }

                "view.MemoryActivity" -> {
                    binding.cvMemory.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_tertiary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_tertiary
                        )
                    )
                    binding.cvHome.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvAgility.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvCombination.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvScore.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                }

                "view.AgilityActivity" -> {
                    binding.cvAgility.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_tertiary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_tertiary
                        )
                    )
                    binding.cvCombination.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvMemory.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvHome.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvScore.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                }

                "view.CombinationActivity" -> {
                    binding.cvCombination.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_tertiary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_tertiary
                        )
                    )
                    binding.cvAgility.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvMemory.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvHome.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvScore.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                }

                "view.ScoreActivity" -> {
                    binding.cvScore.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_tertiary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_tertiary
                        )
                    )
                    binding.cvCombination.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvMemory.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvAgility.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvHome.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                }

                else -> {
                    binding.cvScore.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvCombination.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvMemory.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvAgility.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                    binding.cvHome.setCardBackgroundColor(
                        if (isLight) ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_light_primary
                        ) else ContextCompat.getColor(
                            requireContext(),
                            R.color.matrix_theme_dark_primary
                        )
                    )
                }
            }
        }
    }
}