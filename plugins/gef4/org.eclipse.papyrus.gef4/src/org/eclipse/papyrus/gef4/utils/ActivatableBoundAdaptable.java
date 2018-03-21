package org.eclipse.papyrus.gef4.utils;

import java.util.Map;

import org.eclipse.gef.common.adapt.AdaptableSupport;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.IAdaptable;

import com.google.common.reflect.TypeToken;

import javafx.beans.property.ReadOnlyMapProperty;
import javafx.collections.ObservableMap;

public abstract class ActivatableBoundAdaptable<ADAPTABLE extends IAdaptable, BOUND extends IAdaptable> extends ActivatableBound<BOUND> implements IAdaptable {

	private AdaptableSupport<ADAPTABLE> ads = createAdaptableSupport();

	protected abstract AdaptableSupport<ADAPTABLE> createAdaptableSupport();

	@Override
	public ReadOnlyMapProperty<AdapterKey<?>, Object> adaptersProperty() {
		return ads.adaptersProperty();
	}

	@Override
	public <T> T getAdapter(AdapterKey<T> key) {
		return ads.getAdapter(key);
	}

	@Override
	public <T> T getAdapter(Class<T> key) {
		return ads.getAdapter(key);
	}

	@Override
	public <T> T getAdapter(TypeToken<T> key) {
		return ads.getAdapter(key);
	}

	@Override
	public <T> AdapterKey<T> getAdapterKey(T adapter) {
		return ads.getAdapterKey(adapter);
	}

	@Override
	public ObservableMap<AdapterKey<?>, Object> getAdapters() {
		return ads.getAdapters();
	}

	@Override
	public <T> Map<AdapterKey<? extends T>, T> getAdapters(Class<? super T> key) {
		return ads.getAdapters(key);
	}

	@Override
	public <T> Map<AdapterKey<? extends T>, T> getAdapters(TypeToken<? super T> key) {
		return ads.getAdapters(key);
	}

	@Override
	public <T> void setAdapter(T adapter) {
		ads.setAdapter(adapter);
	}

	@Override
	public <T> void setAdapter(T adapter, String role) {
		ads.setAdapter(adapter, role);
	}

	@Override
	public <T> void setAdapter(TypeToken<T> adapterType, T adapter) {
		ads.setAdapter(adapterType, adapter);
	}

	@Override
	public <T> void setAdapter(TypeToken<T> adapterType, T adapter, String role) {
		ads.setAdapter(adapterType, adapter, role);
	}

	@Override
	public <T> void unsetAdapter(T adapter) {
		ads.unsetAdapter(adapter);
	}

}
