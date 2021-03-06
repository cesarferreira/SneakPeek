package cesarferreira.sneakpeek.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import cesarferreira.faker.Faker
import cesarferreira.sneakpeek.SneakPeek
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var sneakPeek: SneakPeek
    private lateinit var adapter: ListItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        sneakPeek = initializePeekAndPop()

        adapter = ListItemsAdapter(getFakeItems() as ArrayList<ItemViewModel>, sneakPeek)

        recyclerView.adapter = adapter
    }

    private fun getFakeItems() = (0..500).map { ItemViewModel(it, "title $it", Faker.getRandomImage(300, 400)) }.toList()

    private fun initializePeekAndPop(): SneakPeek {
        return SneakPeek.Builder(this)
                .blurBackground(true)
                .peekLayout(R.layout.peek_view)
                .parentViewGroupToDisallowTouchEvents(recyclerView)
                .build()
    }

    public override fun onDestroy() {
        super.onDestroy()
        sneakPeek.destroy()
    }

}
