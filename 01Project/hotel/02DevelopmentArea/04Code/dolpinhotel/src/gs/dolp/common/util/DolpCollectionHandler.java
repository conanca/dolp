package gs.dolp.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DolpCollectionHandler<T> {

	/**
	 * 将一个实体List中所有实体对象的id取出放入一个集合中
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<Integer> getIdsList(List list) throws Exception {
		List<Integer> ids = new ArrayList<Integer>();
		for (Object obj : list) {
			Field f = obj.getClass().getDeclaredField("id");
			f.setAccessible(true);
			ids.add(f.getInt(obj));
		}
		return ids;
	}

	/**
	 * 将一个实体List中所有实体对象的id取出放入一个集合中
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static int[] getIdsArr(List list) throws Exception {
		int[] ids = new int[list.size()];
		int i = 0;
		for (Object obj : list) {
			Field f = obj.getClass().getDeclaredField("id");
			f.setAccessible(true);
			ids[i] = f.getInt(obj);
			i++;
		}
		return ids;
	}

	/**
	 * 将一个实体List中所有实体对象的id取出，用分隔符拼接成一个字符串
	 * @param list
	 * @param separator
	 * @return
	 * @throws Exception
	 */
	public static String getIdsString(List list, String separator) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (Object obj : list) {
			Field f = obj.getClass().getDeclaredField("id");
			f.setAccessible(true);
			sb.append(f.getInt(obj));
			sb.append(separator);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
