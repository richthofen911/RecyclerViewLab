package callofdroidy.net.recyclerviewlab;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yli on 07/10/16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int position){
        viewHolder.setIsRecyclable(false);
        Cell tmp = DataStore.dataSet.get(position);
        viewHolder.tvName.setText(Integer.toString(tmp.getName()));
        viewHolder.tvValue.setText(Integer.toString(tmp.getValue()));
    }

    @Override
    public int getItemCount(){
        return DataStore.dataSet.size();
    }

    public void deleteRow(int pos){
        DataStore.dataSet.remove(pos);
        notifyItemRemoved(pos);
    }

    public void clearAll(){
        int sizeBeforeRemoved = DataStore.dataSet.size();
        if(sizeBeforeRemoved > 0){
            for(int i = 0; i < sizeBeforeRemoved; i++){
                int posRemove = DataStore.dataSet.size() - 1;
                DataStore.dataSet.remove(posRemove);
                notifyItemRemoved(posRemove);
                notifyItemRangeChanged(0, posRemove);
            }

            //notifyItemRangeRemoved(0, sizeBeforeRemoved);
        }
    }
}
