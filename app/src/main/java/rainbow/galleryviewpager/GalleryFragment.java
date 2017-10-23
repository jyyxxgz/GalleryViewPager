package rainbow.galleryviewpager;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;

import rainbow.library.GalleryViewPager;

/**
 * Created by Nirui on 16/8/12.
 */
public class GalleryFragment  extends Fragment {

  final private static int[] PIC_RES = new int[] {
      R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4, R.mipmap.pic5
  };

  private Queue<View> viewPool = new LinkedList<>(); //View池

  @Nullable @Override public View onCreateView(final LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final GalleryViewPager viewPager =
        (GalleryViewPager) inflater.inflate(R.layout.fragmet_gallery, container, false);
    viewPager.setAdapter(new PagerAdapter() {
      @Override public int getCount() {
        return PIC_RES.length;
      }

      @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
      }

      @Override public Object instantiateItem(ViewGroup container, int position) {
        View view;
        ViewHolder viewHolder;
        if(viewPool.size() > 0){
          view = viewPool.poll();
          viewHolder = (ViewHolder) view.getTag();
        }else{
          view = inflater.inflate(R.layout.gallery_item, container, false);
          viewHolder = new ViewHolder();
          viewHolder.textView = ((TextView) view.findViewById(R.id.title));
          viewHolder.imageView = ((ImageView) view.findViewById(R.id.imageview));
          view.setTag(viewHolder);
        }
        viewHolder.textView.setText("Pager " + position);
        viewHolder.imageView.setImageResource(PIC_RES[position]);
        container.addView(view);
        return view;
      }

      @Override public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        viewPool.offer((View) object);
      }

      @Override public float getPageWidth(int position) {
        return 0.8f;//建议值为0.6~1.0之间
      }
    });
    viewPager.setNarrowFactor(0.9f);
    return viewPager;
  }

   class ViewHolder{
     TextView  textView;
     ImageView imageView;
   }
}
