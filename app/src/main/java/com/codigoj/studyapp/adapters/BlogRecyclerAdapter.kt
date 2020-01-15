import android.view.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codigoj.studyapp.Interface.ItemTouchHelperAdapter
import com.codigoj.studyapp.Interface.OnBlogListener
import com.codigoj.studyapp.R
import com.codigoj.studyapp.models.BlogPost
import kotlinx.android.synthetic.main.item_package.view.*
import java.util.*


class BlogRecyclerAdapter(private var onBlogListener: OnBlogListener) : RecyclerView.Adapter<RecyclerView.ViewHolder> (),
        ItemTouchHelperAdapter
 {


     var items: MutableList<BlogPost> = ArrayList()
        get() = field
        set(value) {
            field = value
        }
     companion object{
         var mTouchHelper: ItemTouchHelper? = null
     }

     fun setTouchHelper(itemTouchHelper: ItemTouchHelper){
         mTouchHelper = itemTouchHelper
     }


     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_package, parent, false),
            onBlogListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        when(holder){
            is BlogViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(blogList: MutableList<BlogPost>){
        this.items = blogList
    }


     override fun onItemMove(fromPosition: Int, toPosition: Int) {
         var fromBlog : BlogPost  = items.get(fromPosition)
         items.removeAt(fromPosition)
         items.add(toPosition,fromBlog)
         notifyItemMoved(fromPosition, toPosition)
     }

     override fun onItemSwipe(position: Int) {
        items.removeAt(position)
         notifyItemRemoved(position)
     }



     class BlogViewHolder constructor(
        itemView: View, onBlogListener : OnBlogListener
    ):  RecyclerView.ViewHolder(itemView),
        View.OnTouchListener,
        GestureDetector.OnGestureListener
     {
         var blogImage = itemView.blog_image
         var blogTitle = itemView.blog_title
         var blogAuthor = itemView.blog_author
         var mGestureDetector : GestureDetector?= null
         var onBlogListener: OnBlogListener? = null

         init {
             this.onBlogListener = onBlogListener
             this.mGestureDetector = GestureDetector(itemView.context,this)
             itemView.setOnTouchListener(this)
         }

        fun bind(blogPost: BlogPost){
            blogTitle.setText(blogPost.title)
            blogAuthor.setText(blogPost.username)

            val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(blogPost.image)
                .into(blogImage)
        }

         override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
             mGestureDetector?.onTouchEvent(p1)
             return true
         }

         override fun onShowPress(p0: MotionEvent?) {

         }

         override fun onSingleTapUp(p0: MotionEvent?): Boolean {
             onBlogListener?.onBlogClick(adapterPosition)
             return false
         }

         override fun onDown(p0: MotionEvent?): Boolean {
             return false
         }

         override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
             return false
         }

         override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
             return false
         }

         override fun onLongPress(p0: MotionEvent?) {
             mTouchHelper?.startDrag(this)
         }


     }

 }