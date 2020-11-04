package com.example.noci.lists.input

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noci.ListsInputActivity
import com.example.noci.R
import com.example.noci.databinding.FragmentInputListsBinding
import java.util.*

class ListsInputFragment : Fragment() {

    private lateinit var binding: FragmentInputListsBinding
    private lateinit var inputViewModel: ListsInputViewModel

    val MONTHS =
    arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private val adapter = ShopNoteAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input_lists, container, false)
        inputViewModel = ViewModelProvider(this).get(ListsInputViewModel::class.java)

        binding.inputViewModel = inputViewModel

        binding.showList.adapter = adapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //binding.addDate.text =
            //LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()

        //Hawk.init(context).build()

        //val editChecker = Hawk.get<String>(EDIT_CHECKER)

        //if (editChecker == "edit") {0
        val details = ListsInputFragmentArgs.fromBundle(requireArguments()).lists
        Log.e(" DET ", "$details")

        if (details != null) {
            //binding.addButton.text = "SAVE NOTE"

            binding.addTitle.setText(details.title)
            //binding.addDate.text = details.noteDate
        }
        //}

        inputViewModel.shopReadAll.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                //binding.emptyListTitle.visibility = View.VISIBLE
                //binding.emptyListDescription.visibility = View.VISIBLE
            } else {
                //binding.emptyListTitle.visibility = View.INVISIBLE
                //binding.emptyListDescription.visibility = View.INVISIBLE

                it?.let {
                    adapter.submitList(it)
                }
            }
        })

        inputViewModel.insertInitializer.observe(viewLifecycleOwner, Observer {
            //binding.addTitle.toString() != "" && binding.addDescription.toString() != ""
            val noteTitle = binding.addTitle.text.toString()
            //val noteDate = binding.addDate.text.toString()
            //Hawk.put(EDIT_CHECKER, "nonEdit")
            //if (details != null) {
            //Log.e("NOTE TAG : ", "IS ${details.id} , ${details.title}, ${details.noteDate}, ${details.date}")
            //}
            if (TextUtils.isEmpty(noteTitle)) {
                Toast.makeText(context, "Title field can't be empty!", Toast.LENGTH_SHORT).show()
            } else {
                //if //(details != null) {
                    //inputViewModel.updateNote(details.id, noteTitle)
                //} else {
                //inputViewModel.addNote(noteTitle)

                //}

                onGoBack()
            }
        })

        inputViewModel.onGoBackToMain.observe(viewLifecycleOwner, Observer {
            if (it) {
                val intent = Intent(context, ListsInputActivity::class.java)

                startActivity(intent)
            }
        })

        inputViewModel.insertDateInitializer.observe(viewLifecycleOwner, Observer {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            // date picker dialog

            val datepickerdialog: DatePickerDialog? =
                this.context?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            // Display Selected date in textbox
                            //binding.addDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + " " + year)

                        },
                        year,
                        month,
                        day
                    )
                }

            datepickerdialog!!.show()
        })

        inputViewModel.addToListBool.observe(viewLifecycleOwner, Observer {
            if(it) {
                val subnote = binding.addToText.text.toString()
                val noteId = details?.id

                Log.e(" NOTE : ", " $subnote and $noteId")

                if (noteId != null) {
                    inputViewModel.addNote(subnote, noteId)
                }

                //inputViewModel.addToNote(subnote)
            }
        })
    }

    fun onGoBack() {
        val intent = Intent(context, ListsInputActivity::class.java)

        startActivity(intent)
    }

}