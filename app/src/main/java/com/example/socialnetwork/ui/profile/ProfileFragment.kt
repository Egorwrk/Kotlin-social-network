package com.example.socialnetwork.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.socialnetwork.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.profileUserName
        profileViewModel.login.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val statusView: TextView = binding.profileUserStatus
        profileViewModel.status.observe(viewLifecycleOwner) {
            statusView.text = it
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addBtn.setOnClickListener {
            val textInput: EditText = binding.postTextInput
            if (textInput.text.trim().isNotEmpty()) {
                val newPost = PostItem(textInput.text.toString())
                profileViewModel.addPost(newPost)
                textInput.text.clear()
            } else {
                Toast.makeText(getView()?.context, "empty post text", Toast.LENGTH_SHORT).show()
            }
        }

        profileViewModel.postListLive.observe(viewLifecycleOwner) { posts ->
            val postList: ListView = binding.postList
            val adapter = activity?.let { PostsAdapter(it, posts) }
            postList.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}