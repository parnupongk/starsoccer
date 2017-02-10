package com.isport_starsoccer.list;

import java.util.HashMap;
import java.util.Vector;

import com.isport_starsoccer.R;
import com.isport_starsoccer.data.DataElementSportClip;
import com.isport_starsoccer.service.ImageUtil;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListAdapterClip extends BaseAdapter {
    private Context context = null;
    private Vector<DataElementSportClip> vData = null;
    private ImageUtil imgUtil = null;

    public ListAdapterClip(Context context, Vector<DataElementSportClip> vData, ImageUtil imgUtil) {
        this.context = context;
        this.vData = vData;
        this.imgUtil = imgUtil;
    }

    @Override
    public int getCount() {
        return vData.size();
    }

    @Override
    public Object getItem(int position) {
        return vData.elementAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sub_clip, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.sub_clip_text_title);
        TextView detail = (TextView) convertView.findViewById(R.id.sub_clip_text_detail);
        ImageView image = (ImageView) convertView.findViewById(R.id.sub_clip_image);
        final ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.sub_clip_progress);
        ImageView hilight = (ImageView) convertView.findViewById(R.id.sub_clip_hilight);
        //RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.list_main_layout_data_news);

        convertView.setPadding(5, (int) (15 * imgUtil.scaleSize()), 5, (int) (15 * imgUtil.scaleSize()));
        //layout.setPadding(0, (int)(10*imgUtil.scaleSize()), 0, (int)(10*imgUtil.scaleSize()));
        image.setPadding((int) (10 * imgUtil.scaleSize()), 0, (int) (5 * imgUtil.scaleSize()), 0);


        convertView.setBackgroundColor(Color.rgb(207, 207, 207));


        hilight.setImageBitmap(null);
        image.setLayoutParams(new FrameLayout.LayoutParams((int) (88 * imgUtil.scaleSize()), (int) (66 * imgUtil.scaleSize()), Gravity.CENTER));
        progress.setLayoutParams(new FrameLayout.LayoutParams((int) (30 * imgUtil.scaleSize()), (int) (30 * imgUtil.scaleSize()), Gravity.CENTER));

        if (vData != null && vData.elementAt(position) != null) {
            DataElementSportClip data = vData.elementAt(position);

            SpannableString content = new SpannableString(data.clipDate);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

            title.setText(content);
            detail.setText(data.clipTopic);


            progress.setVisibility(View.VISIBLE);
            Picasso.with(context).load(data.clipImage)
                    .into(image, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (progress != null) {
                                progress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
            /*
            if(hImage!= null && hImage.get(data.clipImage) == null)
			{
				if(!data.checkLoad && data.clipImage.length() > 0)
				{
					
					data.checkLoad = true;
					AsycTaskLoadImage loadImage = new AsycTaskLoadImage(null,null,hImage,null, this);
					loadImage.execute(data.clipImage);
				}
			}
			
			if(hImage!= null && hImage.get(data.clipImage)!= null)
			{
				if(progress.getVisibility() == View.VISIBLE)
					progress.setVisibility(View.INVISIBLE);
				
				image.setImageBitmap(hImage.get(data.clipImage));
			}
			else if(!(data.clipImage.length() > 0))
			{
				//image.setImageResource(R.drawable.img_small);
			}
			else
			{
//				image.setImageResource(R.drawable.img_small);
//				progress.setVisibility(View.INVISIBLE);
				
				image.setImageBitmap(null);
				progress.setVisibility(View.VISIBLE);
			}*/
        }
        return convertView;
    }
}
