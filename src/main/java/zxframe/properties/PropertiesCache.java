package zxframe.properties;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zxframe.properties.model.Properties;
import zxframe.properties.service.PropertiesService;
import zxframe.util.ServiceLocator;


public final class PropertiesCache{
	private static Logger logger = LoggerFactory.getLogger(PropertiesCache.class);  
	private static ConcurrentMap<String, String> properties=new ConcurrentHashMap<String, String>();
	static{
		init();
	}
	public final static String get(String key)
	{
		return properties.get(key);
	}
	public final static int getInt(String key)
	{
		if(properties.containsKey(key)) {
			return Integer.parseInt(PropertiesCache.get(key));
		}else {
			return 0;
		}
	}
	public final static boolean getBoolean(String key)
	{
		if(properties.containsKey(key)) {
			return PropertiesCache.get(key).equals("true");
		}else {
			return false;
		}
	}
	public static void init(){
		long time = System.currentTimeMillis();
		ConcurrentMap<String, String> propertieMap=new ConcurrentHashMap<String, String>();
		PropertiesService propertiesService = ServiceLocator.getSpringBean("propertiesService");
		List<Properties> list = propertiesService.getList();
		for (int i = 0; i < list.size(); i++) {
			Properties p = list.get(i);
			propertieMap.put(p.getKey(), p.getValue());
		}
		properties=propertieMap;
		logger.info("资源加载成功 >>>> 键值对读取 >>>> 键值对数："+properties.size()+" [耗时:"+(System.currentTimeMillis()-time)+" ms]");
	}
}
