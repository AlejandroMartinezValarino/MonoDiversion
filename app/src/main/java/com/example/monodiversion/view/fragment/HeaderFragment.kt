package com.example.monodiversion.view.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.monodiversion.R
import com.example.monodiversion.databinding.FragmentHeaderBinding
import com.example.monodiversion.helper.BoxArrangement
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeaderFragment : Fragment() {

    companion object {
        fun newInstance() = HeaderFragment()
    }

    private val userViewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentHeaderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeaderBinding.inflate(inflater, container, false)
        userViewModel.isLightTheme.observe(viewLifecycleOwner, Observer { isLight ->
            if (isLight) {
                binding.llHeader.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.matrix_theme_light_primary
                    )
                )
                binding.tvName.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.matrix_theme_light_onPrimary
                    )
                )
                binding.tvCountry.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.matrix_theme_light_onPrimary
                    )
                )
                binding.tvScore.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.matrix_theme_light_onPrimary
                    )
                )
                binding.tvSwitch.text = "Light"
                binding.tvSwitch.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.matrix_theme_light_onPrimary
                    )
                )
            } else {
                binding.llHeader.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.matrix_theme_dark_primary
                    )
                )
                binding.tvName.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.matrix_theme_dark_onPrimary
                    )
                )
                binding.tvCountry.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.matrix_theme_dark_onPrimary
                    )
                )
                binding.tvScore.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.matrix_theme_dark_onPrimary
                    )
                )
                binding.tvSwitch.text = "Dark"
                binding.tvSwitch.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.matrix_theme_dark_onPrimary
                    )
                )
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        userViewModel.score.observe(viewLifecycleOwner) { score ->
            val points = score.points.toString() + " points"
            binding.tvScore.text = points
        }
        binding.scTheme.setOnCheckedChangeListener { _, isChecked ->
            userViewModel.updateTheme(isChecked)
        }
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            val flag = user.flag
            if (flag != null) {
                binding.llFlag.orientation =
                    if (flag.orientation == BoxArrangement.HORIZONTAL) HORIZONTAL else VERTICAL

                binding.llFlag.removeAllViews()
                for (color in flag.colors) {
                    val view = View(context)
                    view.setBackgroundColor(color)

                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1f
                    )
                    view.layoutParams = layoutParams

                    binding.llFlag.addView(view)
                }
            }

            binding.tvName.text = user.name
            binding.tvCountry.text = user.country.toString()
        }
    }
}