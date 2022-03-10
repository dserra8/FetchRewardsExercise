package com.example.fetchrewardsexercise.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchrewardsexercise.data.adapter.ItemListAdapter
import com.example.fetchrewardsexercise.databinding.ListFragmentLayoutBinding
import com.example.fetchrewardsexercise.domain.models.Item
import com.example.fetchrewardsexercise.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment that will show the list of items.
 * Using binding to get a reference to views.
 * Using a viewModel to handle all data operations
 */

@AndroidEntryPoint
class ListFragment: Fragment() {

    //Declaring binding so I can access each view
    private var _binding: ListFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListFragmentViewModel by viewModels()

    private lateinit var listAdapter: ItemListAdapter


    /**
     * Using onCreateView to initialize ViewBinding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Once All views are created, I do everything else involving said views
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initializing recycler view adapter and setting up the recycler view
        listAdapter = ItemListAdapter()
        binding.apply {
            recyclerView.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }

        //Setting up a coroutine scope so Flow operations are Main thread safe and lifecycle aware
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listFlow.collectLatest {
                    when(it){
                        //If error, then display error and make it visible
                        is Resource.Error -> {
                            binding.errorTextView.apply {
                                visibility = View.VISIBLE
                                text = it.error?.message ?: "Unknown Error"
                            }
                        }
                        //If loading, make progress bar visible
                        is Resource.Loading -> {
                            binding.loadingProgressBar.visibility = View.VISIBLE
                        }
                        //If Successful, make error and progress bar view invisible and update recycler view with new data
                        is Resource.Success -> {
                            binding.apply {
                                errorTextView.visibility = View.INVISIBLE
                                loadingProgressBar.visibility = View.INVISIBLE
                                //Safe to unwrap nullable type since I make sure not to send null data in repository
                                updateRecyclerView(it.data!!)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Submitting the filtered data to recycler view
     */
    private fun updateRecyclerView(list: List<Item>) {
        listAdapter.submitList(list)
    }

    /**
     * Once views are being destroyed, binding is not needed and can be set to null to eliminate leaks
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}