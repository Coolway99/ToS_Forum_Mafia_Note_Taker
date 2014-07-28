package assets.interfaces;

/**
 * @param <V> The Value, commonly meant to be a List<V>
 * @see List<V>
 */
public interface SaveLoadListener<V> {
	public V gatherSaveData();
	public void inputLoadData( V input);
}
