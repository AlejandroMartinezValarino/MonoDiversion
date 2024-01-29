package com.example.monodiversion.view.fragment

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
        _binding = FragmentBottomMenuBinding.inflate(inflater,container,false)
        initBackground()
        onImageClicked()
        return binding.root
    }

    private fun onImageClicked(){
        var intent:Intent
        binding.ivHome.setOnClickListener{
            intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        binding.ivMemory.setOnClickListener{
            intent = Intent(requireContext(), MemoryActivity::class.java)
            startActivity(intent)
        }
        binding.ivAgility.setOnClickListener{
            intent = Intent(requireContext(), AgilityActivity::class.java)
            startActivity(intent)
        }
        binding.ivScore.setOnClickListener{
            intent = Intent(requireContext(), ScoreActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initBackground(){
        val activityName = activity?.localClassName
        Log.d("+++", "initBackground: $activityName")
        userViewModel.isLightTheme.observe(viewLifecycleOwner){isLight->
            when(activityName){
                "view.MainActivity"->{
                    binding.ivHome.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_tertiary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_tertiary))
                    binding.ivMemory.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivAgility.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivScore.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                }
                "view.MemoryActivity"->{
                    binding.ivMemory.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_tertiary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_tertiary))
                    binding.ivHome.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivAgility.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivScore.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                }
                "view.AgilityActivity"->{
                    binding.ivAgility.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_tertiary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_tertiary))
                    binding.ivMemory.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivHome.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivScore.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                }
                "view.ScoreActivity"->{
                    binding.ivScore.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_tertiary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_tertiary))
                    binding.ivMemory.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivAgility.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivHome.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                }
                else ->{
                    binding.ivScore.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivMemory.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivAgility.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                    binding.ivHome.setBackgroundColor(if (isLight)ContextCompat.getColor(requireContext(),R.color.matrix_theme_light_primary) else ContextCompat.getColor(requireContext(),R.color.matrix_theme_dark_primary))
                }
            }
        }
    }
}