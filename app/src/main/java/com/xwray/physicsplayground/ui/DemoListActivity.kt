package com.xwray.physicsplayground.ui


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xwray.physicsplayground.R
import kotlinx.android.synthetic.main.demo_list.*

/**
 * An activity representing a list of Demos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [DemoDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class DemoListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private val twoPane: Boolean by lazy {
        demo_detail_container != null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_list)

        demo_list.adapter = SimpleItemRecyclerViewAdapter()
    }

    inner class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return with(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)) {
                ViewHolder(this)
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = Demo.values()[position]
            holder.title.text = item.title
            holder.subtitle.text = item.description

            holder.view.setOnClickListener { view ->
                if (twoPane) {
                    addDemoFragment(item)
                } else {
                    Intent(view.context, DemoDetailActivity::class.java).apply {
                        putExtra(DemoDetailActivity.DEMO_KEY, item.name)
                        view.context.startActivity(this)
                    }
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }

        override fun getItemCount(): Int {
            return Demo.values().size
        }

        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById<TextView>(android.R.id.text1)
            val subtitle: TextView = view.findViewById<TextView>(android.R.id.text2)


        }
    }
}
