package callofdroidy.net.recyclerviewlab;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yli on 07/10/16.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView tvName;
    public TextView tvValue;
    public LinearLayout cell;

    public MyViewHolder(final View rootView){
        super(rootView);

        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvValue = (TextView) rootView.findViewById(R.id.tv_value);
        cell = (LinearLayout) rootView.findViewById(R.id.cell);

        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAdapterPosition();
            }
        });
    }

}
