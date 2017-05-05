package martin.simple.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import martin.simple.domain.UserInfo;

/**
 * 可扩展有内存数据库
 * @author mojinchuan@126.com
 *
 * @param <K>
 * @param <V>
 */
public class DataBase<K,V> {

	
	private Map<K,V> map = new ConcurrentHashMap<K,V>();
	private Lock LOCK=new ReentrantLock();//1.对数据更新可见性要求高的话，就对更新操作与查询操作进行加锁，2.否则对性能要求高，且数据量大则不加锁
	
	//数据库表
    private static class LazyHolder {
        // 用户信息列表
        private static final DataBase<Long,UserInfo> USER_INFO_TABLE_INSTANCE = new DataBase<Long,UserInfo>();
    }

    /**
     * 用户信息表实例化
     * @return
     */
    public static DataBase<Long,UserInfo> getUserInfoTableInstance() {
        return LazyHolder.USER_INFO_TABLE_INSTANCE;
    }


	public void put(K id,V model) {
		try {
			LOCK.lock();
			map.put(id, model);
		} finally {
			LOCK.unlock();
		}
		
	}

	public void remove(K id) {
		try {
			LOCK.lock();
			map.remove(id);
		} finally {
			LOCK.unlock();
		}
	}

	public  boolean isEmpty() {
		try {
			LOCK.lock();
			return map.isEmpty();
		} finally {
			LOCK.unlock();
		}
	}

	public  List<V> sortedResult(final String sortField){
		try {
			LOCK.lock();
			List<V> list = new ArrayList<V>(); 
			list.addAll(map.values());
			Collections.sort(list, new Comparator<V>() {  
			    public int compare(V o1, V o2) {  
			    	Class<? extends Object> clazz = o1.getClass();
			    	Method[] methods = clazz.getDeclaredMethods();
			        Object x1 = null;  
			        Object x2 = null;
			    	boolean fieldExists = false;
			    	for(Method method:methods){
			    		if(method.getName().startsWith("get")&&method.getName().endsWith(sortField)){
			    			fieldExists = true;
			    			try {
								x1 = method.invoke(o1);
								x2 = method.invoke(o2);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
			    		}
			    	}
			    	if(!fieldExists)
			    		return 0;
			    	if(x1 == null && x2 == null)
			    		return 0;
			    	if(x1 == null)
			    		return -1;
			    	if(x2 == null)
			    		return 1;
			        return x1.toString().compareTo(x2.toString())>0?1:(x1.toString().compareTo(x2.toString())==0?0:-1);  
			    }  
			});
			
			return list;
		} finally {
			LOCK.unlock();
		}
	}
	
}
