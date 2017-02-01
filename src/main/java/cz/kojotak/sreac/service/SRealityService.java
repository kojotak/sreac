package cz.kojotak.sreac.service;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.kojotak.sreac.to.Inzerat;
import cz.kojotak.sreac.to.Priloha;

@Service
public class SRealityService {

	final RestTemplate rest = new RestTemplate();
	final String sPath = "https://www.sreality.cz";
	final String apiPath = sPath +"/api/cs/v2/estates/";
	
	@Autowired ObjectMapper jsonMapper;
	
	public Inzerat stahni(long sRealityId){
		ResponseEntity<Map<String, Object>> response = 
	            rest.exchange(
	            		apiPath + sRealityId, 
	                    HttpMethod.GET, 
	                    null,
	                    new ParameterizedTypeReference<Map<String, Object>>() {}
	                    );
		Map<String, Object> body = response.getBody();
		Object map = vof(body, "map");
		String meta = vof( body, "meta_description");
		String nazev = vof( vof(body, "name"), "value");
		String popis = vof( vof(body, "text"), "value");
		Integer cena = vof( vof(body, "price_czk"), "value_raw");
		Object coord = vof(body, "map");
		Double lat = vof(coord, "lat");
		Double lon = vof(coord, "lon");
		Point2D.Double gps = new Point2D.Double(lat, lon);
		Object obrazky = vof( vof(body, "_embedded"), "images");
		List<Priloha> imageUrls = new ArrayList<>(); 
		if(obrazky instanceof List){
			List<Object> list = List.class.cast(obrazky);
			for(Object element : list){
				Object image = vof(vof(element, "_links"),"self");
				String imageName = vof(image, "title");
				String imageUrl = vof(image, "href");
				byte[] imageBytes = rest.getForObject(imageUrl, byte[].class);
				imageUrls.add(new Priloha(imageUrl, imageName, imageBytes));
			}
		}
		String json = null;
		try {
			json = jsonMapper.writeValueAsString(body);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return new Inzerat(sRealityId, nazev, popis, meta, gps, cena, json, imageUrls);
	}
	
	<T> T vof(Object object, String key){
		if(object instanceof Map){
			Map<?, ?> map = Map.class.cast(object);
			return (T)map.get(key);
		}
		return null;
	}
}
