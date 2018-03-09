package cesarferreira.sneakpeek.demo

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cesarferreira.faker.loadFromUrl
import cesarferreira.sneakpeek.SneakPeek
import cesarferreira.sneakpeek.listeners.OnGeneralActionListener
import cesarferreira.sneakpeek.listeners.OnHoldAndReleaseListener
import kotlinx.android.synthetic.main.item_list_item.view.*
import kotlinx.android.synthetic.main.peek_view.view.*
import java.util.*


class ListItemsAdapter(private val items: ArrayList<ItemViewModel>,
                       private val sneakPeek: SneakPeek) : RecyclerView.Adapter<ListItemsAdapter.ItemViewHolder>() {

    private val peekView: View = sneakPeek.peekView
    private lateinit var itemView: View

    init {
        setupPeekAndPopHoldAndRelease()
    }

    private fun setupPeekAndPopStandard() {
        this.sneakPeek.setOnGeneralActionListener(object : OnGeneralActionListener {
            override fun onPeek(view: View, position: Int) {
                loadSneakPeak(position)
            }

            override fun onPop(view: View, position: Int) {}
        })
    }


    private fun setupPeekAndPopHoldAndRelease() {
        setupPeekAndPopStandard()

        sneakPeek.addHoldAndReleaseView(R.id.play)
        sneakPeek.addHoldAndReleaseView(R.id.record)
        sneakPeek.addHoldAndReleaseView(R.id.details)

        sneakPeek.setOnHoldAndReleaseListener(object : OnHoldAndReleaseListener {
            override fun onHold(view: View, position: Int) {}
            override fun onLeave(view: View, position: Int) {}
            override fun onRelease(view: View, position: Int) {

                val currentItem = items[position]

                when (view.id) {
                    R.id.play -> makeSnackbar("play")
                    R.id.record -> makeSnackbar("Started recording ${currentItem.title}")
                    R.id.details -> makeSnackbar("details")
                }
            }

        })
    }

    private fun makeSnackbar(string: String) {
        Snackbar.make(itemView.rootView, string, Snackbar.LENGTH_LONG).show()
    }

    private fun loadSneakPeak(position: Int) {
        peekView.titleTextView.text = items[position].title
        peekView.peakImageView.loadFromUrl(items[position].thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ItemViewModel) {
            sneakPeek.addLongClickView(itemView, position)
            itemView.thumbnail.loadFromUrl(item.thumbnail)
        }
    }
}