package com.sarathdr.movies.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collection;
import java.util.List;

/**
 * A list adapter base class that can bind a list of the same elements to views.
 * The subclass will then only deal with binding the data to the view. 
 *
 * @param <T>  the element type in the list.
 */
public abstract class GenericListAdapter<T> extends BaseAdapter {

	/** The (current) list / model providing the data. */
	protected List<T> mModel;
	
	/** Layout inflater of the context. */
	protected final LayoutInflater mInflater;
	
	/** Resource ID of the layout used for each list element. */
	@LayoutRes
	private final int mLayoutResourceId;
	
	/**
	 * Creates a new list adapter.
	 * 
	 * @param context  context
	 * @param resourceId  resource ID of the layout used for each list element
	 */
	public GenericListAdapter(final Context context, @LayoutRes final int resourceId) {
		mLayoutResourceId = resourceId;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}
	/**
	 * Sets the list which provides the data.
	 * @param list  list of data items
	 */
	public final void setList(final List<T> list) {
		mModel = list;
		notifyDataSetChanged();
	}
	
	/**
	 * Returns the data list used by the adapter.
	 * @return the list
	 */
	public final List<T> getList() {
		return mModel;
	}
	
	/** Adds this list to the existing list.
	 * @param list new list to be added.
	 */
	public final void addList(final Collection<T> list) {
		mModel.addAll(list);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (mModel != null) {
			return mModel.size();
		}
		return 0;
	}

	@Override
	public T getItem(final int position) {
		if (mModel != null) {
			return mModel.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final View result;
		if (convertView != null) {
			result = convertView;
		} else {
			result = createNewView(position, parent, mInflater);
		}
		if (mModel != null) {
			final T modelObject = getItem(position);
			bindView(result, parent, position, modelObject);
		}
		return result;
	}

	/**
	 * Creates a new view for the list position.
	 * @param position	the position in the list
	 * @param parent	the parent view
	 * @param inflater	the inflater
	 */
	protected View createNewView(final int position, final ViewGroup parent, final LayoutInflater inflater) {
		View result = inflater.inflate(mLayoutResourceId, parent, false);
		setUpNewView(result, parent);
		return result;
	}

	/**
	 * Initializes a new view for this list.
	 * 
	 * @param view  the view to be initialized
	 * @param parent  its parent
	 */
	protected void setUpNewView(final View view, final ViewGroup parent) {
	}
	
	/**
	 * Updates the data shown by the view based on the given model object.
	 * 
	 * @param view  the view to be updated
	 * @param parent  its parent
	 * @param position  position of the view in the list
	 * @param modelObject  model data
	 */
	protected abstract void bindView(View view, ViewGroup parent, int position, T modelObject);
	

}
