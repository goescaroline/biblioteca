package br.com.cdgoes.converter;


import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import br.com.cdgoes.domain.Livro;

@Named
@FacesConverter(value = "livroConverter", forClass = Livro.class)
public class LivroConverter implements Converter {
	
	private static final String key = "br.com.cdgoes.converter.LivroConverter";
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value.isEmpty()) {
	        return null;
	    }
	    return getViewMap(context).get(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object livro) {
		String id = ((Livro)livro).getId().toString();
		getViewMap(context).put(id, livro);
	    return id;
	}
	
	private Map<String, Object> getViewMap(FacesContext context) {
	    Map<String, Object> viewMap = context.getViewRoot().getViewMap();
	    @SuppressWarnings({ "unchecked", "rawtypes" })
	    Map<String, Object> idMap = (Map) viewMap.get(key);
	    if (idMap == null) {
	        idMap = new HashMap<String, Object>();
	        viewMap.put(key, idMap);
	    }
	    return idMap;
	}

}