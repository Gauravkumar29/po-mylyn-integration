package com.project_open.mylyn.core.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.project_open.mylyn.core.ProjectOpenConstants;
import com.project_open.mylyn.core.model.Marshallable;
import com.rits.cloning.Cloner;

public final class ProjectOpenUtil {
	
    private static Cloner cloner = new Cloner();

    private ProjectOpenUtil() {
        super();
    }

    public static Date marshallDate(String time) {
        Date date = null;
        
        if (StringUtils.isEmpty(time)) {
        	return null;
        }

        try {
        	//FIXME Parse correctly
        	date = DateUtils.parseDate(time.substring(0, 19), new String[] { "yyyy-MM-dd HH:mm:ss" });
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return date;
    }
    
    public static int marshallInt(String integer) {
    	if (StringUtils.isNotEmpty(integer)) {
    		return Integer.parseInt(integer);
    	}
        return 0;
    }
	
	public static String getTicketUrl(String url, String id) {
		return stripSlash(url) + ProjectOpenConstants.TICKET_URL + id;
	}

	public static String stripSlash(String url) {
		if (url.endsWith("/")) {
			return url.substring(0, url.lastIndexOf("/"));
		}
		return url;
	}

    public static <T extends Marshallable> T parseEntity(Class<T> entityClass, JSONObject jsonObject) {
        T entity = null;

        try {
            entity = entityClass.newInstance();
            entity.marshall(jsonObject);
        } catch (Exception e) {
            entity = null;
        }

        return entity;
    }

    public static <T extends Marshallable> List<T> parseEntities(Class<T> entityClass,
            JSONArray jsonArray) {
        List<T> entities = new ArrayList<T>();

        try {
            for (int iter = 0; iter < jsonArray.length(); iter++) {
                T entity = entityClass.newInstance();
                entity.marshall(jsonArray.getJSONObject(iter));
                entities.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return entities;
    }


    public static <T> T cloneEntity(T entity) {
        return cloner.deepClone(entity);
    }

    public static <T> String joinList(List<T> list) {
        StringBuilder result = new StringBuilder();

        String delimiter = "";
        for (T item : list) {
            result.append(delimiter);
            result.append(item.toString());
            delimiter = ", ";
        }

        return result.toString();
    }

    public static List<String> splitString(String string) {
        List<String> result = new ArrayList<String>();
        for (String item : string.split(",\\s?")) {
            result.add(item.trim());
        }
        return result;
    }

    public static List<String> toStringList(List<?> list) {
        List<String> result = new ArrayList<String>();
        for (Object string : list) {
            result.add(string.toString());
        }
        return result;
    }

}
